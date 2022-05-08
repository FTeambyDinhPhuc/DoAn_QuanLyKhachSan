package Controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Customers;
import Model.Room;
import Model.BookRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

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
    @FXML
    private Label txtMaPhong;
    @FXML
    private TextField cccdField;
    @FXML
    private TextField sDTField;
    @FXML
    private TextField tenKhachHangField;
    @FXML
    private DatePicker dateNhanPhong;
    @FXML
    private DatePicker dateTraPhong;
    @FXML
    private Button btnHuy;
    @FXML
    private Button btnNhanPhong;
    @FXML
    private Label lbGiaTienPhong;
    Room room;
    BookRoom bookroom;
    Customers customers;
    private int maPhong1;
	private String color = " #98F576";
	private String color2 = "#1B95EE";
	private String color3 = "#A459EF";
	private String status = "Trống";
	private String status2 = "Đã đặt phòng";
	private String status3 = "Đã nhận phòng";
	private void ConverDate()
	{
	    // Converter
	    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
	        DateTimeFormatter dateFormatter =
	                  DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        public String toString(LocalDate date) {
	            if (date != null) {
	                return dateFormatter.format(date);
	            } else {
	                return "";
	            }
	        }
	        public LocalDate fromString(String string) {
	            if (string != null && !string.isEmpty()) {
	                return LocalDate.parse(string, dateFormatter);
	            } else {
	                return null;
	            }
	        }
	    };   
	    dateNhanPhong.setConverter(converter);
	    dateNhanPhong.setPromptText("dd-MM-yyyy");
	    dateTraPhong.setConverter(converter);
	    dateTraPhong.setPromptText("dd-MM-yyyy");
	}

    private List<Room> getData()
    {
    	List<Room> roomList = new ArrayList<>();
		Room room = new Room();
		List<Room> rooms = room.getDataRoom();
		int count = rooms.size();
		int mp;
		int mttp;
		for(int i = 0; i < count; i++)
		{
			Room r = rooms.get(i);
			Room room_ = new Room();
			room_.setName(r.getSoPhong());
			room_.setPrice(r.getGiaTienPhong());
			room_.setTenLoaiPhong(r.getTenLoaiPhong());
			mp= r.LayMaPhong(Integer.parseInt(r.getSoPhong()));
			mttp= r.LayTrangThaiPhong(mp);			
			String c,s;
			switch(mttp)
			{
				case 1:
					c = color2;
					s = status2;
					break;
				case 2:
					c = color3;
					s = status3;
					break;
				default:
					c = color;
					s = status;
					break;
			}
			room_.setColor(c);
			room_.setStatus(s);
			roomList.add(room_);
		}
		return roomList;
	}
    
    void refresh(List<Room> roomList) {
		int columnVip = 0;
		int rowVip = 1;
		int columnNormal = 0;
		int rowNormal = 1;
		gridVipRoom.getChildren().removeAll();
		gridNormalRoom.getChildren().removeAll();
		try {
			for (int i = 0; i < roomList.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/View/Item.fxml"));
				BorderPane borderpane = fxmlLoader.load();
				ItemController itemcontroller = fxmlLoader.getController();
				itemcontroller.setData(roomList.get(i));
				if(columnVip == 2){
					columnVip = 0;
					rowVip++;
				}
				if(columnNormal == 2){
					columnNormal = 0;
					rowNormal++;
				}
				if(roomList.get(i).getTenLoaiPhong().compareTo("VIP") == 0)
					gridVipRoom.add(borderpane, columnVip++, rowVip);
				else
					gridNormalRoom.add(borderpane, columnNormal++, rowNormal);
				GridPane.setMargin(borderpane, new Insets(0,10,18,10));				
			}
		
		}catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SetButtonTruocDatPhong();
		txtMaPhong.setText("Phòng");
		ConverDate();
		refresh(getData());
	}
	
	// Event Listener on Button[#btnSignIn].onAction
	@FXML
	public void OnServiceScreen(ActionEvent event) throws IOException {	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ServiceScreen.fxml"));
		Stage stage = new Stage(StageStyle.UNDECORATED);
		stage.setScene(new Scene(loader.load()));		
		ServiceScreenController controller = loader.getController();
		controller.setMaPhong(maPhong1);
		stage.showAndWait();
	}
	//Nhận phòng
    @FXML
    void NhanPhong(ActionEvent event) {	
    	SetRoomNhanPhong();	
    	showAlertCheckInSuccess();

    }
    void SetRoomNhanPhong()
    {	
    	List<Room> roomList = getData(); 
		String soPhong2 = Integer.toString(maPhong1);
		for(int i = 0; i < roomList.size(); i++)
		{
			Room r = roomList.get(i);
			if(r.getName().equals(soPhong2))
			{
				r.setColor(color3);
				r.setStatus(status3);		
				int mp= r.LayMaPhong(maPhong1);
				r.UpdateStatus(2, mp);
				break;
			}
		}
    	btnBookRoom.setDisable(true);
    	btnService.setDisable(false);
    	btnHuy.setDisable(true);
    	btnNhanPhong.setDisable(true);
    	btnPayment.setDisable(false);
		refresh(roomList);
    }
    void SetRoomDatPhong()
    {	
    	List<Room> roomList = getData(); 
		String soPhong2 = Integer.toString(maPhong1);
		for(int i = 0; i < roomList.size(); i++)
		{
			Room r = roomList.get(i);
			if(r.getName().equals(soPhong2))
			{
				r.setColor(color2);
				r.setStatus(status2);		
				int mp= r.LayMaPhong(maPhong1);
				r.UpdateStatus(1, mp);
				break;
			}
		}

		refresh(roomList);
    }
    public void SetRoomThanhToan()
    {	
    	List<Room> roomList = getData(); 
		String soPhong2 = Integer.toString(maPhong1);
		for(int i = 0; i < roomList.size(); i++)
		{
			Room r = roomList.get(i);
			if(r.getName().equals(soPhong2))
			{
				r.setColor(color);
				r.setStatus(status);		
				int mp= r.LayMaPhong(maPhong1);
				r.UpdateStatus(0, mp);
				break;
			}
		}
		refresh(roomList);
    }
	@FXML
	public void Payment(ActionEvent event) throws IOException {	
		try {
			if(cccdField.getText().isEmpty() || tenKhachHangField.getText().isEmpty()||sDTField.getText().isEmpty()||dateNhanPhong.getValue()==null||dateTraPhong.getValue()==null)
			{
				showAlertWarningSetText();
				return;
			}
			LocalDate localDateNhanPhong = dateNhanPhong.getValue();
			LocalDate dateNow =LocalDate.now();
			if(localDateNhanPhong.isBefore(dateNow))
			{
				showAlertCheckInDateSmallerGetDate();
				return;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BillScreen.fxml"));
			Stage stage = new Stage(StageStyle.UNDECORATED);
			stage.setScene(new Scene(loader.load()));		
			BillScreenController controller1 = loader.getController();
			controller1.setMaPhong(maPhong1);
			stage.showAndWait();
			List<Room> roomList = getData(); 
			refresh(roomList);		
			Clean();
		} catch (Exception e) {
			showAlertPayFail();
		}

	}
	
	@FXML
	public void BookRoom(ActionEvent event) throws IOException{
		if(cccdField.getText().isEmpty() || tenKhachHangField.getText().isEmpty()||sDTField.getText().isEmpty()||dateNhanPhong.getValue()==null||dateTraPhong.getValue()==null)
		{
			showAlertWarningSetText();
			return;
		}
		LocalDate localDateNhanPhong = dateNhanPhong.getValue();
		LocalDate dateNow =LocalDate.now();
		if(localDateNhanPhong.isBefore(dateNow))
		{
			showAlertCheckInDateSmallerGetDate();
			return;
		}
		try {
			SetRoomDatPhong();
			bookroom = new BookRoom();
			room = new Room();
			Customers customers = new Customers();
			LocalDate tgdp = dateNhanPhong.getValue();
			Date thoiGianDatPhong = java.sql.Date.valueOf(tgdp);
			LocalDate tgtp = dateTraPhong.getValue();
			Date thoiGianTraPhong = java.sql.Date.valueOf(tgtp);
			int maKH = customers.LayMaKhachHang(cccdField.getText());
			int mp= room.LayMaPhong(maPhong1);	
			bookroom.ThemPhieuDatPhong(thoiGianDatPhong, thoiGianTraPhong, maKH, mp);
	    	btnBookRoom.setDisable(true);
	    	btnService.setDisable(true);
	    	btnHuy.setDisable(false);
	    	btnNhanPhong.setDisable(false);
	    	btnPayment.setDisable(true);
	    	showAlertBookRoomSuccess();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	private void Clean()
	{
		txtMaPhong.setText("Phòng");
		tenKhachHangField.setText("");
		cccdField.setText("");
		sDTField.setText("");
		dateNhanPhong.setValue(null);
		dateTraPhong.setValue(null);
		lbGiaTienPhong.setText("0 vnđ/Ngày");
	}
 
    @FXML
    void clickScrollPaneNormal(MouseEvent event) {
    	xuLyPhong(event);
    }
    
    private void xuLyPhong(MouseEvent event)
    { 	
    	int maPhong = 0;
    	try {
	    	if (event.getClickCount()==2)
			{
	    		Text asd = (Text) event.getTarget();
		       	try {
		             int result = Integer.parseInt(asd.getText());
		             maPhong = result;
		             maPhong1=maPhong;
		             getDSHK();
		        } catch (NumberFormatException e) {
		        	
		        }
		       	
			} else
			{
    	    	btnBookRoom.setDisable(true);
    	    	btnService.setDisable(true);
    	    	btnHuy.setDisable(true);
    	    	btnNhanPhong.setDisable(true);
    	    	btnPayment.setDisable(true);   	    	
				Clean();
			}
    	} catch(Exception e) {
    		 
    	}

    	if(maPhong > 0) {
        	txtMaPhong.setText("Phòng " +Integer.toString(maPhong1));
    	}
    }
    
    @FXML
    void clickScrollPaneVIP(MouseEvent event) {
    	xuLyPhong(event);
    }	
    @FXML
    void HuyDatPhong(ActionEvent event) {
    	try {
    		Room r = new Room();
    		int mp1 = r.LayMaPhong(maPhong1);
        	BookRoom bookr1 = new BookRoom();
        	bookr1.XoaDatPhong(mp1);
        	bookr1.SetDefaultBookRoom();
	    	showAlertCancelBookRoom();
	    	r.UpdateStatus(0, mp1);
			List<Room> roomList = getData(); 
			refresh(roomList);
        	Clean();
			r.UpdateStatus(0, mp1);
            
		} catch (Exception e) {
			// TODO: handle exception
		}

    }      
    @FXML
    void onEnter(ActionEvent event) {
    	try {
        	String tkh1;
        	String sdt1;
        	List<Customers> KHList = new ArrayList<>();
        	Customers customers = new Customers();
        	KHList =customers.LayDS(cccdField.getText());
        	int count = KHList.size();
        	for(int i=0;i<count;i++)
        	{
        		Customers c = KHList.get(i);
    			tkh1= c.getTenKhachHang();   
    			sdt1=c.getSoDienThoai();
    			tenKhachHangField.setText(tkh1);
    			sDTField.setText(sdt1);
        		//System.out.println(tkh1+sdt1);
        	} 	
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    private void getDSHK()
    {
    	String tkh2;
    	String sdt2;
    	String cccd2;
    	Date thoiGianNhanPhong;
    	Date thoiGianTraPhong;
    	float giaTienPhong =0;
    	try {
        	List<Customers> KHList1 = new ArrayList<>();
            Customers customers = new Customers();
            Room room = new Room();
            int soP= room.LayMaPhong(maPhong1);
            giaTienPhong =room.LayGiaTienPhong(soP);
            lbGiaTienPhong.setText(Float.toString(giaTienPhong)+" vnđ/Ngày");
            KHList1= customers.LayThongTinKhachHang(soP);
            for(int i=0;i<KHList1.size();i++)
            {
            	Customers c1 = KHList1.get(i);
            	tkh2=c1.getTenKhachHang();
            	sdt2 = c1.getSoDienThoai();
            	cccd2= c1.getCCCD();
                tenKhachHangField.setText(tkh2);
               	cccdField.setText(cccd2);
                sDTField.setText(sdt2);
            }
            BookRoom bookRoom = new BookRoom();
            List<BookRoom> TGList = new ArrayList<>();
            TGList= bookRoom.GetTime(soP);
            for(int i=0;i<TGList.size();i++)
            {
            	BookRoom br = TGList.get(i);
            	thoiGianNhanPhong=br.getThoiGianNhanPhong();
            	thoiGianTraPhong=br.getThoiGianTraPhong();
            	LocalDate tgnp = thoiGianNhanPhong.toLocalDate();
            	LocalDate tgtp = thoiGianTraPhong.toLocalDate();
            	dateNhanPhong.setValue(tgnp);;
            	dateTraPhong.setValue(tgtp);
            }
            int ttp;
            
            ttp= room.LayTrangThaiPhong(soP);
            if(ttp==2)
            {
    	    	btnBookRoom.setDisable(true);
    	    	btnService.setDisable(false);
    	    	btnHuy.setDisable(true);
    	    	btnNhanPhong.setDisable(true);
    	    	btnPayment.setDisable(false);
            }
            if(ttp==1)
            {
    	    	btnBookRoom.setDisable(true);
    	    	btnService.setDisable(true);
    	    	btnHuy.setDisable(false);
    	    	btnNhanPhong.setDisable(false);
    	    	btnPayment.setDisable(true);
            }
            if(ttp==0)
            {
    	    	btnBookRoom.setDisable(false);
    	    	btnService.setDisable(true);
    	    	btnHuy.setDisable(true);
    	    	btnNhanPhong.setDisable(true);
    	    	btnPayment.setDisable(true);
            }
           
		} catch (Exception e) {
			// TODO: handle exception
		}  	
    }  
    private void SetButtonTruocDatPhong()
    {
    	btnBookRoom.setDisable(false);
    	btnService.setDisable(true);
    	btnHuy.setDisable(true);
    	btnNhanPhong.setDisable(true);
    	btnPayment.setDisable(true);
    }
	// Hiển thị Warning Alert cảnh báo nhập đầy đủ thông tin
	private void showAlertWarningSetText() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo");
		alert.setHeaderText(null);
		alert.setContentText("Vui lòng nhập đầy đủ thông tin!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert đặt phòng thành công
	private void showAlertBookRoomSuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Đặt phòng thành công!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert hủy đặt phòng
	private void showAlertCancelBookRoom() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Hủy đặt phòng thành công!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert nhận phòng thành công
	private void showAlertCheckInSuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Nhận phòng thành công!");
		alert.showAndWait();
	}
	// Hiển thị Warning Alert cảnh báo khi ngày nhận phòng bé hơn ngày hiện tại
	private void showAlertCheckInDateSmallerGetDate() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo");
		alert.setHeaderText(null);
		alert.setContentText("Ngày nhận phòng bé hơn ngày hiện tại!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert thanh toán thất bại
	private void showAlertPayFail() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Thanh toán thất bại!");

		alert.showAndWait();
	}
}
