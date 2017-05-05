package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a power.
 * 
 * @author suhuiso
 *
 */
public class Power extends RecursiveTreeObject<Power> {

	private final IntegerProperty pid;
	private final DoubleProperty cpu;
	private final DoubleProperty mem;
	private final DoubleProperty power;
	private final StringProperty time;
	private final StringProperty command;

	/**
	 * Default constructor.
	 */
	public Power() {
		this(0, 0, 0, 0, null, null);
	}
	
	/**
	 * Constructor with some initial data.
	 * 
	 * @param pid number PID
	 * @param cpu CPU
	 * @param mem MEM
	 * @param power Power usage
	 * @param time Time
	 * @param command Command
	 */
	public Power(int pid, double cpu, double mem, double power, String time, String command) {
		this.pid = new SimpleIntegerProperty(pid);
		this.cpu = new SimpleDoubleProperty(cpu);
		this.mem = new SimpleDoubleProperty(mem);
		this.power = new SimpleDoubleProperty(power);
		this.time = new SimpleStringProperty(time);
		this.command = new SimpleStringProperty(command);
		
	}
	
	public int getPid() {
		return pid.get();
	}

	public void setPid(int pid) {
		this.pid.set(pid);
	}
	
	public IntegerProperty pidProperty() {
		return pid;
	}

	public double getCpu() {
		return cpu.get();
	}

	public void setCpu(double cpu) {
		this.cpu.set(cpu);
	}
	
	public DoubleProperty cpuProperty() {
		return cpu;
	}
	
	public double getMem(){
		return mem.get();
	}
	
	public void setMem(double mem){
		this.mem.set(mem);
	}
	

	public DoubleProperty memProperty() {
		return mem;
	}
	
	
	public double getPower(){
		return power.get();
	}
	
	public void setPower(double power){
		this.power.set(power);
	}
	
	public DoubleProperty powerProperty(){
		return power;
	}
	
	public String getTime(){
		return time.get();
	}
	
	public void setTime(String time){
		this.time.set(time);
	}
	
	public StringProperty timeProperty(){
		return time;
	}
	
	public String getCommand(){
		return command.get();
	}
	
	public void setCommand(String command){
		this.command.set(command);
	}
	
	public StringProperty commandProperty(){
		return command;
	}

}