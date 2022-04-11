package Model;

public class Customer {
	private int MaKhachHang;
	private String TenKhachHang;
	private String SoDienThoai;
	private String CCCD;
	public Customer() {
		
	}
	public Customer(int maKhachHang, String tenKhachHang, String soDienThoai, String cCCD) {
		MaKhachHang = maKhachHang;
		TenKhachHang = tenKhachHang;
		SoDienThoai = soDienThoai;
		CCCD = cCCD;
	}
	public int getMaKhachHang() {
		return MaKhachHang;
	}
	public void setMaKhachHang(int maKhachHang) {
		MaKhachHang = maKhachHang;
	}
	public String getTenKhachHang() {
		return TenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		TenKhachHang = tenKhachHang;
	}
	public String getSoDienThoai() {
		return SoDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		SoDienThoai = soDienThoai;
	}
	public String getCCCD() {
		return CCCD;
	}
	public void setCCCD(String cCCD) {
		CCCD = cCCD;
	}
	
	
	
	
}
