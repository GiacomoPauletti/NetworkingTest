import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

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
