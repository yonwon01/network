package chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatWindow {
	private BufferedReader br;// 받아야함
	private PrintWriter pw;
	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private String name;
	private Socket socket;
	private String nickname;

	ChatServerThread chatServer;

	public ChatWindow(Socket socket, String name) {
		this.socket = socket;
		frame = new Frame(name + " 채팅방");
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		this.name = name + ":";
	}

	public void show() throws IOException {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// quit보내서 소켓 닫는 작업이 필요
				System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.pack();

		br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		// br = new BufferedReader(new InputStreamReader(socket.getInputStream(),
		// StandardCharsets.UTF_8));

		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

		new ChatClientReceiveThread().start();

	}

	private void sendMessage() {
		// new ChatClientReceiveThread().start();
		String message = textField.getText();

		// 리시브 클래스에 테스트에어리아가 있어야함
		// textArea.append("hello");
		textArea.append(name + message);// 원래는 리시브 thread안에 있어야함
		textArea.append("\n");//

		textField.setText("");// 나갔으니까 비어주는거
		textField.requestFocus(); // 버튼누르고 다시 포커스 돌아오는거
	}

	public class ChatClientReceiveThread extends Thread {
		// private BufferedReader

		@Override
		public void run() {
			while (true) {
				String line = null;
				try {
					line = br.readLine();
					// ChatServerThread.log(line);
					String[] tokens = line.split(":");
					if ("join".equals(tokens[0])) {
						textArea.append(tokens[1] + "님이 입장하였습니다.");
						textArea.append("\n");//
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

}
