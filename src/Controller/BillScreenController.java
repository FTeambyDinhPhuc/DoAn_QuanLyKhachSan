package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Bill;
import Model.BookRoom;
import Model.BookService;
import Model.Customers;
import Model.DetailBill;
import Model.Room;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class BillScreenController implements Initializable{	
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnClose;
    @FXML
    private TextField cccdField;

    @FXML
    private DatePicker dateNhanPhong;

    @FXML
    private DatePicker dateTraPhong;

    @FXML
    private Label lbThanhTien;

    @FXML
    private TableColumn<BookService, Integer> maDichVuColumn;

    @FXML
    private TextField sDTField;

    @FXML
    private TableColumn<BookService, Integer> soLuongColumn;

    @FXML
    private TableView<BookService> tableThanhToan;

    @FXML
    private TableColumn<BookService, Integer> tenDichVuColumn;

    @FXML
    private TextField tenKhachHangField;
    @FXML
    private Label soPhongTextField;
	Stage stage;
    int maPhong;
    BookService bookService;
    int soP1=0;
    float thanhTien=0;
    float tienPhong =0;
    float tongTien =0;
    long day=0;
	ObservableList<BookService> DSSList = FXCollections.observableArrayList();
	ObservableList<BookService> SLGTList = FXCollections.observableArrayList();
	// Event Listener on Button[#btnConfirm].onAction
	@FXML
	public void Confirm(ActionEvent event)throws IOException {		
		InsertBill();
		showAlertInformation();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}
	 @FXML
	 public void Close(ActionEvent event) {
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 stage.close();
	 }
	 private void GetData()
	 {
	    	String tkh2;
	    	String sdt2;
	    	String cccd2;
	    	Date thoiGianNhanPhong;
	    	Date thoiGianTraPhong;
	    	try {
	        	List<Customers> KHList1 = new ArrayList<>();
	            Customers customers = new Customers();
	            Room room = new Room();
	            int soP= room.LayMaPhong(maPhong);
	            soP1 =soP;
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
	            	dateNhanPhong.setValue(tgnp);
	            	dateTraPhong.setValue(tgtp);
	                Date time1 = java.sql.Date.valueOf(tgnp);	
	                Date time2 = java.sql.Date.valueOf(tgtp);	
	            	long getDiff = time2.getTime()-time1.getTime();
	            	long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
	            	day=getDaysDiff;
	            	
	            }
	            BookService bookService = new BookService();
	            SLGTList = bookService.LaySoLuongVaGiaTien(soP);
	            for(int i=0;i<SLGTList.size();i++)
	            {
	            	BookService bsv1 = SLGTList.get(i);
	            	thanhTien = thanhTien + bsv1.getSoLuong()*bsv1.getGiaTien();
	            }        
	            Room r = new Room();
	            tienPhong=r.LayGiaTienPhong(soP);
	            tongTien = thanhTien+tienPhong*day;
	            lbThanhTien.setText(String.format("%.0f", tongTien)+" VND");
	    	}
	            catch (Exception e) {
	    			// TODO: handle exception
	 }
	 }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			soPhongTextField.setText(Integer.toString(maPhong));
			GetData();
			bookService = new BookService();
			DSSList = bookService.GetDataService(soP1);
			ConverDate();
			refreshTable();
			

		
	});
	}
	//refreshTableView
	private void refreshTable()
	{
		DSSList.clear();
		bookService = new BookService();
		DSSList = bookService.GetDataService(soP1);
		UpdateTable();
		//Search();
	}
	
	private void UpdateTable()
	{
		maDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("MaDichVu"));
		tenDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("TenDichVu"));
		soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
		tableThanhToan.setItems(DSSList);	
	}	
	public void setMaPhong(int maphong) {
		maPhong = maphong;
	}
	private void InsertBill()
	{
    	String cccd2;
    	Date thoiGianNhanPhong = null;
    	Date thoiGianTraPhong= null;
    	int maKhachHang1 = 0;
    	try {
        	List<Customers> KHList1 = new ArrayList<>();
            Customers customers = new Customers();
            Room room = new Room();
            int soP= room.LayMaPhong(maPhong);
            soP1 =soP;
            KHList1= customers.LayThongTinKhachHang(soP);
            for(int i=0;i<KHList1.size();i++)
            {
            	Customers c1 = KHList1.get(i);            	
            	cccd2= c1.getCCCD();
            	maKhachHang1 = c1.LayMaKhachHang(cccd2);
            }
            BookRoom bookRoom = new BookRoom();
            List<BookRoom> TGList = new ArrayList<>();
            TGList= bookRoom.GetTime(soP);
            for(int i=0;i<TGList.size();i++)
            {
            	BookRoom br = TGList.get(i);
            	thoiGianNhanPhong=br.getThoiGianNhanPhong();
            	thoiGianTraPhong=br.getThoiGianTraPhong();
            }
            BookService bookService = new BookService();
            SLGTList = bookService.LaySoLuongVaGiaTien(soP);
            Bill bill = new Bill();
            if(bill.ThemHoaDon(soP, maKhachHang1, thoiGianNhanPhong, thoiGianTraPhong, tongTien)==true)
            {
            	List<BookService> GTDVList = new ArrayList<>();
            	BookService bsv2 = new BookService();
            	GTDVList = bsv2.GetDataService(soP);
            	int maHoaDon1=0;
            	maHoaDon1=bill.LayMaHoaDon(soP, maKhachHang1, thoiGianNhanPhong, thoiGianTraPhong, tongTien);
            	for(int i=0;i<GTDVList.size();i++)
            	{
            		bsv2 = GTDVList.get(i);
                	DetailBill dtb = new DetailBill();
                	dtb.ThemChiTietHoaDon(maHoaDon1, bsv2.getMaDichVu(), bsv2.getSoLuong());
            	}
            }
            BookRoom br10 = new BookRoom();
            BookService bsv10 = new BookService();
            br10.XoaDatPhong(soP);
            bsv10.XoaPhieuSuDungDichVu(soP);
            bsv10.SetDefaultService();
            br10.SetDefaultBookRoom();
            room.UpdateStatus(0, soP);
    	}
            catch (Exception e) {
    			// TODO: handle exception
            }		
	}
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
	// Hiển thị Information Alert không có Header Text
	private void showAlertInformation() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Thanh toán thành công!");
		alert.showAndWait();
	}
}
