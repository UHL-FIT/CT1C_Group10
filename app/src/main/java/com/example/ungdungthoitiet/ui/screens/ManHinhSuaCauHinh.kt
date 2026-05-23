package com.example.ungdungthoitiet.ui.screens

import androidx.compose.foundation.layout.* // Import các thành phần bố cục như Column, Row, Spacer
import androidx.compose.foundation.selection.selectable // Import để tạo khả năng chọn cho thành phần
import androidx.compose.foundation.selection.selectableGroup // Import để nhóm các thành phần chọn lại với nhau
import androidx.compose.material3.* // Import các thành phần giao diện Material Design 3
import androidx.compose.runtime.Composable // Import annotation định nghĩa hàm giao diện
import androidx.compose.ui.Alignment // Import để căn chỉnh các thành phần
import androidx.compose.ui.Modifier // Import để tùy chỉnh thuộc tính của thành phần
import androidx.compose.ui.semantics.Role // Import định nghĩa vai trò của thành phần (như RadioButton)
import androidx.compose.ui.text.font.FontWeight // Import để định dạng độ đậm của chữ
import androidx.compose.ui.unit.dp // Import đơn vị đo mật độ điểm ảnh
import androidx.compose.ui.unit.sp // Import đơn vị đo kích thước chữ
import androidx.compose.ui.tooling.preview.Preview // Import để tạo xem trước giao diện
import com.example.ungdungthoitiet.data.TrangThaiUiThoiTiet // Import trạng thái giao diện tổng quát

/**
 * Hàm giao diện màn hình Sửa & Cấu hình của ứng dụng.
 * Cho phép người dùng bật/tắt chế độ xóa và thay đổi đơn vị đo nhiệt độ.
 */
@Composable
fun ManHinhSuaCauHinh(
    trangThai: TrangThaiUiThoiTiet, // Biến trạng thái chứa toàn bộ dữ liệu cấu hình
    onDaoCheDoXoa: () -> Unit, // Sự kiện khi bấm nút đảo ngược chế độ sửa danh sách
    onThayDoiDonVi: (String) -> Unit, // Sự kiện khi bấm chọn thay đổi đơn vị nhiệt độ
    onQuayLai: () -> Unit // Sự kiện khi bấm nút quay lại màn hình chính
) {
    // Khối dọc chính sắp xếp các thành phần từ trên xuống dưới
    Column(
        modifier = Modifier
            .fillMaxSize() // Chiếm toàn bộ chiều rộng và chiều cao màn hình
            .padding(16.dp), // Cách lề xung quanh 16 đơn vị dp
        horizontalAlignment = Alignment.CenterHorizontally // Căn giữa các thành phần theo chiều ngang
    ) {
        // --- Dòng văn bản tiêu đề trang lớn ---
        Text(
            text = "Màn hình Sửa & Cấu hình", // Nội dung tiêu đề
            fontSize = 24.sp, // Cỡ chữ lớn 24 đơn vị sp
            fontWeight = FontWeight.Bold, // Định dạng chữ đậm
            modifier = Modifier.padding(vertical = 24.dp) // Cách trên và dưới 24 đơn vị dp
        )

        Spacer(modifier = Modifier.height(16.dp)) // Tạo khoảng cách nhỏ 16 đơn vị dp

        // --- Nút bấm 'Sửa danh sách' ---
        // Nội dung nút bấm thay đổi linh hoạt dựa trên trạng thái cheDoSuaDanhSach
        Button(
            onClick = onDaoCheDoXoa, // Gọi hàm xử lý khi click để đảo trạng thái
            modifier = Modifier.fillMaxWidth() // Chiếm toàn bộ chiều rộng hiện có
        ) {
            Text(
                text = if (trangThai.cheDoSuaDanhSach) {
                    "Tắt chế độ xóa danh sách" // Nhãn khi đang ở chế độ sửa
                } else {
                    "Bật chế độ sửa danh sách (Hiện nút xóa)" // Nhãn khi không ở chế độ sửa
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp)) // Tạo khoảng cách lớn 32 đơn vị dp

        // --- Khu vực cấu hình hiển thị nhiệt độ ---
        Column(
            modifier = Modifier.fillMaxWidth(), // Chiếm toàn bộ chiều rộng
            horizontalAlignment = Alignment.Start // Căn lề trái cho các thành phần bên trong
        ) {
            // Dòng văn bản hướng dẫn cấu hình
            Text(
                text = "Thay đổi đơn vị hiển thị nhiệt độ:", // Nội dung hướng dẫn
                fontSize = 18.sp, // Cỡ chữ 18 đơn vị sp
                fontWeight = FontWeight.Medium // Định dạng chữ đậm vừa
            )
            
            Spacer(modifier = Modifier.height(12.dp)) // Tạo khoảng cách nhỏ 12 đơn vị dp

            // Hàng ngang chứa hai nút chọn RadioButton đặt cạnh nhau
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Chiếm toàn bộ chiều rộng hàng
                    .selectableGroup(), // Nhóm các nút chọn để chỉ được chọn một cái
                verticalAlignment = Alignment.CenterVertically, // Căn giữa theo chiều dọc
                horizontalArrangement = Arrangement.spacedBy(24.dp) // Khoảng cách giữa các nút là 24 dp
            ) {
                // Thành phần chọn cho 'Độ C (°C)'
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa chữ và nút chọn
                    modifier = Modifier.selectable(
                        selected = (trangThai.donViNhietDo == "°C"), // Kiểm tra trạng thái hiện tại
                        onClick = { onThayDoiDonVi("°C") }, // Gọi hàm cập nhật khi click
                        role = Role.RadioButton // Khai báo vai trò là nút chọn Radio
                    )
                ) {
                    RadioButton(
                        selected = (trangThai.donViNhietDo == "°C"), // Trạng thái chọn của nút
                        onClick = null // Sự kiện click đã được xử lý ở hàng Row bao ngoài
                    )
                    Text(text = "Độ C (°C)", modifier = Modifier.padding(start = 8.dp)) // Nhãn cho nút chọn
                }

                // Thành phần chọn cho 'Độ F (°F)'
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Căn giữa chữ và nút chọn
                    modifier = Modifier.selectable(
                        selected = (trangThai.donViNhietDo == "°F"), // Kiểm tra trạng thái hiện tại
                        onClick = { onThayDoiDonVi("°F") }, // Gọi hàm cập nhật khi click
                        role = Role.RadioButton // Khai báo vai trò là nút chọn Radio
                    )
                ) {
                    RadioButton(
                        selected = (trangThai.donViNhietDo == "°F"), // Trạng thái chọn của nút
                        onClick = null // Sự kiện click đã được xử lý ở hàng Row bao ngoài
                    )
                    Text(text = "Độ F (°F)", modifier = Modifier.padding(start = 8.dp)) // Nhãn cho nút chọn
                }
            }
        }

        // Tạo khoảng trống co giãn để đẩy nút 'Quay lại' xuống dưới cùng
        Spacer(modifier = Modifier.weight(1f))

        // --- Nút bấm 'Quay lại Màn hình chính Dashboard' ---
        Button(
            onClick = onQuayLai, // Gọi hàm quay lại khi click
            modifier = Modifier
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng
                .padding(bottom = 16.dp) // Cách lề dưới 16 đơn vị dp
        ) {
            Text(text = "Quay lại Màn hình chính Dashboard") // Nhãn của nút quay lại
        }
    }
}

/**
 * Hàm hỗ trợ xem trước giao diện màn hình Sửa cấu hình trong Android Studio.
 */
@Preview(showBackground = true, name = "Xem trước Sửa cấu hình")
@Composable
fun XemTruocManHinhSuaCauHinh() {
    // Hiển thị màn hình với trạng thái mặc định
    ManHinhSuaCauHinh(
        trangThai = TrangThaiUiThoiTiet(donViNhietDo = "°C", cheDoSuaDanhSach = false),
        onDaoCheDoXoa = {},
        onThayDoiDonVi = {},
        onQuayLai = {}
    )
}
