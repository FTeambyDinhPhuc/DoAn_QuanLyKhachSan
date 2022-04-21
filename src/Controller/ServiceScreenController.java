package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ServiceScreenController {
	@FXML
	private Button btnConfirm;

	Stage stage;
	
	@FXML
	public void Confirm(ActionEvent event) throws IOException {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
}
