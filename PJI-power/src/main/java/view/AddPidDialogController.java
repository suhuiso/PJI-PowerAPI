package view;

import com.jfoenix.controls.JFXTextField;

import controller.PowerThread;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Power;

/**
 * View for a dialog which permits to add a power data with its PID
 * 
 * @author suhuiso
 *
 */
public class AddPidDialogController {
	
	@FXML
	private AnchorPane ap;
	@FXML
	private JFXTextField pidField;
	@FXML
	private JFXTextField feqField;
	
	private Stage pidStage;
	private Power power;
	private boolean ok=false;
	
	/**
	 * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
	 */
	@FXML
	private void initiallize(){
		
	}
	
	/**
	 * Set the stage.
	 * @param dialogStage stage
	 */
	public void setDialogStage(Stage dialogStage){
		this.pidStage = dialogStage;
	}
	
	/**
	 * Set the power with its PID input
	 * @param power power
	 */
	public void setPower(Power power){
		this.power = power;
		pidField.setText(Integer.toString(power.getPid()));

	}
	
	public boolean ok(){
		return ok;
	}
	
	/**
	 * Check the input is valid or not. The PID input should be a numerical number.\
	 * If the PID input is invalid(PID is 0 or PID is not numerical), 
	 * an error message will appear to notify the user.
	 * 
	 * @return true if input is valid
	 */
	public boolean inputValid(){
		try {
			if(Integer.parseInt(pidField.getText()) == 0){
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("PID can not be 0.\n"+"Please input your new PID again!");
				alert.getButtonTypes().setAll(new ButtonType("OK"));
				
				alert.getDialogPane().getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
			    alert.getDialogPane().getStyleClass().add("myDialog");
				
				alert.showAndWait();
				
				return false;
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("PID should be numerical.\n"+"Please input your new PID again!");
			alert.getButtonTypes().setAll(new ButtonType("OK"));
			
			alert.getDialogPane().getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
		    alert.getDialogPane().getStyleClass().add("myDialog");

			alert.showAndWait();
			pidField.setText(null);
			return false;
		}
		return true;
	}
	
	/**
	 * Action of button OK which permits a record of new power.
	 */
	@FXML
	public void handleButtonOk(){
		if(inputValid()){
			power.setPid(Integer.parseInt(pidField.getText()));
			PowerThread thread = new PowerThread(power);
			thread.start();
			ok = true;
			pidStage.close();
		}
	}
	
	/**
	 * Action of button Cancel.
	 */
	@FXML
	public void handleButtonCancel(){
		pidStage.close();
	}

}
