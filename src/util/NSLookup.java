package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(System.in);
			String host = sc.nextLine();
			InetAddress[] inetAddresses = InetAddress.getAllByName(host);
		for(InetAddress inetAddress:inetAddresses) {
			System.out.println(inetAddress.getHostAddress());
			
		}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ip는 유일하지만 한 도메인에는 여러 IP를 생성할 수 있기 때문에 배열로 생성

	}

}
