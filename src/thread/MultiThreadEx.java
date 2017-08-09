package thread;

public class MultiThreadEx {

	public static void main(String[] args) {

		Thread thread1 = new AlphabetThread();//1.thread class
		Thread thread2 = new Thread(new DigitThread());//2.thread interface
		new Thread(new Runnable() {//3.main에다가 바로 구현하는법

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(char c='A';c<='Z';c++) {
					System.out.println(c);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
		thread1.start();
		thread2.start();

		for (int i = 0; i <= 9; i++) {
			System.out.print(i);
			try {
				thread1.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// for(char c='a';c<='z';c++) {

		// System.out.print(c);
		// }
	}

}
