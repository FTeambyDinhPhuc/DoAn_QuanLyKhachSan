package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.BookService;
import Model.Room;
import Model.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ServiceScreenController implements Initializable {
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnClose;
    @FXML
    private TableView<Service> tableServiceScreen; 
    @FXML
    private TableColumn<Service, Float> giaDichVuColumn;
    @FXML
    private TextField keyWordSearch;
    @FXML
    private TableColumn<Service, Integer> soLuongColumn;
    @FXML
    private TableColumn<Service, String> tenDichVuColumn;
    @FXML
    private TableColumn<Service, Integer> maDichVuColumn;
    @FXML
    private Label thanhTienField;
    @FXML
    private Label soPhongTextField;
	Stage stage;
    Service service;
    Room room;
    ObservableList<Service> serviceList = FXCollections.observableArrayList();	
    BookService bookService;   
    int soLuongDV=0;
    int maPhong;
    
    @FXML
    void Confirm(ActionEvent event) {
    	
    	float thanhTien = 0;
    	double sl = 0;
    	float gt = 0;
    	int sl1=0;
    	int check1=0;
		room = new Room();
		int mp;
		 int error =0;
		double tong=0;
		bookService = new BookService();
    	ObservableList<Service> dataRows = tableServiceScreen.getItems();
    	for(int i = 0; i < dataRows.size(); i++) { 
    		try {
    			sl = Double.parseDouble(dataRows.get(i).getNhapSoLuong().getText());
    			tong=tong+sl;
			} catch (Exception e) {
				error++;
			}
			int check =check_real_integer_number(tong);	
			check1=check;
    	} 
    	if(check1==1 && error==0)
    	{
			for(int j = 0; j < dataRows.size(); j++) 
			{ 
				sl1=Integer.parseInt(dataRows.get(j).getNhapSoLuong().getText());
				gt = dataRows.get(j).getGiaTien();
				thanhTien += sl1 * gt;
				thanhTienField.setText(String.format("%.0f", thanhTien)+ " VND");
				mp = room.LayMaPhong(maPhong);
				bookService.ThemPhieuSuDungDichVu(mp, dataRows.get(j).getMaDichVu(), sl1);
				showAlertWithoutHeaderText();
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();			
				stage.close();	
			}	
    	}
    	else
    	{
        	showAlertQuantityIsAnInteger();
    		return;	
    	}
	}

	private static int check_real_integer_number(double n){
	    int flag = 1;
	    if (Math.ceil(n) != Math.floor(n)) flag = 0;
	    return flag;
	}
	@FXML
	 public void Close(ActionEvent event) {
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 stage.close();
	 }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			soPhongTextField.setText(Integer.toString(maPhong));			
			service = new Service();
			serviceList = service.getDataService1();	
			UpdateTable();
			Search();
		});
	}
	
	public void setMaPhong(int maphong) {
		maPhong = maphong;
	}
	private void Search()
	{
		//Initial filtered list
		FilteredList<Service> filteredData =new FilteredList<>(serviceList, b->true);
		keyWordSearch.textProperty().addListener((observable,oldValue, newValue) -> {
				filteredData.setPredicate(service ->{			
				if(newValue.isEmpty() || newValue==null)
				{
					return true;
				}
				String searchKeyword = newValue.toLowerCase();
				if(service.getTenDichVu().toLowerCase().indexOf(searchKeyword) > -1)
				{
					return true;
				}else
					
					return false;
			}); 
		});
		SortedList<Service> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableServiceScreen.comparatorProperty());
		tableServiceScreen.setItems(sortedData);			
	}
	//Update Table
	private void UpdateTable()
	{
		maDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("MaDichVu"));
		tenDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("TenDichVu"));
		giaDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("GiaTien"));
		soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("nhapSoLuong"));
		tableServiceScreen.setItems(serviceList);	
	}	
	// Hiển thị Information Alert Thêm dịch vụ thành công
	private void showAlertWithoutHeaderText() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo!");
		alert.setHeaderText(null);
		alert.setContentText("Thêm dịch vụ thành công!");
		alert.showAndWait();
	}
	// Hiển thị Information Alert số lượng là số nguyên
	private void showAlertQuantityIsAnInteger() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo!");
		alert.setHeaderText(null);
		alert.setContentText("Số lượng phải là số nguyên!");
		alert.showAndWait();
	}
}
