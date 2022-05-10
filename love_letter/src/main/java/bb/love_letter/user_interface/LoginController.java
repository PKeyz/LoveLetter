package bb.love_letter.user_interface;

import bb.love_letter.game.User;
import bb.love_letter.networking.UserEvent;
import bb.love_letter.networking.Envelope;
import bb.love_letter.networking.NetworkConnection;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginController {
    public LoginModel model;

    public LoginController (LoginModel model) {
        this.model = model;
    }

    protected void connectToServer(String ip, int port, String username) {
        model.setIp(ip);
        model.setPort(port);
        model.setUsername(username);

        try {
            Socket client = new Socket(ip, port);
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            //request as a client
            User user = new User(username);
            UserEvent userEvent = new UserEvent(user, UserEvent.UserEventType.LOGIN_REQUEST);
            Envelope request = new Envelope(userEvent, Envelope.TypeEnum.USEREVENT);
            String jsonRequest = request.toJson();
            dataOutputStream.writeUTF(jsonRequest);
            String response = dataInputStream.readUTF();
            Envelope envelope = Envelope.deserializeEnvelopeFromJson(response);

            if (envelope.getType() == Envelope.TypeEnum.USEREVENT) {
                UserEvent loginResponseEvent = (UserEvent) envelope.getPayload();
                if (loginResponseEvent.getUserEventType() == UserEvent.UserEventType.LOGIN_CONFIRMATION) {
                    NetworkConnection.getInstance().init(client, dataInputStream, dataOutputStream, user);
                    model.setSuccessfulLogin(true);
                } else if (loginResponseEvent.getUserEventType() == UserEvent.UserEventType.LOGIN_ERROR) {
                    System.out.println("Error: The username " + user.getName() + " is already taken!");
                }
            } else {
                System.out.println("Error: Could Not Connect To Server!");
            }

        } catch (UnknownHostException ex) {
            model.setErrorMessage("Server was not found! (Check ip and port)");
        } catch (IOException ex) {
            model.setErrorMessage("An I/O exception occured! (Check ip and port)");
        }
    }
}