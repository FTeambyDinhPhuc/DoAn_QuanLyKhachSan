package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RoomPageController implements Initializable {
	@FXML
	private ScrollPane scrollVipRoom;
	@FXML
	private ScrollPane scrollNormalRoom;
    @FXML
    private GridPane gridVipRoom;
    @FXML
    private GridPane gridNormalRoom;
	
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
}
