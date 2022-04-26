package Model;

public class CategoryRoom {
	private int MaLoaiPhong;
	private String TenLoaiPhong;
	
	public CategoryRoom() {
		
	}

	public CategoryRoom(int maLoaiPhong, String tenLoaiPhong) {
		
		MaLoaiPhong = maLoaiPhong;
		TenLoaiPhong = tenLoaiPhong;
	}

	public int getMaLoaiPhong() {
		return MaLoaiPhong;
	}

	public void setMaLoaiPhong(int maLoaiPhong) {
		MaLoaiPhong = maLoaiPhong;
	}

	public String getTenLoaiPhong() {
		return TenLoaiPhong;
	}

	public void setTenLoaiPhong(String tenLoaiPhong) {
		TenLoaiPhong = tenLoaiPhong;
	}
	
}
