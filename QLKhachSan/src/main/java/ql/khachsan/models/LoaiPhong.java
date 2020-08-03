package ql.khachsan.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "LoaiPhong")
public class LoaiPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLoaiPhong")
    private int idLoaiPhong;

    public void setIdLoaiPhong(int idLoaiPhong) {
        this.idLoaiPhong = idLoaiPhong;
    }

    public int getIdLoaiPhong() {
        return idLoaiPhong;
    }

    @Column(name = "TenLoaiPhong")
    private String tenLoaiPhong;

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    @Column(name = "SoNguoiToiDa")
    private int soNguoiToiDa;

    public int getSoNguoiToiDa() {
        return soNguoiToiDa;
    }

    public void setSoNguoiToiDa(int soNguoiToiDa) {
        this.soNguoiToiDa = soNguoiToiDa;
    }

    @Column(name = "Gia")
    private float gia;

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiPhong", cascade = CascadeType.REMOVE)
    private Set<Phong> dsPhong;
}
