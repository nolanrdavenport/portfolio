import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
public class GetIP {
    public static void main(String[] args) throws Exception {
        Enumeration Interfaces = NetworkInterface.getNetworkInterfaces();
        while(Interfaces.hasMoreElements())
        {
            NetworkInterface Interface = (NetworkInterface)Interfaces.nextElement();
            Enumeration Addresses = Interface.getInetAddresses();
            while(Addresses.hasMoreElements())
            {
                InetAddress Address = (InetAddress)Addresses.nextElement();
                System.out.println(Address.getHostAddress());
            }
        }
    }
}