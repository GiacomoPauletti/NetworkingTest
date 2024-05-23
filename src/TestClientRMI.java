import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TestClientRMI implements ITestClientRMI {

    public TestClientRMI() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        TestClientRMI client = new TestClientRMI();
        ITestServerRMI server;
        try {
            server = (ITestServerRMI) registry.lookup("TestServerRMI");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        try {
            server.sayHello(client);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void helloBack() throws RemoteException {
        System.out.println("Hey, server replied me :)");
    }
}
