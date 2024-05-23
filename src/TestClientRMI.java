import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TestClientRMI extends UnicastRemoteObject implements ITestClientRMI {

    public TestClientRMI() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry;
        try {
            //registry = LocateRegistry.getRegistry(5555);
            registry = LocateRegistry.getRegistry("192.168.1.107", 5555);
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
