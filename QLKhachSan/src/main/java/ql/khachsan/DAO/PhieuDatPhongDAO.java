package ql.khachsan.DAO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ql.khachsan.App;
import ql.khachsan.models.PhieuDatPhong;

import java.util.List;

public class PhieuDatPhongDAO {
    public static int addPhieu(PhieuDatPhong phieuDatPhong) {
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
            list = session.createNativeQuery("select * " +
                    "from phieudatphong pdp " +
                    "where pdp.idphong = :id and pdp.idphieudatphong = (select max(pdp2.idphieudatphong) from phieudatphong pdp2 where pdp2.idphong = pdp.idphong)", PhieuDatPhong.class).setParameter("id", id).getResultList();
            for (PhieuDatPhong p : list)
                Hibernate.initialize(p.getKhachHang());

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
