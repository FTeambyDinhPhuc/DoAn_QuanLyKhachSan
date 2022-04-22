package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import javafx.event.ActionEvent;

public class BillScreenController {
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnClose;
	
	Stage stage;

	// Event Listener on Button[#btnConfirm].onAction
	@FXML
	public void Confirm(ActionEvent event)throws IOException {
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/View/SuccessfulConfirmation.fxml"));
		DialogPane successfulConfirmationDialog = fxmlloader.load();
		Dialog<Void> dialog = new Dialog<>();
		dialog.setDialogPane(successfulConfirmationDialog);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.showAndWait();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	 @FXML
	 public void Close(ActionEvent event) {
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 stage.close();
	 }
}
