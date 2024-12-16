package org.askrindo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "asuransi_mikro_bahari")
public class AsuransiMikroBahari {

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

    @Column(name = "no_id_kapal", nullable = false, length = 50)
    private String noIDKapal;

    @Column(name = "jenis_kapal", nullable = false, length = 150)
    private String jenisKapal;

    @Column(name = "konstruksi_kapal", nullable = false, length = 150)
    private String konstruksiKapal;

    @Column(name = "penggunaan_kapal", nullable = false, length = 150)
    private String penggunaanKapal;

    @Column(name = "harga_kapal", nullable = false)
    private BigDecimal hargaKapal;

    @Column(name = "jenis_paket", nullable = false, length = 150)
    private String jenisPaket;

    @Column(name = "nomor_sertifikat", nullable = false, length = 50)
    private String nomorSertifikat;

    @Column(name = "premi", nullable = false)
    private BigDecimal premi;

}
