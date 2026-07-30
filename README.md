Câu 1:
Cơ chế Service Discovery:
  - Service Registration: Khi một Microservice (Order, Inventory) khởi chạy, nó tự động đăng ký tên dịch vụ (service-id), địa chỉ IP, và cổng (Port) với Service Registry (Eureka Server).
  - Service Lookup / Resolution: Khi API Gateway nhận request từ client, nó tra cứu danh bạ trên Eureka Server bằng IP/Port khả dụng và chuyển hướng request.
  - Heartbeat: Các service gửi tín hiệu heartbeat định kỳ về Eureka để xác nhận trạng thái hoạt động (Active/Down).
API Gateway không nên gọi trực tiếp IP/Port cứng (Hardcode)
  - Môi trường linh hoạt (Dynamic IPs): Trong kiến trúc Cloud, các instance của service có thể bị tắt, khởi động lại, hoặc mở rộng bất cứ lúc nào, khiến IP/Port bị thay đổi liên tục.
  - Tải động (Load Balancing): Nếu hardcode IP, Gateway sẽ không thể tự phân phối tải (Load Balance) sang nhiều instance khác nhau của cùng một service.
  - Khả năng bảo trì: Thay đổi hạ tầng và buộc phải cập nhật lại cấu hình (configuration file) của Gateway và redeploy hệ thống.
Câu 2:
  - Khởi chạy thêm nhiều instance của Order Service:
    + Chạy nhiều instance trên các cổng khác nhau (hoặc trên các Docker container khác nhau).
    + Mỗi instance mới khi chạy sẽ tự động đăng ký thông tin IP/Port của mình lên Eureka Server dưới cùng một tên ứng dụng (Ví dụ: ORDER-SERVICE).
  - Cơ chế Load Balancing tự động từ Gateway:
    + Trong Spring Cloud Gateway, cấu hình route sử dụng URI dạng: lb://ORDER-SERVICE.
    + Tiền tố lb:// kích hoạt client-side load balancer. Gateway sẽ tự động lấy danh sách IP từ Eureka Server và phân phối request đến các instance mới mở rộng mà không cần sửa lại file application.yml hay code của Gateway   
   
Câu 3:
  - Open Feign: 
    + Cơ chế hoạt động: Request/Response trực tiếp từ Order Service sang Inventory Service.
    + Ưu điểm: Đơn giản, dễ cài đặt và kiểm thử. Phản hồi tức thì: Trả về kết quả thành công/ thất bại cho client ngay lập tức. Đảm bảo tính nhất quản dữ liệu cao.
    + Nhược điểm: Sự phụ thuộc cao: Nếu inventory service bị sập, order service sẽ bị lỗi theo. Thời gian phản hồi cao hơn do phải chờ Inventory Service xử lý xong. Khó mở rộng khi có nhiều service cùng phụ thuộc vào luồng order
    + Phù hợp khi cần kiểm tra tồn kho tức thì trước khi tạo đơn hàng để báo ngay cho người dùng
  - Kafka:
    + Cơ chế hoạt động: Order Service đẩy sự kiện lên Topic, Inventory Service lắng nghe và xử lý.
    + Ưu điểm: Phân tách hoàn toàn giữa các service. Khả năng chịu tải cực cao, tránh làm theo Order Service khi traffic lớn. Nếu Inventory Service bị lỗi, sự kiện vẫn lưu giữ trên Kafka để xử lý sau.
    + Nhược điểm: Phức tạp trong việc cài đặt, cấu hình và quản lý hạ tầng. Dữ liệu nhất quán sau -> cần xử lý các case phức tạp như hoàn tiền/ hủy đơn nếu hết hàng. Khó phản hồi kết quả trực tiếp cho Client ngay lập tức.
    + Phù hợp cho hệ thống có lượng truy cập lớn, cho phép tạo đơn trạng thái PENDING trước, sau đó trừ kho và cập nhật đơn thành COMPLETED hoặc CANCELLED.
