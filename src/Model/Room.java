package Model;

import java.util.Date;

public class Room {
	private int MaPhong;
	private int SoPhong;
	private int SucChua;
	private int SoGiuong;
	private float GiaTienPhong;
	private Date ThoiGianNhanPhong;
	public Room() {
		
	}
	public Room(int maPhong, int soPhong, int sucChua, int soGiuong, float giaTienPhong, Date thoiGianNhanPhong) {
		super();
		MaPhong = maPhong;
		SoPhong = soPhong;
		SucChua = sucChua;
		SoGiuong = soGiuong;
		GiaTienPhong = giaTienPhong;
		ThoiGianNhanPhong = thoiGianNhanPhong;
	}
	public int getMaPhong() {
		return MaPhong;
	}
	public void setMaPhong(int maPhong) {
		MaPhong = maPhong;
	}
	public int getSoPhong() {
		return SoPhong;
	}
	public void setSoPhong(int soPhong) {
		SoPhong = soPhong;
	}
	public int getSucChua() {
		return SucChua;
	}
	public void setSucChua(int sucChua) {
		SucChua = sucChua;
	}
	public int getSoGiuong() {
		return SoGiuong;
	}
	public void setSoGiuong(int soGiuong) {
		SoGiuong = soGiuong;
	}
	public float getGiaTienPhong() {
		return GiaTienPhong;
	}
	public void setGiaTienPhong(float giaTienPhong) {
		GiaTienPhong = giaTienPhong;
	}
	public Date getThoiGianNhanPhong() {
		return ThoiGianNhanPhong;
	}
	public void setThoiGianNhanPhong(Date thoiGianNhanPhong) {
		ThoiGianNhanPhong = thoiGianNhanPhong;
	}
	
	
public class Room {
	private String name;
	private double price;
	private String color;
	private String status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

}
