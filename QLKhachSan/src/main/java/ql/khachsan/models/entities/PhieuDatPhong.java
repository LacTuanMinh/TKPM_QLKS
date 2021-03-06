package ql.khachsan.models.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PhieuDatPhong")
public class PhieuDatPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPhieuDatPhong")
    private int idPhieuDatPhong;

    public void setIdPhieuDatPhong(int idPhieuDatPhong) {
        this.idPhieuDatPhong = idPhieuDatPhong;
    }

    public int getIdPhieuDatPhong() {
        return idPhieuDatPhong;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPhong", referencedColumnName = "IDPhong",
            foreignKey = @ForeignKey(name = "FK_PhieuDatPhong_Phong"))
    private Phong phong;

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDNhanVien", referencedColumnName = "IDNhanVien",
            foreignKey = @ForeignKey(name = "FK_PhieuDatPhong_NhanVien"))
    private NhanVien nhanVien;

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKhachHang", referencedColumnName = "IDKhachHang",
            foreignKey = @ForeignKey(name = "FK_PhieuDatPhong_KhachHang"))
    private KhachHang khachHang;

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    @Column(name = "NgayThue")
    private Date ngayThue;

    public Date getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(Date ngayThue) {
        this.ngayThue = ngayThue;
    }

    @Column(name = "NgayTra")
    private Date ngayTra;

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    @Column(name = "TongTien")
    private Float tongTien;

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(Float TongTien) {
        this.tongTien = TongTien;
    }

    @Column(name = "HasDiscount")
    private boolean hasDisCount;

    public boolean isHasDisCount() {
        return hasDisCount;
    }

    public void setHasDisCount(boolean hasDisCount) {
        this.hasDisCount = hasDisCount;
    }

    public PhieuDatPhong() {
    }

    public PhieuDatPhong(Phong phong, NhanVien nhanVien, KhachHang khachHang,
                         Date ngayThue, Date ngayTra,Float tongTien, boolean hasDisCount) {
        this.phong = phong;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.hasDisCount = hasDisCount;
    }
}
