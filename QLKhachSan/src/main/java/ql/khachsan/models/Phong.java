package ql.khachsan.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Phong")
public class Phong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPhong")
    private int idPhong;

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public int getIdPhong() {
        return idPhong;
    }

    @Column(name = "TenPhong")
    private String tenPhong;

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    @Column(name = "TrangThai")
    public int trangThai;

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDLoaiPhong", nullable = false, referencedColumnName = "IDLoaiPhong",
            foreignKey = @ForeignKey(name = "FK_Phong_LoaiPhong"))
    private LoaiPhong loaiPhong;

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phong", cascade = CascadeType.REMOVE)
    private Set<PhieuDatPhong> dsPhieuDatPhong;
}
