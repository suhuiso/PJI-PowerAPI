package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import controller.MainApp;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Power;

/**
 * View for the whole power table overview.
 * 
 * @author suhuiso
 *
 */
public class TableOverviewController implements Initializable {
	
	@FXML
	public AnchorPane main;
	@FXML
	public StackPane stackPane;
    @FXML
	public JFXTreeTableView<Power> powerTable;
    @FXML
	private ContextMenu deleteMenu;
    @FXML
	private ContextMenu addMenu;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private MenuItem statisticsItem;
    @FXML
    private MenuItem addPidItem;
    @FXML
    private MenuItem addAppItem;
    
    private JFXTreeTableColumn<Power, Number> pidColumn;
    private JFXTreeTableColumn<Power, Number> cpuColumn;
    private JFXTreeTableColumn<Power, Number> memColumn;
    private JFXTreeTableColumn<Power, Number> powerColumn;
    private JFXTreeTableColumn<Power, String> timeColumn;
    private JFXTreeTableColumn<Power, String> commandColumn;
	
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TableOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @SuppressWarnings("unchecked")
	public void initialize(URL url, ResourceBundle rb) {
    	mainApp = new MainApp();
    	// Initialize the table with the six columns.
    	pidColumn = new JFXTreeTableColumn<Power, Number>("PID");
    	pidColumn.setPrefWidth(powerTable.getPrefWidth()/6);
    	pidColumn.setMinWidth(100);
        pidColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Power, Number>, ObservableValue<Number>>() {

			public ObservableValue<Number> call(CellDataFeatures<Power, Number> param) {
				return param.getValue().getValue().pidProperty();
			}
        	
        });
        
        cpuColumn = new JFXTreeTableColumn<Power, Number>("CPU%");
    	cpuColumn.setPrefWidth(powerTable.getPrefWidth()/6);
    	cpuColumn.setMinWidth(100);
        cpuColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Power, Number>, ObservableValue<Number>>() {

			public ObservableValue<Number> call(CellDataFeatures<Power, Number> param) {
				return param.getValue().getValue().cpuProperty();
			}
        	
        });
        
        memColumn = new JFXTreeTableColumn<Power, Number>("MEM%");
        memColumn.setPrefWidth(powerTable.getPrefWidth()/6);
    	memColumn.setMinWidth(100);
        memColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Power, Number>, ObservableValue<Number>>() {

			public ObservableValue<Number> call(CellDataFeatures<Power, Number> param) {
				return param.getValue().getValue().memProperty();
			}
        	
        });
        
        powerColumn = new JFXTreeTableColumn<Power, Number>("Power(W)");
        powerColumn.setPrefWidth(powerTable.getPrefWidth()/6);
    	powerColumn.setMinWidth(100);
        powerColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Power, Number>, ObservableValue<Number>>() {

			public ObservableValue<Number> call(CellDataFeatures<Power, Number> param) {
				return param.getValue().getValue().powerProperty();
			}
        	
        });
        
        timeColumn = new JFXTreeTableColumn<Power, String>("TIME");
        timeColumn.setPrefWidth(powerTable.getPrefWidth()/6);
    	timeColumn.setMinWidth(100);
        timeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Power, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<Power, String> param) {
				return param.getValue().getValue().timeProperty();
			}
        	
        });
        
        commandColumn = new JFXTreeTableColumn<Power, String>("Command");
        commandColumn.setPrefWidth(powerTable.getPrefWidth()/6);
    	commandColumn.setMinWidth(100);
        commandColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Power, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<Power, String> param) {
				return param.getValue().getValue().commandProperty();
			}
        	
        });
        final TreeItem<Power> root = new RecursiveTreeItem<Power>(mainApp.powerData, RecursiveTreeObject::getChildren);
        powerTable.getColumns().setAll(pidColumn, cpuColumn, memColumn, powerColumn, timeColumn, commandColumn);
        powerTable.setRoot(root);
        powerTable.setShowRoot(false);
        
        //Table action begins.
        //A context menu functions on a table click which permits to activate the dialogs of adding the new power.
        powerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        final ContextMenu addMenu = new ContextMenu();
        Label addPid = new Label("Moniteur à PID");
        MenuItem addPidItem = new CustomMenuItem(addPid);
        Label addApp = new Label("Moniteur en Command");
        MenuItem addAppItem = new CustomMenuItem(addApp);
        
        //The action of menu item which permits to activate the dialog of adding power with its PID.   
        addPid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY) {
					try {
						handleAddPidItemMenu();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}	
			
		});
        
        //The action of menu item which permits to activate the dialog of adding power with its APP.   
        addApp.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY) {
					try {
						handleAddAppItemMenu();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
        });
        
        //Add the menu items in the context menu.
		addMenu.getItems().add(addPidItem);
		addMenu.getItems().add(addAppItem);
       
		//A context menu functions on a row click which permits to activate the dialogs of adding the new power.
        powerTable.setRowFactory(new Callback<TreeTableView<Power>, TreeTableRow<Power>>(){

			public TreeTableRow<Power> call(TreeTableView<Power> param) {
				final TreeTableRow<Power> row = new TreeTableRow<>();
				final ContextMenu menuOnRow = new ContextMenu();
				Label delete = new Label("Delete");
				Label statistics = new Label("Statistics");
				MenuItem deleteItem = new CustomMenuItem(delete);
				MenuItem statisticsItem =new CustomMenuItem(statistics);
				
				//The action of menu item which permits to delete the rows chosen.
				delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

					public void handle(MouseEvent event) {
						if(event.getButton() == MouseButton.PRIMARY) {

							@SuppressWarnings("rawtypes")
							ArrayList<TreeItem> rows =  new ArrayList<TreeItem> (powerTable.getSelectionModel().getSelectedItems());
							powerTable.getRoot().getChildren().removeAll(rows);
						}
					}
				});
				
				//The action of menu item which permits to show the statistic of the rows chosen.
				statistics.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent event) {
						if(event.getButton() == MouseButton.PRIMARY) {
							final CategoryAxis xAxis = new CategoryAxis();
					    	final NumberAxis yAxis = new NumberAxis();
					    	yAxis.setLabel("Power%");
					    	
					    	final LineChart<String, Number>lineChart = new LineChart<String, Number>(xAxis, yAxis);
					    	lineChart.setTitle("Power Statistics");
					    	
							Stage stage = new Stage();
							ArrayList<TreeItem<Power>> rows =  new ArrayList<TreeItem<Power>> (powerTable.getSelectionModel().getSelectedItems());
							int MAX_DATA_POINTS = 40;
							for(TreeItem<Power> row : rows){
								XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
								series.setName("PID:"+String.valueOf(row.getValue().getPid()));
								powerColumn.getCellObservableValue(row).addListener((observable, oldValue, newValue) -> {
				
									Platform.runLater(new Runnable(){

										@Override
										public void run() {
											series.getData().add(new XYChart.Data<String, Number>((new Date()).toString(), (Double)newValue));
											if (series.getData().size() > MAX_DATA_POINTS) {
									            series.getData().remove(0, series.getData().size() - MAX_DATA_POINTS);
									        }
										}
									});	
								});
								
								lineChart.getData().add(series);
							}
			
							lineChart.setAnimated(false);
							lineChart.getXAxis().setTickLabelsVisible(false);
							lineChart.setCreateSymbols(false);
					    	Scene scene = new Scene(lineChart, 800, 600);
					    	stage.setScene(scene);
					    	stage.showAndWait();
						}
					}
				});
				
				//Add the menu items in the context menu.
				menuOnRow.getItems().add(deleteItem);
				menuOnRow.getItems().add(statisticsItem);
				
				// only display delete context menu for non-null items:
			    row.contextMenuProperty().bind(
			      Bindings.when(Bindings.isNotNull(row.itemProperty()))
			      .then(menuOnRow)
			      .otherwise((ContextMenu)null));
			    
				return row;
			}
        });
        
        //To permit the action of activating those context menus when a right click appears on the table.
        powerTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			public void handle(MouseEvent e) {
				if(e.getButton() == MouseButton.SECONDARY)
					Platform.runLater(new Runnable(){

						public void run() {
							powerTable.setContextMenu(deleteMenu);
							powerTable.setContextMenu(addMenu);
						}
					});	
			}
    	});  
    }
   
    /**
     * The action of the item menu which activates the appearance of dialog which 
	 * permits to add a new power data with its PID.
     * When the PID input is already existed, it can not be recorded in the power table 
     * and an error message will appear to notify the user. 
     * The power table can just record the unique power data.
     * 
     * @throws IOException if an I/O error occurs
     */
	@FXML
	protected void handleAddPidItemMenu() throws IOException {
    	Power newPower = new Power();
    	boolean add = mainApp.showAddPidDialog(newPower);
    	if(add){
    		mainApp.getPowerData().add(newPower);
    		int i = 0;
    		for (TreeItem<Power> item : powerTable.getRoot().getChildren()) {
    			if(item.getValue().getPid() == newPower.getPid()){
    				i++;
    				if(i == 2){
    	    			Alert alert = new Alert(Alert.AlertType.ERROR);
    	    			alert.setTitle("Error");
    	    			alert.setHeaderText(null);
    	    			alert.setContentText("PID Exisits.\n"+"Please click ok to remove your input!");
    	    			alert.getButtonTypes().setAll(new ButtonType("OK"));
    	    			alert.getDialogPane().getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
    	    		    alert.getDialogPane().getStyleClass().add("myDialog");
    	    			alert.showAndWait();

    	    			powerTable.getRoot().getChildren().remove(item);
    	    			break;
    	    		}
        		}
    		}
    	}
	}

	/**
	 * The action of the item menu which activates the appearance of dialog which 
	 * permits to add a new power data with its APP.
	 * 
	 * @throws IOException  If an I/O error occurs
	 */
	@FXML
    protected void handleAddAppItemMenu() throws IOException {
		mainApp.showAddAppDialog();
	}
    
}