package com.example.ungdungthoitiet.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder
import java.util.*

class KhoDuLieuThoiTiet {

    private val apiKey = "d63a62c66acd69ab3c89a169c0367cf6"

    suspend fun layThoiTiet(tenThanhPho: String): DuLieuThoiTiet = withContext(Dispatchers.IO) {
        try {
            val encodedCity = URLEncoder.encode(tenThanhPho, "UTF-8")

            // 1. Lấy thời tiết hiện tại
            val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=$encodedCity&appid=$apiKey&units=metric&lang=vi"
            val weatherJson = JSONObject(URL(weatherUrl).readText())
            
            val main = weatherJson.getJSONObject("main")
            val weather = weatherJson.getJSONArray("weather").getJSONObject(0)
            val wind = weatherJson.getJSONObject("wind")
            val timezoneOffset = weatherJson.getLong("timezone")
            
            val currentTemp = main.getDouble("temp").toInt()
            val currentDesc = weather.getString("description")

            // 2. Lấy dự báo thời tiết
            val forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?q=$encodedCity&appid=$apiKey&units=metric&lang=vi"
            val forecastResponse = URL(forecastUrl).readText()
            val forecastJson = JSONObject(forecastResponse)
            val forecastList = forecastJson.getJSONArray("list")

            val duBaoTheoGio = mutableListOf<String>()
            val duBaoTheoTuan = mutableListOf<String>()

            // Thêm mốc hiện tại
            duBaoTheoGio.add("Bây giờ - ${currentTemp}°C ($currentDesc)")

            val currentTime = System.currentTimeMillis() / 1000
            
            // Xử lý dự báo 24 giờ tiếp theo
            var countGio = 0
            for (i in 0 until forecastList.length()) {
                val item = forecastList.getJSONObject(i)
                val dt = item.getLong("dt")
                if (dt > currentTime && countGio < 4) {
                    val localTimeMillis = (dt + timezoneOffset) * 1000
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = localTimeMillis
                    val timeStr = String.format(Locale.US, "%02d:00", calendar.get(Calendar.HOUR_OF_DAY))
                    val temp = item.getJSONObject("main").getDouble("temp").toInt()
                    val desc = item.getJSONArray("weather").getJSONObject(0).getString("description")
                    duBaoTheoGio.add("$timeStr - ${temp}°C ($desc)")
                    countGio++
                }
            }

            // Xử lý dự báo 5 ngày tới (Đảm bảo lấy đủ 5 ngày khác nhau)
            val calendarToday = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendarToday.timeInMillis = (currentTime + timezoneOffset) * 1000
            val todayDate = calendarToday.get(Calendar.DAY_OF_YEAR)

            val addedDays = mutableSetOf<Int>()
            
            // Bước 1: Thử lấy mốc 12h-15h cho mỗi ngày
            for (i in 0 until forecastList.length()) {
                val item = forecastList.getJSONObject(i)
                val dt = item.getLong("dt")
                val calendarItem = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendarItem.timeInMillis = (dt + timezoneOffset) * 1000
                
                val itemDay = calendarItem.get(Calendar.DAY_OF_YEAR)
                val itemHour = calendarItem.get(Calendar.HOUR_OF_DAY)

                if (itemDay > todayDate && !addedDays.contains(itemDay) && itemHour >= 12 && itemHour <= 15) {
                    val dateStr = "${calendarItem.get(Calendar.DAY_OF_MONTH)}/${calendarItem.get(Calendar.MONTH) + 1}"
                    val temp = item.getJSONObject("main").getDouble("temp").toInt()
                    val desc = item.getJSONArray("weather").getJSONObject(0).getString("description")
                    duBaoTheoTuan.add("$dateStr - ${temp}°C ($desc)")
                    addedDays.add(itemDay)
                }
            }

            // Bước 2: Nếu chưa đủ 5 ngày (do mốc cuối API không rơi vào 12h-15h), lấy bất kỳ mốc nào của ngày còn thiếu
            if (duBaoTheoTuan.size < 5) {
                for (i in 0 until forecastList.length()) {
                    val item = forecastList.getJSONObject(i)
                    val dt = item.getLong("dt")
                    val calendarItem = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendarItem.timeInMillis = (dt + timezoneOffset) * 1000
                    val itemDay = calendarItem.get(Calendar.DAY_OF_YEAR)

                    if (itemDay > todayDate && !addedDays.contains(itemDay)) {
                        val dateStr = "${calendarItem.get(Calendar.DAY_OF_MONTH)}/${calendarItem.get(Calendar.MONTH) + 1}"
                        val temp = item.getJSONObject("main").getDouble("temp").toInt()
                        val desc = item.getJSONArray("weather").getJSONObject(0).getString("description")
                        duBaoTheoTuan.add("$dateStr - ${temp}°C ($desc)")
                        addedDays.add(itemDay)
                    }
                    if (duBaoTheoTuan.size >= 5) break
                }
            }

            // Sắp xếp lại danh sách theo thời gian nếu cần (ở đây list API đã sắp xếp sẵn rồi)

            // Tính Max/Min từ dự báo 24h
            var maxTemp = -999.0
            var minTemp = 999.0
            for (i in 0 until minOf(8, forecastList.length())) {
                val temp = forecastList.getJSONObject(i).getJSONObject("main").getDouble("temp")
                if (temp > maxTemp) maxTemp = temp
                if (temp < minTemp) minTemp = temp
            }

            DuLieuThoiTiet(
                tenThanhPho = tenThanhPho,
                nhietDo = currentTemp,
                trangThai = currentDesc,
                doAm = main.getInt("humidity"),
                tocDoGio = wind.getDouble("speed") * 3.6,
                apSuat = main.getInt("pressure"),
                nhietDoCaoNhat = maxTemp.toInt(),
                nhietDoThapNhat = minTemp.toInt(),
                duBaoTheoGio = duBaoTheoGio,
                duBaoTheoTuan = duBaoTheoTuan
            )
        } catch (e: Exception) {
            val message = if (e.message?.contains("404") == true) "Không tìm thấy" else ""
            DuLieuThoiTiet(tenThanhPho, 0, message, 0, 0.0, 0, 0, 0, emptyList(), emptyList())
        }
    }

    /**
     * Tìm kiếm danh sách các thành phố gợi ý dựa trên từ khóa nhập vào.
     */
    suspend fun timKiemThanhPho(tuKhoa: String): List<String> = withContext(Dispatchers.IO) {
        try {
            val encodedQuery = URLEncoder.encode(tuKhoa, "UTF-8")
            val url = "https://api.openweathermap.org/geo/1.0/direct?q=$encodedQuery&limit=5&appid=$apiKey"
            val response = URL(url).readText()
            val jsonArray = org.json.JSONArray(response)
            
            val danhSachGoiY = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val name = obj.getString("name")
                // Chỉ lấy tên thành phố theo yêu cầu của bạn
                if (!danhSachGoiY.contains(name)) {
                    danhSachGoiY.add(name)
                }
            }
            danhSachGoiY
        } catch (e: Exception) {
            emptyList()
        }
    }
}
