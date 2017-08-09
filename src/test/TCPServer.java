package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//1~4소켓 연결//5~6은 데이터 통신
public class TCPServer {
	private static final int SERVER_PORT = 5000;

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
			Socket socket = serverSocket.accept();// blocking///blocking될때: accept일때랑 read할때

			// 4.연결성공
			InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();// 상대방 소켓 어드레스
			int remoteHostPort = remoteSocketAddress.getPort();
			String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();// inet address가 나옴

			System.out.println("[server] connected from " + remoteHostAddress + ":" + remoteHostPort);

			try {
				// 5.I/O stream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// 6.데이터 읽기(read)
				while (true) {
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);// 읽은 수가 리턴
					if (readByteCount <= -1) {
						System.out.println("[server] disconnection By client");
						break;
					}

					String data = new String(buffer, 0, readByteCount, "utf-8");// 0부터readbycount까지 byte를 string으로
																				// encoding
					System.out.println(("[server] received:" + data));

					// 7.데이터 쓰기
					os.write(data.getBytes("utf-8"));// string->byte로 디코딩 해야함

				}
			}catch(SocketException e) {
				//상대편이 소켓을 정상적으로 닫지 않고 종료한 경우
				System.out.println("[server] sudden closed by client");
			} 
			catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (serverSocket != null && serverSocket.isClosed() == false) {// serversocket이 닫혀 있지 않으면
					serverSocket.close();
				}
			}
		} catch (IOException e) {
			System.out.println("IO 에러");
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {// serversocket이 닫혀 있지 않으면
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
