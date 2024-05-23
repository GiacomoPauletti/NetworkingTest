import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITestClientRMI extends Remote {
    void helloBack() throws RemoteException;
}
