import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

}
