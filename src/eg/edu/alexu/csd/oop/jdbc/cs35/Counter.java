package eg.edu.alexu.csd.oop.jdbc.cs35;

public class Counter extends Thread{

	private int timeout;
	private Thread mainThread;
	
	public Counter(int timeout, Thread mainThread) {
		this.timeout = timeout;
		this.mainThread = mainThread;
	}
	
	@Override
	public void run()
	{
		if(this.timeout == -1)
			return;
		
		try {
			sleep(timeout * 1000);
			mainThread.interrupt();
		} catch (InterruptedException e) {
			
		}
	}
}
