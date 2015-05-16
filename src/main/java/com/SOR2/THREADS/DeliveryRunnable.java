package com.SOR2.THREADS;

public class DeliveryRunnable implements Runnable {

	private boolean running;

	@Override
	public void run() {
		System.out.println("starting DeliveryThread");
		running = true;
		int i = 1;
		while (running) {
			System.out.println("DeliveryThread is running Cycle:" + i);
			try {
				Thread.sleep(1000);
				System.out.println("DeliveryThread going to sleep");
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
