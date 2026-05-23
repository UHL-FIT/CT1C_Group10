package com.example.ungdungthoitiet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ungdungthoitiet.data.DuLieuThoiTiet
import com.example.ungdungthoitiet.data.TrangThaiUiThoiTiet

@Composable
fun ManHinhTimKiem(
    trangThai: TrangThaiUiThoiTiet,
    onGocChuThayDoi: (String) -> Unit,
    onChonGoiY: (String) -> Unit,
    onXacNhanThem: (DuLieuThoiTiet) -> Unit,
    onBamNutHuyBo: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Text(text = "Tìm kiếm thành phố", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = trangThai.chuoiTimKiemHienTai,
                onValueChange = { onGocChuThayDoi(it) },
                label = { Text("Nhập tên thành phố...") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onBamNutHuyBo) {
                Text("Hủy")
            }
        }

        if (trangThai.dangTaiDuLieu) {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            }
        }

        // Hiển thị danh sách gợi ý
        if (trangThai.danhSachGoiYTimKiem.isNotEmpty() && trangThai.thanhPhoXemTruoc == null) {
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                items(trangThai.danhSachGoiYTimKiem) { ten ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onChonGoiY(ten) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
                    ) {
                        Text(text = ten, modifier = Modifier.padding(16.dp), fontSize = 16.sp)
                    }
                }
            }
        }

        // Hiển thị kết quả xem trước sau khi chọn gợi ý
        if (trangThai.thanhPhoXemTruoc != null && !trangThai.dangTaiDuLieu) {
            val ketQua = trangThai.thanhPhoXemTruoc
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium).padding(16.dp)
            ) {
                Text(text = "THÔNG TIN TÌM THẤY", color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = ketQua.tenThanhPho, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Nhiệt độ: ${ketQua.nhietDo}${trangThai.donViNhietDo}", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Text(text = "Trạng thái: ${ketQua.trangThai}", fontSize = 16.sp)
                
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = { onGocChuThayDoi("") }, modifier = Modifier.weight(1f)) {
                        Text("Xóa")
                    }
                    Button(onClick = { onXacNhanThem(ketQua) }, modifier = Modifier.weight(1f)) {
                        Text("Thêm")
                    }
                }
            }
        }
    }
}
