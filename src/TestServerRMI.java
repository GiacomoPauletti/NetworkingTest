import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketOption;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class TestServerRMI implements ITestServerRMI  {
    public TestServerRMI() {

        try {
            UnicastRemoteObject.exportObject(this, 0, new MyRMIClientSocketFactory()
            , port -> new ServerSocket(port));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /*
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
         */
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(5555);
            registry.bind("TestServerRMI", new TestServerRMI());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            //throw new RuntimeException(e);
        }
    }

    @Override
    public void sayHello(ITestClientRMI client) throws RemoteException {
        while ( true ) {
            System.out.println("Client said hello");
            try {
                client.helloBack();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    public class MyRMIClientSocketFactory implements RMIClientSocketFactory, Serializable {
        @Override
        public Socket createSocket(String host, int port) throws IOException {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(15 * 1000);
            return socket;
        }

        @Override
        public boolean equals(Object obj) {
            return obj.getClass().equals(this.getClass());
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
