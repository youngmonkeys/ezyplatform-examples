Book Store: Hướng dẫn import dự án vào IntelliJ IDEA
## Mục lục
- [1. Cài đặt môi trường](#1-cài-đặt-môi-trường)
- [2. Import dự án vào IDE](#2-import-dự-án-vào-ide)
- [3. Khởi chạy dự án thành công](#3-khởi-chạy-dự-án-thành-công)
- [Troubleshooting](#troubleshooting)

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


---

## Troubleshooting

### Lỗi format code
- `Ctrl + Alt + L` → Reformat code

### Lỗi import không dùng
- `Ctrl + Alt + O` → Optimize imports

📸 **Ảnh minh họa**  
![fix unimport](<docs/images/fix_unimport.png>)
