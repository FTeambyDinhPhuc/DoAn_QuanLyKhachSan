package Controller;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

import Model.Customers;
import Model.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

public class ServiceManagePageController implements Initializable{
	@FXML
	private Button btnDL;
	@FXML
    private Button btnEditing;
	@FXML
	private TableView<Service> tableService;
    @FXML
    private TextField keyWordSearchService;
	@FXML
    private TableColumn<Service, Float> donGiaColumn;
    @FXML
    private TableColumn<Service, Integer> sttDichVuColumn;
    @FXML
    private TableColumn<Service, String> tenDichVuColumn;
    @FXML
    private TextField txtDonGia;
    @FXML
    private TextField txtTenDichVu;
    
    Stage stage;
    Service service;
    ObservableList<Service> serviceList = FXCollections.observableArrayList();
	// Event Listener on Button[#btnDL].onAction
	@FXML
	public void DL(ActionEvent event)throws IOException {
		try {
			TablePosition pos = tableService.getSelectionModel().getSelectedCells().get(0);
			int index = pos.getRow();
			int mdv = (int)tableService.getItems().get(index).getMaDichVu();
			service = tableService.getSelectionModel().getSelectedItem();
			Service service = new Service();
			service.XoaDichVu(mdv);
			refreshTable();
			showAlertDeleteService();
			Clean();
			} catch(Exception e) {
				showAlert(Alert.AlertType.ERROR, stage, "Th??ng b??o!" , "Ch??a ch???n kh??ch h??ng ????? x??a!");
			}
	}
	
	@FXML
	void Editing(ActionEvent event)throws IOException  {
		String tenDichVu = txtTenDichVu.getText();
		String btnName =btnEditing.getText();
		String dg =txtDonGia.getText();
		if(!IsNumber(dg))
		{
			WarningAlerKTDonGia();
			return;
		}
		if(txtTenDichVu.getText()==""|| txtDonGia.getText()=="")
		{
			WarningAlertInput();
		}
		else 
		{
			Float donGia= Float.parseFloat(txtDonGia.getText());
			if(btnName.compareTo("Th??m") == 0){
				// th??m
				service = new Service();
				service.ThemDichVu(tenDichVu, donGia);
				Clean();
				refreshTable();						
				showAlertAddNewService();
			} else {
				// ch???nh s???a
				Edit();				
			}
		}
		refreshTable();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		service = new Service();
		serviceList = service.getDataService();
		refreshTable();
	} 
	
	@FXML
    void CellClickRowSevice(MouseEvent event) {
		try {
			if (event.getClickCount()==2)
			{
				Service clickService= tableService.getSelectionModel().getSelectedItem();
				txtTenDichVu.setText(String.valueOf(clickService.getTenDichVu()));
				txtDonGia.setText(String.valueOf(clickService.getGiaTien()));
				btnEditing.setText("Ch???nh s???a");
			}else
			{
				Clean();
				btnEditing.setText("Th??m");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	//refreshTableView
	private void refreshTable()
	{
		serviceList.clear();
		service = new Service();
		serviceList = service.getDataService();
		UpdateTable();
		Search();
	}
	//Update Table
	private void UpdateTable()
	{
		sttDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("MaDichVu"));
		tenDichVuColumn.setCellValueFactory(new PropertyValueFactory<>("TenDichVu"));
		donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("GiaTien"));
		tableService.setItems(serviceList);	
	}	
	//Clean
	private void Clean()
	{
		txtTenDichVu.clear();
		txtDonGia.clear();	
	}
	private void Search()
	{
		//Initial filtered list
		FilteredList<Service> filteredData =new FilteredList<>(serviceList, b->true);
		keyWordSearchService.textProperty().addListener((observable,oldValue, newValue) -> {
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
		sortedData.comparatorProperty().bind(tableService.comparatorProperty());
		tableService.setItems(sortedData);			
	}
	private static void showAlert(AlertType alertType,Stage stage, String title, String message)
	{
		Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	//Edit
	private void Edit()
	{
		TablePosition<?, ?> pos = tableService.getSelectionModel().getSelectedCells().get(0);
		int index = pos.getRow();
		int mdv = (int)tableService.getItems().get(index).getMaDichVu();
		String tdv= txtTenDichVu.getText();
		Float dg = Float.parseFloat(txtDonGia.getText());
		Service service = new Service();
		service.EditDichVu(tdv, dg, mdv);
		Clean();
		btnEditing.setText("Th??m");
		refreshTable();
		showAlertEditService();
	}
	//Warning Alert Nh???p
	private void WarningAlertInput() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("C???nh b??o!");
		alert.setHeaderText(null);
		alert.setContentText("Vui l??ng nh???p ?????y ????? th??ng tin!");
		alert.showAndWait();
	}
	//Test Number
	public static boolean IsNumber(String x)
	{
		NumberFormat format = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		format.parse(x, pos);
		return x.length()==pos.getIndex();
		
	}
	//Warning Alert Ki???m tra ????n gi??
	private void WarningAlerKTDonGia()
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("C???nh b??o!");
		alert.setHeaderText(null);
		alert.setContentText("????n gi?? vui l??ng nh???p s???!");
		alert.showAndWait();
	}
	// Hi???n th??? Information Alert Th??m m???i d???ch v??? 
	private void showAlertAddNewService() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Th??ng b??o");
		alert.setHeaderText(null);
		alert.setContentText("Th??m m???i d???ch v??? th??nh c??ng!");
		alert.showAndWait();
	}
	// Hi???n th??? Information Alert S???a d???ch v???
	private void showAlertEditService() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Th??ng b??o");
		alert.setHeaderText(null);
		alert.setContentText("S???a d???ch v??? th??nh c??ng!");
		alert.showAndWait();
	}
	// Hi???n th??? Information Alert X??a d???ch v???
	private void showAlertDeleteService() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Th??ng b??o");
		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("X??a d???ch v??? th??nh c??ng!");
		alert.showAndWait();
	}
}
