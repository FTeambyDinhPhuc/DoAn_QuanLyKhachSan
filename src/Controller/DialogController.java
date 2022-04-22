package Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class DialogController {
	@FXML
	private DialogPane dp;
	@FXML
	private Button btnOki;
	@FXML
	private Button btnYesDL;
	@FXML
	private Button btnNoDL;
	
	
	Stage stage;

	// Event Listener on Button[#btnOki].onAction
	@FXML
	public void Oki(ActionEvent event) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@FXML
    public void NoDL(ActionEvent event) {
		System.out.print("Nhan khong thi thoi");
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
    }

    @FXML
    public void YesDL(ActionEvent event) {
    	System.out.print("Nhan co thi lam gi di");
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
    }
}
