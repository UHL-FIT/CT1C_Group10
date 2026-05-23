package com.example.ungdungthoitiet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ungdungthoitiet.data.DuLieuThoiTiet
import com.example.ungdungthoitiet.data.TrangThaiUiThoiTiet

@Composable
fun ManHinhChiTietThanhPho(
    trangThai: TrangThaiUiThoiTiet,
    onQuayLai: () -> Unit,
    onMoCaiDat: () -> Unit,
    onDoiNhietDo: (String) -> Unit,
    onDoiGio: (String) -> Unit,
    onDoiLuongMua: (String) -> Unit,
    onDoiKhoangCach: (String) -> Unit,
    onDongBangSua: () -> Unit
) {
    val thanhPho = trangThai.thanhPhoDuocChon

    if (thanhPho != null) {
        val laDoF = trangThai.donViNhietDo == "°F"
        fun doiNhietDo(c: Int) = if (laDoF) (c * 9 / 5) + 32 else c

        fun layNhietDo(s: String): Int {
            return try {
                s.substringAfter("- ").substringBefore("°").trim().toInt()
            } catch (e: Exception) {
                0
            }
        }

        fun layBieuTuong(moTa: String): String {
            val d = moTa.lowercase()
            return when {
                d.contains("mưa") -> "🌧️"
                d.contains("mây") -> "☁️"
                d.contains("nắng") || d.contains("quang") -> "☀️"
                d.contains("dông") || d.contains("sét") -> "⛈️"
                else -> "☀️"
            }
        }

        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Chi tiết thời tiết",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = onMoCaiDat) {
                        Text("Cài đặt")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Khối thông tin chính
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    Text(text = "Vị trí: ${thanhPho.tenThanhPho}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${doiNhietDo(thanhPho.nhietDo)}${trangThai.donViNhietDo}",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "Trạng thái: ${thanhPho.trangThai}", fontSize = 16.sp)
                    Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(text = "Thấp nhất: ${doiNhietDo(thanhPho.nhietDoThapNhat)}°", color = Color(0xFF1976D2))
                        Text(text = "Cao nhất: ${doiNhietDo(thanhPho.nhietDoCaoNhat)}°", color = Color(0xFFD32F2F))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Dự báo 24h
                Text(text = "Dự báo trong 24 giờ", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(thanhPho.duBaoTheoGio) { item ->
                        val thoiGian = item.substringBefore(" -")
                        val moTa = item.substringAfter("(").substringBefore(")")
                        val nhietDo = doiNhietDo(layNhietDo(item))
                        Column(
                            modifier = Modifier
                                .background(Color(0xFFFAFAFA), shape = MaterialTheme.shapes.small)
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = thoiGian, fontSize = 12.sp, color = Color.Gray)
                            Text(text = layBieuTuong(moTa), fontSize = 24.sp)
                            Text(text = "${nhietDo}°", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Dự báo 5 ngày
                Text(text = "Dự báo 5 ngày tới", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    thanhPho.duBaoTheoTuan.forEach { item ->
                        val ngayThang = item.substringBefore(" -")
                        val moTa = item.substringAfter("(").substringBefore(")")
                        val nhietDo = doiNhietDo(layNhietDo(item))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFAFAFA), shape = MaterialTheme.shapes.small)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = ngayThang, modifier = Modifier.weight(1f))
                            Text(text = layBieuTuong(moTa), modifier = Modifier.padding(horizontal = 12.dp))
                            Text(text = "${nhietDo}${trangThai.donViNhietDo}", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Thông số bổ trợ
                Text(text = "Thông số bổ trợ", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                
                val tocDoGioHienThi = if (trangThai.donViGio == "mph") thanhPho.tocDoGio * 0.62137 else thanhPho.tocDoGio
                val luongMuaHienThi = if (trangThai.donViLuongMua == "inch") 5.0 / 25.4 else 5.0
                val khoangCachHienThi = if (trangThai.donViKhoangCach == "mi") 10.0 * 0.62137 else 10.0

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OThongSo("Độ ẩm", "${thanhPho.doAm}%", Modifier.weight(1f))
                        OThongSo("Tốc độ gió", "${String.format(java.util.Locale.US, "%.1f", tocDoGioHienThi)} ${trangThai.donViGio}", Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OThongSo("Lượng mưa", "${String.format(java.util.Locale.US, "%.2f", luongMuaHienThi)} ${trangThai.donViLuongMua}", Modifier.weight(1f))
                        OThongSo("Áp suất", "${thanhPho.apSuat} hPa", Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OThongSo("Tầm nhìn xa", "${String.format(java.util.Locale.US, "%.1f", khoangCachHienThi)} ${trangThai.donViKhoangCach}", Modifier.weight(1f))
                        Spacer(Modifier.weight(1f))
                    }
                }

                Button(
                    onClick = onQuayLai,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 16.dp)
                ) {
                    Text(text = "Quay lại màn hình chính ", fontSize = 15.sp)
                }
            }

            // Bảng cài đặt nổi (Overlay)
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
}

@Composable
private fun OThongSo(nhan: String, giaTri: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.small)
            .padding(12.dp)
    ) {
        Text(text = nhan, fontSize = 11.sp, color = Color.Gray)
        Text(text = giaTri, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun XemTruocChiTiet() {
    val mau = DuLieuThoiTiet("Hà Nội", 34, "Mây rải rác", 58, 5.0, 1005, 35, 27, listOf("Bây giờ - 34°C (mây)"), listOf("Ngày mai - 34°C (mây)"))
    ManHinhChiTietThanhPho(TrangThaiUiThoiTiet(thanhPhoDuocChon = mau), {}, {}, {}, {}, {}, {}, {})
}
