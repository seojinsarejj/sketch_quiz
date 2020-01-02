package chat.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import chat.server.ServerBackground;

public class ClientGUI extends JFrame implements ActionListener {

	public static int pwidth = 10;
	public static int ewidth = 50;

	ImageIcon icon = new ImageIcon(("C:\\JAVA\\workspace\\asdf\\src\\sketch.jpg"));
	ImageIcon icon2 = new ImageIcon(("C:\\Users\\user\\Desktop\\loginpage3.jpg"));
	public static JPanel drawpanel = new JPanel();
	public static boolean isplayer = true;
	static Color c = Color.black;
	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea(40, 25);
	private JTextArea jta_1 = new JTextArea(40, 25);
	private JTextArea jta_2 = new JTextArea(40, 25);
	public static String answer = "정답";
	private JTextField jtf = new JTextField(25);
	private JTextField ans = new JTextField(25);
	JButton submit = new JButton("SUBMIT");
	JButton start = new JButton("START");
	private ClientBackground client = new ClientBackground();
	private static String nickName;
	private static String ip;
	private Image screenImage;
	private Graphics screenGraphic;

//--------------------------------------------------
	void draw(int x, int y) {

		Graphics g = getGraphics();

		String color = "1";
		g.setColor(c);

		Graphics2D g2d = (Graphics2D) g;
		if (c == Color.WHITE)
			g2d.setStroke(new BasicStroke(ewidth));
		else
			g2d.setStroke(new BasicStroke(pwidth));

		if (x < 1 || x > 1170 || y < 60 || y > 800) {

		} else {
			g2d.drawLine(x + 110, y + 110, x + 110, y + 110);

			if (c == Color.BLACK) {
				color = "1";
			} else if (c == Color.WHITE) {
				color = "0";
			}

			String msg = String.valueOf(x) + " " + String.valueOf(y) + " " + color;

			client.sendMessage(msg);

		}

	}

//--------------------------------------------------
	void ConnectGUI() {

		JPanel bg2 = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		TextField tf = new TextField(25);
		TextField tf1 = new TextField(5);
		JButton b = new JButton("Login");
		JFrame f = new JFrame("Login");

		f.setSize(1900, 1000);
		f.setLayout(null);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JLabel li = new JLabel("IP Address :");
		JLabel li2 = new JLabel("Name :");
		JPanel p = new JPanel();

		li.setFont(new Font("맑은고딕", Font.BOLD, 80));
		li.setBounds(530, 300, 200, 100);
		li2.setFont(new Font("맑은고딕", Font.BOLD, 80));
		li2.setBounds(597, 410, 120, 100);
		tf.setFont(new Font("맑은고딕", Font.BOLD, 80));
		tf.setBounds(730, 300, 500, 100);
		tf1.setFont(new Font("맑은고딕", Font.BOLD, 80));
		tf1.setBounds(730, 410, 500, 100);

		f.add(li);
		f.add(tf);
		f.add(li2);
		f.add(tf1);

		f.add(p);
		b.setBounds(730, 520, 500, 100);
		f.add(b);
		f.setVisible(true);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == b)
					ip = tf.getText();
				nickName = tf1.getText();
				System.out.println("enter");
				tf.setText("");
				tf1.setText("");
			}
		});
	}

//-------------------------------------------------------------
	public ClientGUI() {
		ConnectGUI();
		Scanner sc = new Scanner(System.in);
		String a = sc.nextLine();

		setLayout(null);
		JButton btn = new JButton("PENCIL");
		/////////////////////////////////////////////////////
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if (btn.getText().equals("PENCIL")) {
					btn.setText("ERASER");
					c = Color.WHITE;
				} else {
					btn.setText("PENCIL");
					c = Color.BLACK;
				}
			}
		});

		//////////////////////////////////////////////////////

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				answer = ans.getText();
				ans.setText("");
			}
		});

		///////////////////////////

		drawpanel.setBounds(110, 80, 1190, 800);
		JScrollPane sp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(1330, 495, 550, 405);
		jta.setEditable(false);
		jtf.addActionListener(this);

		JPanel bg = new JPanel() {

			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		bg.setLayout(null);
		bg.setBounds(0, 0, 1900, 1000);
		bg.add(drawpanel);
		jtf.setBounds(1330, 908, 550, 42);

		bg.add(jtf);

		bg.add(sp);

		sp.setVisible(true);

		JScrollPane scrollPane = new JScrollPane(bg);
		setContentPane(scrollPane);

		drawpanel.setBackground(Color.WHITE);
		drawpanel.add(btn);
		drawpanel.add(ans);
		drawpanel.add(submit);

		drawpanel.addMouseMotionListener(new MyMouseListener());
		bg.add("Center", drawpanel);
		jtf.addActionListener(this);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1900, 1000);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("방");
		client.setGui(this);
		client.setNickname(nickName);
		client.setIP("127.0.0.1");
		client.connect();
	}

	public class MyMouseListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if (isplayer) {

				draw(e.getX(), e.getY());

			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

//------------------------------------------------------------
	public static void main(String[] args) {
		new ClientGUI();

	}

//---------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = nickName + " : " + jtf.getText() + "\n";
		System.out.print(msg);

		if (jtf.getText().equals("")) {
		} else {
			client.sendMessage(msg);
			

			jta.setCaretPosition(jta.getDocument().getLength());
		}
		jtf.setText("");

	}

//---------------------------------------------------------------
	public void appendMsg(String msg) {

		StringTokenizer st1 = new StringTokenizer(msg);
		String one = st1.nextToken();
		String two = st1.nextToken();

		if (two.equals(":")) {

			jta.append(msg);
			StringTokenizer st2 = new StringTokenizer(msg);
			one = st2.nextToken();
			two = st2.nextToken();
			String three = st2.nextToken();
			if(three.equals(answer)) {
				msg = "start " + one;
				client.sendMessage(msg);
			}

		} else if (one.equals("start")) {

			if (two.equals(nickName)) {

				jta.append("--------" + two + "님의 차례입니다!--------\n");

				Graphics g = getGraphics();
				g.setColor(Color.WHITE);

				Graphics2D g2d = (Graphics2D) g;

				g.fillRect(50, 145, 1255, 834);

				isplayer = true;
			} else {
				jta.append("--------" + two + "님의 차례입니다!--------\n");

				Graphics g = getGraphics();
				g.setColor(Color.WHITE);

				Graphics2D g2d = (Graphics2D) g;

				g.fillRect(50, 145, 1255, 834);

				isplayer = false;
			}
		} 

		else {

			StringTokenizer st = new StringTokenizer(msg);
			int xx = Integer.parseInt(st.nextToken());
			int yy = Integer.parseInt(st.nextToken());
			int color = Integer.parseInt(st.nextToken());

			Color c = Color.BLACK;

			if (color == 1) {
				c = Color.BLACK;
			} else if (color == 0) {
				c = Color.WHITE;
			}

			Graphics g = getGraphics();
			g.setColor(c);

			Graphics2D g2d = (Graphics2D) g;

			if (c == Color.WHITE)
				g2d.setStroke(new BasicStroke(ewidth));
			else
				g2d.setStroke(new BasicStroke(pwidth));

			if (xx > 1450 || yy < 50) {

			} else {

				g2d.drawLine(xx + 110, yy + 110, xx + 110, yy + 110);

			}
		}
	}

//-------------------------------------------------------

	// -------------------------------------------

}