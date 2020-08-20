package ql.khachsan.DAO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ql.khachsan.App;
import ql.khachsan.models.PhieuDatPhong;
import ql.khachsan.models.LoaiPhong;
import ql.khachsan.models.Phong;

import java.util.Date;
import java.util.List;

public class PhongDAO {
    public static List<Phong> getAllPhong() {
        Session session = App.sessionFactory.getCurrentSession();
        List<Phong> list = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT P FROM Phong P JOIN FETCH P.loaiPhong LP";
            list = session.createQuery(sql, Phong.class).list();

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static List<String> getAllTenPhong() {
        Session session = App.sessionFactory.getCurrentSession();
        List<String> list = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT P.tenPhong FROM Phong P";
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

    public static void add(Phong phong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.persist(phong);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void update(Phong phong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.update(phong);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void delete(Phong phong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.delete(phong);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
