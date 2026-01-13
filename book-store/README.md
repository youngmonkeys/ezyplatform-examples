Book Store: Hướng dẫn import dự án vào IntelliJ IDEA
## Mục lục
- [1. Cài đặt môi trường](#1-cài-đặt-môi-trường)
- [2. Import dự án vào IDE](#2-import-dự-án-vào-ide)
- [3. Khởi chạy dự án thành công](#3-khởi-chạy-dự-án-thành-công)
- [4. Hướng dẫn export và deploy dự án Book Store](#4-hướng-dẫn-export-và-deploy-dự-án-Book-Store)

---

## 1. Cài đặt môi trường

### 1.1 Chuẩn bị
- **OS**: Windows (hướng dẫn trong tài liệu đang minh họa Windows).
- **JDK**: Java **8** (IntelliJ cấu hình SDK 1.8). 
- **Git**: để clone source.
- **IntelliJ IDEA**: (Community/Ultimate đều được, miễn import & run được).

### 1.2 Tải EzyPlatform (Development) và SDK

1) Clone source `ezyplatform-development`:
```bash
git clone https://github.com/youngmonkeys/ezyplatform-development.git
```
![git clone ezy develop](<docs/images/git_clone_ezy_develop.png>)

2) Tải **EzyPlatform** và **EzyPlatform SDK** tại:
- https://ezyplatform.com 
![ezy home](<docs/images/ezy_home.png>)

**Thực hiện download thành công EzyPlatform**
![folder](<docs/images/folder.png>)

---

### 1.3 Cấu hình biến môi trường (Windows)

#### Bước 1 — Thêm biến hệ thống
Mở:
`Control Panel → System and Security → System → Advanced system settings → Environment Variables`

Thêm 2 biến **System variables**: 
![system variables](<docs/images/system_variables.png>)

| Tên biến           | Giá trị (ví dụ)              |
| ------------------ | ---------------------------- |
| `EZYPLATFORM_HOME` | `D:\Project\ezyplatform`     |
| `EZYPLATFORM_SDK`  | `D:\Project\ezyplatform-sdk` |

#### Bước 2 — Thêm vào `Path`
Thêm:
```
%EZYPLATFORM_SDK%\bin
```
📸 **Ảnh minh họa**: cửa sổ Environment Variables và thêm SDK vào Path. ![system variable path](<docs/images/system_variable_path.png>)

#### Bước 3 — Kiểm tra biến môi trường
Mở CMD và chạy:
```bat
echo %EZYPLATFORM_HOME%
```
Nếu in ra đúng đường dẫn là OK. 
![check path](<docs/images/check_path.png>)

---

### 1.4 Cấu hình database (MySQL)

Mở file:
`settings/setup.properties` và cấu hình thông tin kết nối DB. 

📸 **Ảnh minh họa**: file `setup.properties` với `datasource.jdbc_url`, user/pass…  
![setup mysql](<docs/images/setup_mysql.png>)

---

### 1.5 Cấu hình Web & Admin

* Cấu hình Web: `web/settings/config.properties`  ![setup web](<docs/images/setup_web.png>)
* Cấu hình Admin: `admin/settings/config.properties` ![set up admin](<docs/images/set_up_admin.png>)

---

### 1.6 Build EzyPlatform

Chạy script build để đóng gói class thành JAR: 
```bat
build.bat
```

📸 **Ảnh minh họa**: console build chạy các bước đóng gói.  
![build bat](<docs/images/build_bat.png>)

---

## 2. Import dự án vào IDE

### 2.1 Tải project Book Store

Dùng sparse checkout để lấy folder `book-store`:
```bash
git clone --no-checkout https://github.com/youngmonkeys/ezyplatform-examples.git
cd ezyplatform-examples
git sparse-checkout init --cone
git sparse-checkout set book-store
git checkout main
```

📸 **Ảnh minh họa**: các lệnh sparse-checkout và thư mục `book-store` đã xuất hiện.  
![clone book store](<docs/images/clone_book_store.png>)

---

### 2.2 Mở project trong IntelliJ IDEA

1) Mở IntelliJ IDEA  
2) Chọn **Open**  
3) Trỏ tới thư mục `book-store`

📸 **Ảnh minh họa**: màn hình Welcome của IntelliJ và thao tác Open project.  
![import project](<docs/images/import_project.png>)

---

### 2.3 Chọn SDK Java 1.8 cho project

Vào:
`File → Project Structure → Project → SDK` chọn **1.8** 

📸 **Ảnh minh họa**: Project Structure chọn SDK 1.8.  
![Pasted image 20260109094159](<docs/images/Setup_project_structure.png>)

---

### 2.4 Thiết lập Path Variable trong IntelliJ

Vào:
`Settings → Path Variables` rồi thêm:

- Name: `EZYPLATFORM_HOME`
- Value: `D:\Project\ezyplatform` 

📸 **Ảnh minh họa**: IntelliJ Settings → Path Variables.  
![setup path variables](<docs/images/setup_path_variables.png>)

---

### 2.5 Cấu hình Maven trong IntelliJ

Vào:
`Settings → Build, Execution, Deployment → Build Tools → Maven → Importing\Runner`

Thiết lập VM Options:
```
-Denv.EZYPLATFORM_HOME=D:\Project\ezyplatform
``` 

📸 **Ảnh minh họa**: Maven Importing có VM Options.  
![setup maven import](<docs/images/setup_maven_import.png>)

Cũng ở maven setting, tìm đến Runner và bổ sung VM Options:
```
-Denv.EZYPLATFORM_HOME=/App/ezyplatform
```

Và add Environment variables: `EZYPLATFORM_HOME=/App/ezyplatform`

---


## 3. Khởi chạy dự án thành công

### 3.1 Chạy Admin Console

Khởi động **EzyPlatform Admin**. 
Sau khi chạy thành công, truy cập trang quản trị (Admin).

📸 **Ảnh minh họa**  
![run console admin](<docs/images/run_console_admin.png>)

---

### 3.2 Cài đặt Plugins cần thiết

Trong Admin Console, cài đặt các plugin sau:

```
ezyarticle
ezycommerce
ezypayment
ezysupport
ezymail
ezylogin
ezyaccount
ezyrating
ezymarketing
ezychat
ezycrm
```

Thao tác:
`Plugins → Web → Add New → Add → Active`

📸 **Ảnh minh họa**  
![add plugins ezy](<docs/images/add_plugins_ezy.png>)  
![add plugins ex](<docs/images/add_plugins_ex.png>)

---

### 3.3 Update và Link Plugins vào Book Store

1) Tắt EzyPlatform  
2) Chạy cập nhật:
```bat
update.bat
```
![run update bat](<docs/images/run_update_bat.png>)
3) Link các plugin:
![ezy link](<docs/images/ezy_link.png>)

Sau khi link:
- `pom.xml` được bổ sung dependency  
![file pom bookstore](<docs/images/file_pom_bookstore.png>)
- Các lớp `XxxStartupTest` được cập nhật `@ComponentScan`
![component scan](<docs/images/component_scan.png>)

---

### 3.4 Maven Install

Trong IntelliJ:
`Maven → Lifecycle → install`

📸 **Ảnh minh họa**  
![ezy maven install](<docs/images/ezy_maven_install.png>)
Thực hiện install thành công
![maven build success](<docs/images/maven_build_success.png>)

---

### 3.5 Cấu hình lớp Main (Run Configuration)

Vào:
`Run → Edit Configurations`
![Edit run Config](<docs/images/Edit__run_Config.png>)
- Add **VM Options**
![Modify run conf VM options](<docs/images/Modify_run_conf_VM_options.png>)
- Add **Environment Variables**
![Modify run conf Env](<docs/images/Modify_run-conf_Env.png>)

---

### 3.6 Kết quả chạy thành công

📸 **Ảnh minh họa**  
- Admin: hiển thị danh sách sản phẩm / sách  
![giao dien admin](<docs/images/giao_dien_admin.png>)

- Web: hiển thị danh sách sách theo category & keyword  
![Giao dien web](<docs/images/Giao dien web.png>)

### 3.7 Troubleshooting

#### Lỗi format code
- `Ctrl + Alt + L` → Reformat code

#### Lỗi import không dùng
- `Ctrl + Alt + O` → Optimize imports

📸 **Ảnh minh họa**  
![fix unimport](<docs/images/fix_unimport.png>)


---


## 4. Hướng dẫn export và deploy dự án Book Store

Phần này mô tả chi tiết các bước **export** source Book Store thành bản deploy
và **triển khai hệ thống lên server Linux (Ubuntu)** để vận hành thực tế.

---

### 4.1 Hướng dẫn export dự án

#### 4.1.1 Bổ sung dependency

Trước khi thực hiện export, cần đảm bảo project **Book Store đã được link đầy đủ plugin**
và các dependency tương ứng đã được cập nhật vào `pom.xml`.
Thực hiện chỉnh sửa file module.properties

📸 **Ảnh minh họa**: danh sách dependency sau khi link plugin.
![Bổ sung dependency](<docs/images/add-dependencies.png>)

---

#### 4.1.2 Thực hiện export dự án

Chạy script sau để build và đóng gói project:

```bat
export.bat
```

📸 **Ảnh minh họa**: quá trình chạy script export.
![Chạy export.bat](<docs/images/run-export-bat.png>)

Sau khi hoàn tất, thư mục export sẽ được sinh ra, chứa các file cần thiết để deploy.

📸 **Ảnh minh họa**: kết quả export thành công.
![Kết quả export](<docs/images/export-result.png>)

---

### 4.2 Hướng dẫn deploy dự án lên máy chủ

#### 4.2.1 Chuẩn bị môi trường

- **OS**: Ubuntu 20.04 hoặc 22.04 (khuyến nghị)
- **Domain**: ví dụ `bookstore.com`
- subdomain admin: `admin.bookstore.com`

---

#### 4.2.2 Thiết lập đăng nhập SSH bằng public key

```bash
mkdir -p ~/.ssh
nano ~/.ssh/authorized_keys
# Dán public key của bạn vào đây, lưu và thoát
```

---

#### 4.2.3 Thiết lập timezone cho server

Liệt kê timezone:

```bash
timedatectl list-timezones | grep Asia
```

Thiết lập timezone Việt Nam:

```bash
sudo timedatectl set-timezone Asia/Ho_Chi_Minh
timedatectl
```

---

#### 4.2.4 Cấu hình Firewall (UFW)

```bash
sudo ufw default deny incoming
sudo ufw default allow outgoing
sudo ufw allow ssh
sudo ufw allow http
sudo ufw allow https
sudo ufw enable
```

---

#### 4.2.5 Cài đặt MySQL

```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql.service
```

---

#### 4.2.6 Secure & cấu hình MySQL

```bash
sudo mysql
```

Thiết lập mật khẩu cho `root` (nếu cần):

```sql
ALTER USER 'root'@'localhost'
IDENTIFIED WITH mysql_native_password BY '<new_password>';
exit
```

Chạy hardening:

```bash
sudo mysql_secure_installation
```

---

#### 4.2.7 Tạo database và user cho Book Store

```bash
sudo mysql -u root -p
```

```sql
CREATE SCHEMA `book_store`
DEFAULT CHARACTER SET utf8
COLLATE utf8_bin;
```

Tạo user riêng và cấp quyền:

```sql
CREATE USER 'bookstore_user'@'localhost'
IDENTIFIED BY '<strong_password>';

GRANT ALL PRIVILEGES ON book_store.*
TO 'bookstore_user'@'localhost';
```

![Cấp quyền database](<docs/images/grant-db-privileges.png>)

---

#### 4.2.8 Cài đặt và cấu hình Nginx

**Cài đặt Nginx**

```bash
sudo apt update
sudo apt install nginx
systemctl status nginx
```

Reload/restart khi cần:

```bash
sudo systemctl reload nginx
# hoặc
sudo systemctl restart nginx
```

![Cài đặt Nginx](<docs/images/install-nginx.png>)

**Cấu hình reverse proxy cho domain** 

Xoá config default tại 2 thư mục:

- `/etc/nginx/sites-enabled`
    
- `/etc/nginx/sites-available`  

Di chuyển vào thư mục `/etc/nginx/sites-enabled` và tạo lần lượt 2 file cho site public và site admin.

Tạo file site public (ví dụ):

```bash
sudo nano /etc/nginx/sites-enabled/bookstore.com
```

Nội dung mẫu (site public chạy port `8080`):
```nginx
server {
    server_name bookstore.com www.bookstore.com;

    location / {
        proxy_set_header X-Forwarded-For $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Host $http_host;
        proxy_pass "http://127.0.0.1:8080";
        client_max_body_size 50M;
    }

    location ~* \.(css|js|woff|woff2|ttf|gif|jpg|jpeg|png|svg|webp)$ {
            expires 1h;
            add_header Cache-Control "public, max-age=86400";
            proxy_pass "http://127.0.0.1:8080";
    }

    location ~* .(mp3|mp4|ogg)$ {
            expires 24h;
            add_header Cache-Control "public, max-age=86400";
            add_header Accept-Ranges bytes;
            proxy_pass "http://127.0.0.1:8080";
    }

    location /api/v1/media/add {
        proxy_pass http://127.0.0.1:8080;
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

Tạo file site admin(ví dụ):

```bash
sudo nano /etc/nginx/sites-enabled/admin.bookstore.com
```

Nội dung mẫu (site admin chạy port `9090`):
```
server {
    server_name admin.bookstore.com;


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
    location ~* \.(css|js|woff|woff2|ttf|gif|jpg|jpeg|png|webp)$ {
        expires 24h;
        proxy_pass "http://127.0.0.1:9090";
    }
}
```

Test & reload:

```bash
sudo nginx -t
```
 
![Cấu hình SSL](<docs/images/setting-nginx-success.png>)

```
sudo systemctl reload nginx
```


---

#### 4.2.9 Cài SSL miễn phí với Certbot

```bash
sudo apt install certbot python3-certbot-nginx
sudo certbot
```

Sau khi sử dụng certbot xong có thể file cấu hình `/etc/nginx/sites-enabled/admin.bookstore.com` sẽ bị thay đổi thế này:

![Cấu hình SSL](<docs/images/certbot-nginx.png>)

---

#### 4.2.10 Cài đặt Java Runtime

Cài đặt JDK 8:

```bash
sudo apt update
java -version

sudo apt install openjdk-8-jre-headless
```

Set `JAVA_HOME` (ví dụ):
```bash
nano ~/.bash_profile
# thêm dòng:
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
# áp dụng:
source ~/.bash_profile
```

---

#### 4.2.11 Cài đặt EzyPlatform

Sử dụng `wget` và `inspect` nút download tại Home để lấy bản mới nhất

```bash
wget https://ezyplatform.com/api/v1/platforms/0.9.8/download
mv download ezyplatform.zip
unzip ezyplatform.zip
```

![Download EzyPlatform](<docs/images/inspect-download-version.png>)

Tìm đến folder `ezyplatform/settings` và thay đổi file `setup.properties` (ví dụ: `nano settings/setup.properties`) với các thông tin cơ sở dữ liệu bạn đã tạo trước đó, ví dụ: (bạn chỉnh theo dự án):

```properties
datasource.jdbc_url=jdbc:mysql://localhost:3306/<databaseName>
datasource.driver_class_name=com.mysql.cj.jdbc.Driver
datasource.username=<username>
datasource.password=<password>
tables.create_manually=false
```

Khởi động lần đầu:

```bash
bash cli.sh "console admin"
```

Khi log hiển thị `EZHTTP READY` và không xuất hiện exception,
điều đó cho thấy **EzyPlatform đã khởi động thành công** và sẵn sàng cấu hình hệ thống.

---

#### 4.2.12 Kích hoạt module và theme

Sau khi hệ thống Admin khởi động thành công, truy cập **Dashboard Admin** và thực hiện
các bước cấu hình sau:

1) **Dashboard → Web → Settings → Submit**  
   Áp dụng cấu hình cho Web site.
![Web settings](<docs/images/web-settings.png>)
2) **Dashboard → Admin → Settings → Submit**  
   Áp dụng cấu hình cho Admin site.
![Admin settings](<docs/images/admin-settings.png>)
3) Vào **Theme → Add New → Add Modules** để thêm các module cần thiết.
![Add modules](<docs/images/add-modules.png>)
4) Thực hiện **Install** và **Active** toàn bộ dependency của Book Store.
![Active module](<docs/images/active-module.png>)
![Install dependencies](<docs/images/install-dependencies.png>)
5) Chọn **Active & Restart** để kích hoạt module và khởi động lại Admin  
   (hoặc vào `Dashboard → Admin → Restart`).
![Restart admin](<docs/images/active-restart-admin.png>)


🎉 **Hoàn tất quá trình deploy dự án Book Store.**
