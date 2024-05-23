
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TestClientSocket {

    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("127.0.0.1", 1515);
        } catch (IOException e) {
            System.out.println("Server not available");
            throw new RuntimeException(e);
            //SocketWrapper socketWrapper = new SocketWrapper(socket, false, Logger.getLogger(SocketWrapper.class.getName()));
        }
        Scanner tmpUsrIn = null;
        ObjectInputStream tmpSocketIn = null;
        ObjectOutputStream tmpSocketOut = null;
        try {
            tmpUsrIn = new Scanner(System.in);
            tmpSocketIn = new ObjectInputStream(socket.getInputStream());
            tmpSocketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to get socket input/output streams");
            throw new RuntimeException(e);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        final Scanner userIn = tmpUsrIn;
        final ObjectInputStream socketIn = tmpSocketIn;
        final ObjectOutputStream socketOut = tmpSocketOut;


        Thread t = new Thread(() -> {
            try {
                while (true) {
                    String result = (String) socketIn.readObject();
                    System.out.println(result);
                    System.out.flush();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        });
        t.start();
        Thread in = new Thread(() -> {
            try {
                while (true) {
                    String toSend = userIn.nextLine();
                    socketOut.writeObject(toSend);
                    socketOut.flush();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        });

        in.start();
        try {
            in.join();
            t.join();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }


}
