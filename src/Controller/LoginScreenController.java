package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import Model.Account;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginScreenController {
	@FXML
	private TextField txtUser;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnSignIn;
	

	private Stage stage;
	private Scene scene;
	private Parent root;

	// Event Listener on Button[#btnSignIn].onAction
	@FXML
	public void SignIn(ActionEvent event) throws IOException {
		//System.out.println(txtUser.getText());
				//System.out.println(txtPassword.getText());
				if(txtUser.getText().isEmpty() && txtPassword.getText().isEmpty())
				{
					showAlert(Alert.AlertType.ERROR, stage, "Thông báo!" , "Vui lòng nhập tên đăng nhập và mật khẩu!");
					return;
				}
				if(txtUser.getText().isEmpty())
				{
					showAlert(Alert.AlertType.ERROR, stage, "Thông báo!" , "Vui lòng nhập tên đăng nhập!");
					return;
				}
				if(txtPassword.getText().isEmpty())
				{
					showAlert(Alert.AlertType.ERROR, stage,"Thông báo!", "Vui lòng nhập mật khẩu!");
					return;
				}	
				String userName = txtUser.getText();
				String passWord = txtPassword.getText();
				Account acc = new Account();
				boolean flag = acc.validate(userName, passWord);
				if(!flag)
				{
					infoBox("Vui lòng nhập chính xác tên đăng nhập và mật khẩu!",null, "Thông báo!");
				}else 
				{
					infoBox("Đăng nhập thành công!",null,"Thông báo!");
					Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
					stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.centerOnScreen();
					stage.show();
				}	
	}
	public static void infoBox(String infoMessage, String headerText, String title) 
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}
	private static void showAlert(AlertType alertType,Stage stage, String title, String message)
	{
		Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
		}
}
