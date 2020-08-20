package ql.khachsan.DAO;

import com.itextpdf.text.pdf.PdfPTable;
import org.hibernate.Session;
import ql.khachsan.App;
import ql.khachsan.models.LoaiPhong;

import java.util.Date;
import java.util.List;

public class LoaiPhongDAO {
    public static List<LoaiPhong> getAllLoaiPhong() {
        Session session = App.sessionFactory.getCurrentSession();
        List<LoaiPhong> list = null;
        try {
            session.getTransaction().begin();

            String sql = "FROM LoaiPhong";
            list = session.createQuery(sql, LoaiPhong.class).list();

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static void add(LoaiPhong loaiPhong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.persist(loaiPhong);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void update(LoaiPhong loaiPhong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.update(loaiPhong);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void delete(LoaiPhong loaiPhong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.delete(loaiPhong);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
