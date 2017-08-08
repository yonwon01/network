package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName=inetAddress.getHostName();
			String hostAddress=inetAddress.getHostAddress();
			byte[] addresses=inetAddress.getAddress();
			
			
			for(int i=0;i<addresses.length;i++) {
				System.out.print(addresses[i]&0x000000ff);
				if(i<3) {
					System.out.print(".");
				}
			}
			
			System.out.println();
			System.out.println(hostAddress);
			System.out.println(hostName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
