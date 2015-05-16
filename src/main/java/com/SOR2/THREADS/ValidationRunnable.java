package com.SOR2.THREADS;

public class ValidationRunnable implements Runnable {

	private boolean running;

	@Override
	public void run() {
		System.out.println("starting ValidationThread");
		running = true;
		int i = 1;
		while (running) {
			System.out.println("ValidationThread is running Cycle:" + i);
			try {
				Thread.sleep(1000);
				System.out.println("ValidationThread going to sleep");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
