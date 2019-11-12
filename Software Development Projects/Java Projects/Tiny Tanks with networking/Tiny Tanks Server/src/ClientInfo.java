import java.net.*;
public class ClientInfo{
    private InetAddress IP;
    private int port;
    private String color;
    public ClientInfo(InetAddress IP, int port, String color){
        this.IP = IP;
        this.port = port;
        this.color = color;
    }
    
    public InetAddress getIP(){
        return IP;
    }
    public int getPort(){
        return port;
    }
    public String getColor(){
        return color;
    }
    
    public void setIP(InetAddress IP){
        this.IP = IP;
    }
    public void setPort(int port){
        this.port = port;
    }
    public void setColor(String color){
        this.color = color;
    }
}