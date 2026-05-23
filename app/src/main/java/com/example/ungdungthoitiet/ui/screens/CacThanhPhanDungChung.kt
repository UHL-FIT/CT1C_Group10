package com.example.ungdungthoitiet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ungdungthoitiet.data.TrangThaiUiThoiTiet

@Composable
fun BangCaiDat(
    trangThai: TrangThaiUiThoiTiet,
    onDoiNhietDo: (String) -> Unit,
    onDoiGio: (String) -> Unit,
    onDoiLuongMua: (String) -> Unit,
    onDoiKhoangCach: (String) -> Unit,
    onDong: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f).background(Color.White).padding(16.dp)
        ) {
            Text("Bảng tùy chỉnh", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))

            ThanhChonDonVi("Nhiệt độ", "°C", "°F", trangThai.donViNhietDo, onDoiNhietDo)
            ThanhChonDonVi("Tốc độ gió", "km/h", "mph", trangThai.donViGio, onDoiGio)
            ThanhChonDonVi("Lượng mưa", "mm", "inch", trangThai.donViLuongMua, onDoiLuongMua)
            ThanhChonDonVi("Khoảng cách", "km", "mi", trangThai.donViKhoangCach, onDoiKhoangCach)

            Spacer(Modifier.height(16.dp))
            Button(onClick = onDong, modifier = Modifier.fillMaxWidth()) {
                Text("Đóng")
            }
        }
    }
}

@Composable
fun ThanhChonDonVi(
    tieuDe: String,
    chon1: String,
    chon2: String,
    giaTriHienTai: String,
    onThayDoi: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(tieuDe)
        Row(modifier = Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
            OptionRadio(chon1, giaTriHienTai == chon1) { onThayDoi(chon1) }
            Spacer(Modifier.width(32.dp))
            OptionRadio(chon2, giaTriHienTai == chon2) { onThayDoi(chon2) }
        }
    }
}

@Composable
fun OptionRadio(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(selected = selected, onClick = onClick, role = Role.RadioButton)
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(text)
    }
}
