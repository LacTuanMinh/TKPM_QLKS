package ql.khachsan.models;

import javax.persistence.*;

@Entity
@Table(name = "ThamSo")
public class ThamSo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "PhanTram10Ngay")
    private float phanTram10Ngay;

    public float getPhanTram10Ngay() {
        return phanTram10Ngay;
    }

    public void setPhanTram10Ngay(float phanTram10Ngay) {
        this.phanTram10Ngay = phanTram10Ngay;
    }

    @Column(name = "PhanTram20Ngay")
    private float phanTram20Ngay;

    public float getPhanTram20Ngay() {
        return phanTram20Ngay;
    }

    public void setPhanTram20Ngay(float phanTram20Ngay) {
        this.phanTram20Ngay = phanTram20Ngay;
    }

    @Column(name = "IsDiable")
    private boolean isDisable;

    public boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(boolean disable) {
        isDisable = disable;
    }
}
