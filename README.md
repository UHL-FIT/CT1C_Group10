# WeatherNow - Ứng dụng Thời tiết thông minh
Ứng dụng di động Android cung cấp thông tin thời tiết thời gian thực, dự báo theo giờ và theo ngày bằng cách khai thác dữ liệu từ OpenWeather API. Ứng dụng được xây dựng trên nền tảng Jetpack Compose, tuân thủ kiến trúc MVVM và đáp ứng các tiêu chí trong Biểu điểm chấm BTL LTTTBDD_v1.0.xlsx - Template.pdf.
---
## 1. Thông tin nhóm và thành viên
- Lớp: K9. CT1C - Nhóm 10
- Trường: Đại học Hạ Long
- Danh sách thành viên:
  + SV1 (Leader): Bùi Khánh Minh - MSV: 23DH201130
  + SV2: Nguyễn Xuân Tùng - MSV: 23DH201142 
  + SV3: Nguyễn Mạnh Hải - MSV: 23DH201118 
  + SV4: Trần Xuân Việt - MSV: 23DH201144
---
## 2. Đường dẫn tệp nộp kèm
- File APK chạy được: Tải trực tiếp tại mục Releases của kho lưu trữ GitHub này (Bản Latest Release)
---
## 3. Công nghệ và Kiến trúc áp dụng
- UI Layer: Xây dựng bằng Composable function, áp dụng Material 3 Theme thống nhất về màu sắc, font chữ và hình khối. Sử dụng thư viện Coil để tải ảnh trạng thái thời tiết từ URL.
- State Management: Áp dụng kiến trúc luồng dữ liệu một chiều (UDF), tách biệt logic trong ViewModel, sử dụng StateFlow giúp không mất dữ liệu khi xoay màn hình.
- Navigation: Điều hướng qua lại giữa các màn hình đúng chuẩn Navigation Compose, nút Back hoạt động hợp lý.
- Data Layer: Áp dụng Repository Pattern để tách biệt các nguồn dữ liệu.
  + Kết nối mạng: Dùng Retrofit và Kotlin Coroutines để gọi REST API từ OpenWeather.
  + Lưu trữ cục bộ: Dưới dạng Room Database cho dữ liệu offline và DataStore cho cài đặt ứng dụng.
---
## 4. Các tính năng đã hoàn thiện
- Tính năng Cơ bản:
  + Giao diện tự động cập nhật khi trạng thái dữ liệu (State) thay đổi, xử lý đúng các sự kiện click và nhập liệu của người dùng.
  + Ghi log hệ thống (Logcat) tối thiểu 4 callback của vòng đời Activity Lifecycle.
  + Sử dụng LazyColumn để hiển thị danh sách cuộn mượt mà các thành phố đang theo dõi.

- Tính năng Nâng cao:
  + Kết nối trực tiếp với API thực, có cơ chế hiển thị trạng thái Loading/Error rõ ràng khi tải dữ liệu.
  + Chế độ hoạt động ngoại tuyến (Offline Mode) bằng cách lưu dữ liệu vào Room, cho phép ứng dụng chạy được cả khi không có mạng.
  + Bảng tùy chỉnh cài đặt bằng DataStore để lưu và thay đổi các đơn vị đo (nhiệt độ, tốc độ gió, lượng mưa, khoảng cách) trên toàn ứng dụng.
---
## 5. Hướng dẫn cài đặt và chạy thử
- Cách 1: Cài đặt trực tiếp từ file APK (Dành cho Giảng viên chấm nhanh)
  + Bước 1: Vào mục Releases ở góc phải giao diện GitHub này, chọn bản Release mới nhất.
  + Bước 2: Tải file app-debug.apk đính kèm trong phần Assets của bản Release.
  + Bước 3: Kéo thả file APK vào trình giả lập (Emulator) hoặc cài trực tiếp trên điện thoại Android thật để trải nghiệm.

- Cách 2: Chạy từ mã nguồn bằng Android Studio
  + Bước 1: Mở thư mục mã nguồn ứng dụng bằng phần mềm Android Studio.
  + Bước 2: Đợi hệ thống đồng bộ xong cấu hình dự án thông qua Gradle Sync.
  + Bước 3: Kết nối thiết bị thật (đã bật Gỡ lỗi USB) hoặc mở máy ảo Android.
  + Bước 4: Trên thanh công cụ, nhấn nút Run App (hoặc tổ hợp phím Shift + F10) để biên dịch và cài đặt ứng dụng.
