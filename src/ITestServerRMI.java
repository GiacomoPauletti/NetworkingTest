import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITestServerRMI extends Remote {
    void sayHello(ITestClientRMI client) throws RemoteException;
}
