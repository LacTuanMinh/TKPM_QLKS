package ql.khachsan.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ql.khachsan.App;
import ql.khachsan.models.KhachHang;

import java.util.List;

public class KhachHangDAO {

    public static int addOrUpdateKhachHang(KhachHang khachHang) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(khachHang);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return khachHang.getIdKhachHang();
        }
    }

    public static KhachHang getKhachHangByCMND(String cmnd) {
        Session session = App.sessionFactory.getCurrentSession();
        List<KhachHang> list = null;
        try {
            Transaction tx = session.beginTransaction();
            list = session.createNativeQuery(
                    "select * from khachhang where cmnd = :cmnd", KhachHang.class).setParameter("cmnd", cmnd).getResultList();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list.size() == 0? null : list.get(0);
        }
    }
}
