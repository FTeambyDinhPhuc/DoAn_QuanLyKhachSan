package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import Model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

public class RoomManagePageController implements Initializable {
	@FXML
	private Button btnEditing;
	@FXML
	private Button btnDL;
	@FXML
	private TableView<Room> tableRoom;
	@FXML
    private TableColumn<Room, Integer> maPhongColumn;
	@FXML
    private TableColumn<Room, Float> donGiaColomn;
	@FXML
	private TableColumn<Room, String> loaiPhongColumn;
	@FXML
	private TableColumn<Room, String> soPhongColumn;
	@FXML
	private TableColumn<Room, Integer> soGiuongColumn;
    @FXML
    private TextField keyWordTextField;
    @FXML
    private TextField txtDonGia;
    @FXML
    private TextField txtLoaiPhong;
    @FXML
    private TextField txtSoGiuong;
    @FXML
    private TextField txtTenPhong;
    @FXML
    private Label lbLoaiPhong;
    @FXML
    private Label lbSoPhong;
	Stage stage;
	Room room;
	
	ObservableList<Room> roomList = FXCollections.observableArrayList();
   
	// Event Listener on Button[#btnEditing].onAction
	@FXML
	public void Editing(ActionEvent event) throws IOException {
		
		String btnName = btnEditing.getText();
		if(txtDonGia.getText()==""||txtLoaiPhong.getText()==""||txtSoGiuong.getText()==""||txtTenPhong.getText()=="")
		{
			WarningAlertInput();
		}
		else 
		{
			if(btnName.compareTo("Thêm") == 0){
			// thêm
			ConstaintAddRoom();

			} else {
			// chỉnh sửa
			Edit();				
			}
		}
		refreshTable();		
	}

	public static boolean IsNumber(String x)
	{
		NumberFormat format = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		format.parse(x, pos);
		return x.length()==pos.getIndex();
		
	}
	private void ConstaintAddRoom()
	{
		String soPhong = txtTenPhong.getText();		
		String tenLoaiPhong = txtLoaiPhong.getText();
		String donGia =txtDonGia.getText();
		String soGiuong= txtSoGiuong.getText();
		if(!IsNumber(donGia))
		{
			WarningAlertConstraintInput();
			return;
		}
		if(!IsNumber(soGiuong))
		{
			WarningAlertConstraintInput();
			return;
		}		
		if(tenLoaiPhong.compareTo("VIP")==0| tenLoaiPhong.compareTo("Thường")==0)
		{
			room = new Room();
			Float donG = Float.parseFloat(txtDonGia.getText());
			int soG = Integer.parseInt(txtSoGiuong.getText());
			int mlp= room.LayMaLoaiPhong(tenLoaiPhong);
			room.ThemPhong(soPhong, soG, mlp, donG);
			Clean();
			refreshTable();		
			showAlertAddNewRoomSuccess();
		}
		else
		{
			WarningAlertConstraintDetailRoom();
			return;
		}
	}
	// Event Listener on Button[#btnDL].onAction
	@FXML
	public void DL(ActionEvent event) throws IOException {
		try {
			TablePosition<?, ?> pos = tableRoom.getSelectionModel().getSelectedCells().get(0);
			int index = pos.getRow();
			int mp = (int)tableRoom.getItems().get(index).getMaPhong();
			Room room = new Room();
			room.XoaPhong(mp);
			refreshTable();
			showAlertDeleteRoomSuccess();
			Clean();
			} catch(Exception e) {
				showAlert(Alert.AlertType.ERROR, stage, "Thông báo!" , "Chưa chọn khách hàng để xóa!");			}
	}
	//Edit
	private void Edit()
	{
		try {
			String soPhong = txtTenPhong.getText();		
			String tenLoaiPhong = txtLoaiPhong.getText();
			String donGia =txtDonGia.getText();
			String soGiuong= txtSoGiuong.getText();
			if(!IsNumber(donGia))
			{
				WarningAlertConstraintInput();
				return;
			}
			if(!IsNumber(soGiuong))
			{
				WarningAlertConstraintInput();
				return;
			}	
			if(tenLoaiPhong.compareTo("VIP")==0| tenLoaiPhong.compareTo("Thường")==0)
			{
				TablePosition pos = tableRoom.getSelectionModel().getSelectedCells().get(0);
				int index = pos.getRow();
				int mp = (int)tableRoom.getItems().get(index).getMaPhong();
				String tp= txtTenPhong.getText();
				String lp =txtLoaiPhong.getText();
				int sg = Integer.parseInt(txtSoGiuong.getText());
				int mlp= room.LayMaLoaiPhong(lp); 
				Float gtp= Float.parseFloat(txtDonGia.getText());
				
				Room room1 = new Room();
				room1.Edit(tp, sg, mlp, gtp, mp);
				Clean();
				showAlertEditRoomSuccess();
				refreshTable();
			}
			else
			{
				WarningAlertConstraintDetailRoom();
				return;
			}
		} catch (Exception e) {
			showAlertAddRoomIsFail();
		}

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		room = new Room();
		roomList = room.getDataRoom();
		refreshTable();	
		
	}
	//refreshTableView
	private void refreshTable()
	{
		//roomList.clear();
		room = new Room();
		roomList = room.getDataRoom();
		UpdateTable();
		Search();
	}
	//Update Table
	private void UpdateTable()
	{
		maPhongColumn.setCellValueFactory(new PropertyValueFactory<>("MaPhong"));
		soPhongColumn.setCellValueFactory(new PropertyValueFactory<>("SoPhong"));
		soGiuongColumn.setCellValueFactory(new PropertyValueFactory<>("SoGiuong"));
		loaiPhongColumn.setCellValueFactory(new PropertyValueFactory<>("TenLoaiPhong"));
		donGiaColomn.setCellValueFactory(new PropertyValueFactory<>("GiaTienPhong"));
		tableRoom.setItems(roomList);	
	}
    @FXML
    void rowClick(MouseEvent event) {
		try {
			if (event.getClickCount()==2)
			{
				Room clickRoom = tableRoom.getSelectionModel().getSelectedItem();
				txtTenPhong.setText(String.valueOf(clickRoom.getSoPhong()));
				txtSoGiuong.setText(String.valueOf(clickRoom.getSoGiuong()));
				txtLoaiPhong.setText(String.valueOf(clickRoom.getTenLoaiPhong()));
				txtDonGia.setText(String.valueOf(clickRoom.getGiaTienPhong()));				
				lbSoPhong.setText(String.valueOf(clickRoom.getSoPhong())+"  ");
				lbLoaiPhong.setText("  "+String.valueOf(clickRoom.getTenLoaiPhong()));			
				btnEditing.setText("Chỉnh sửa");
			}else
			{
				Clean();
				btnEditing.setText("Thêm");

			}
		} catch (Exception e) {
			// TODO: handle exception
		}		   	
    }
    
	//Clean
	private void Clean()
	{
		txtTenPhong.clear();
		txtSoGiuong.clear();
		txtLoaiPhong.clear();	
		txtDonGia.clear();
		lbSoPhong.setText("000  ");
		lbLoaiPhong.setText("  XXX");
	}
	private void Search()
	{
		//Initial filtered list
		FilteredList<Room> filteredData =new FilteredList<>(roomList, b->true);
		keyWordTextField.textProperty().addListener((observable,oldValue, newValue) -> {
				filteredData.setPredicate(room ->{			
				if(newValue.isEmpty() || newValue==null)
				{
					return true;
				}
				String searchKeyword = newValue.toLowerCase();
				if(room.getSoPhong().toLowerCase().indexOf(searchKeyword) > -1)
				{
					return true;
				}
				else if(room.getTenLoaiPhong().toLowerCase().indexOf(searchKeyword) >-1)
				{
					return true;
				}else
					return false;
			}); 
		});
		SortedList<Room> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableRoom.comparatorProperty());
		tableRoom.setItems(sortedData);			
	}
	//Warning Alert Nhập
	private void WarningAlertInput() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo!");
		alert.setHeaderText(null);
		alert.setContentText("Vui lòng nhập đầy đủ thông tin!");
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
	// Hiển thị Information Alert Thêm phòng thành công
	private void showAlertAddNewRoomSuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Thêm phòng thành công!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert sửa phòng thành công
	private void showAlertEditRoomSuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Sửa thông tin phòng thành công!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert xóa phòng thành công
	private void showAlertDeleteRoomSuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Xóa phòng thành công!");
		alert.showAndWait();
	}
	//Warning Alert ràng buộc số giường và đơn giá
	private void WarningAlertConstraintInput() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo!");
		alert.setHeaderText(null);
		alert.setContentText("Số giường và đơn giá phải nhập số!");
		alert.showAndWait();
	}	
	//Warning Alert ràng buộc nhập loại phòng
	private void WarningAlertConstraintDetailRoom() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo!");
		alert.setHeaderText(null);
		alert.setContentText("Nhập đúng định dạng loại phòng 'Thường' hoặc 'VIP'");
		alert.showAndWait();
	}	
	// Hiển thị Information Alert xóa phòng thành công
	private void showAlertAddRoomIsFail() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Sửa phòng thất bại, vui lòng chọn lại phòng!");
		alert.showAndWait();
	}
}
