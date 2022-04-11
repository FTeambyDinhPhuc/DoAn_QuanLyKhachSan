package Model;

import java.sql.Date;

public class Service {
	private int MaDichVu;
	private String TenDichVu;
	private Date ThoiGianSuDung;
	private float GiaTien;
	public Service() {
	
	}
	public Service(int maDichVu, String tenDichVu, Date thoiGianSuDung, float giaTien) {
		MaDichVu = maDichVu;
		TenDichVu = tenDichVu;
		ThoiGianSuDung = thoiGianSuDung;
		GiaTien = giaTien;
	}
	public int getMaDichVu() {
		return MaDichVu;
	}
	public void setMaDichVu(int maDichVu) {
		MaDichVu = maDichVu;
	}
	public String getTenDichVu() {
		return TenDichVu;
	}
	public void setTenDichVu(String tenDichVu) {
		TenDichVu = tenDichVu;
	}
	public Date getThoiGianSuDung() {
		return ThoiGianSuDung;
	}
	public void setThoiGianSuDung(Date thoiGianSuDung) {
		ThoiGianSuDung = thoiGianSuDung;
	}
	public float getGiaTien() {
		return GiaTien;
	}
	public void setGiaTien(float giaTien) {
		GiaTien = giaTien;
	}
	
	
	
}
