package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Model.Bill;
import Model.DetailBill;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

public class StatisticalPageController implements Initializable{
	final String excelFilePath = "D://ThongKeHoaDon.xlsx";
    @FXML
    private TableColumn<DetailBill, String> dichVuColumn;

    @FXML
    private TableColumn<DetailBill, Float> giaTienColumn;

    @FXML
    private TableColumn<Bill, String> khachHangColumn;

    @FXML
    private TableColumn<Bill, Integer> maHoaDonColumn;

    @FXML
    private TableColumn<DetailBill, Integer> maHoaDonOfCTHD;

    @FXML
    private TableColumn<DetailBill, Integer> soLuongColumn;

    @FXML
    private TableColumn<Bill, String> soPhongColumn;

    @FXML
    private TableView<DetailBill> tableChiTietHoaDon;

    @FXML
    private TableView<Bill> tableHoaDon;

    @FXML
    private TableColumn<Bill, Date> thoiGianNhanPhongColumn;

    @FXML
    private TableColumn<Bill, Date> thoiGianTraPhongColumn;

    @FXML
    private TableColumn<Bill, Float> tongTienColumn;
    @FXML
    private TextField keyWordSearch;
    @FXML
    private ImageView exportExcel;
    private FileOutputStream fis;
    Bill bill = new Bill();
	ObservableList<Bill> billList = FXCollections.observableArrayList();
	ObservableList<DetailBill> detailBillList = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		refreshTable();
		Search();
		//SearchDPK();
	}
	private void refreshTable()
	{
		billList.clear();
		bill = new Bill();
		billList = bill.getDataBill();
		UpdateTableBill();
		Search();
		//SearchDPK();

	}
	//Update Table Bill
	private void UpdateTableBill()
	{
		
		maHoaDonColumn.setCellValueFactory(new PropertyValueFactory<>("MaHoaDon"));
		soPhongColumn.setCellValueFactory(new PropertyValueFactory<>("SoPhong"));
		khachHangColumn.setCellValueFactory(new PropertyValueFactory<>("TenKhachHang"));
		thoiGianNhanPhongColumn.setCellValueFactory(new PropertyValueFactory<>("ThoiGianNhanPhong"));
		thoiGianTraPhongColumn.setCellValueFactory(new PropertyValueFactory<>("ThoiGianTraPhong"));
		tongTienColumn.setCellValueFactory(new PropertyValueFactory<>("TongTien"));
		tableHoaDon.setItems(billList);	
	}	

	@FXML
    void clickRow(MouseEvent event) {
		try {
			if (event.getClickCount()==2)
			{
				Bill clickBill = tableHoaDon.getSelectionModel().getSelectedItem();
				DetailBill detailBill = new DetailBill();
				detailBillList = detailBill.GetDataDetailBill(clickBill.getMaHoaDon());
				maHoaDonOfCTHD.setCellValueFactory(new PropertyValueFactory<>("MaHoaDon"));
				dichVuColumn.setCellValueFactory(new PropertyValueFactory<>("TenDichVu"));
				giaTienColumn.setCellValueFactory(new PropertyValueFactory<>("GiaTien"));
				soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("SoLuong"));
				tableChiTietHoaDon.setItems(detailBillList);	
			}else
			{
				//Clean();				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
    }
	private void Search()
	{

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		//Initial filtered list
		FilteredList<Bill> filteredData =new FilteredList<>(billList, b->true);
		keyWordSearch.textProperty().addListener((observable,oldValue, newValue) -> {
				filteredData.setPredicate(bill ->{			
				if(newValue.isEmpty() || newValue==null)
				{
					return true;
				}
				String searchKeyword = newValue.toLowerCase();
				if(bill.getSoPhong().toLowerCase().indexOf(searchKeyword) > -1)
				{
					return true;
				}
				else if(Integer.valueOf(bill.getMaHoaDon()).toString().equals(searchKeyword.toLowerCase()))
				{
					return true;
				}
				else if(bill.getTenKhachHang().toLowerCase().indexOf(searchKeyword) > -1)
				{
					return true;
				}else if(dateFormat.format(bill.getThoiGianNhanPhong()).toLowerCase().indexOf(searchKeyword) > -1)
				{
					return true;
				}else if(dateFormat.format(bill.getThoiGianTraPhong()).toLowerCase().indexOf(searchKeyword) > -1)
				{
					return true;
				}else
					
					return false;
				
			}); 
		});
		SortedList<Bill> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableHoaDon.comparatorProperty());
		tableHoaDon.setItems(sortedData);			
	}	
    @FXML
    void clickSearch(MouseEvent event) {
		try {
			if (event.getClickCount()==2)
			{
				showAlertKeySearch();
				Search();
			}
			else
			{
				refreshTable();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	// Hiển thị Information Alert tìm kiếm bằng keySearch
	private void showAlertKeySearch() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Tìm kiếm từ khóa!");
		alert.showAndWait();
	}
    @FXML
    void onClick(ActionEvent event) {
    	try {

    	       try (XSSFWorkbook workbook = new XSSFWorkbook()) {
    	    	   XSSFSheet sheet=workbook.createSheet("Hóa đơn");
    	    	   int rownum = 0;
				   XSSFRow row =null;
				   XSSFCell cell=null;
				   row=sheet.createRow(rownum);
				   cell=row.createCell(0,CellType.STRING);
				   cell.setCellValue("Mã Hóa Đơn");  	        
				   cell=row.createCell(1,CellType.STRING);
				   cell.setCellValue("Số Phòng");  	        
				   cell=row.createCell(2,CellType.STRING);
				   cell.setCellValue("Tên Khách Hàng");    	        
				   cell=row.createCell(3,CellType.STRING);
				   cell.setCellValue("Thời Gian Nhận Phòng");	    	        
				   cell=row.createCell(4,CellType.STRING);
				   cell.setCellValue("Thời Gian Trả Phòng");	    	        
				   cell=row.createCell(5,CellType.STRING);
				   cell.setCellValue("Thành Tiền");
				   for(int i=0; i<billList.size(); i++)
				    {
					   
				        sheet.autoSizeColumn(i);    
			            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
			            String strDateNhanPhong = dateFormat.format(billList.get(i).getThoiGianNhanPhong());
			            String strDateTraPhong = dateFormat.format(billList.get(i).getThoiGianTraPhong());
				        row=sheet.createRow(1+i);  	 
				        cell=row.createCell(0,CellType.NUMERIC);
				        cell.setCellValue(billList.get(i).getMaHoaDon());   	            
				        
				        cell=row.createCell(1,CellType.NUMERIC);
				        cell.setCellValue(Integer.parseInt(billList.get(i).getSoPhong()));
				        
				        cell=row.createCell(2,CellType.STRING);
				        cell.setCellValue(billList.get(i).getTenKhachHang());
				        
				        cell=row.createCell(3,CellType.STRING);
				        cell.setCellValue(strDateNhanPhong);
				        
				        cell=row.createCell(4,CellType.STRING);
				        cell.setCellValue(strDateTraPhong);
				        
				        cell=row.createCell(5,CellType.NUMERIC);
				        cell.setCellValue(billList.get(i).getTongTien());
                   
				    }


				    File f = new File(excelFilePath);
				    try {
				        FileOutputStream fis = new FileOutputStream(f);
				        workbook.write(fis);
				        fis.close();					
					} catch (Exception e) {
						System.out.println(e);
					}
			}
    	       showAlertGetExcel();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
    }
	// Hiển thị Information Alert không có Header Text
	private void showAlertGetExcel() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Xuất Excel thành công!");

		alert.showAndWait();
	}

}
