package org.askrindo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AsuransiMikroRumahkuDto {

    private String id;

    @NotBlank
    private String namaTertanggung;

    @Pattern(regexp = "\\d{16}", message = "Nomor KTP harus terdiri dari 16 digit angka")
    private String nomorKTP;

    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank
    private String nomorTelepon;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate jangkaWaktuAwal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate jangkaWaktuAkhir;

    @NotBlank
    private String informasiKepemilikan;

    @NotBlank
    private String alamat;

    @NotBlank
    private String namaAhliWaris;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate tanggalLahir;

    private String nomorTeleponAhliWaris;

    @NotBlank
    private String hubungan;

    @NotBlank
    private String jenisPaket;

    private String nomorSertifikat;

    private BigDecimal premi;
}
