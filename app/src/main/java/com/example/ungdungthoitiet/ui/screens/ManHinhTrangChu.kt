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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ungdungthoitiet.data.DuLieuThoiTiet
import com.example.ungdungthoitiet.data.TrangThaiUiThoiTiet

@Composable
fun ManHinhTrangChu(
    trangThai: TrangThaiUiThoiTiet,
    onChonThanhPho: (DuLieuThoiTiet) -> Unit,
    onMoBangSuaCauHinh: () -> Unit,
    onBatCheDoSua: () -> Unit,
    onXongSuaDanhSach: () -> Unit,
    onBamXoaThanhPho: (String) -> Unit,
    onChuyenTimKiem: () -> Unit,
    onChuyenGioiThieu: () -> Unit,
    onDoiNhietDo: (String) -> Unit,
    onDoiGio: (String) -> Unit,
    onDoiLuongMua: (String) -> Unit,
    onDoiKhoangCach: (String) -> Unit,
    onDongBangSua: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            ThanhCongCuTrangChu(
                dangSua = trangThai.cheDoSuaDanhSach,
                onChuyenTimKiem = onChuyenTimKiem,
                onMoCaiDat = onMoBangSuaCauHinh,
                onBatSua = onBatCheDoSua,
                onXongSua = onXongSuaDanhSach
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(trangThai.danhSachThanhPho) { thanhPho ->
                    TheThoiTiet(
                        thanhPho = thanhPho,
                        donViNhietDo = trangThai.donViNhietDo,
                        donViGio = trangThai.donViGio,
                        dangSua = trangThai.cheDoSuaDanhSach,
                        onClick = { onChonThanhPho(thanhPho) },
                        onXoa = { onBamXoaThanhPho(thanhPho.tenThanhPho) }
                    )
                }
            }

            Button(
                onClick = onChuyenGioiThieu,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp,bottom = 20.dp)
            ) {
                Text("Về chúng tôi")
            }
        }

        if (trangThai.hienBangSuaCauHinh) {
            BangCaiDat(
                trangThai = trangThai,
                onDoiNhietDo = onDoiNhietDo,
                onDoiGio = onDoiGio,
                onDoiLuongMua = onDoiLuongMua,
                onDoiKhoangCach = onDoiKhoangCach,
                onDong = onDongBangSua
            )
        }
    }
}

@Composable
private fun ThanhCongCuTrangChu(
    dangSua: Boolean,
    onChuyenTimKiem: () -> Unit,
    onMoCaiDat: () -> Unit,
    onBatSua: () -> Unit,
    onXongSua: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onChuyenTimKiem) {
            Text("Tìm kiếm thành phố")
        }

        if (dangSua) {
            Button(
                onClick = onXongSua,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Xong", color = Color.White)
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = onMoCaiDat) { Text("Cài đặt") }
                Button(onClick = onBatSua) { Text("Sửa") }
            }
        }
    }
}

@Composable
private fun TheThoiTiet(
    thanhPho: DuLieuThoiTiet,
    donViNhietDo: String,
    donViGio: String,
    dangSua: Boolean,
    onClick: () -> Unit,
    onXoa: () -> Unit
) {
    val isFahrenheit = donViNhietDo == "°F"
    fun toDisp(t: Int) = if (isFahrenheit) (t * 9 / 5) + 32 else t

    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Vị trí: ${thanhPho.tenThanhPho}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                if (dangSua) {
                    Button(onClick = onXoa, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text("Xóa", color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Trạng thái: ${thanhPho.trangThai}", fontSize = 15.sp)
            Text("Nhiệt độ: ${toDisp(thanhPho.nhietDo)}$donViNhietDo", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Cao nhất: ${toDisp(thanhPho.nhietDoCaoNhat)}°", color = Color(0xFFD32F2F))
                Text("Thấp nhất: ${toDisp(thanhPho.nhietDoThapNhat)}°", color = Color(0xFF1976D2))
            }
            
            Spacer(Modifier.height(4.dp))
            Text("Độ ẩm: ${thanhPho.doAm}% | Gió: ${thanhPho.tocDoGio.toInt()} $donViGio", fontSize = 13.sp, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun XemTruocManHinhTrangChu() {
    val mau = DuLieuThoiTiet(
        "Hà Nội", 30, "Mây rải rác", 70, 20.0, 1012, 33, 26,
        listOf("Bây giờ - 30°C"),
        listOf("23/5 - 30°C")
    )
    ManHinhTrangChu(
        trangThai = TrangThaiUiThoiTiet(danhSachThanhPho = listOf(mau)),
        onChonThanhPho = {}, onMoBangSuaCauHinh = {}, onBatCheDoSua = {}, onXongSuaDanhSach = {},
        onBamXoaThanhPho = {}, onChuyenTimKiem = {}, onChuyenGioiThieu = {}, onDoiNhietDo = {},
        onDoiGio = {}, onDoiLuongMua = {}, onDoiKhoangCach = {}, onDongBangSua = {}
    )
}
