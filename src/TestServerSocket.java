import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestServerSocket {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(1515);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread listener = new Thread( () -> {
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            Scanner userIn;
            ObjectInputStream socketIn;
            ObjectOutputStream socketOut;
            try {
                userIn = new Scanner(System.in);
                socketOut = new ObjectOutputStream(socket.getOutputStream());
                socketIn = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            while ( true ) {
                String result;
                try {
                    result = userIn.nextLine();
                    result += (String) socketIn.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                System.out.println(result);
                String response = ">>" + result;
                try {
                    socketOut.writeObject(response);
                    socketOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                if (result.equals("quit")) {
                    return;
                }
            }
        });
        listener.start();
        try {
            listener.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
