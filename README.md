# FootballMatchSchedule

Ini adalah aplikasi jadwal pertandingan sepakbola untuk Submission III dan IV Kotlin Android Developer Expert (KADE) Dicoding

# Skenario Pengujian Pada Submission IV

### Skenario Pengujian pada MainActivity :

1. Periksa jika recyclerview dengan id league_list ditampilkan atau tidak
   * Periksa jika recyclerview dengan id league_list dapat bekerja dengan baik:
   * Lakukan aksi scroll ke posisi item dengan index ke 7
   * Lakukan aksi klik pada item dengan index ke 7
2. Periksa jika pengguna bisa menambahkan pertandingan terakhir (Last Match) pada daftar favorit dengan baik:
   * Lakukan aksi klik pada item dengan index ke 0
   * Setelah berpindah ke MatchActivity, tunggu selama 10 detik sampai loading selesai
   * Periksa apakah recyclerview dengan id match_list yang berada pada tab "LAST MATCH" ditampilkan atau tidak
   * Lakukan aksi klik pada view pertama yang memiliki tulisan "Leicester"
   * Setelah berpindah ke MatchDetailActivity, periksa jika tombol favorit ditampilkan
   * Lakukan aksi klik pada tombol favorit
   * Tekan tombol back dua kali, agar berpindah kembali ke MainActivity
   * Lakukan aksi klik pada option menu yang berada di action bar
   * Lakukan aksi klik pada menu yang memiliki tulisan "Favorite Match"
   * Setelah berpindah ke FavoriteActivity, periksa apakah ditemukan view yang memiliki tulisan "Leicester" dalam recyclerview dengan id match_list pada tab "LAST MATCH"
   * Jika ditemukan, berarti aksi penambahan pertandingan terakhir (Last Match) pada daftar favorit berhasil.
3. Periksa jika pengguna bisa menambahkan pertandingan selanjutnya (Next Match) pada daftar favorit dengan baik:
   * Lakukan aksi klik pada item dengan index ke 0
   * Setelah berpindah ke MatchActivity, lakukan aksi klik pada tab "NEXT MATCH"
   * Tunggu selama 10 detik sampai loading selesai
   * Periksa apakah recyclerview dengan id match_list yang berada pada tab "NEXT MATCH" ditampilkan atau tidak
   * Lakukan aksi klik pada view pertama yang memiliki tulisan "Liverpool"
   * Setelah berpindah ke MatchDetailActivity, periksa jika tombol favorit ditampilkan
   * Lakukan aksi klik pada tombol favorit
   * Tekan tombol back dua kali, agar berpindah kembali ke MainActivity
   * Lakukan aksi klik pada option menu yang berada di action bar
   * Lakukan aksi klik pada menu yang memiliki tulisan "Favorite Match"
   * Setelah berpindah ke FavoriteActivity, lakukan aksi klik pada tab "NEXT MATCH"
   * Periksa apakah ditemukan view yang memiliki tulisan "Liverpool" dalam recyclerview dengan id match_list pada tab "NEXT MATCH"
   * Jika ditemukan, berarti aksi penambahan pertandingan selanjutnya (Next Match) pada daftar favorit berhasil.

### Skenario Pengujian pada MatchActivity :

1. Sebelum melakukan instrument test pada MatchActivity, persiapkan sample data Liga, kemudian masukkan sample data tersebut pada Intent Extra. Sample data yang digunakan adalah: 
   * Name : English Premier League
   * Image : R.drawable.english_premier_league
   * ID : 4328
2. Periksa apakah data yang berada pada intent extra dapat ditampilkan dengan baik
   * Periksa apakah view dengan id league_logo ditampilkan atau tidak
   * Periksa apakah view dengan id league_name_top memiliki teks "English Premier League"
3. Periksa apakah recyclerview pada tab "LAST MATCH" dapat berfungsi dengan baik:
   * Tunggu selama 10 detik hingga loading selesai
   * Periksa apakah recyclerview dengan id match_list ditampilkan
   * Lakukan aksi scroll ke posisi item dengan index ke 5
   * Lakukan aksi klik pada item dengan index ke 5
4. Periksa apakah recyclerview pada tab "NEXT MATCH" dapat berfungsi dengan baik:
   * Lakukan aksi klik pada tab "NEXT MATCH"
   * Periksa apakah recyclerview dengan id match_list ditampilkan
   * Lakukan aksi scroll ke posisi item dengan index ke 5
   * Lakukan aksi klik pada item dengan index ke 5
   * Lakukan aksi klik pada option menu yang berada pada action bar
   * Lakukan aksi klik pada menu yang memiliki tulisan "Detail"
   * Setelah berpindah ke MatchDetailActivity, periksa jika view dengan id league_name memiliki teks "English Premiere League"

### Skenario Pengujian pada SearchActivity :

1. Periksa apakah pencarian nama pertandingan bisa bekerja dengan baik jika ditemukan hasil:
   * Lakukan aksi pengetikan teks "Barcelona" pada searchview, kemudian tekan tombol cari pada soft keyboard
   * Tunggu selama 5 detik sampai loading selesai
   * Periksa apakah view dengan id no_result tidak ditampilkan
   * Periksa apakah ada item pada recyclerview dengan id match_list yang mengandung tulisan "Barcelona"
2. Periksa apakah pencarian nama pertandingan bisa bekerja dengan baik jika tidak ditemukan hasil:
   * Lakukan aksi pengetikan teks "Barcelona" pada searchview, kemudian tekan tombol cari pada soft keyboard
   * Tunggu selama 5 detik sampai loading selesai
   * Periksa apakah view dengan id no_result ditampilkan
