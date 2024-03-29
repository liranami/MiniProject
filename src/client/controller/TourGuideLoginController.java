package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
import client.logic.Visitor;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Description of TourGuideLoginController This controller responsible of
 * getting the id from the user And if the id matches the id that is written in
 * the data base The user can carry on to the main menu.
 * 
 * @author Elad Kobi
 */
public class TourGuideLoginController extends AbstractScenes {
	TourGuide tourguide;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	@FXML
	private Text MsgFromController;

	@FXML
	private Button LogINGuideBtn;

	@FXML
	private TextField GuideID;

	/**
	 * Description of notFound - ID not found
	 *
	 */
	public void notFound() {

		MsgFromController.setText("Visitor ID Not Found");
	}

	/**
	 * Description of isFound - ID is found
	 *
	 */
	public void isFound() {
		switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "Main Menu");
	}

	public static TourGuideLoginController instance;
	public Object gID = "4";

	/** Description of LogINGuideButton - ID is found 
     *@param event A button that logs into the system.
     */
    
    @FXML
    void LogINGuideButton(ActionEvent event) {
    	String guideID = GuideID.getText();
    	boolean check=checkID(guideID);
    	if (guideID.trim().isEmpty()||check==false) {
    		MsgFromController.setText("You must enter an ID number");
    	}
        else {
        	TourGuide tourguide= new TourGuide(guideID,null,null,null,null);
           	DataTransfer data = new DataTransfer(TypeOfMessage.TOURGUIDELOGIN,tourguide);
		ClientUI.chat.accept(data);
		}
    }

	public static boolean checkID(String ID) {
		if (ID.length() == 9 && ID.matches("[0-9]+"))
			return true;
		return false;
	}

	@FXML
	void backButton(ActionEvent event) {
		switchScenes("/client/boundaries/Travelers.fxml", "Teavelers");
	}

	/**
	 * Description of initialize
	 * 
	 * @see https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}
