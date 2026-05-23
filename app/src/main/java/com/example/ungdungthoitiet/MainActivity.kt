package com.example.ungdungthoitiet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ungdungthoitiet.ui.screens.*
import com.example.ungdungthoitiet.ui.theme.UngDungThoiTietTheme
import com.example.ungdungthoitiet.viewmodel.MoHinhXemThoiTiet

class MainActivity : ComponentActivity() {

    private val moHinhXemUi: MoHinhXemThoiTiet by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UngDungThoiTietTheme {
                val navController = rememberNavController()
                val uiState by moHinhXemUi.uiState.collectAsState()

                NavHost(navController = navController, startDestination = "man_hinh_chinh") {
                    
                    composable("man_hinh_chinh") {
                        ManHinhTrangChu(
                            trangThai = uiState,
                            onChonThanhPho = {
                                moHinhXemUi.chonThanhPhoXemChiTiet(it)
                                navController.navigate("man_hinh_chi_tiet")
                            },
                            onMoBangSuaCauHinh = { moHinhXemUi.hienThiBangSuaCauHinh(true) },
                            onBatCheDoSua = { moHinhXemUi.kichHoatCheDoSuaDanhSach() },
                            onXongSuaDanhSach = { moHinhXemUi.hoanThanhSuaDanhSach() },
                            onBamXoaThanhPho = { moHinhXemUi.xoaThanhPhoKhoiTrangChu(it) },
                            onChuyenTimKiem = { navController.navigate("man_hinh_tim") },
                            onChuyenGioiThieu = { navController.navigate("man_hinh_gioi_thieu") },
                            onDoiNhietDo = { moHinhXemUi.thayDoiDonViNhietDo(it) },
                            onDoiGio = { moHinhXemUi.thayDoiDonViGio(it) },
                            onDoiLuongMua = { moHinhXemUi.thayDoiDonViLuongMua(it) },
                            onDoiKhoangCach = { moHinhXemUi.thayDoiDonViKhoangCach(it) },
                            onDongBangSua = { moHinhXemUi.hienThiBangSuaCauHinh(false) }
                        )
                    }

                    composable("man_hinh_tim") {
                        ManHinhTimKiem(
                            trangThai = uiState,
                            onGocChuThayDoi = { moHinhXemUi.capNhatChuoiTimKiemVaLocGoiY(it) },
                            onChonGoiY = { moHinhXemUi.taiDuLieuThanhPhoChiTiet(it) },
                            onXacNhanThem = {
                                moHinhXemUi.themThanhPhoVaoTrangChu(it)
                                navController.popBackStack()
                            },
                            onBamNutHuyBo = {
                                moHinhXemUi.datLaiTrangThaiHuyBo()
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("man_hinh_chi_tiet") {
                        ManHinhChiTietThanhPho(
                            trangThai = uiState,
                            onQuayLai = { navController.popBackStack() },
                            onMoCaiDat = { moHinhXemUi.hienThiBangSuaCauHinh(true) },
                            onDoiNhietDo = { moHinhXemUi.thayDoiDonViNhietDo(it) },
                            onDoiGio = { moHinhXemUi.thayDoiDonViGio(it) },
                            onDoiLuongMua = { moHinhXemUi.thayDoiDonViLuongMua(it) },
                            onDoiKhoangCach = { moHinhXemUi.thayDoiDonViKhoangCach(it) },
                            onDongBangSua = { moHinhXemUi.hienThiBangSuaCauHinh(false) }
                        )
                    }

                    composable("man_hinh_gioi_thieu") {
                        ManHinhGioiThieuNhom(
                            onQuayLaiTrangChu = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
