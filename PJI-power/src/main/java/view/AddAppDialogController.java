package view;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * View for a dialog which permits to add a power data with its APP
 * 
 * @author suhuiso
 *
 */
public class AddAppDialogController {

	@FXML
	private JFXTextField appField;
	@FXML
	private JFXTextField feqField;
	
	private Stage dialogStage;
	
	/**
	 * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
	 */
	@FXML
	private void initiallize(){
		
	}
	
	/**
	 * /**
	 * Set the stage.
	 * @param dialogStage stage
	 */
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Action of a button OK which permits a record of new power.
	 */
	@FXML
	public void handleButtonOk(){
		//to complete
		dialogStage.close();
	}
	
	/**
	 * Action of a button Cancel
	 */
	@FXML
	public void handleButtonCancel(){
		dialogStage.close();
	}
	
}
