package controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Power;

/**
 * Controller class for creating a thread which allows the power data to change as real time changes
 * 
 * @author suhuiso
 *
 */
public class PowerThread extends Thread {
	
	private Power power;
	final long sleepTime = 1000;
	private int i =0;
	private String time;
	private Date date;
	static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	
	public PowerThread(Power power){
		this.setPower(power);
	}

	public Power getPower() {
		return power;
	}

	public void setPower(Power power) {
		this.power = power;
	}
	
	/**
	 * Augment the time as the second changes
	 * 
	 * @param i
	 * @return time
	 */
	public String timeSetting(int i){
		date = new Date(this.i*1000-3600000);
		time = format.format(date);
		return time;
	}
	
	
	@Override
	public void run() {
		while(true){
			power.setCpu((double)(new BigDecimal(Math.random() * 101)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			power.setMem((double)(new BigDecimal(Math.random() * 101)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			power.setPower((double)(new BigDecimal(Math.random() * 101)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			this.i++;
			power.setTime(timeSetting(this.i));
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}
