package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {// 항상 파라미터로 소켓을 넣음
		this.socket = socket;
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
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			// OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);// true안해놓으면
																												// 버퍼가
																												// 다
																												// 차야지
																												// 나간다.

			// 6.데이터 읽기(read)
			while (true) {
				String message = br.readLine();
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

	private void consoleLog(String log) {
		System.out.println("[server:" + getId() + "]");
	}

}
