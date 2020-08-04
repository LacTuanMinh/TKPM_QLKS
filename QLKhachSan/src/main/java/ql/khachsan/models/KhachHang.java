package ql.khachsan.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "KhachHang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDKhachHang")
    private int idKhachHang;

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    @Column(name = "TenKhachHang")
    private String tenKhachHang;

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    @Column(name = "CMND", unique = true)
    private String cmnd;

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getCmnd() {
        return cmnd;
    }

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    @Column(name = "DiaChi")
    private String diaChi;

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "khachHang", cascade = CascadeType.REMOVE)
    private Set<PhieuDatPhong> dsPhieuDatPhong;

    public Set<PhieuDatPhong> getDsPhieuDatPhong() {
        return dsPhieuDatPhong;
    }

    public void setDsPhieuDatPhong(Set<PhieuDatPhong> dsPhieuDatPhong) {
        this.dsPhieuDatPhong = dsPhieuDatPhong;
    }

    public KhachHang(String tenKhachHang, String cmnd, String soDienThoai, String diaChi, Set<PhieuDatPhong> dsPhieuDatPhong) {
        this.tenKhachHang = tenKhachHang;
        this.cmnd = cmnd;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.dsPhieuDatPhong = dsPhieuDatPhong;
    }

    public KhachHang() {
    }
}
