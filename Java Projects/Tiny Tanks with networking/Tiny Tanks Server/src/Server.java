import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.MessageDigestSpi;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import javax.swing.*;
import java.awt.*;

public class Server {
	private Set<ClientInfo> clients;

	private DatagramSocket socket;
	private int numClients;

	private JFrame frame;
	private JPanel panel;
	private JTextArea text;
	private boolean launching = true;

	public static void main(String[] args) {
		new Server();
		while (true) {
		}
	}

	public Server() {
		// server window
		frame = new JFrame("Tiny Tanks Server");
		panel = new JPanel();
		text = new JTextArea("Server Terminal");

		frame.setSize(1000, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setAlwaysOnTop(false);

		frame.add(panel);
		panel.add(text);
		numClients = 1;
		try {
			clients = new HashSet<ClientInfo>();

			socket = new DatagramSocket(50000);
			// new Thread(new SendThread()).start();
			new Thread(new ReceiveThread()).start();
			new Thread(new clientUpdater()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendClientList() {
		String clientString = "clients_";
		for (ClientInfo client : clients) {
			clientString += client.getColor() + "_";
		}
		for (ClientInfo client : clients) {
			try {
				int port = client.getPort();

				byte[] buf = clientString.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, client.getIP(), port);
				// send
				socket.send(packet);
			} catch (Exception e) {
			}
		}
	}

	public void broadcastState(String msg, String[] info) {

		for (ClientInfo client : clients) {
			try {
				int port = client.getPort();
				byte[] buf = msg.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, client.getIP(), port);
				if (!info[1].equals(client.getColor())) {
					socket.send(packet);
				}

			} catch (Exception e) {
			}
		}
	}

	public void sendClientInfo(ClientInfo client) {
		try {
			String a = "Cinfo_" + client.getPort() + "_" + client.getIP().toString() + "_" + client.getColor();

			byte[] bufa = a.getBytes();
			DatagramPacket packeta = new DatagramPacket(bufa, bufa.length, client.getIP(), client.getPort());
			// send
			socket.send(packeta);
		} catch (Exception e) {

		}
	}

	public void shoot(String msg) {
		for (ClientInfo client : clients) {
			try {
				int port = client.getPort();
				byte[] buf = msg.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, client.getIP(), port);
				socket.send(packet);
			} catch (Exception e) {
			}
		}
	}

	public void startGame() throws InterruptedException {
		for (ClientInfo client : clients) {
			launching = false;

			try {
				int port = client.getPort();
				String start = "start_" + numClients;
				byte[] buf = start.getBytes();
				DatagramPacket packeta = new DatagramPacket(buf, buf.length, client.getIP(), port);
				socket.send(packeta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class clientUpdater implements Runnable {
		public void run() {
			while (launching) {
				try {
					Thread.sleep(1000);
					if (clients.size() > 0) {
						sendClientList();

					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
	
	public void death() {
		for (ClientInfo client : clients) {
			try {
				int port = client.getPort();
				String msg = "death";
				byte[] buf = msg.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, client.getIP(), port);
				socket.send(packet);
			} catch (Exception e) {
				
			}
		}
	}

	public class ReceiveThread implements Runnable {
		public void run() {
			while (true) {
				try {
					byte[] buf = new byte[2048];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);// must create empty packet
					socket.receive(packet);// fills up packet with data

					String msg = new String(packet.getData()).trim();
					String msgCopy = msg;
					String[] info = msg.split("_");
					
					
					if (msg.equals("join")) {
						if (numClients == 1) {
							clients.add(new ClientInfo(packet.getAddress(), packet.getPort(), "Blue"));
							sendClientInfo(new ClientInfo(packet.getAddress(), packet.getPort(), "Blue"));
						}
						if (numClients == 2) {
							clients.add(new ClientInfo(packet.getAddress(), packet.getPort(), "Red"));
							sendClientInfo(new ClientInfo(packet.getAddress(), packet.getPort(), "Red"));
						}
						if (numClients == 3) {
							clients.add(new ClientInfo(packet.getAddress(), packet.getPort(), "Green"));
							sendClientInfo(new ClientInfo(packet.getAddress(), packet.getPort(), "Green"));
						}
						if (numClients == 4) {
							clients.add(new ClientInfo(packet.getAddress(), packet.getPort(), "Yellow"));
							sendClientInfo(new ClientInfo(packet.getAddress(), packet.getPort(), "Yellow"));
						}

						for (ClientInfo client : clients) {
							String a = "Cinfo_" + client.getPort() + "_" + client.getIP().toString() + "_"
									+ client.getColor();
							byte[] bufa = a.getBytes();
							DatagramPacket packeta = new DatagramPacket(bufa, bufa.length, client.getIP(),
									client.getPort());
							;
							// send
							socket.send(packeta);
						}

						numClients++;
						sendClientList();
					}

					if (info[0].equals("start")) {
						startGame();
					}

					if (info[0].equals("shoot")) {
						shoot(msgCopy);
					}

					if (info[0].equals("state")) {
						broadcastState(msgCopy, info);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}