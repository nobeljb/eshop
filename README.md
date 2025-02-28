# Penerapan Prinsip SOLID dalam Proyek

## 1) Prinsip yang Diterapkan dalam Proyek

Dalam proyek ini, kami menerapkan prinsip SOLID untuk meningkatkan kualitas kode. Berikut adalah prinsip-prinsip yang diterapkan:

- **Single Responsibility Principle (SRP):**
   - Memisahkan `CarWriteService` dan `CarReadService` untuk memisahkan tanggung jawab antara operasi baca dan operasi tulis pada entitas `Car`.
   - `CarController` hanya menangani logika yang berkaitan dengan HTTP request tanpa mencampurkan logika bisnis.

- **Open/Closed Principle (OCP):**
   - Struktur `CarService` memungkinkan penambahan fitur baru tanpa mengubah implementasi yang ada dengan menggunakan interface `CarReadService` dan `CarWriteService`.

- **Liskov Substitution Principle (LSP):**
   - Interface `CarService` dipecah menjadi `CarReadService` dan `CarWriteService` untuk memastikan bahwa implementasi layanan tetap konsisten dan dapat diganti tanpa mempengaruhi sistem.

- **Interface Segregation Principle (ISP):**
   - Memisahkan `CarReadService` dan `CarWriteService` agar kelas yang hanya membutuhkan operasi baca tidak harus mengimplementasikan operasi tulis, dan sebaliknya.

- **Dependency Inversion Principle (DIP):**
   - Menggunakan `CarReadService` dan `CarWriteService` sebagai abstraksi agar `CarController` tidak bergantung langsung pada implementasi spesifik dari repository.

## 2) Keuntungan Penerapan SOLID

- **Kode lebih mudah dipahami dan dipelihara**
   - Dengan memisahkan layanan baca dan tulis, kode menjadi lebih modular dan mudah dimodifikasi tanpa harus mengubah seluruh layanan.

- **Meningkatkan fleksibilitas**
   - Dengan menggunakan interface, kita dapat mengganti implementasi layanan tanpa mengubah kode yang bergantung padanya.

- **Mengurangi ketergantungan antar komponen**
   - Dengan memisahkan tanggung jawab, perubahan di satu bagian kode tidak akan berdampak besar pada bagian lainnya.

### Contoh Keuntungan:
Sebelumnya, `CarService` menangani semua operasi terkait `Car`, termasuk pencarian dan modifikasi data. Dengan menerapkan SRP dan ISP, layanan ini dibagi menjadi:

```java
public interface CarReadService {
    Car findById(String carId);
    List<Car> findAll();
}

public interface CarWriteService {
    Car create(Car car);
    void update(String carId, Car car);
    void deleteCarById(String carId);
}
```

Sekarang, jika ada perubahan dalam operasi pencarian, kita hanya perlu memodifikasi `CarReadService` tanpa mengubah `CarWriteService`.

## 3) Kekurangan Jika Tidak Menerapkan SOLID

- **Kode sulit diperluas dan dimodifikasi**
   - Jika semua layanan terpusat dalam satu kelas, setiap perubahan kecil bisa berdampak besar pada keseluruhan sistem.

- **Kesulitan dalam pengujian (testing)**
   - Jika kode tidak dipisahkan dengan baik, pengujian unit menjadi sulit dilakukan karena banyak ketergantungan yang tidak diperlukan.

- **Ketergantungan tinggi antar komponen**
   - Jika `CarController` langsung bergantung pada `CarRepository`, maka setiap perubahan pada `CarRepository` dapat menyebabkan perubahan besar pada seluruh sistem.

### Contoh Masalah Tanpa SOLID:
Sebelumnya, satu service menangani semua operasi:
```java
public class CarService {
    public Car findById(String carId) { ... }
    public List<Car> findAll() { ... }
    public Car create(Car car) { ... }
    public void update(String carId, Car car) { ... }
    public void deleteCarById(String carId) { ... }
}
```
Dengan struktur ini, setiap perubahan dalam operasi baca akan mempengaruhi operasi tulis juga, sehingga sulit dikelola.

Dengan menerapkan SOLID, proyek menjadi lebih modular, fleksibel, dan mudah diperluas di masa depan.

