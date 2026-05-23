package com.example.ungdungthoitiet.ui.screens

import androidx.compose.foundation.layout.* // Import các thành phần bố cục như Column, Row, Spacer
import androidx.compose.foundation.rememberScrollState // Import hàm để nhớ trạng thái cuộn của màn hình
import androidx.compose.foundation.verticalScroll // Import thuộc tính để tạo khả năng cuộn dọc
import androidx.compose.material3.* // Import các thành phần giao diện Material Design 3
import androidx.compose.runtime.Composable // Import annotation định nghĩa hàm giao diện
import androidx.compose.ui.Alignment // Import để căn chỉnh các thành phần
import androidx.compose.ui.Modifier // Import để tùy chỉnh thuộc tính của thành phần
import androidx.compose.ui.graphics.Color // Import lớp quản lý màu sắc
import androidx.compose.ui.text.font.FontWeight // Import để định dạng độ đậm của chữ
import androidx.compose.ui.text.style.TextAlign // Import để căn chỉnh văn bản
import androidx.compose.ui.unit.dp // Import đơn vị đo mật độ điểm ảnh
import androidx.compose.ui.unit.sp // Import đơn vị đo kích thước chữ
import androidx.compose.ui.tooling.preview.Preview // Import để tạo xem trước giao diện

/**
 * Hàm giao diện màn hình Giới thiệu nhóm và ứng dụng.
 * Đây là tệp giao diện dự phòng cho phép các thành viên nhóm dán mã nguồn sau.
 */
@Composable
fun ManHinhGioiThieuNhom(
    onQuayLaiTrangChu: () -> Unit // Sự kiện khi người dùng nhấn nút quay lại màn hình chính
) {
    // Khối dọc chính bao phủ toàn bộ màn hình và cho phép cuộn nội dung
    Column(
        modifier = Modifier
            .fillMaxSize() // Chiếm toàn bộ chiều rộng và chiều cao màn hình
            .verticalScroll(rememberScrollState()) // Kích hoạt khả năng cuộn dọc khi nội dung dài
            .padding(16.dp), // Cách lề xung quanh 16 đơn vị dp
        horizontalAlignment = Alignment.CenterHorizontally // Căn giữa các thành phần theo chiều ngang
    ) {
        // --- Văn bản tiêu đề ---
        Text(
            text = "Về chúng tôi - WeatherNow", // Nội dung tiêu đề trang
            fontSize = 24.sp, // Cỡ chữ lớn 24 đơn vị sp
            fontWeight = FontWeight.Bold, // Định dạng chữ đậm
            modifier = Modifier.padding(vertical = 16.dp) // Cách trên và dưới 16 đơn vị dp
        )

        // --- Biểu tượng ứng dụng bằng chữ ---
        Text(
            text = "☁️", // Biểu tượng đám mây bằng ký tự văn bản
            fontSize = 80.sp // Cỡ chữ cực lớn để làm nổi bật biểu tượng
        )
        
        // Tên ứng dụng và phiên bản
        Text(
            text = "WeatherNow Application v1.0", // Tên và phiên bản ứng dụng
            fontSize = 20.sp, // Cỡ chữ 20 đơn vị sp
            fontWeight = FontWeight.Medium // Định dạng chữ đậm vừa
        )

        Spacer(modifier = Modifier.height(32.dp)) // Tạo khoảng cách 32 đơn vị dp

        // --- Khung thẻ Card hiển thị lý do thiết kế ứng dụng (Requirement) ---
        Card(
            modifier = Modifier.fillMaxWidth(), // Chiều rộng tối đa theo màn hình
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Độ nổi bóng của thẻ
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Tiêu đề của phần yêu cầu (Requirement)
                Text(
                    text = "LÝ DO THIẾT KẾ ỨNG DỤNG (REQUIREMENT)", // Nội dung tiêu đề in đậm
                    fontWeight = FontWeight.Bold, // Định dạng chữ đậm
                    fontSize = 16.sp, // Cỡ chữ 16 đơn vị sp
                    modifier = Modifier.padding(bottom = 8.dp) // Cách phía dưới 8 đơn vị dp
                )
                // Đoạn văn bản thuyết minh lý do thiết kế
                Text(
                    text = "Thời tiết tại khu vực tỉnh Quảng Ninh nói riêng và cả nước nói chung ngày càng diễn biến phức tạp, biến đổi thất thường do hiện tượng biến đổi khí hậu toàn cầu. Ứng dụng WeatherNow được nghiên cứu phát triển nhằm cung cấp giải pháp tra cứu thông tin thời tiết trực quan, nhanh chóng theo thời gian thực, giúp người dùng chủ động bảo vệ sức khỏe và tối ưu hóa kế hoạch di chuyển, lao động hàng ngày.",
                    textAlign = TextAlign.Justify, // Căn đều hai bên văn bản
                    fontSize = 14.sp, // Cỡ chữ 14 đơn vị sp
                    lineHeight = 20.sp // Khoảng cách giữa các dòng văn bản
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // Tạo khoảng cách 24 đơn vị dp

        // --- Khung danh sách hiển thị phân chia thành viên nhóm ---
        Card(
            modifier = Modifier.fillMaxWidth(), // Chiều rộng tối đa
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Màu nền của thẻ
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Tiêu đề phần phân chia thành viên
                Text(
                    text = "HỆ THỐNG PHÂN CHIA THÀNH VIÊN NHÓM & ĐÓNG GÓP", // Nội dung tiêu đề in đậm
                    fontWeight = FontWeight.Bold, // Định dạng chữ đậm
                    fontSize = 16.sp, // Cỡ chữ 16 đơn vị sp
                    modifier = Modifier.padding(bottom = 12.dp) // Cách phía dưới 12 đơn vị dp
                )
                
                // Thông tin chi tiết 4 thành viên nhóm
                ThongTinThanhVien(hoTen = "Thành viên 01", vaiTro = "Nhóm trưởng - Code Logic", dongGop = "100%")
                ThongTinThanhVien(hoTen = "Thành viên 02", vaiTro = "Thiết kế UI/UX - Đồ họa", dongGop = "100%")
                ThongTinThanhVien(hoTen = "Thành viên 03", vaiTro = "Xử lý Dữ liệu - API", dongGop = "100%")
                ThongTinThanhVien(hoTen = "Thành viên 04", vaiTro = "Kiểm thử - Viết báo cáo", dongGop = "100%")
            }
        }

        Spacer(modifier = Modifier.height(32.dp)) // Tạo khoảng cách 32 đơn vị dp

        // --- Dòng chữ cuối trang hiển thị bản quyền ---
        Text(
            text = "GVHD: Thầy Vũ Duy Sơn | Trường Đại học Hạ Long", // Thông tin giảng viên hướng dẫn và trường
            fontSize = 14.sp, // Cỡ chữ 14 đơn vị sp
            color = MaterialTheme.colorScheme.outline, // Màu chữ nhạt để làm thông tin bản quyền
            textAlign = TextAlign.Center // Căn giữa văn bản
        )

        Spacer(modifier = Modifier.height(24.dp)) // Tạo khoảng cách 24 đơn vị dp

        // --- Nút bấm lớn rộng tối đa ở đáy ---
        Button(
            onClick = onQuayLaiTrangChu, // Gọi hàm quay lại màn hình chính khi click
            modifier = Modifier
                .fillMaxWidth() // Chiều rộng tối đa màn hình
                .padding(bottom = 16.dp) // Cách lề dưới 16 đơn vị dp
        ) {
            Text(
                text = "Quay lại Màn hình chính", // Nhãn hiển thị của nút
                fontSize = 16.sp, // Cỡ chữ 16 đơn vị sp
                fontWeight = FontWeight.Bold // Định dạng chữ đậm cho nút
            )
        }
    }
}

/**
 * Thành phần hỗ trợ hiển thị thông tin của một thành viên nhóm.
 * @param hoTen Họ và tên thành viên.
 * @param vaiTro Vai trò và nhiệm vụ đảm nhiệm.
 * @param dongGop Mức độ đóng góp cống hiến.
 */
@Composable
private fun ThongTinThanhVien(hoTen: String, vaiTro: String, dongGop: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        // Tên thành viên in đậm
        Text(text = hoTen, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        // Hàng ngang hiển thị vai trò và mức độ đóng góp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Đẩy hai thông tin về hai phía
        ) {
            Text(text = vaiTro, fontSize = 12.sp, color = Color.Gray) // Hiển thị vai trò
            Text(text = "Đóng góp: $dongGop", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary) // Hiển thị mức đóng góp
        }
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp), thickness = 0.5.dp) // Đường kẻ mờ phân tách
    }
}

/**
 * Hàm hỗ trợ xem trước giao diện màn hình Giới thiệu nhóm trong Android Studio.
 */
@Preview(showBackground = true, name = "Xem trước Giới thiệu")
@Composable
fun XemTruocManHinhGioiThieuNhom() {
    ManHinhGioiThieuNhom(onQuayLaiTrangChu = {})
}
