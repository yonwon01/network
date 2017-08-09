package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "192.168.1.142";
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		try {
			// 1. Socket 생성
			socket = new Socket();
			// 2.서버 연결
			// java test.TCPServer 엔터
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 3.IO받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"), true);
			// 4.쓰기/읽기
			while(true) {
				System.out.println(">>");
				String message=scanner.nextLine();
				if("exit".equals(message)) {
					break;
				}
				
				
				//메세지 보내기
				pw.println(message);
				
				//에코메시지 받기
				String echoMessage=br.readLine();
				if(echoMessage==null) {
					System.out.println("[client] Disconnection by Server ");
					break;
				}
				//출력
				System.out.println("<<"+echoMessage );
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {try {
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
