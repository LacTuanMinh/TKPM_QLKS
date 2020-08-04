package ql.khachsan.DAO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ql.khachsan.App;
import ql.khachsan.models.PhieuDatPhong;
import ql.khachsan.models.Phong;

import java.util.List;

public class PhongDAO {

    public static List<Phong> getPhong() {
        Session session = App.sessionFactory.getCurrentSession();
        List<Phong> list = null;
        try {
            session.getTransaction().begin();
            list = session.createNativeQuery(
                    "select * from phong ",Phong.class).list();
            for(Phong p : list)
            {
                Hibernate.initialize(p.getLoaiPhong());
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static void updatePhong(Phong phong){
        Session session = App.sessionFactory.getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(phong);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
