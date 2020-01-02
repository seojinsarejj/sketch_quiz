package chat.server;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame implements ActionListener {

	ImageIcon icon = new ImageIcon(("C:\\JAVA\\workspace\\asdf\\src\\sketch.jpg"));
	
	public static int pwidth = 10;
	public static int ewidth = 50;
	public static JPanel drawpanel = new JPanel();
	public static boolean isplayer = true;
	public static JPanel tool = new JPanel();
	public static String answer;
	static String ans;
	public static Color c = Color.black;
	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea(25, 15);
	private JTextField jtf = new JTextField();
	private JTextArea jta_1 = new JTextArea(25, 15);
	private JTextArea jta_2 = new JTextArea(25, 15);
	JButton start = new JButton("START");
	private ServerBackground server = new ServerBackground();

	// -----------------------------------------------------------------------

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

			server.sendMessage(msg);

		}

	}

	// -----------------------------------------------------------------------
	public ServerGUI() throws IOException {

		setLayout(null);
		JButton btn = new JButton("PENCIL");

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

		drawpanel.setBounds(110, 80, 1190, 800);
		JScrollPane sp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(1330, 495, 550, 405);

		jtf.addActionListener(this);

		JPanel bg = new JPanel() {
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
		bg.setLayout(null);
		bg.setBounds(0, 0, 1900, 1000);
		bg.add(drawpanel);
		jtf.setBounds(1330, 908, 550, 42);

		jta_1.setBounds(1494, 50, 400, 20);
		jta_2.setBounds(1494, 100, 400, 20);
		bg.add(jtf);

		bg.add(sp);
		sp.setVisible(true);

		JScrollPane scrollPane = new JScrollPane(bg);
		setContentPane(scrollPane);

		

		drawpanel.setBackground(Color.WHITE);
		drawpanel.add(btn);
		drawpanel.add(start);
		
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = "start " + ServerBackground.nicknames.get(0); 
				server.sendMessage(msg);
			}
		});

		drawpanel.addMouseMotionListener(new MyMouseListener());
		bg.add("Center", drawpanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1900, 1000);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("¼­¹ö");
		server.setGui(this);
		server.setting();

	}
	// -----------------------------------------------------------------------

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

	// ------------------------------------------------------------------------
	public static void main(String[] args) throws IOException {
		new ServerGUI();
	}
	// ------------------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) {

		String msg = "server : " + jtf.getText() + "\n";
		System.out.print(msg);
		if (jtf.getText().equals("")) {
		} else {
			server.sendMessage(msg);
		}
		appendMsg(msg);
		jtf.setText("");
	}

	// --------------------------------------------------------------------------

	public void appendMsg(String msg) {

		StringTokenizer st1 = new StringTokenizer(msg);
		String one = st1.nextToken();
		String two = st1.nextToken();
		if (two.equals(":")) {
			jta.append(msg);
			if (msg.equals(answer)) {

			}
			jta.setCaretPosition(jta.getDocument().getLength());
		}else if (one.equals("start")) {
			msg = "start " + two;
			server.sendMessage(msg);
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

	// --------------------------------------------------------------------------

}
