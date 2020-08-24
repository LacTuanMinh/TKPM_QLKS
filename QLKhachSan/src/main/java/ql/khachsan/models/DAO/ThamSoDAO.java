package ql.khachsan.models.DAO;

import org.hibernate.Session;
import ql.khachsan.App;
import ql.khachsan.models.entities.ThamSo;

import java.util.List;

public class ThamSoDAO {

    public static ThamSo getThamSo() {
        Session session = App.sessionFactory.getCurrentSession();
        List<ThamSo> result = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT TS FROM ThamSo TS";
            result = session.createQuery(sql, ThamSo.class).list();

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return result.size() == 0 ? null : result.get(0);
        }
    }

    public static void update(ThamSo thamSo) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.update(thamSo);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
