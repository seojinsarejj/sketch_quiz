package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground {

	private ServerSocket serverSocket;
	private Socket socket;
	private ServerGUI gui;
	private String msg;

	private Map<String, DataOutputStream> clientMap = new HashMap<String, DataOutputStream>();

	public static ArrayList<String> nicknames = new ArrayList<>();

	public void setGui(ServerGUI gui) {
		this.gui = gui;
	}

	public static void main(String[] args) {
		ServerBackground serverBackground = new ServerBackground();
		serverBackground.setting();
	}

	public void setting() {

		try {

			Collections.synchronizedMap(clientMap);
			ServerSocket serverSocket = new ServerSocket(7777);

			while (true) {

				
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + " ¿¡ Á¢¼Ó");

				Receiver receiver = new Receiver(socket);
				receiver.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addClient(String nick, DataOutputStream out) throws IOException {

		clientMap.put(nick, out);
		nicknames.add(nick);

	}

	public void removeClient(String nick) {

		clientMap.remove(nick);
		for (int i = 0; i < nicknames.size(); i++) {
			if (nick.equals(nicknames.get(i))) {
				nicknames.remove(i);
			}
		}

	}

	public void sendMessage(String msg) {
		Iterator<String> iterator = clientMap.keySet().iterator();
		String key = "";

		while (iterator.hasNext()) {
			key = iterator.next();//
			try {
				clientMap.get(key).writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ---------------------------------------------
	class Receiver extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;

		public Receiver(Socket socket) {
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				nick = in.readUTF();
				addClient(nick, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			try {
				while (in != null) {
					msg = in.readUTF();//
					sendMessage(msg);
					gui.appendMsg(msg);
				}
			} catch (Exception e) {

				removeClient(nick);
			}
		}
	}

}