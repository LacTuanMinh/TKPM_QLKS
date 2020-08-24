package ql.khachsan.models.DAO;

import org.hibernate.Session;
import ql.khachsan.App;
import ql.khachsan.models.entities.HoaDonThanhToan;

public class HoaDonThanhToanDAO {
    public static void add(HoaDonThanhToan hoaDonThanhToan) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.persist(hoaDonThanhToan);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
