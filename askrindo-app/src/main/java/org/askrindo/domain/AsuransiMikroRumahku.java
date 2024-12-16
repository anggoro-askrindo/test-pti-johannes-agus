package org.askrindo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asuransi_mikro_rumahku")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsuransiMikroRumahku {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "nama_tertanggung", nullable = false, length = 100)
    private String namaTertanggung;

    @Column(name = "nomor_ktp", nullable = false, length = 16)
    private String nomorKTP;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "nomor_telepon", nullable = false, length = 30)
    private String nomorTelepon;

    @Column(name = "jangka_waktu_awal", nullable = false)
    private LocalDate jangkaWaktuAwal;

    @Column(name = "jangka_waktu_akhir", nullable = false)
    private LocalDate jangkaWaktuAkhir;

    @Column(name = "informasi_kepemilikan", nullable = false, length = 150)
    private String informasiKepemilikan;

    @Column(name = "alamat", nullable = false, length = 255)
    private String alamat;

    @Column(name = "nama_ahli_waris", nullable = false, length = 100)
    private String namaAhliWaris;

    @Column(name = "tanggal_lahir")
    private LocalDate tanggalLahir;

    @Column(name = "nomor_telepon_ahli_waris", length = 30)
    private String nomorTeleponAhliWaris;

    @Column(name = "hubungan", nullable = false, length = 150)
    private String hubungan;

    @Column(name = "jenis_paket", nullable = false, length = 150)
    private String jenisPaket;
}

