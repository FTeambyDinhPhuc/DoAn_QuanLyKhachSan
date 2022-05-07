package Controller;

import Model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ItemController {
	
    @FXML
    private BorderPane bpRoom;

    @FXML
    private Label lbNumberRoom;

    @FXML
    private Label lbStatusRoom;
    
    private Room room;

    public void setData(Room room) {
    	this.room = room;
    	bpRoom.setStyle("-fx-background-color: " + room.getColor() + "; -fx-background-radius: 15;");
    	lbNumberRoom.setText(room.getName());
    	lbStatusRoom.setText(room.getStatus());
    }
}
