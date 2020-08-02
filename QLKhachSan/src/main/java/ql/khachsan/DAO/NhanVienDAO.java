package ql.khachsan.DAO;

import org.hibernate.Session;
import ql.khachsan.App;
import ql.khachsan.models.NhanVien;

import java.util.List;

public class NhanVienDAO {

    public static Object[] getIdAndPasswordByUserName(String username) {
        Session session = App.sessionFactory.getCurrentSession();
        List<Object[]> objects = null;
        try {
            session.getTransaction().begin();
            objects = session.createNativeQuery(
                    "select idnhanvien, matkhau from nhanvien where tentaikhoan = :username").setParameter("username", username).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return objects.size() == 0 ? null : objects.get(0);
        }
    }

    public static NhanVien getNhanVienById(int id) {
        Session session = App.sessionFactory.getCurrentSession();
        List<NhanVien> list = null;
        try {
            session.getTransaction().begin();
            list = session.createNativeQuery(
                    "select * " +
                            "from nhanvien " +
                            "where idnhanvien = :id", NhanVien.class).setParameter("id", id).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list == null ? null : list.get(0);
        }
    }
}
