-- 1. Buat Tabel Buku (books)
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    judul VARCHAR(255) NOT NULL,
    penulis VARCHAR(255) NOT NULL,
    penerbit VARCHAR(255),
    tahun_terbit INT,
    kondisi_buku VARCHAR (50) DEFAULT 'Good',
    stok INT NOT NULL DEFAULT 1,
    status_ketersediaan BOOLEAN DEFAULT TRUE
);

-- 2. Buat Tabel Pengguna (users)
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    nim_nip VARCHAR(50) UNIQUE NOT NULL,
    nama VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    peran VARCHAR(50) NOT NULL DEFAULT 'MAHASISWA', -- MAHASISWA / DOSEN / ADMIN
    rating VARCHAR(50) NOT NULL DEFAULT 'A' -- B/C/D
);

-- 3. Buat Tabel Transaksi Peminjaman (loans)
CREATE TABLE IF NOT EXISTS borrowings (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    tanggal_pinjam TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tanggal_kembali TIMESTAMP,
    kondisi_buku VARCHAR (50),
    skor_peminjam INT NOT NULL DEFAULT 100,
    status VARCHAR(50) DEFAULT 'DIPINJAM', -- DIPINJAM / DIKEMBALIKAN
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Buat Tabel Audit Log (audit_logs)
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    aksi VARCHAR(100) NOT NULL,
    keterangan TEXT,
    waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);