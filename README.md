# Link deploy
> https://melted-fenelia-nobeljb-316a96d3.koyeb.app/
---
## Exercise 1

### Clean Code Principles Applied:
1. **Meaningful Naming**:
    - Variabel dan method menggunakan nama yang deskriptif, seperti `createProduct()`, `updateProduct()`, `deleteProduct()`, agar mudah dipahami.

2. **Single Responsibility Principle (SRP)**:
    - Setiap method hanya menangani satu tugas spesifik (misalnya, `createProduct()` hanya menangani pembuatan produk).

3. **Consistent Formatting**:
    - Menggunakan konvensi Java Spring Boot yang konsisten, seperti PascalCase untuk kelas dan camelCase untuk variabel/method.

4. **Code Reusability**:
    - Menggunakan service layer (`ProductService`) untuk memisahkan logika bisnis dari controller.

### Secure Coding Practices Applied:
1. **Input Validation**:
    - Menggunakan `@Valid` di controller untuk memvalidasi input dari user.
    - Mencegah SQL Injection dengan menggunakan JPA Repository.

2. **Error Handling**:
    - Menggunakan exception handling (`@ExceptionHandler`) agar aplikasi tetap stabil jika terjadi kesalahan.

3. **Access Control**:
    - Menambahkan validasi agar hanya admin yang dapat mengedit atau menghapus produk.

### Areas for Improvement:
1. **Improve Validation**
    - Saat ini, validasi dilakukan di controller, lebih baik memindahkannya ke level service untuk pemisahan tanggung jawab yang lebih baik.

2. **Better Error Messages**
    - Saat input kosong, tampilkan pesan error yang lebih user-friendly.

3. **Unit Testing**
    - Perlu menambahkan unit test menggunakan JUnit dan Mockito untuk memastikan semua fitur berjalan dengan baik.

##  Exercise 2

### 1. Bagaimana rasanya setelah menulis unit test?
Setelah menulis unit test, saya merasa lebih yakin bahwa fitur-fitur yang dibuat pada proyek ini berjalan sesuai harapan. Unit test juga membantu menemukan potensi bug lebih awal sebelum aplikasi di-deploy nanti.

### 2. Berapa banyak unit test yang harus ada dalam satu kelas?
Jumlah unit test tergantung pada kompleksitas kelas. Namun, setiap method sebaiknya memiliki setidaknya:
- Skenario positif: Saat fitur bekerja sesuai harapan.
- Skenario negatif: Saat fitur gagal (contoh: data tidak ditemukan, input tidak valid).

### 3. Bagaimana cara memastikan unit test sudah cukup?
- Menggunakan berbagai skenario uji untuk menghindari bug tersembunyi.
- Memeriksa code coverage menggunakan tools seperti JaCoCo.
- Melakukan review ulang untuk melihat apakah ada kemungkinan error lain.

### 4. Apakah 100% code coverage berarti tidak ada bug?
Tidak, Code coverage hanya mengukur seberapa banyak kode yang diuji, bukan seberapa benar kode tersebut. Kemungkinan lain yaitu:
- Tidak semua edge case diuji.
- Bisa saja ada kesalahan dalam logika bisnis.
- Tidak mencakup skenario dunia nyata (misalnya pengguna melakukan input aneh).

### 5. Masalah Clean Code dalam Functional Test
Saat menambahkan fitur verifikasi jumlah produk dalam daftar, kode yang ditulis bisa menjadi terlalu berulang. Ini menurunkan kualitas karena:
- Banyak duplikasi kode, terutama pada setup dan teardown WebDriver.
- Sulit dikelola, karena jika ada perubahan kecil di setup, semua file harus diperbarui.
- Membuat pengujian tidak fleksibel, karena setiap file punya logika setup sendiri.

#### **Solusi untuk Clean Code yang Lebih Baik**
1. **Gunakan Base Test Class**
    - Pindahkan setup dan teardown ke kelas `BaseFunctionalTest.java`, lalu semua functional test cukup mewarisinya.

2. **Gunakan Parameterized Tests**
    - Dengan JUnit, dengan menjalankan satu test case dengan berbagai input.

3. **Terapkan Page Object Model (POM)**
    - Buat kelas khusus untuk halaman web yang sering diuji, seperti `ProductPage.java`.

# **Refleksi Modul 2**

---

#### **Masalah Kualitas Kode yang Diperbaiki & Strategi Penyelesaiannya**
Selama proses latihan, saya menemukan beberapa masalah kualitas kode yang perlu diperbaiki, di antaranya:

1. **Format Kode yang Tidak Konsisten** – Beberapa bagian kode memiliki indentasi yang tidak rapi dan ada impor yang tidak digunakan. Saya memperbaikinya dengan menjalankan linter seperti `flake8` dan `black` agar format kode lebih bersih dan standar.
2. **Keamanan Kode** – Ada peringatan tentang hardcoded secret di file settings. Untuk mengatasinya, saya menggantinya dengan variabel lingkungan agar lebih aman dan tidak tersimpan langsung di dalam kode.
3. **Kode Redundan & Kurang Efisien** – Saya menemukan beberapa fungsi yang memiliki logika berulang atau variabel yang tidak digunakan. Saya refaktor kode agar lebih ringkas dan mudah dipahami tanpa mengubah fungsinya.
4. **Tes yang Gagal** – Beberapa pengujian unit gagal karena asumsi yang salah dalam pengujian. Saya memperbaiki assertion dalam test case agar sesuai dengan perilaku sistem yang sebenarnya.

#### **Apakah CI/CD yang Dibuat Sudah Memenuhi Prinsip Continuous Integration & Deployment?**
Menurut saya, implementasi CI/CD ini sudah memenuhi konsep Continuous Integration dan Continuous Deployment karena:

1. **Pengujian Otomatis & Pengecekan Kualitas Kode** – Setiap kali ada perubahan kode yang dikirim ke repository, pipeline secara otomatis menjalankan tes unit dan analisis kode. Ini membantu memastikan kode yang masuk tidak merusak fungsionalitas yang sudah ada.
2. **Deployment yang Mulus** – Setelah semua tes lulus, kode langsung dideploy ke PaaS tanpa perlu campur tangan manual. Ini membuat proses deployment lebih cepat dan minim kesalahan.
3. **Feedback Cepat untuk Developer** – Karena pipeline berjalan setiap kali ada perubahan kode, tim bisa langsung tahu jika ada yang perlu diperbaiki. Ini mempercepat proses pengembangan dan mengurangi risiko bug di produksi.

---
