package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.BorderPane;

public class MainScreenController {
	@FXML
	private BorderPane bp;
	@FXML
	private Button btnRoomPage;
	@FXML
	private Button btnStatisticalPage;
	@FXML
	private Button btnCustomerPage;
	@FXML
	private Button btnManagePage;
	@FXML
	private AnchorPane ap;

	// Event Listener on Button[#btnRoomPage].onAction
	@FXML
	public void RoomPage(ActionEvent event) {
		LoadPage("RoomPage");
	}
	// Event Listener on Button[#btnStatisticalPage].onAction
	@FXML
	public void StatisticalPage(ActionEvent event) {
		LoadPage("StatisticalPage");
	}
	// Event Listener on Button[#btnCustomerPage].onAction
	@FXML
	public void CustomerPage(ActionEvent event) {
		LoadPage("CustomerPage");
	}
	// Event Listener on Button[#btnManagePage].onAction
	@FXML
	public void ManagePage(ActionEvent event) {
		LoadPage("ManagePage");
	}
	
	private void LoadPage (String page) {
		Parent root = null;
		
		try {
			root = FXMLLoader.load(getClass().getResource("/View/"+page+".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bp.setCenter(root);
	}
}
