package com.example.ungdungthoitiet.data

// Lớp dữ liệu đại diện cho thông tin thời tiết của một thành phố theo tài liệu bài 3A-1
data class DuLieuThoiTiet(
    val tenThanhPho: String,      // Tên thành phố (Kiểu chuỗi)
    val nhietDo: Int,             // Nhiệt độ (Kiểu số nguyên)
    val trangThai: String,         // Trạng thái thời tiết (Kiểu chuỗi)
    val doAm: Int,                // Độ ẩm không khí (Kiểu số nguyên)
    val tocDoGio: Double,         // Tốc độ gió thổi (Kiểu số thực)
    val apSuat: Int,              // Áp suất khí quyển (Kiểu số nguyên)
    val nhietDoCaoNhat: Int,      // Nhiệt độ cao nhất (Kiểu số nguyên)
    val nhietDoThapNhat: Int,     // Nhiệt độ thấp nhất (Kiểu số nguyên)
    val duBaoTheoGio: List<String>, // Danh sách dự báo theo giờ (Kiểu chuỗi)
    val duBaoTheoTuan: List<String> // Danh sách dự báo theo tuần (Kiểu chuỗi)
)

// Lớp lưu trữ trạng thái giao diện người dùng theo kiến trúc dữ liệu chảy một chiều (UDF) của bài 4A-1
data class TrangThaiUiThoiTiet(
    val danhSachThanhPho: List<DuLieuThoiTiet> = emptyList(), // Danh sách hiển thị ở Trang chủ
    val thanhPhoDuocChon: DuLieuThoiTiet? = null,             // Xem chi tiết (nullable)
    val thanhPhoXemTruoc: DuLieuThoiTiet? = null,             // Xem trước tìm kiếm (nullable)

    // Cấu hình các đơn vị đo mở rộng theo yêu cầu của bạn
    val donViNhietDo: String = "°C",   // Đơn vị nhiệt độ
    val donViGio: String = "km/h",     // Đơn vị tốc độ gió
    val donViLuongMua: String = "mm",   // Đơn vị lượng mưa
    val donViKhoangCach: String = "km", // Đơn vị khoảng cách

    val cheDoSuaDanhSach: Boolean = false,   // Trạng thái nút bấm sửa danh sách / Hiện nút xóa và nút Xong
    val hienBangSuaCauHinh: Boolean = false, // Trạng thái bảng cài đặt con mở đè lên trang chủ
    val danhSachGoiYTimKiem: List<String> = emptyList(), // Danh sách tên thành phố gợi ý
    val chuoiTimKiemHienTai: String = "",
    val thongBaoLoiNhapLieu: String? = null,
    val dangTaiDuLieu: Boolean = false
)