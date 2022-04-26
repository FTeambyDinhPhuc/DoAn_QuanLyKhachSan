package Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.io.IOException;

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
	public boolean chu;
	// Event Listener on Button[#btnOki].onAction
	@FXML
	public void Oki(ActionEvent event) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@FXML
    public void NoDL(ActionEvent event) {
		chu=false;
		Stage st;
		st = (Stage)((Node)event.getSource()).getScene().getWindow();	
		st.close();
    }

    @FXML
    public void YesDL(ActionEvent event) {
    	chu=true;
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();	
    }
    
}
