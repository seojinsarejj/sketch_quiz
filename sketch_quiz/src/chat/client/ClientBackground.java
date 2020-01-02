package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground {
	private DataInputStream in;
	private DataOutputStream out;
	private ClientGUI gui;
	private String msg;
	private String nickName;
	private String ip = null;

	public static void main(String[] args) {
		ClientBackground clientBackground = new ClientBackground();
		clientBackground.connect();
	}

	
	public void connect() {
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket(ip, 7777);
			System.out.println("�꽌踰꾩뿉 �뿰寃곕맖");

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());

			out.writeUTF(nickName);

			while (in != null) {
				msg = in.readUTF();
				gui.appendMsg(msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		try {
			out.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg,String msg2) {
		try {
			out.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			out.writeUTF(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNickname(String nickName) {
		this.nickName = nickName;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	void setGui(ClientGUI gui) {
		this.gui = gui;
	}

}