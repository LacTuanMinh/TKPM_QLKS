package ql.khachsan.models.DAO;

import org.hibernate.Session;
import ql.khachsan.App;
import ql.khachsan.models.entities.LoaiNhanVien;

import java.util.List;

public class LoaiNhanVienDAO {
    public static List<String> getTenLoaiNhanVienCol() {
        Session session = App.sessionFactory.getCurrentSession();
        List<String> list = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT LNV.tenLoaiNhanVien FROM LoaiNhanVien LNV";
            list = session.createQuery(sql, String.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static List<LoaiNhanVien> getAllLoaiNhanVien() {
        Session session = App.sessionFactory.getCurrentSession();
        List<LoaiNhanVien> list = null;
        try {
            session.getTransaction().begin();

            String sql = "FROM LoaiNhanVien";
            list = session.createQuery(sql, LoaiNhanVien.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static LoaiNhanVien getOneById(int id) {
        Session session = App.sessionFactory.getCurrentSession();
        LoaiNhanVien loaiNhanVien = null;
        try {
            session.getTransaction().begin();

            String sql = "FROM LoaiNhanVien LNV WHERE LNV.idLoaiNhanVien = :id";
            loaiNhanVien = session.createQuery(sql, LoaiNhanVien.class).
                    setParameter("id", id).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return loaiNhanVien;
        }
    }
}
