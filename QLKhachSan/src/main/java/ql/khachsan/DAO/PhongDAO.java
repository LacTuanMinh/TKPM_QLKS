package ql.khachsan.DAO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import ql.khachsan.App;
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
}
