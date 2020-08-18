package ql.khachsan.DAO;

import org.hibernate.Session;
import ql.khachsan.App;
import ql.khachsan.models.LoaiNhanVien;
import ql.khachsan.models.NhanVien;

import java.util.List;

public class NhanVienDAO {

    public static Object[] getIdAndPasswordByUserName(String username) {
        Session session = App.sessionFactory.getCurrentSession();
        List<Object[]> objects = null;
        try {
            session.getTransaction().begin();
            String sql = "SELECT IDNhanVien, MatKhau FROM NhanVien WHERE TenTaiKhoan = :username";
            objects = session.createNativeQuery(sql).setParameter("username", username).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return objects.size() == 0 ? null : objects.get(0);
        }
    }

    public static NhanVien getNhanVienById(int id) {
        Session session = App.sessionFactory.getCurrentSession();
        List<NhanVien> list = null;
        try {
            session.getTransaction().begin();
            String sql = "SELECT NV FROM NhanVien NV JOIN FETCH NV.loaiNhanVien LNV WHERE NV.idNhanVien = :id";
            list = session.createQuery(sql, NhanVien.class).setParameter("id", id).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list == null ? null : list.get(0);
        }
    }

    public static List<NhanVien> getAllNhanVien() {
        Session session = App.sessionFactory.getCurrentSession();
        List<NhanVien> list = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT NV FROM NhanVien NV JOIN FETCH NV.loaiNhanVien LNV ORDER BY NV.idNhanVien";
            list = session.createQuery(sql, NhanVien.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static List<String> getAllTenTaiKhoan() {
        Session session = App.sessionFactory.getCurrentSession();
        List<String> list = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT NV.tenTaiKhoan FROM NhanVien NV";
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

    public static void add(NhanVien nhanVien) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.persist(nhanVien);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void update(NhanVien nhanVien) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.update(nhanVien);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void delete(NhanVien nhanVien) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            session.delete(nhanVien);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void updateLuongAndChucVu(int id, float luong, LoaiNhanVien loaiNhanVien) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            NhanVien nhanVien = session.get(NhanVien.class, id);

            nhanVien.setLuongThang(luong);
            nhanVien.setLoaiNhanVien(loaiNhanVien);

            session.update(nhanVien);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void updatePassword(int id, String password) {
        Session session = App.sessionFactory.getCurrentSession();
        try {
            session.getTransaction().begin();

            NhanVien nhanVien = session.get(NhanVien.class, id);

            nhanVien.setMatKhau(password);

            session.update(nhanVien);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
