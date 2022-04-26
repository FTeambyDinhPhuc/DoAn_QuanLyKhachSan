package Model;

import java.sql.Date;

public class Bill {
	private int MaHoaDon;
	private Date NgayLap;
	private float TongTien;
	
	public Bill() {
		
	}

	public Bill(int maHoaDon, Date ngayLap, float tongTien) {
		MaHoaDon = maHoaDon;
		NgayLap = ngayLap;
		TongTien = tongTien;
	}

	public int getMaHoaDon() {
		return MaHoaDon;
	}

	public void setMaHoaDon(int maHoaDon) {
		MaHoaDon = maHoaDon;
	}

	public Date getNgayLap() {
		return NgayLap;
	}

	public void setNgayLap(Date ngayLap) {
		NgayLap = ngayLap;
	}

	public float getTongTien() {
		return TongTien;
	}

	public void setTongTien(float tongTien) {
		TongTien = tongTien;
	}
	
	
	
	
	
	
}
