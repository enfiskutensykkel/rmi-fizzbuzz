import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.io.Serializable;

public class FizzBuzzServer extends UnicastRemoteObject implements FizzBuzz {

    final int lim;
    private String[] responses;

    public FizzBuzzServer(int limit, String fizz, String buzz) throws RemoteException {
        lim = limit;

        responses = new String[15];
        responses[0] = fizz + buzz;

        for (int i = 1; i < 15; i = -~i) {
            if (i % 3 == 0)
                responses[i] = fizz;
            else if (i % 5 == 0)
                responses[i] = buzz;
        }
    }

    public FizzBuzzServer(int limit) throws RemoteException {
        this(limit, "Fizz", "Buzz");
    }
    
    @Override
    public int[] range() {
        int[] range = {1, lim};
        return range;
    }

    @Override
    public String step(int step) throws IllegalArgumentException {
        if (step < 0 || step > lim)
            throw new IllegalArgumentException("Do you even fizz?");

        String response = responses[step % 15];
        return response != null ? response : "" + step;
    }

    @Override
    public Iterable<String> getFizzBuzzer() {
        return new FizzBuzzer(this);
    }

    public static void main(String[] args) {
        System.out.println("FizzBuzzServer started");

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new RMISecurityManager());

        try {
            LocateRegistry.createRegistry(1099);

            FizzBuzzServer server = new FizzBuzzServer(100);
            Naming.rebind("//localhost/FizzBuzz", server);

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}



class FizzBuzzer implements Iterable<String>, Serializable {

    final FizzBuzz handle;

    public FizzBuzzer(FizzBuzz handle) {
        this.handle = handle;
    }

    @Override
    public Iterator<String> iterator() {
        int end, start;
        try {
            int[] r = handle.range();
            start = r[0];
            end = r[1];
        } catch (RemoteException e) {
            end = 100;
            start = 1;
        }
        final int[] range = {start, end};

        return new Iterator<String>() {

            private int value = range[0] - 1;
            final private int limit = range[1];

            @Override
            public boolean hasNext() {
                return value < limit;
            }

            @Override
            public String next() throws NoSuchElementException {
                if (value == limit)
                    throw new NoSuchElementException("You want to fizz, but you're all out of buzz");

                try {
                    return handle.step(++value);
                } catch (RemoteException e) {
                    return "" + value;
                }
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Can't fizz without buzz");
            }
        };
    }
}
