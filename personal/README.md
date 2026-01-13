# Personal

Thanks: https://github.com/codingstella/personal-blog-website?tab=readme-ov-file

# 1. 🧰 Cài đặt môi trường

## 1.1. Tải và cài dặt JDK 8, thiết lập biến môi trường JAVA_HOME
- [Link tải JDK](https://www.oracle.com/asean/java/technologies/javase/javase8-archive-downloads.html)
- [Thiết lập biến môi trường](https://www.baeldung.com/java-home-on-windows-mac-os-x-linux)
- [Xem chi tiết hơn tại](https://youngmonkeys.org/ezyplatform/guides/install-ezyplatform?lang=vi)

## 1.2. Cài đặt Mysql
[Link MySQL](https://dev.mysql.com/downloads/)
* Cài đặt cả MySQL Workbench để quản trị cơ sở dữ liệu.
* Cần nhớ tài khoản, mật khẩu root để sử dụng cho bước sau.

## 1.3. Tải gói cài đặt Ezyplatform
[Link tải](https://ezyplatform.com/)
* Tải cả Platform và SDK
* Giải nén Platform mở tệp <pre>settings/setup.properties</pre> sửa đổi thông tin kết nối đến database.

## 1.4. Cài đặt Ezyplatform thành công
1. Di chuyển vào thư mục Ezyplatform, mở terminal tại thư mục này.
2. Chạy lệnh:
    - với Linux/MacOS `bash cli.sh "console admin"`
    - với Windows `.\cli.bat "console admin"`
Nếu thấy hiển thị **EZYHTTP READY** là đã cài đặt thành công. Nếu có ngoại lệ xảy ra, nghĩa là cấu hình sai datasource
3. Truy cập URL: [http://localhost:9090/setup-admin](http://localhost:9090/setup-admin)
Cấu hình tài khoản super admin - tải khoản quản trị sau này
4. Dừng Ezyplatfrom
    - Đối với Linux/MacOS: chạy lệnh: `bash cli.sh stop`
    - Đối với Windows: Đóng tất cả cửa sổ cmd.

## 1.5. Thiết lập biến môi trường EZYPLATFORM_HOME
Làm theo hướng dẫn ở phàn 2.3 trong link sau: [Link](https://youngmonkeys.org/ezyplatform/guides/install-ezyplatform?lang=vi)

## 1.6. Cài đặt EzyPlatform SDK và EzyPlatform Development
1. Thiết lập biến môi trường EZYPLATFORM_SDK. Sử dụng SDK đã tải ở 1.3, cài đặt theo phần 2 trong [Link](https://youngmonkeys.org/ezyplatform/guides/install-ezyplatform-sdk?lang=vi)
2. Cài đặt apache maven [Link](https://maven.apache.org/install.html)
3. Mở terminal ở thử mục ezyplatform-development tải ở bước 1.3
    - Đối với Linux/MacOS: chạy lệnh `bash build.sh`
    - Đối với Windows: chạy lệnh `build.bat`

## 1.7. Tạo project với Ezyplatform
1. Mở terminal tại folder đặt dự án. Chạy lệnh tạo dự án:
    - Đối với Linux/MacOS: sử dụng ezy.sh create-project
    - Đối với Windows: sử dụng ezy.bat create-project
    * Chi tiết lệnh xem ở phần 4 trong [Link](https://youngmonkeys.org/ezyplatform/guides/ezyplatform-sdk-commands?lang=vi)
2. Mở project vừa tạo bằng IDE [Intellij](https://www.jetbrains.com/idea/download/other.html)
    - Thiết lập JDK 8 ở Intellij ở Main Menu - Project Structure. Trong phần Project Settings, SDK chọn JDK8 đã cài đặt ở 1.1
    - Lưu ý lỗi IntelliJ không nhân path variable [Link](https://youngmonkeys.org/ezyplatform/guides/fix-intellij-not-recognizing-path-variable?lang=vi)
    - Click biểu tượng Maven ở menu bên phải, trong tên project, thư mục Lifecycle, chạy install
    - Tại phần Project của Intellij, vào thư theme của project (tên theo format là project-name-theme), mở file:
`src/test/java.com.(tên package và project).web.test/(Tên project)TestThemeStartupTest`
click chuột phải vào dòng bất kỳ trong file vừa mở, chạy Run hoặc Debug. Khi chạy thành công terminal sẽ lên dòng EZYHTTP READY.
    - Mờ trình duyệt truy cập vào localhost:8080. Nếu lên vòng tròn may mắn là bạn đã khởi chạy dự án thành công.
    - Sau khi chạy thành công menu trên cùng của Intellij xuất hiện file ThemeStartupTest đang run/debug. Click vào đó chọn Edit Configurations
sửa Working directory thành folder **Theme** của dự án.
    - Rerun/Redebug để project nhận config mới.
    - Sửa nội dung file home.html ở thư mục `theme/src/resources/templates/home.html`, save file và load lại trang http://localhost:8080 bạn sẽ thấy thay đổi  
*Sửa lại Working directory ở bước trên để load lại thay đổi dễ dàng ở front-end*

# 2. ⚙️ Cấu hình dự án
1. Clone dự án personal về máy của bạn
2. Copy query ở `personal-admin-plugin/src/main/resources/scripts/scripts.sql`
3. Mở MySQL Workbench để thêm bảng:
- Chạy query
  
```sql

use ezyplatform;

CREATE TABLE IF NOT EXISTS `personal_post_word_counts` (
    `post_id` bigint unsigned NOT NULL,
    `word_count` bigint NOT NULL DEFAULT 0,
    `updated_at` datetime,
    PRIMARY KEY (`post_id`),
    INDEX `index_word_count_post_id` (`word_count`, `post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci; `

```
4. Làm tiếp tục theo bước 3 phần 1.7 với project personal

# 3. ▶️ Chạy ứng dụng
- Truy cập http://localhost:8080 để xem trang home là trang blog
- Truy cập http://localhost:9090, menu Posts để tạo bài viết  
*Lưu ý cần chạy console admin hoặc chạy PersonalAdminPluginStartupTest thì mới truy cập được trang admin*

# 4. 📦 Deploy Ezyplatform trên Ubuntu
*Cần chuẩn bị VPS và tên miền, cài đặt 2 bản ghi DNS: @ và admin ở tên miền trỏ về IP Address của VPS  

## 4.1.  Tạo ssh key để truy cập nhanh vào server
- Mở terminal trên máy của bạn chạy lệnh `ssh-keygen`. Terminal sẽ hỏi nơi lưu key mặc định sẽ là `c/Users/<Tên_máy_tính>/.ssh/<tên_file_ssh>`
- Kết quả trong folder .ssh sẽ có 2 file: 1 là private key (không chia sẻ), 2 là public key file đuôi .pub
- Chạy lệnh `cat .\.ssh\<tên_file_key>.pub` hoặc mở file .pub bằng trình editor, **copy nội dung của public key** để add vào server, giúp truy cập nhanh bằng ssh không cần dùng mật khẩu để đăng nhập các lần sau.
- Chạy lệnh `ssh root@<IP Address>`tại lần truy cập đầu tiên, do chưa cài ssh server sẽ hỏi mật khẩu. Gõ mật khẩu để đăng nhập.
- Tạo file lưu ssh key trên server chạy lệnh: `mkdir -p ~/.ssh`
- Truy cập file lưu key `nano ~/.ssh/authorized_keys` paste public key đã copy ở máy local. Save file và thoát.
- Chạy lệnh `exit` để thoát khỏi server và thử đăng nhập lại `ssh root@<IP Address>` sẽ thấy server không hỏi mật khẩu nữa.
	
## 4.2. Cài đặt timezone trên server
- Tìm timezone: `timedatectl list-timezones | grep Ho` *Ho* là Hồ trong Hồ Chí Minh, sử dụng tên tương ứng, phân biệt hoa thường với timezone bạn muốn đặt
- Copy timezone tương ứng bạn muốn chọn chạy lệnh: `sudo timedatectl set-timezone Asia/Ho_Chi_Minh`
- Chạy lệnh `timedatectl` để xem kết quả.
	
## 4.3. Cài đặt tường lửa
- Làm theo phần 3.Cài đặt tường lửa trong link sau [Link](https://youngmonkeys.org/ezyplatform/guides/deploy-ezyplatform-on-ubuntu?lang=vi)
	
## 4.4. Cài đặt Mysql
- Làm theo phần 4 trong link sau [Link](https://youngmonkeys.org/ezyplatform/guides/deploy-ezyplatform-on-ubuntu?lang=vi)
- Cấu hình user trong mysql: 
	- Vào mysql `sudo mysql`
	- Thiết lập user: `ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '<new password>';`
	- Thoát mysql: `exit`
	- Đăng nhập lại tài khoản root với mật khẩu vừa tạo: `mysql -u root -p`
	- Cài đặt đăng nhập qua socket: `ALTER USER 'root'@'localhost' IDENTIFIED WITH auth_socket;`. Sau đó thoát mysql `exit`
- Thiết lập bảo mật mysql: `sudo mysql_secure_installation`. Hệ thống sẽ đưa ra câu hỏi yes/no về các vấn đề sau:
	- Thiết lập mật khẩu cho root
	- Xóa người dùng ẩn danh
	- Chặn đăng nhập Root từ xa
	- Xóa cơ sở dữ liệu test
	- Cập nhật bảng đặc quyền
- Tạo database tên là ezyplatform (có thể đổi tên tùy ý)
``` sql
CREATE SCHEMA `ezyplatform` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
```
- Tạo người dùng:
``` sql
CREATE USER 'ezyplatform'@'localhost' IDENTIFIED BY '<password>';
```
- Gán quyền người dùng vừa tạo:
``` sql
GRANT ALL PRIVILEGES ON ezyplatform . * TO 'ezyplatform'@'localhost';
```
## 4.5. Cài đặt Nginx
### 4.5.1. Cài đặt
- Lệnh cài đặt
```
sudo apt update
sudo apt install nginx
```
- Kiểm tra xem Nginx đã hoạt động chưa: `systemctl status nginx`
### 4.5.2. Cấu hình
- Xóa file mặc định trong `/etc/nginx/sites-enabled` và `/etc/nginx/sites-available`, chạy lần lượt từng dòng lệnh:
```
cd /etc/nginx/sites-enabled
rm *
cd /etc/nginx/sites-available
rm *
```
- Di chuyển vào thư mực `/etc/nginx/sites-enabled`, mở nano và tạo 2 file cho site public và site admin. Chạy lần lượt các lệnh sau:
```
cd /etc/nginx/sites-enabled
nano
```
Sau khi mở nano, paste nội dung site public sau vào và lưu lại với tên file là tên miền. Tham khảo chi tiết hơn các cài đặt khác tại mục 7 trong [Link](https://youngmonkeys.org/ezyplatform/guides/deploy-ezyplatform-on-ubuntu?lang=vi) . Lưu ý đổi tên hostname:
```
server {
    server_name <host name>;

    location / {
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Host $http_host;
        proxy_pass "http://127.0.0.1:8080";
        client_max_body_size 50M;
    }
}
```
Lại mở nano, paste nội dung site admin sau và lưu lại với tên file là admin.tên_miền. Lưu ý đổi tên host name:
```
server {
    server_name admin.<host name>;

    location / {
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Host $http_host;
        proxy_pass "http://127.0.0.1:9090";
        client_max_body_size 100M;
    }

    location /api/v1/media/add {
        proxy_pass http://127.0.0.1:9090;
        proxy_http_version 1.1;
        proxy_set_header Host              $host;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-Forwarded-For   $proxy_add_x_forwarded_for;

        proxy_request_buffering off;
        proxy_buffering off;
        client_max_body_size 100M;
    }
}
```
- Kiểm tra cấu hình `sudo nginx -t`, nếu hiển thị như sau là thành công:
```
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
```
- Khởi động lại nginx: `sudo systemctl reload nginx`
## 4.6. Cài đặt certbot
- Cài đăt certbot
```
sudo apt install certbot python3-certbot-nginx
sudo certbot
```
Khi chạy `sudo certbot`, hệ thống sẽ hiển thị danh sách tên miền cần cài certbot, chọn số tương ứng với tên miền cả public và admin (chọn nhiều bằng cách nhập dãy số ngăn cách nhau bởi dấu phẩy)
- Sau khi cài đặt thành công, reload lại nginx: `sudo systemctl reload nginx`
## 4.7. Cài đặt JDK 8
## 4.8. Cài đặt ezyplatform
- Chạy 2 câu lệnh sau
```
sudo apt update
java -version
```
Hệ thống sẽ hiển thị ra danh sách cài đặt java:
```
apt install default-jre            
apt install openjdk-11-jre-headless
apt install openjdk-8-jre-headless 
apt install openjdk-9-jre-headless
```
Copy `apt install openjdk-8-jre-headless` và chạy lệnh tiếp tục.
- Cài đặt java home  
Chạy lệnh `nano ~\.bash_profile`  
Thêm dòng sau vào nội dung file: `export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64`. Save lại và thoát nano  
Chạy lệnh `source ~/.bash_profile`
## 4.9. Cài đặt ezyplatform
- Tài ezyplatform: `wget https://ezyplatform.com/api/v1/platforms/0.9.8/download && mv download ezyplatform.zip`
- Unzip file: `unzip ezyplatform.zip` để có folder ezyplatform trên server. Có thể đổi tên folder tùy thích. Lưu ý nếu chưa cài unzip cần chạy lệnh để cài: `apt install unzip`
- Thay đổi setting của ezyplatform (nếu đổi tên folder ezyplatform ở trên thì cần thay tên đúng khi cd):
```
cd ezyplatform
nano settings/setup.properties
```
Đổi thông tin db:
```
datasource.jdbc_url=jdbc:mysql://localhost:3306/<databaseName>
datasource.driver_class_name=com.mysql.cj.jdbc.Driver
datasource.username=<username>
datasource.password=<password>
tables.create_manually=false
```
- Chạy thử admin để check lỗi `bash cli.sh "console admin"` nếu hiển hiện **EZHTTP READY** là thành công. Thoát chế độ console `Ctrl + C`
- Start admin ở chế độ background: `bash cli.sh "start admin"`
- Start web ở chế độ background: `bash cli.sh "start web"`
- Sử dụng: `tail -f logs/admin-server.log` để theo dõi log admin, `tail -f logs/web-server.log` theo dõi log web
- Truy cập vào trang admin, thiết lập tài khoản, cập nhật Web URL, MAX_HEAP_SIZE ở admin để 256 để tiết kiệm ram server, chi tiết xem tại mục [Cài đặt ezyplatform](https://youngmonkeys.org/ezyplatform/guides/deploy-ezyplatform-on-ubuntu?lang=vi)
## 4.10. Cài đặt plugin, đưa dự án personal lên server
- Tại trang admin vào menu Plugins -> Web cài đặt plugin sau: **EzyArticle, EzySupport **
- Mở terminal trong thư mục personal chạy lệnh `export.bat` sau khi chạy xong sẽ có 1 file zip trong `personal/target/projects`
- Trong folder `personal-admin-plugin`,và `personal-theme` khai báo thêm phụ thuộc trong file `module.properties`:
```
dependencies=ezyarticle,ezysupport
```
- Vào trang admin, menu Themes, chọn Update Manually để up file zip lên server
