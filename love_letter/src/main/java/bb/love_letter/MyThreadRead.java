package bb.love_letter;

import java.io.DataInputStream;

class MyThreadRead extends Thread{
    DataInputStream is;
    User user;
    public MyThreadRead(DataInputStream i, User user){
        is=i;
        this.user = user;
    }
    public void run()
    {
        try{
            String json=null;
            while(true){
                // RECEIVE MESSAGE FROM SERVER
                json = is.readUTF();
                if(json != null) {
                    Envelope envelope = Util.deserializeJsontoEnvelope(json);
                    if (envelope.getType() == Envelope.TypeEnum.USEREVENT) {
                        UserEvent userEvent = (UserEvent) envelope.getPayload();
                        User newUser = userEvent.getUser();
                        if (!newUser.getName().equals(this.user.getName())) {
                            System.out.println(user.getName() + " joined the room");
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