package org.rcloud.medical.clientx;

public class WebserverThread extends Thread {

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			WebServer.getInstance().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
