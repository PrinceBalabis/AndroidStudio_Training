package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ServerConnection {
    String[] answer;
    Object[] answerImage;
    String question;
    Socket socket;
    boolean answerIsImage = false;
    boolean answerIsReturned = false;
    boolean isConnected = false;

    public ServerConnection(String question) {
        this.question = question;
        if (question.contains("image_")) {
            answerIsImage = true;
        }
        askServer();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String[] getAnswer() {
        return answer;
    }

    public Object[] getImageAnswer() {
        return answerImage;
    }

    public boolean isAnswerReturned() {
        return answerIsReturned;
    }


    public void askServer() {
        try {
            socket = new Socket(InetAddress.getByName("195.178.234.232"), 25565);
            isConnected = true;
            System.out.println("KOPPLAD TILL SERVERAPPLIKATIONEN");
        } catch (Exception e) {
        }
        if (isConnected()) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeUTF(question);
                oos.flush();
                System.out.println("SKICKAT FRÅGA TILL SERVERAPPLIKATIONEN");
            } catch (Exception e1) {
            }

            try {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                try {
                    if (answerIsImage) {
                        answerImage = (Object[]) input.readObject();
                    } else {
                        answer = (String[]) input.readObject();
                    }
                    System.out.println("SVAR UTSKRIVEN");
                } catch (ClassNotFoundException e) {
                }
            } catch (IOException e) {
            }
            try {
                socket.close();
                System.out.println("SERVER FRÅNKOPPLAD");
            } catch (IOException e) {
            }
            answerIsReturned = true;
        }
    }
}