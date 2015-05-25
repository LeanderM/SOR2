package com.SOR2.THREADS;

/**
 * Klasse die de threads aanmaakt en start
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */

public class ThreadHandler {

	ValidationRunnable validationRunnable;
	Thread validationThread;
	DeliveryRunnable deliveryRunnable;
	Thread deliveryThread;

	/**
	 * Constructor
	 *
	 */
	public ThreadHandler() {
		validationRunnable = new ValidationRunnable();
		deliveryRunnable = new DeliveryRunnable();
	}

	/**
	 * Stopt runnables in threads en start de threads
	 *
	 */
	public void startThreads() {
		validationThread = new Thread(validationRunnable);
		validationThread.start();

		deliveryThread = new Thread(deliveryRunnable);
		deliveryThread.start();
	}

	/**
	 * setting setRunning to false will make it that the Run() methods finish
	 * effectively stopping the threads
	 * 
	 */
	public void stopThreads() {
		validationRunnable.setRunning(false);
		deliveryRunnable.setRunning(false);
	}

}
