package chat;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread {

	private Socket socket;
	private String nickname;
	List<Writer> listWriters = new ArrayList<Writer>();
	BufferedReader br;
	PrintWriter pw;

	public ChatServerThread(Socket socket) {
		this.socket = socket;
	}

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 4.연결성공
		InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();// 상대방 소켓 어드레스
		int remoteHostPort = remoteSocketAddress.getPort();
		String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();// inet address가 나옴

		consoleLog("[server] connected from " + remoteHostAddress + ":" + remoteHostPort);

		try {
			// 5.I/O stream 받아오기
			// InputStream is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			// OutputStream os = socket.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);// true안해놓으면
																									// 버퍼가
																									// 다
																									// 차야지
																									// 나간다.

			// 6.데이터 읽기(read)
			while (true) {
				String message = br.readLine();
				System.out.println("처음" + message);
				String[] tokens = message.split(":");
				if ("join".equals(tokens[0])) {
					System.out.println("join!!");
					doJoin(tokens[1], pw);
					// System.out.println("토근"+tokens[1]);
					// broadcast(nickname+" 님이 입장 하였습니다.");
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit();
				} else {
					System.out.println("에러");
				}

				// System.out.println("message" + message);
				// consoleLog(message);
				if (message == null) {// 정상종료
					consoleLog("[server] disconnection By client");
					break;
				}

				consoleLog(("[server] received:" + message));

				// 7.데이터 쓰기
				pw.println(message);
			}
		} catch (SocketException e) {
			// 상대편이 소켓을 정상적으로 닫지 않고 종료한 경우
			System.out.println("[server] sudden closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void doQuit() {
		// TODO Auto-generated method stub

	}

	private void doMessage(String string) {
		// TODO Auto-generated method stub

	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				System.out.println(data);
				// PrintWriter printWriter = (PrintWriter) writer;
				
				pw.println(data);
				pw.flush();
				
				System.out.println( "!!!!!!!!!!!" );
				
			}
		}
	}

	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
		// this.listWriteㄴrs = listWriters;
		String data = nickName + "님이 참여하였습니다.";

		broadcast(data);
		addWriter(writer);

		pw.println("join:ok");
		pw.flush();

		// System.out.println("이건" + writer);
	}

	private void addWriter(Writer writer) {
		// TODO Auto-generated method stub
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void consoleLog(String log) {
		System.out.println("[Chat server:" + getId() + "]");
	}
	public static void log( String log ) { 
		System.out.println( "[chat-client] " + log ); 
 	}	 
}
