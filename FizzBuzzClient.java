import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class FizzBuzzClient {
    
    public static void main(String[] args) {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new RMISecurityManager());

        try {
            FizzBuzz remoteObject = (FizzBuzz) Naming.lookup("//localhost/FizzBuzz");

            for (String k: remoteObject.getFizzBuzzer())
                System.out.println(k);

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
