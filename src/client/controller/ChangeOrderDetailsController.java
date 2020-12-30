package client.controller;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import client.ClientUI;
import client.logic.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeOrderDetailsController extends AbstractScenes{
	public Order ord = new Order(null,null,null,null,null,null,null,null);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private Text msgFromController;
    
    @FXML
    private TextField amountOfVisitorsTxt;
    
    @FXML
    private Text helloTxt;
    
    @FXML
    private URL location;

    @FXML
    private TextField orderNumberTxt;

    @FXML
    private Button applyBtn;

    @FXML
    private Button btnLogout;
    
    @FXML
    private TextField timeTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> parkComboBox;

    @FXML
    private ImageView backBtn;

    @FXML
    private Button orderManagementBtn;

    @FXML
    private Button changeOrderDetailsBtn;

    @FXML
    private Button printDetailsBtn;

    @FXML
    private Button cancelOrderBtn;

    public static ChangeOrderDetailsController instance;
    ObservableList<String> list;
    
    @FXML
    void Apply(ActionEvent event) {
    	//Save Changes to Order Details in DB --> Fix.
    	String time = timeTxt.getText();
		if (time.trim().isEmpty()) {
			msgFromController.setFill(Color.RED);
			msgFromController.setText("Please enter a time");
		}
		else
		{
			//actually check in DB whatever needed.
			msgFromController.setFill(Color.GREEN);
			msgFromController.setText("Updated Successfully");
		}
    }

    @FXML
    void CancelOrder(ActionEvent event) throws IOException{
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/Cancel Confirmation.fxml"));
		Parent current = fxmlLoad.load();
		helpWindow.initModality(Modality.APPLICATION_MODAL);
		helpWindow.setTitle("Cancel Confirmation");
		Scene scene = new Scene(current);
		helpWindow.setMinHeight(230);
		helpWindow.setMinWidth(350);
		helpWindow.setMaxHeight(230);
		helpWindow.setMaxWidth(350);
		helpWindow.setScene(scene);
		helpWindow.showAndWait();
    }

    @FXML
    void OrderManagement(ActionEvent event) { //same as last button, but in different position
    	switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
    }

    @FXML
    void PrintDetails(ActionEvent event) {
    	//fix -- Printing.
    }
    
    @FXML
    void Exit(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }
    
    private void setParkComboBox() {
    	ArrayList<String> al = new ArrayList<String>();	
		al.add("Park1");
		al.add("Park2");
		al.add("Park3");
		list = FXCollections.observableArrayList(al);
		parkComboBox.setItems(list);
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){ //method for dealing with dates.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    	ord= OrderManagementController.instance.ord;
    	setParkComboBox(); // call a func above.
    	parkComboBox.getSelectionModel().select(ord.getParkName());
    	orderNumberTxt.setText(ord.getOrderNumber());
    	amountOfVisitorsTxt.setText(ord.getNumOfVisitors());
    	helloTxt.setText("Hello " + ord.getNameOnOrder());
    	timeTxt.setText(ord.getHour());
    	try {
            datePicker.setValue(LOCAL_DATE(ord.getDate()));
        } catch (NullPointerException e) {}
    }
}
