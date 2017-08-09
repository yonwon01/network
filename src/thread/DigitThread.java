package thread;

public class DigitThread implements Runnable {

public void run() {
		
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
