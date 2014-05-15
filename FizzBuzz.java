import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;

public interface FizzBuzz extends Remote {

    public int[] range() throws RemoteException; 

    public String step(int value) throws RemoteException, IllegalArgumentException; 

    public Iterable<String> getFizzBuzzer() throws RemoteException;
}
