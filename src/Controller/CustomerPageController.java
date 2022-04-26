package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.text.TabableView;

import Model.Customers;
import application.ConnectSql;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class CustomerPageController implements Initializable{
	ConnectSql Sql = new ConnectSql();
	private static final String SELECT_QUERY_KH = "SELECT * FROM KhachHang";
	@FXML
	private Button btnEditing;
	@FXML
	private Button btnDL;
	@FXML
	private TableView<Customers> tableCustomers;
	@FXML
	private TextField txtCCCD;
	@FXML
	private TextField txtSDT;
	@FXML
	private TextField txtTenKhachHang;
	@FXML
	private TableColumn<Customers, String> cccdColumn;
	@FXML
	private TableColumn<Customers, String> hoTenColumn;
	@FXML
	private TableColumn<Customers, String> sdtColumn;
	@FXML
	private TableColumn<Customers, Integer> sttColumn;
	Customers customers;
	
	ObservableList<Customers> customersList =FXCollections.observableArrayList();
	// Event Listener on Button[#btnEditing].onAction
	@FXML
	public void Editing(ActionEvent event)throws IOException, SQLException  {
		String tenKhachHang= txtTenKhachHang.getText();
		String soDienThoai = txtSDT.getText();
		String cCCD = txtCCCD.getText();
		if(!IsNumber(cCCD))
		{
			WarningAlerKTCCCD();
			return;
		}
		if(!IsNumber(soDienThoai))
		{
			 WarningAlerSDT();
			 return;
		}
		if(txtCCCD.getText()==""||txtTenKhachHang.getText()==""||txtSDT.getText()=="")
		{
			WarningAlertInput();
		}
		else 
		{				
			customers = new Customers();
			customers.ThemKhachHang(tenKhachHang, soDienThoai, cCCD);
			Clean();
			refreshTable();						
			//Edit();	
		}				
	}
	public static boolean IsNumber(String x)
	{
		NumberFormat format = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		format.parse(x, pos);
		return x.length()==pos.getIndex();
		
	}
	
	
	// Event Listener on Button[#btnDL].onAction
	@FXML
	public void DL(ActionEvent event){
		
		TablePosition<?, ?> pos = tableCustomers.getSelectionModel().getSelectedCells().get(0);
		int index = pos.getRow();
		int mkh3 = (int)tableCustomers.getItems().get(index).getMaKhachHang();
		customers = tableCustomers.getSelectionModel().getSelectedItem();
		Customers cus = new Customers();
		boolean xoaKhachHang = cus.XoaKhachHang(mkh3);
		refreshTable();	
	}
	
	//InitList
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connection conn =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			ResultSet rs = conn.createStatement().executeQuery(SELECT_QUERY_KH);
			while (rs.next())
			{
				customersList.add(new Customers(rs.getInt("MaKhachHang"),rs.getString("TenKhachHang"),rs.getString("CCCD"),rs.getString("SoDienThoai")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sttColumn.setCellValueFactory(new PropertyValueFactory<>("MaKhachHang"));
		hoTenColumn.setCellValueFactory(new PropertyValueFactory<>("TenKhachHang"));
		cccdColumn.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
		sdtColumn.setCellValueFactory(new PropertyValueFactory<>("SoDienThoai"));	
		tableCustomers.setItems(customersList);	
		
	}
	
	//Row click
	@FXML
    void rowClick(MouseEvent event) {
		if (event.getClickCount()==2)
		{
			Customers clickCustomers = tableCustomers.getSelectionModel().getSelectedItem();
			txtTenKhachHang.setText(String.valueOf(clickCustomers.getTenKhachHang()));
			txtSDT.setText(String.valueOf(clickCustomers.getSoDienThoai()));
			txtCCCD.setText(String.valueOf(clickCustomers.getCCCD()));	
		}else
		{
			Clean();
		}
    }
	
	//Clean
	private void Clean()
	{
		txtTenKhachHang.clear();
		txtSDT.clear();
		txtCCCD.clear();		
	}
	
	//refreshTableView
	private void refreshTable()
	{
		try {
			customersList.clear();
			Connection conn =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			ResultSet rs = conn.createStatement().executeQuery(SELECT_QUERY_KH);
			while (rs.next())
			{
				customersList.add(new Customers(rs.getInt("MaKhachHang"),rs.getString("TenKhachHang"),rs.getString("CCCD"),rs.getString("SoDienThoai")));
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	//Update Table
	private void UpdateTable()
	{
		
		sttColumn.setCellValueFactory(new PropertyValueFactory<>("MaKhachHang"));
		hoTenColumn.setCellValueFactory(new PropertyValueFactory<>("TenKhachHang"));
		cccdColumn.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
		sdtColumn.setCellValueFactory(new PropertyValueFactory<>("SoDienThoai"));
		tableCustomers.setItems(customersList);	
	}	
	//Edit
	private void Edit()
	{
		@SuppressWarnings("rawtypes")
		TablePosition pos = tableCustomers.getSelectionModel().getSelectedCells().get(0);
		int index = pos.getRow();
		int mkh = (int)tableCustomers.getItems().get(index).getMaKhachHang();
		String tkh= txtTenKhachHang.getText();
		String sdt = txtSDT.getText();
		String cccd = txtCCCD.getText();
		Customers cus1 = new Customers();
		cus1.Edit(tkh, sdt, cccd,mkh);
		UpdateTable();
		Clean();
		refreshTable();
	}
	//Warning Alert Nhập
	private void WarningAlertInput() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo!");
		alert.setHeaderText(null);
		alert.setContentText("Vui lòng nhập thông tin!");
		alert.showAndWait();
	}
	//Warning Alert Kiểm tra CCCD
	private void WarningAlerKTCCCD()
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo!");
		alert.setHeaderText(null);
		alert.setContentText("CCCD vui lòng nhập số!");
		alert.showAndWait();
	}
	//Warning Alert Kiểm tra số điện thoại
	private void WarningAlerSDT()
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Cảnh báo!");
		alert.setHeaderText(null);
		alert.setContentText("Vui lòng nhập đúng số điện thoại!");
		alert.showAndWait();
	}
	
}
