package org.askrindo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AsuransiMikroBahariDto {

    private String id;

    @NotBlank
    private String namaTertanggung;

    @Pattern(regexp = "\\d{16}", message = "Nomor KTP harus terdiri dari 16 digit angka")
    private String nomorKTP;

    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank
    private String nomorTelepon;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate jangkaWaktuAwal;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate jangkaWaktuAkhir;

    @NotBlank(message = "Nomor ID Kapal tidak boleh kosong")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Nomor ID Kapal hanya boleh berisi huruf dan angka")
    private String noIDKapal;

    @NotBlank
    private String jenisKapal;

    @NotBlank
    private String konstruksiKapal;

    @NotBlank
    private String penggunaanKapal;

    @NotNull
    private BigDecimal hargaKapal;

    @NotBlank
    private String jenisPaket;

    private String nomorSertifikat; // Tambahan kolom

    private BigDecimal premi; // Tambahan kolom

}
