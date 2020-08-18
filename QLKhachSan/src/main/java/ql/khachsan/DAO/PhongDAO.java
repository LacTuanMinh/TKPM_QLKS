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

    public static List<Object[]> getBaoCaoDoanhThu(int watchType, int year, int quarter, int month, Date date) {
        Session session = App.sessionFactory.getCurrentSession();
        List<Object[]> list = null;
        try {
            session.getTransaction().begin();

            int from = 0;
            int to = 0;
            if (quarter == 1) {
                from = 1;
                to = 3;
            }
            else if (quarter == 2) {
                from = 4;
                to = 6;
            }
            else if (quarter == 3) {
                from = 7;
                to = 9;
            }
            else if (quarter == 4) {
                from = 10;
                to = 12;
            }

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT P.IDPhong, P.TenPhong, LP.TenLoaiPhong, CAST(COUNT(PDP.IDPhong) AS CHAR(32)) AS SoLuotThue, " +
                    "CAST(IFNULL(SUM(PDP.TongTien), 0) AS CHAR(32)) AS DoanhThu FROM Phong P LEFT JOIN (SELECT PDP.* FROM PhieuDatPhong PDP ");

            // Xem theo ngày
            if (watchType == 1) {
                sql.append("WHERE PDP.NgayThue = :date) AS PDP ON P.IDPhong = PDP.IDPhong " +
                        "JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDPhong ORDER BY P.IDPhong");
                list = session.createNativeQuery(sql.toString()).setParameter("date", date).list();
            }
            // Xem theo tháng
            else if (watchType == 2) {
                sql.append("WHERE MONTH(PDP.NgayThue) = :month AND YEAR(PDP.NgayThue) = :year) AS PDP " +
                        "ON P.IDPhong = PDP.IDPhong JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong " +
                        "GROUP BY P.IDPhong ORDER BY P.IDPhong");
                list = session.createNativeQuery(sql.toString())
                        .setParameter("month", month)
                        .setParameter("year", year)
                        .list();
            }
            // Xem theo quý
            else if (watchType == 3) {
                sql.append("WHERE MONTH(PDP.NgayThue) >= :from AND MONTH(PDP.NgayThue) <= :to AND " +
                        "YEAR(PDP.NgayThue) = :year) AS PDP ON P.IDPhong = PDP.IDPhong JOIN " +
                        "LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDPhong ORDER BY P.IDPhong");
                list = session.createNativeQuery(sql.toString())
                        .setParameter("from", from)
                        .setParameter("to", to)
                        .setParameter("year", year)
                        .list();
            }
            // Xem theo năm
            else if (watchType == 4) {
                sql.append("WHERE YEAR(PDP.NgayThue) = :year) AS PDP ON P.IDPhong = PDP.IDPhong " +
                        "JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong " +
                        "GROUP BY P.IDPhong ORDER BY P.IDPhong");
                list = session.createNativeQuery(sql.toString()).setParameter("year", year).list();
            }
            // Xem tất cả
            else {
                sql.append(") AS PDP ON P.IDPhong = PDP.IDPhong JOIN LoaiPhong LP " +
                        "ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDPhong ORDER BY P.IDPhong");
                list = session.createNativeQuery(sql.toString()).list();
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
