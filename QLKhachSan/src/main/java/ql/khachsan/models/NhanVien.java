package ql.khachsan.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDNhanVien")
    private int idNhanVien;

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    @Column(name = "HoTen")
    private String hoTen;

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    @Column(name = "GioiTinh", columnDefinition = "VARCHAR(3)")
    private String gioiTinh;

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Column(name = "DiaChi")
    private String diaChi;

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Column(name = "QueQuan")
    private String queQuan;

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    @Column(name = "NgayBatDauDiLam")
    private Date ngayBatDauDiLam;

    public Date getNgayBatDauDiLam() {
        return ngayBatDauDiLam;
    }

    public void setNgayBatDauDiLam(Date ngayBatDauDiLam) {
        this.ngayBatDauDiLam = ngayBatDauDiLam;
    }

    @Column(name = "TenTaiKhoan")
    private String tenTaiKhoan;

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    @Column(name = "MatKhau", columnDefinition = "VARCHAR(1000)")
    private String matKhau;

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Column(name = "LuongThang")
    private float luongThang;

    public float getLuongThang() {
        return luongThang;
    }

    public void setLuongThang(float luongThang) {
        this.luongThang = luongThang;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDLoaiNhanVien", nullable = false, referencedColumnName = "IDLoaiNhanVien",
            foreignKey = @ForeignKey(name = "FK_NhanVien_LoaiNhanVien"))
    private LoaiNhanVien loaiNhanVien;

    public LoaiNhanVien getLoaiNhanVien() {
        return loaiNhanVien;
    }

    public void setLoaiNhanVien(LoaiNhanVien loaiNhanVien) {
        this.loaiNhanVien = loaiNhanVien;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nhanVien", cascade = CascadeType.REMOVE)
    private Set<PhieuDatPhong> dsPhieuDatPhong;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nhanVien", cascade = CascadeType.REMOVE)
    private Set<HoaDonThanhToan> dsHoaDonThanhToan;
}
