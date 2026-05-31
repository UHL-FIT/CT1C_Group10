package com.example.ungdungthoitiet.data

import com.example.ungdungthoitiet.data.remote.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class KhoDuLieuThoiTiet {

    private val maApi = "d63a62c66acd69ab3c89a169c0367cf6"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val openWeatherApi = retrofit.create(OpenWeatherApi::class.java)

    /**
     * Lấy dữ liệu thời tiết thực tế từ OpenWeather API sử dụng Retrofit.
     */
    suspend fun layThoiTiet(tenThanhPho: String): DuLieuThoiTiet = withContext(Dispatchers.IO) {
        try {
            // 1. Lấy thời tiết hiện tại
            val weatherResponse = openWeatherApi.getCurrentWeather(tenThanhPho, maApi)
            
            val nhietDoHienTai = weatherResponse.main.temp.toInt()
            val moTaHienTai = weatherResponse.weather.firstOrNull()?.description ?: ""
            val lechMuiGio = weatherResponse.timezone

            // 2. Lấy dự báo thời tiết
            val forecastResponse = openWeatherApi.getForecast(tenThanhPho, maApi)
            val danhSachDuBao = forecastResponse.list

            val duBaoTheoGio = mutableListOf<String>()
            val duBaoTheoTuan = mutableListOf<String>()

            // Thêm mốc hiện tại
            duBaoTheoGio.add("Bây giờ - ${nhietDoHienTai}°C ($moTaHienTai)")

            val thoiGianHienTai = System.currentTimeMillis() / 1000
            
            // Xử lý dự báo 24 giờ tiếp theo
            var soLuongGio = 0
            for (phanTu in danhSachDuBao) {
                val thoiGian = phanTu.dt
                if (thoiGian > thoiGianHienTai && soLuongGio < 4) {
                    val msDiaPhuong = (thoiGian + lechMuiGio) * 1000
                    val lich = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    lich.timeInMillis = msDiaPhuong
                    val chuoiThoiGian = String.format(Locale.US, "%02d:00", lich.get(Calendar.HOUR_OF_DAY))
                    val nhietDo = phanTu.main.temp.toInt()
                    val moTa = phanTu.weather.firstOrNull()?.description ?: ""
                    duBaoTheoGio.add("$chuoiThoiGian - ${nhietDo}°C ($moTa)")
                    soLuongGio++
                }
            }

            // Xử lý dự báo 5 ngày tới
            val lichHomNay = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            lichHomNay.timeInMillis = (thoiGianHienTai + lechMuiGio) * 1000
            val ngayHomNay = lichHomNay.get(Calendar.DAY_OF_YEAR)

            val danhSachNgayDaThem = mutableSetOf<Int>()
            
            // Bước 1: Ưu tiên mốc trưa
            for (phanTu in danhSachDuBao) {
                val thoiGian = phanTu.dt
                val lichPhanTu = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                lichPhanTu.timeInMillis = (thoiGian + lechMuiGio) * 1000
                
                val ngayPhanTu = lichPhanTu.get(Calendar.DAY_OF_YEAR)
                val gioPhanTu = lichPhanTu.get(Calendar.HOUR_OF_DAY)

                if (ngayPhanTu > ngayHomNay && !danhSachNgayDaThem.contains(ngayPhanTu) && gioPhanTu >= 12 && gioPhanTu <= 15) {
                    val chuoiNgayThang = "${lichPhanTu.get(Calendar.DAY_OF_MONTH)}/${lichPhanTu.get(Calendar.MONTH) + 1}"
                    val nhietDo = phanTu.main.temp.toInt()
                    val moTa = phanTu.weather.firstOrNull()?.description ?: ""
                    duBaoTheoTuan.add("$chuoiNgayThang - ${nhietDo}°C ($moTa)")
                    danhSachNgayDaThem.add(ngayPhanTu)
                }
            }

            // Bước 2: Bổ sung ngày thiếu
            if (duBaoTheoTuan.size < 5) {
                for (phanTu in danhSachDuBao) {
                    val thoiGian = phanTu.dt
                    val lichPhanTu = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    lichPhanTu.timeInMillis = (thoiGian + lechMuiGio) * 1000
                    val ngayPhanTu = lichPhanTu.get(Calendar.DAY_OF_YEAR)

                    if (ngayPhanTu > ngayHomNay && !danhSachNgayDaThem.contains(ngayPhanTu)) {
                        val chuoiNgayThang = "${lichPhanTu.get(Calendar.DAY_OF_MONTH)}/${lichPhanTu.get(Calendar.MONTH) + 1}"
                        val nhietDo = phanTu.main.temp.toInt()
                        val moTa = phanTu.weather.firstOrNull()?.description ?: ""
                        duBaoTheoTuan.add("$chuoiNgayThang - ${nhietDo}°C ($moTa)")
                        danhSachNgayDaThem.add(ngayPhanTu)
                    }
                    if (duBaoTheoTuan.size >= 5) break
                }
            }

            // Tính nhiệt độ cao nhất/thấp nhất trong 24h
            var caoNhat = -999.0
            var thapNhat = 999.0
            val limit = minOf(8, danhSachDuBao.size)
            for (i in 0 until limit) {
                val nhietDo = danhSachDuBao[i].main.temp
                if (nhietDo > caoNhat) caoNhat = nhietDo
                if (nhietDo < thapNhat) thapNhat = nhietDo
            }

            DuLieuThoiTiet(
                tenThanhPho = tenThanhPho,
                nhietDo = nhietDoHienTai,
                trangThai = moTaHienTai,
                doAm = weatherResponse.main.humidity,
                tocDoGio = weatherResponse.wind.speed * 3.6,
                apSuat = weatherResponse.main.pressure,
                nhietDoCaoNhat = caoNhat.toInt(),
                nhietDoThapNhat = thapNhat.toInt(),
                duBaoTheoGio = duBaoTheoGio,
                duBaoTheoTuan = duBaoTheoTuan
            )
        } catch (e: Exception) {
            val thongBao = if (e.message?.contains("404") == true) "Không tìm thấy" else ""
            DuLieuThoiTiet(tenThanhPho, 0, thongBao, 0, 0.0, 0, 0, 0, emptyList(), emptyList())
        }
    }

    /**
     * Tìm kiếm gợi ý tên thành phố sử dụng Retrofit.
     */
    suspend fun timKiemThanhPho(tuKhoa: String): List<String> = withContext(Dispatchers.IO) {
        try {
            val geocodingResponse = openWeatherApi.getGeocoding(tuKhoa, 5, maApi)
            val danhSachGoiY = mutableListOf<String>()
            for (item in geocodingResponse) {
                val ten = item.name
                if (!danhSachGoiY.contains(ten)) {
                    danhSachGoiY.add(ten)
                }
            }
            danhSachGoiY
        } catch (e: Exception) {
            emptyList()
        }
    }
}
