package ql.khachsan.models.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ql.khachsan.App;
import ql.khachsan.models.entities.PhieuDatPhong;

import java.util.List;

public class PhieuDatPhongDAO {
    public static int addOrUpdatePhieu(PhieuDatPhong phieuDatPhong) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(phieuDatPhong);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return phieuDatPhong.getIdPhieuDatPhong();
        }
    }

    public static PhieuDatPhong getPhieuDatPhongByIDPhong(int id) {
        Session session = App.sessionFactory.getCurrentSession();
        List<PhieuDatPhong> list = null;
        try {
            Transaction tx = session.beginTransaction();

            String sql = "SELECT PDP FROM PhieuDatPhong PDP JOIN FETCH PDP.khachHang KH " +
                    "JOIN FETCH PDP.phong P JOIN FETCH PDP.nhanVien NV JOIN FETCH P.loaiPhong LP " +
                    "WHERE P.idPhong = :id AND PDP.idPhieuDatPhong = " +
                    "(SELECT MAX(PDP2.idPhieuDatPhong) FROM PhieuDatPhong PDP2 WHERE PDP.phong = PDP2.phong)";
            list = session.createQuery(sql, PhieuDatPhong.class)
                    .setParameter("id", id).getResultList();

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list.size() == 0 ? null : list.get(0);
        }
    }
}
