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
            sql.append("SELECT LP.IDLoaiPhong, LP.TenLoaiPhong, CAST(COUNT(PDP.IDPhong) AS CHAR(32)) AS SoLuotThue, " +
                    "CAST(IFNULL(SUM(PDP.TongTien), 0) AS CHAR(32)) AS DoanhThu FROM Phong P LEFT JOIN (SELECT PDP.* FROM PhieuDatPhong PDP ");

            // Xem theo ngày
            if (watchType == 1) {
                sql.append("WHERE PDP.NgayThue = :date) AS PDP ON P.IDPhong = PDP.IDPhong JOIN " +
                        "LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDLoaiPhong");
                list = session.createNativeQuery(sql.toString()).setParameter("date", date).list();
            }
            // Xem theo tháng
            else if (watchType == 2) {
                sql.append("WHERE MONTH(PDP.NgayThue) = :month AND YEAR(PDP.NgayThue) = :year) AS PDP " +
                        "ON P.IDPhong = PDP.IDPhong JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong " +
                        "GROUP BY P.IDLoaiPhong");
                list = session.createNativeQuery(sql.toString())
                        .setParameter("month", month)
                        .setParameter("year", year)
                        .list();
            }
            // Xem theo quý
            else if (watchType == 3) {
                sql.append("WHERE MONTH(PDP.NgayThue) >= :from AND MONTH(PDP.NgayThue) <= :to AND " +
                        "YEAR(PDP.NgayThue) = :year) AS PDP ON P.IDPhong = PDP.IDPhong JOIN LoaiPhong LP " +
                        "ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDLoaiPhong");
                list = session.createNativeQuery(sql.toString())
                        .setParameter("from", from)
                        .setParameter("to", to)
                        .setParameter("year", year)
                        .list();
            }
            // Xem theo năm
            else if (watchType == 4) {
                sql.append("WHERE YEAR(PDP.NgayThue) = :year) AS PDP ON P.IDPhong = PDP.IDPhong " +
                        "JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDLoaiPhong");
                list = session.createNativeQuery(sql.toString()).setParameter("year", year).list();
            }
            // Xem tất cả
            else {
                sql.append(") AS PDP ON P.IDPhong = PDP.IDPhong JOIN LoaiPhong LP " +
                        "ON P.IDLoaiPhong = LP.IDLoaiPhong GROUP BY P.IDLoaiPhong");
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
