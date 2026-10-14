# Tugas Praktikum 5 - Desain dan Pemrograman Berorientasi Objek 2026 - (Java Swing GUI & MySQL Database)

Saya Zufar Ahmad Maulidy dengan NIM 2400285 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## 1. Desain Program
program ini merupakan aplikasi manajemen data karyawan (*PeopleData*) berbasis Java Swing GUI yang terhubung ke database relasional MySQL menggunakan Java Database Connectivity (JDBC). Arsitektur aplikasi terdiri dari:
- **`Database.java`**: menangani koneksi ke server MySQL via JDBC driver (`mysql-connector-j`) serta menyediakan method eksekusi query SQL (`selectQuery` dan `insertUpdateDeleteQuery`).
- **`PeopleMenu.java`**: mengelola antarmuka grafis (GUI), penanganan aksi tombol (event listener), validasi data input, dan manipulasi data tabel (*DefaultTableModel*).
- **`Person.java`**: model data objek (*jika masih digunakan*).

## 2. Penjelasan Alur
1. **Koneksi Database**: saat aplikasi dijalankan, constructor `PeopleMenu` menginisialisasi objek `Database` yang terhubung ke `jdbc:mysql://localhost:3306/db_people`.
2. **Read Data**: method `setTable()` mengeksekusi `SELECT * FROM person` dan memuat setiap baris data ke dalam `personTable`.
3. **Validasi & Insert**: saat tombol *Add* ditekan:
   - memeriksa apakah ada field input yang kosong. Jika kosong, muncul peringatan error.
   - memeriksa apakah ID sudah ada di tabel melalui query `SELECT`. Jika sudah ada, muncul peringatan duplikasi ID.
   - jika valid, query `INSERT INTO person` dieksekusi.
4. **Update & Delete**: mengambil nilai langsung dari baris tabel terpilih tanpa `ArrayList`, lalu mengeksekusi `UPDATE` atau `DELETE` berdasarkan `id`.

## 3. Dokumentasi Eksekusi Program
1. kita akan menjalankan program yang telah ditulis sebelumnya, dan berikut adalah tampilan awal-nya
   
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 38 40" src="https://github.com/user-attachments/assets/02de2c67-a19a-4fcf-a8c0-0a6ea053b15e" />

2. lalu kita akan mencoba untuk mengisi data sesuai dengan spesifikasi yang dibutuhkan, dan setelah data selesai diisi lalu ditambahkan, maka akan muncul pop-up yang menginformasikan bahwa data berhasil ditambahkan

   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 40 29" src="https://github.com/user-attachments/assets/76f790d0-9ca1-43e9-b117-2ac1f251d27f" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 40 40" src="https://github.com/user-attachments/assets/413d56b5-c1a4-42aa-babf-b8f4d4baa22d" />

3. untuk case selanjutnya kita akan mencoba untuk mengisi data selanjutnya, tapi kali ini kita akan mencoba mengosongkan salah satu kolom, mulai dari kolom ID yang dibiarkan kosong, kolom nama yang dibiarkan kosong, kolom tahun lahir yang dibiarkan kosong, hingga kolom email yang dibiarkan kosong, jika kita melakukan salah satu tindakan tersebut, maka akan muncul pop-up bahwa kolom harus diisi semuanya sesuai dengan spesifikasi yang diminta

   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 41 42" src="https://github.com/user-attachments/assets/94bcae43-6af0-4590-9ba4-2ca8d7eb2ae6" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 41 51" src="https://github.com/user-attachments/assets/61ea0294-bc7f-451c-8870-2f0d88244835" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 42 05" src="https://github.com/user-attachments/assets/3b9b220b-d2c2-4f70-90e7-06de116764b3" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 42 24" src="https://github.com/user-attachments/assets/a3466319-34ea-4066-a988-b27aa65b2a91" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 42 43" src="https://github.com/user-attachments/assets/0f7676e3-79ee-4c1e-a2ab-51d578e2d809" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 43 05" src="https://github.com/user-attachments/assets/ff35c8da-e57a-47ec-9a50-f13f4418e56a" />

   untuk yang terakhir akan kita isi semua data yang diminta dan menambahkan data tersebut ke dalam Database

   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 46 39" src="https://github.com/user-attachments/assets/21589b05-bc77-4964-925a-875915986b83" />

5. pada case kali ini kita akan mencoba mengisi ID yang sama seperti ID yang sudah tertera sebelumnya (ID = P013) pada Database, maka akan muncul pop-up yang menginformasikan bahwa data tersebut telah terdaftar

   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 45 06" src="https://github.com/user-attachments/assets/4c332637-d272-4b99-b72e-119d892e97e2" />

6. lalu untuk menghapus data yang sudah masuk database, bisa dengan cara klik salah satu data, maka di bagian sisi kanan atas akan muncul tombol tambahan bertulisan DELETE, dan kita tinggal menghapus data tersebut dengan klik persetujuan sebagai konfirmasi bahwa kita setuju data tersebut akan dihapus. dapat dilihat pada 2 gambar dibawah, gambar pertama masih ada data karyawan no.7 **`[ID P007, Gita Gutawa, 1992, Full-Time]`**, tetapi pada gambar dibawahnya, data karyawan no.7 sudah tidak ada karena sudah dihapus

   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 46 39" src="https://github.com/user-attachments/assets/f821b96e-65f4-4c0c-9c9f-8c5f0a093699" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 48 35" src="https://github.com/user-attachments/assets/389489e6-184f-42c7-a1b1-b81895b7abc8" />

7. lalu untuk case selanjutnya kita akan mencoba mengubah data yang sebelumnya telah ditambahkan, kita bisa melakukannya dengan menekan data yang ingin diubah, lalu menuju kolom untuk langsung mengedit, dan menekan tombol UPDATE untuk memperbarui data, dan akan muncul pop-up bahwa data berhasil diubah

   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 49 16" src="https://github.com/user-attachments/assets/936188aa-160b-4986-88bf-3ee351446dbe" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 49 22" src="https://github.com/user-attachments/assets/7020e9e1-862c-479f-8399-d341e9b792ee" />
   <img width="1470" height="956" alt="Screenshot 2026-10-14 at 11 49 29" src="https://github.com/user-attachments/assets/be2c3370-1443-4e74-9565-e06accd232d7" />

SELESAI, TERIMA KASIH














