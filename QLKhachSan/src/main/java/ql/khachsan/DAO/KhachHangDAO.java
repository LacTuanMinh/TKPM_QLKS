package ql.khachsan.DAO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ql.khachsan.App;
import ql.khachsan.models.KhachHang;
import ql.khachsan.models.Phong;

import java.util.List;

public class KhachHangDAO {

    public static int addKhachHang(KhachHang khachHang){
        Session session = App.sessionFactory.getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(khachHang);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return khachHang.getIdKhachHang();
        }
    }
}
