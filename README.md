Câu 1:
Cơ chế Service Discovery:
  - Service Registration: Khi một Microservice (Order, Inventory) khởi chạy, nó tự động đăng ký tên dịch vụ (service-id), địa chỉ IP, và cổng (Port) với Service Registry (Eureka Server).
  - Service Lookup / Resolution: Khi API Gateway nhận request từ client, nó tra cứu danh bạ trên Eureka Server bằng IP/Port khả dụng và chuyển hướng request.
  - Heartbeat: Các service gửi tín hiệu heartbeat định kỳ về Eureka để xác nhận trạng thái hoạt động (Active/Down).
