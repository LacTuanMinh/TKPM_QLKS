package ql.khachsan.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "HoaDonThanhToan")
public class HoaDonThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDHoaDon")
    private int idHoaDon;

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPhieuDatPhong", nullable = false, unique = true)
    private PhieuDatPhong phieuDatPhong;

    public PhieuDatPhong getPhieuDatPhong() {
        return phieuDatPhong;
    }

    public void setPhieuDatPhong(PhieuDatPhong phieuDatPhong) {
        this.phieuDatPhong = phieuDatPhong;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDNhanVien", nullable = false, referencedColumnName = "IDNhanVien",
            foreignKey = @ForeignKey(name = "FK_HoaDonThanhToan_NhanVien"))
    private NhanVien nhanVien;

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKhuyenMai", referencedColumnName = "IDKhuyenMai",
            foreignKey = @ForeignKey(name = "FK_HoaDonThanhToan_KhuyenMai"))
    private KhuyenMai khuyenMai;

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public HoaDonThanhToan() {

    }

    public HoaDonThanhToan(PhieuDatPhong phieuDatPhong, NhanVien nhanVien, KhuyenMai khuyenMai) {
        this.phieuDatPhong = phieuDatPhong;
        this.nhanVien = nhanVien;
        this.khuyenMai = khuyenMai;
    }
}
