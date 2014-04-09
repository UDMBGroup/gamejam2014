package edu.virginia.cs.sgd.game.timer;

public class Timer {
	private int maxTime;
	private int current;
	private boolean pause;
	private Thread t;
	private Clock c;
	 
	
	public Timer() {
		maxTime = 300;
		current = maxTime;
		pause = false;
		c = new Clock();
		t = new Thread(c);
		t.start();
	}
	
	public void pause() {
		if (!pause) {
			pause = true;
		}
	}
	
	public void resume() {
		if (pause) {
			pause = false;
		}
	}
	
	public int getCurrentTime() {
		return current;
	}
	
	public String toString() {
		int min = current/60;
		int sec = current%60;
		if (sec < 10) {
			return "" + min + ":0" + sec;
		}
		return "" + min + ":" + sec;
	}
	
	public class Clock implements Runnable {

		@Override
		public void run() {
			for (int i = maxTime; i > 0; i--) {
				if (pause) {
					i++;
					continue;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				current--;
			}
		}
		
	}
	
}
