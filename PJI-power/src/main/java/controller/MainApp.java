package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Power;
import view.AddAppDialogController;
import view.AddPidDialogController;

/**
 * The main class of application.
 *
 * @author suhuiso
 *
 */
public class MainApp extends Application {

    private Stage primaryStage;    
    
    /**
     * An observable list of power data.
     */  
    public ObservableList<Power> powerData = FXCollections.observableArrayList();
    

    /**
     * Return the list of power data.
     * 
     * @return
     */
    public ObservableList<Power> getPowerData() {
        return powerData;
    }
    
    /**
     * Initialize the power table overview.
     */
    public void start(Stage stage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("../view/TableOverview.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Power API App");
        stage.show();
        
    }
      
    /**
     * Initialize the dialog which allow to add a new power data with PID number in the power table.
     * 
     * @param power power data
     * @return true if the power data can be recorded
     * @throws IOException if an I/O error occurs
     */
    public boolean showAddPidDialog(Power power) throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/AddPidDialog.fxml"));
			AnchorPane addDataDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Monitor à PID");
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(addDataDialog);
			dialogStage.setScene(scene);

			AddPidDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPower(power);
			
			dialogStage.showAndWait();
			
			return controller.ok();
    }
    
    /**
     * Initialize the dialog which allow to add a new power data with APP in the power table.
     * 
     * @throws IOException if an I/O error occurs
     *
     */
    public void showAddAppDialog() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/AddAppDialog.fxml"));
			AnchorPane addDataDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Monitor en APP");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(addDataDialog);
			dialogStage.setScene(scene);
			
			AddAppDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
    }

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
    	launch(args);
    }
}