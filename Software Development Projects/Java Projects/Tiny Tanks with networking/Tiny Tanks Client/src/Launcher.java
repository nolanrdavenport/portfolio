import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
import javax.xml.soap.Text;

import java.net.*;
import java.util.Random;
public class Launcher{
    private JFrame frame;
    private JButton startButton;
    private JButton joinButton;
    private JPanel launchPanel;
    private JTextArea clientList;
    private JTextField ipInput;
    private JLabel ipInputLabel;
    private String color;
    private String serverIP;
    private int portMain;
    private DatagramSocket socket;
    private boolean launching = true;
    public static void main(String[] args) {
        new Launcher();

    }

    public Launcher() {
        Random rand = new Random();
        portMain = rand.nextInt(40000);
        //networking
        try{
            socket = new DatagramSocket(portMain);
            new Thread(new ReceiveThread()).start();

            
        }catch(Exception e){
            e.printStackTrace();
        }
        //GUI
        frame = new JFrame("Tiny Tanks Launcher");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setAlwaysOnTop(false);
        ipInputLabel = new JLabel("IP Address");
        ipInput = new JTextField();
        joinButton = new JButton("Join Server");
        joinButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                serverIP = ipInput.getText();
                
                try{
                    InetAddress IP = InetAddress.getByName(serverIP);
                    int port = 50000;
                    Thread.sleep(1000);
                    String join = "join";
                    byte[] buf = join.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, IP, port);
                    socket.send(packet);
                }catch(Exception ex){ ex.printStackTrace(); }
            }
        });
        clientList = new JTextArea("Press start when everyone has joined... \nClient List:" );
        
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        //InetAddress IP = InetAddress.getByName("10.105.56.125");
                        InetAddress IP = InetAddress.getByName(ipInput.getText());
                        int port = 50000;
                        String start = "start_"+color;
                        byte[] buf = start.getBytes();
                        DatagramPacket packet = new DatagramPacket(buf, buf.length, IP, port);
                        socket.send(packet);
                        frame.setVisible(false);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }

                }

            });
        launchPanel = new JPanel(new GridLayout(0,1));

        frame.add(launchPanel);
        launchPanel.add(ipInputLabel);
        launchPanel.add(ipInput);
        launchPanel.add(joinButton);
        launchPanel.add(clientList);
        launchPanel.add(startButton);
        

        frame.setVisible(true);
    }
    
    public class ReceiveThread implements Runnable{
        public void run(){
            while(launching){
                try{
                   
                    byte[] buf = new byte[2048];
                    DatagramPacket p = new DatagramPacket(buf, buf.length);
                    socket.receive(p);                    
                    String msg = new String(p.getData()).trim();
                    
                   
                    
                    String[] msgs = msg.split("_");
                    
                    
                    if(msgs[0].equals("clients")){
                       
                        clientList.setText("Press start when everyone has joined... \nClient List:");
                        for(int i = 1; i < msgs.length; i++){
                            clientList.append("\n"+msgs[i]);
                        }
                    }
                    if(msgs[0].equals("Cinfo")){
                        color = msgs[3];
                        
                        portMain = Integer.parseInt(msgs[1]);
                        socket = new DatagramSocket(portMain);
                        
                        
                    }
                    if(msgs[0].equals("start")) {
                        socket.close();
                        new Game(color, portMain, Integer.parseInt(msgs[1]), ipInput.getText()); 
                        launching = false;
                        
                    }
                    
                    
                }catch(Exception e){}
            }
        }
    }
}
