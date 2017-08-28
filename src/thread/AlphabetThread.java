package thread;

public class AlphabetThread extends Thread {
//할거면 소켓을 무조건 생성
	
	
	
	@Override
	public void run() {
		//돌고있는 것을 중간에 다른 쓰레드가 들어오는 거 막는거->synchronzied//remove할떄도 마찬가지
		//synchronized(list){
		for(char c='a';c<='z';c++) {
			System.out.print(c);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
		
	
}
