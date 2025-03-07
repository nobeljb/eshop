Berikut adalah jawaban refleksi berdasarkan pertanyaan yang diberikan:

---

### **Refleksi terhadap Alur TDD**
Mengikuti alur **Test-Driven Development (TDD)** memberikan banyak manfaat dalam proses pengembangan perangkat lunak. Evaluasi saya terhadap pendekatan TDD dalam latihan ini:

1. **Apakah TDD cukup berguna?**
  - Ya, TDD membantu memastikan bahwa setiap fitur yang dikembangkan telah diuji sebelum diimplementasikan, sehingga mengurangi bug di tahap awal.
  - Pendekatan ini juga membantu dalam mendesain kode yang lebih modular dan mudah dipahami.
  - Namun, terkadang TDD terasa lebih lambat dibandingkan menulis kode langsung, terutama saat harus banyak refaktor.

2. **Apa yang bisa diperbaiki untuk ke depannya?**
  - Meningkatkan cakupan pengujian: Beberapa edge case baru ditemukan setelah implementasi. Ke depan, saya harus lebih teliti dalam mengidentifikasi edge case sejak awal.
  - Mengoptimalkan penggunaan mock dalam pengujian: Beberapa pengujian masih bergantung pada implementasi nyata daripada menggunakan mocking dengan baik.
  - Menjaga kejelasan dalam siklus RED-GREEN-REFACTOR: Terkadang ada kecenderungan untuk langsung melakukan refaktor tanpa melewati siklus secara berurutan.

---

### **Refleksi terhadap Prinsip F.I.R.S.T.**
Dalam tutorial ini, saya telah membuat unit test untuk berbagai fitur. Berikut adalah evaluasi terhadap kepatuhan terhadap prinsip F.I.R.S.T.:

1. **Fast (Cepat)** 
  - Sebagian besar unit test berjalan cepat karena hanya menguji unit kecil kode.
  - Namun, beberapa pengujian yang melibatkan repository bisa dioptimalkan lebih lanjut dengan menggunakan in-memory database atau mocking yang lebih efisien.

2. **Independent (Independen)** 
  - Setiap test berjalan sendiri tanpa bergantung pada test lainnya.
  - Namun, perlu memastikan tidak ada state yang tersisa dari satu test ke test lainnya.

3. **Repeatable (Dapat Diulang)** 
  - Test dapat dijalankan berkali-kali dengan hasil yang sama di lingkungan yang sama.
  - Penggunaan mock membantu memastikan kestabilan pengujian.

4. **Self-Validating (Memvalidasi Diri Sendiri)** 
  - Semua test menggunakan assert yang jelas untuk menyatakan apakah pengujian berhasil atau gagal.
  - Tidak memerlukan validasi manual.

5. **Timely (Tepat Waktu)** 
  - Sebagian besar test ditulis sebelum implementasi (sesuai TDD).
  - Namun, ada beberapa kasus di mana pengujian baru dibuat setelah kode ditulis karena keterlambatan dalam mendefinisikan spesifikasi test yang jelas.

---