package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

import java.io.IOException;

import javafx.event.ActionEvent;

public class RoomManagePageController {
	@FXML
	private Button btnEditing;
	@FXML
	private Button btnDL;

	// Event Listener on Button[#btnEditing].onAction
	@FXML
	public void Editing(ActionEvent event) throws IOException {
		//hiện bản thông báo khi chổ nhập bị trống
				FXMLLoader fxmlloader = new FXMLLoader();
				fxmlloader.setLocation(getClass().getResource("/View/ErrorLeftBlank.fxml"));
				DialogPane errorLeftBlankDialog = fxmlloader.load();
				Dialog<Void> dialog = new Dialog<>();
				dialog.setDialogPane(errorLeftBlankDialog);
				dialog.initStyle(StageStyle.UNDECORATED);
				dialog.showAndWait();
	}
	// Event Listener on Button[#btnDL].onAction
	@FXML
	public void DL(ActionEvent event) throws IOException {
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/View/DeleteConfirmation.fxml"));
		DialogPane deleteConfirmationDialog = fxmlloader.load();
		Dialog<Void> dialog = new Dialog<>();
		dialog.setDialogPane(deleteConfirmationDialog);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.showAndWait();
	}
}
