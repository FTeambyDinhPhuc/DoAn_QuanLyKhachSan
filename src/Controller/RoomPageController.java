package Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Model.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RoomPageController implements Initializable {
	@FXML
	private ScrollPane scrollVipRoom;
	@FXML
	private ScrollPane scrollNormalRoom;
    @FXML
    private GridPane gridVipRoom;
    @FXML
    private GridPane gridNormalRoom;
    @FXML
    private Button btnService;
    @FXML
    private Button btnPayment;
    @FXML
    private Button btnBookRoom;
	
	private List<Room> rooms = new ArrayList<>();

	private List<Room> getData(){
		//List<Room> rooms = new ArrayList<>();
		Room room;
		
		for (int i=0; i < 20; i++) {
			room = new Room();
			room.setName("10"+i);
			room.setPrice(250);
			room.setColor("98F576");
			room.setStatus("Trong");
			rooms.add(room);
		}
		
		return rooms;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rooms.addAll(getData());
		int column =0;
		int row = 1;
		try {
			for (int i = 0; i < rooms.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/View/Item.fxml"));
				BorderPane borderpane = fxmlLoader.load();
				ItemController itemcontroller = fxmlLoader.getController();
				itemcontroller.setData(rooms.get(i));
				if(column == 2 ){
					column =0;
					row ++;
				}
				gridVipRoom.add(borderpane, column++, row);
				
				GridPane.setMargin(borderpane, new Insets(0,10,18,10));
				
			}
		
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	// Event Listener on Button[#btnSignIn].onAction
	@FXML
	public void OnServiceScreen(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/View/ServiceScreen.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}


	@FXML
	public void Payment(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/View/BillScreen.fxml"));
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void BookRoom(ActionEvent event) throws IOException{
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/View/ErrorLeftBlank.fxml"));
		DialogPane errorLeftBlankDialog = fxmlloader.load();
		Dialog<Void> dialog = new Dialog<>();
		dialog.setDialogPane(errorLeftBlankDialog);
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.showAndWait();
	}
	

}
