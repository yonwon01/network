package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import thread.EchoServerReceiveThread;

public class EchoServer {
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1.서버 소켓 생성
			serverSocket = new ServerSocket();
			// 2.바인딩(Binding)
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();// ip주소

			serverSocket.bind(new InetSocketAddress(localhostAddress, SERVER_PORT));// 대도록 포트는 5000번이상//소켓은 ip+port

			System.out.println("[server] binding " + localhostAddress + ":" + SERVER_PORT);

			// 3.연결요청 기다림(accept)

			while (true) {
				Socket socket = serverSocket.accept();// blocking///blocking될때: accept일때랑 read할때
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}
			
			} catch (SocketException e) {
				// 상대편이 소켓을 정상적으로 닫지 않고 종료한 경우
				System.out.println("[server] sudden closed by client");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (serverSocket != null && serverSocket.isClosed() == false) {// serversocket이 닫혀 있지 않으면
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 
		

	}

}
