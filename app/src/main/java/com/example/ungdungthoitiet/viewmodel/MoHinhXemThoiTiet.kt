package com.example.ungdungthoitiet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ungdungthoitiet.data.DuLieuThoiTiet
import com.example.ungdungthoitiet.data.KhoDuLieuThoiTiet
import com.example.ungdungthoitiet.data.TrangThaiUiThoiTiet
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoHinhXemThoiTiet : ViewModel() {

    private val khoDuLieu = KhoDuLieuThoiTiet()

    private val _trangThaiUi = MutableStateFlow(TrangThaiUiThoiTiet())
    val uiState: StateFlow<TrangThaiUiThoiTiet> = _trangThaiUi.asStateFlow()

    private var searchJob: Job? = null

    init {
        taiDuLieuThoiTietMacDinh()
    }

    private fun taiDuLieuThoiTietMacDinh() {
        viewModelScope.launch {
            try {
                _trangThaiUi.update { it.copy(dangTaiDuLieu = true) }
                val danhSachTen = listOf("Hà Nội", "Hải Phòng", "Đà Nẵng")
                val danhSachMoi = danhSachTen.map { khoDuLieu.layThoiTiet(it) }
                _trangThaiUi.update {
                    it.copy(danhSachThanhPho = danhSachMoi, dangTaiDuLieu = false)
                }
            } catch (e: Exception) {
                _trangThaiUi.update { it.copy(dangTaiDuLieu = false) }
            }
        }
    }

    /**
     * Lấy dữ liệu thời tiết chi tiết cho thành phố được chọn.
     */
    fun taiDuLieuThanhPhoChiTiet(tenThanhPho: String) {
        viewModelScope.launch {
            try {
                _trangThaiUi.update { it.copy(dangTaiDuLieu = true) }
                val duLieu = khoDuLieu.layThoiTiet(tenThanhPho)
                _trangThaiUi.update {
                    it.copy(
                        thanhPhoXemTruoc = duLieu,
                        danhSachGoiYTimKiem = emptyList(),
                        dangTaiDuLieu = false
                    )
                }
            } catch (e: Exception) {
                _trangThaiUi.update { it.copy(dangTaiDuLieu = false) }
            }
        }
    }

    /**
     * Tìm kiếm gợi ý tên thành phố khi người dùng gõ.
     */
    fun capNhatChuoiTimKiemVaLocGoiY(chuoiNhap: String) {
        _trangThaiUi.update { it.copy(chuoiTimKiemHienTai = chuoiNhap, thanhPhoXemTruoc = null) }
        searchJob?.cancel()
        
        if (chuoiNhap.length >= 2) {
            searchJob = viewModelScope.launch {
                delay(500)
                _trangThaiUi.update { it.copy(dangTaiDuLieu = true) }
                val goiY = khoDuLieu.timKiemThanhPho(chuoiNhap)
                _trangThaiUi.update { 
                    it.copy(danhSachGoiYTimKiem = goiY, dangTaiDuLieu = false) 
                }
            }
        } else {
            _trangThaiUi.update { it.copy(danhSachGoiYTimKiem = emptyList(), thongBaoLoiNhapLieu = null) }
        }
    }

    fun themThanhPhoVaoTrangChu(thanhPho: DuLieuThoiTiet) {
        val daTonTai = _trangThaiUi.value.danhSachThanhPho.any {
            it.tenThanhPho.trim().equals(thanhPho.tenThanhPho.trim(), ignoreCase = true)
        }

        if (!daTonTai) {
            _trangThaiUi.update { trang ->
                trang.copy(
                    danhSachThanhPho = trang.danhSachThanhPho + thanhPho,
                    thanhPhoXemTruoc = null,
                    chuoiTimKiemHienTai = "",
                    danhSachGoiYTimKiem = emptyList()
                )
            }
        } else {
            _trangThaiUi.update { 
                it.copy(thanhPhoXemTruoc = null, chuoiTimKiemHienTai = "", thongBaoLoiNhapLieu = "Đã có trong danh sách") 
            }
        }
    }

    fun xoaThanhPhoKhoiTrangChu(tenThanhPho: String) {
        _trangThaiUi.update { trang ->
            trang.copy(danhSachThanhPho = trang.danhSachThanhPho.filterNot { it.tenThanhPho == tenThanhPho })
        }
    }

    fun chonThanhPhoXemChiTiet(thanhPho: DuLieuThoiTiet) {
        _trangThaiUi.update { it.copy(thanhPhoDuocChon = thanhPho) }
    }

    fun hienThiBangSuaCauHinh(hienThi: Boolean) = _trangThaiUi.update { it.copy(hienBangSuaCauHinh = hienThi) }
    fun kichHoatCheDoSuaDanhSach() = _trangThaiUi.update { it.copy(cheDoSuaDanhSach = true) }
    fun hoanThanhSuaDanhSach() = _trangThaiUi.update { it.copy(cheDoSuaDanhSach = false) }
    fun thayDoiDonViNhietDo(donVi: String) = _trangThaiUi.update { it.copy(donViNhietDo = donVi) }
    fun thayDoiDonViGio(donVi: String) = _trangThaiUi.update { it.copy(donViGio = donVi) }
    fun thayDoiDonViLuongMua(donVi: String) = _trangThaiUi.update { it.copy(donViLuongMua = donVi) }
    fun thayDoiDonViKhoangCach(donVi: String) = _trangThaiUi.update { it.copy(donViKhoangCach = donVi) }

    fun datLaiTrangThaiHuyBo() {
        _trangThaiUi.update {
            it.copy(thanhPhoXemTruoc = null, chuoiTimKiemHienTai = "", danhSachGoiYTimKiem = emptyList(), thongBaoLoiNhapLieu = null)
        }
    }
}
