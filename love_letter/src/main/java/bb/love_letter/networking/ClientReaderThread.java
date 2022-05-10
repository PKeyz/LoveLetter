package bb.love_letter.networking;

import bb.love_letter.user_interface.ChatMessage;
import bb.love_letter.game.User;

import java.io.DataInputStream;

/**
 * This is the Thread that receives all messages from the Server and handles them as needed. It is used by
 * the CLI version of Love Letter.
 *
 * @author Bence Ament
 */
public class ClientReaderThread extends Thread{
    DataInputStream dataInputStream;
    User user;
    public ClientReaderThread(DataInputStream dataInputStream, User user){
        this.dataInputStream = dataInputStream;
        this.user = user;
    }

    /**
     * Handling of messages received from the Server
     */
    public void run()
    {
        try{
            String json=null;
            while(true){
                // RECEIVE MESSAGE FROM SERVER
                json = dataInputStream.readUTF();
                if(json != null) {
                    Envelope envelope = Envelope.deserializeEnvelopeFromJson(json);
                    if (envelope.getType() == Envelope.TypeEnum.USEREVENT) {
                        UserEvent userEvent = (UserEvent) envelope.getPayload();
                        if (userEvent.getUserEventType() == UserEvent.UserEventType.LOGIN_CONFIRMATION) {
                            User newUser = userEvent.getUser();
                            if (!newUser.getName().equals(this.user.getName())) {
                                System.out.println(newUser.getName() + " joined the room");
                            }
                        } else if (userEvent.getUserEventType() == UserEvent.UserEventType.LOGOUT_CONFIRMATION) {
                            System.out.println(userEvent.getUser().getName() + " left the room");
                        }

                    } else if (envelope.getType() == Envelope.TypeEnum.CHATMESSAGE) {
                        ChatMessage chatMessage = (ChatMessage) envelope.getPayload();
                        System.out.println(chatMessage.getSender().getName() + ": " + chatMessage.getMessage());
                    }
                }
                json = null;
            }
        }
        catch(Exception e){

        }
    }
}