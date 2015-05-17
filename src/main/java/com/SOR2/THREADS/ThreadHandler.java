package com.SOR2.THREADS;

public class ThreadHandler {

	ValidationRunnable validationRunnable;
	Thread validationThread;
	DeliveryRunnable deliveryRunnable;
	Thread deliveryThread;

	public ThreadHandler() {
		validationRunnable = new ValidationRunnable();
		deliveryRunnable = new DeliveryRunnable();
	}

	public void startThreads() {
		validationThread = new Thread(validationRunnable);
		validationThread.start();
		// Sleep zodat de console duidelijker te lezen is
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		deliveryThread = new Thread(deliveryRunnable);
		deliveryThread.start();
	}

	// setting setRunning to false will make it that the Run() method finishes
	// stopping the thread
	// does almost the same as stopThreads but differently
	public void stopThreads() {
		validationRunnable.setRunning(false);
		deliveryRunnable.setRunning(false);
	}

}
