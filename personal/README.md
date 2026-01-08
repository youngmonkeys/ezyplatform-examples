# Personal

Thanks: https://github.com/codingstella/personal-blog-website?tab=readme-ov-file

# 1. 🧰 Cài đặt môi trường

## 1.1. Tải và cài dặt JDK 8, thiết lập biến môi trường JAVA_HOME
[Link tải JDK](https://www.oracle.com/asean/java/technologies/javase/javase8-archive-downloads.html)
[Thiết lập biến môi trường](https://www.baeldung.com/java-home-on-windows-mac-os-x-linux)
[Xem chi tiết hơn tại](https://youngmonkeys.org/ezyplatform/guides/install-ezyplatform?lang=vi)

## 1.2. Cài đặt Mysql
[Link MySQL](https://dev.mysql.com/downloads/)
* Cài đặt cả MySQL Workbench để quản trị cơ sở dữ liệu.
* Cần nhớ tài khoản, mật khẩu root để sử dụng cho bước sau.

## 1.3. Tải gói cài đặt Ezyplatform
[Link tải](https://ezyplatform.com/)
* Tải cả Platform và SDK
* Giải nén Platform mở tệp * <pre>settings/setup.properties</pre> *sửa đổi thông tin kết nối đến database.*

## 1.4. Cài đặt Ezyplatform thành công
1. Di chuyển vào thư mục Ezyplatform, mở terminal tại thư mục này.
2. Chạy lệnh:
- với Linux/MacOS <pre>bash cli.sh "console admin"</pre>
- với Windows <pre>.\cli.bat "console admin"</pre>
Nếu thấy hiển thị **EZYHTTP READY** là đã cài đặt thành công. Nếu có ngoại lệ xảy ra, nghĩa là cấu hình sai datasource
3. Truy cập URL: [http://localhost:9090/setup-admin](http://localhost:9090/setup-admin)
Cấu hình tài khoản super admin - tải khoản quản trị sau này
4. Dừng Ezyplatfrom
- Đối với Linux/MacOS: chạy lệnh: <pre>bash cli.sh stop</pre>
- Đối với Windows: Đóng tất cả cửa sổ cmd.

## 1.5. Thiết lập biến môi trường EZYPLATFORM_HOME
Làm theo hướng dẫn ở phàn 2.3 trong link sau: [Link](https://youngmonkeys.org/ezyplatform/guides/install-ezyplatform?lang=vi)

## 1.6. Cài đặt EzyPlatform SDK
1. Thiết lập biến môi trường EZYPLATFORM_SDK. Sử dụng SDK đã tải ở 1.3, cài đặt theo phần 2 trong [Link](https://youngmonkeys.org/ezyplatform/guides/install-ezyplatform-sdk?lang=vi)
2. Cài đặt apache maven [Link](https://maven.apache.org/install.html)
3. Mở terminal ở thử mục ezyplatform-development tải ở bước 1.3
- Đối với Linux/MacOS: chạy lệnh <pre>bash build.sh</pre>
- Đối với Windows: chạy lệnh <pre>build.bat</pre>

## 1.7. Tạo project với Ezyplatform
1. Mở terminal tại folder đặt dự án. Chạy lệnh tạo dự án:
- Đối với Linux/MacOS: sử dụng ezy.sh create-project
- Đối với Windows: sử dụng ezy.bat create-project
* Chi tiết lệnh xem ở phần 4 trong [Link](https://youngmonkeys.org/ezyplatform/guides/ezyplatform-sdk-commands?lang=vi)
3. Mở project vừa tạo bằng IDE [Intellij](https://www.jetbrains.com/idea/download/other.html)
- Thiết lập JDK 8 ở Intellij ở Main Menu - Project Structure. Trong phần Project Settings, SDK chọn JDK8 đã cài đặt ở 1.1
- Lưu ý lỗi IntelliJ không nhân path variable [Link](https://youngmonkeys.org/ezyplatform/guides/fix-intellij-not-recognizing-path-variable?lang=vi)
- Click biểu tượng Maven ở menu bên phải, trong tên project, thư mục Lifecycle, chạy install
- Tại phần Project của Intellij, vào thư theme của project (tên theo format là project-name-theme), mở file:
*src/test/java.com.(tên package và project).web.test/(Tên project)TestThemeStartupTest*
click chuột phải vào dòng bất kỳ trong file vừa mở, chạy Run hoặc Debug. Khi chạy thành công terminal sẽ lên dòng EZYHTTP READY.
- Mờ trình duyệt truy cập vào localhost:8080. Nếu lên vòng tròn may mắn là bạn đã khởi chạy dự án thành công.
- Sau khi chạy thành công menu trên cùng của Intellij xuất hiện file ThemeStartupTest đang run/debug. Click vào đó chọn Edit Configurations
sửa Working directory thành folder **Theme** của dự án.
- Rerun/Redebug để project nhận config mới.
- Sửa nội dung file home.html ở *thư mục theme/src/resources/templates/home.html*, save file và load lại trang localhost:8080 bạn sẽ thấy thay đổi
*Sửa lại Working directory ở bước trên để load lại thay đổi dễ dàng ở front-end*

# 2. ⚙️ Cấu hình dự án
1. Clone dự án personal về máy của bạn
2. Copy query ở *personal-admin-plugin/src/main/resources/scripts/scripts.sql*
3. Mở MySQL Workbench để thêm bảng:
- Chạy query <pre>use ezyplatform
CREATE TABLE IF NOT EXISTS `personal_post_word_counts` (
    `post_id` bigint unsigned NOT NULL,
    `word_count` bigint NOT NULL DEFAULT 0,
    `updated_at` datetime,
    PRIMARY KEY (`post_id`),
    INDEX `index_word_count_post_id` (`word_count`, `post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;</pre>
4. Làm tiếp tục theo bước 3 phần 1.7 với project personal

# 3. ▶️ Chạy ứng dụng
- Truy cập localhost:8080 để xem trang home là trang blog
- Truy cập localhost:9090, menu Posts để tạo bài viết
*Lưu ý cần chạy console admin hoặc chạy PersonalAdminPluginStartupTest thì mới truy cập được trang admin*
