package ql.khachsan.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "LoaiNhanVien")
public class LoaiNhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLoaiNhanVien")
    private int idLoaiNhanVien;

    public int getIdLoaiNhanVien() {
        return idLoaiNhanVien;
    }

    public void setIdLoaiNhanVien(int idLoaiNhanVien) {
        this.idLoaiNhanVien = idLoaiNhanVien;
    }

    @Column(name = "TenLoaiNhanVien")
    private String tenLoaiNhanVien;

    public String getTenLoaiNhanVien() {
        return tenLoaiNhanVien;
    }

    public void setTenLoaiNhanVien(String tenLoaiNhanVien) {
        this.tenLoaiNhanVien = tenLoaiNhanVien;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiNhanVien", cascade = CascadeType.REMOVE)
    private Set<NhanVien> dsNhanVien;
}
