package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.MonthDay;

public class ManagerDiscountController extends AbstractScenes {
	public static ManagerDiscountController instance;
	Integer month;
	Integer day;
	String color;
	String datesToShow[][];
	String discount;
	Map<Integer, Month> map = new HashMap<Integer, Month>();
	List<LocalDate> discountDates = new ArrayList<>();
	@FXML
	private TextField discountField;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button btnSave;

	@FXML
	private Text managerName;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnStatus;

	@FXML
	private Button btnReport;

	@FXML
	private Button btmManagingPark;

	@FXML
	void logout(ActionEvent event) {
//    	exitConnection
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	@FXML
	void saveDiscountAndDate(ActionEvent event) {
		String discount = discountField.getText();
		LocalDate discountDate = datePicker.getValue();
		if (discount.trim().isEmpty() || discountDate == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("One of the fields is missing");
			alert.show();
		} else {
			String date = discountDate.toString();
			ArrayList<String> dateAndDiscount = new ArrayList<String>();
			dateAndDiscount.add(discount);
			dateAndDiscount.add(date);
			dateAndDiscount.add(ChatClient.worker.getPark().getNumberOfPark());
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO_REQUEST, dateAndDiscount);
			discountField.clear();
			datePicker.setValue(null);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Wait To Department Manager To Approve");
			alert.show();
			ClientUI.chat.accept(data);
		}
	}

	public void notFond() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Can not take data from DataBase!");
		alert.show();
	}

	@FXML
	void showManagingPark(ActionEvent event) throws IOException {

		switchScenes("/client/boundaries/managingPark.fxml", "Manager");
	}

	@FXML
	void showReport(ActionEvent event) {
		switchScenes("/client/boundaries/reportManager.fxml", "Manager");
	}

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/manager.fxml", "Manager");
	}

	final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					for (int i = 0; i < discountDates.size(); i++) {
						if (LocalDate.from(item).equals(discountDates.get(i))) {
							discount = datesToShow[i][1];
							setTooltip(new Tooltip("Discount : " + discount + "%"));
							if (datesToShow[i][2].equals("toCheck")) {
								color = "#ff4444;";
							} else {
								color = "#26e055;";
							}
							setStyle("-fx-background-color: " + color);
						}
					}
				}
			};
		}
	};

	private Month convertToMonth(int month) {
		return map.get(month);
	}

	public void updateDatePicker() {
		this.datesToShow = ChatClient.datesToShow;
		for (int i = 0; i < datesToShow.length; i++) {
			String[] result = datesToShow[i][0].split("-");
			int year = Integer.valueOf(result[0]);
			month = Integer.valueOf(result[1]);
			day = Integer.valueOf(result[2]);
			discountDates.add(LocalDate.of(year, convertToMonth(month), day));
		}
		datePicker.setDayCellFactory(dayCellFactory);
	}

	private void checkIfThereNewDatesToShow() {
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO, new ParkInfo(null,
				ChatClient.worker.getPark().getNumberOfPark(), null, null, null, ChatClient.worker.getRole()));
		ClientUI.chat.accept(data);
	}

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());
		map.put(1, Month.JANUARY);
		map.put(2, Month.FEBRUARY);
		map.put(3, Month.MARCH);
		map.put(4, Month.APRIL);
		map.put(5, Month.MAY);
		map.put(6, Month.JUNE);
		map.put(7, Month.JULY);
		map.put(8, Month.AUGUST);
		map.put(9, Month.SEPTEMBER);
		map.put(10, Month.OCTOBER);
		map.put(11, Month.NOVEMBER);
		map.put(12, Month.DECEMBER);
		checkIfThereNewDatesToShow();
	}

}