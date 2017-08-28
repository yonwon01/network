package chat;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {
	// bufferedREader넘겨야함
	private static final String SERVER_IP = "192.168.1.142";
	private static final int SERVER_PORT = 7000;
	private TextArea textArea;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);

		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			String name = null;
			// 쓰기/읽기
			// 3.IO받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			while (true) {

				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				name = scanner.nextLine();
				pw.println("join:" + name);
			//	br.readLine();
				String request = br.readLine();

				new ChatWindow(socket,name).show();

				

			}
			// -->join 둘리\r\n 보내주고
			// join ok 를 받으면 밑에 꺼 띄움
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() != false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	


}
