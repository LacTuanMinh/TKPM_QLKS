package ql.khachsan.DAO;

import org.hibernate.Session;
import ql.khachsan.App;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DoanhThuDAO {
    public static List<Object[]> getBaoCaoDoanhThuTheoPhong(int watchType, int year, int quarter, int month, Date date) {
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

    public static List<Object[]> getBaoCaoDoanhThuTheoLoaiPhong(int watchType, int year, int quarter, int month, Date date) {
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
                    "CAST(IFNULL(SUM(PDP.TongTien), 0) AS CHAR(32)) AS DoanhThu FROM " +
                    "Phong P LEFT JOIN (SELECT PDP.* FROM PhieuDatPhong PDP ");

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

    public static List<Object[]> getDoanhThuTrong1NamTheoTungThang(int year) {
        Session session = App.sessionFactory.getCurrentSession();
        List<Object[]> list = null;
        try {
            session.getTransaction().begin();

            String sql = "SELECT Months.Month AS Thang, CAST(IFNULL(SUM(Temp.SoLuong), 0) AS CHAR(32)) AS SoLuotThue, " +
                    "CAST(IFNULL(SUM(Temp.DoanhThu), 0) AS CHAR(32)) AS DoanhThu\n" +
                    "FROM (\n" +
                    "\tSELECT DATE_FORMAT(NOW(), '%m') AS Month\n" +
                    "\tUNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 2 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 3 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 4 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 5 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 6 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 7 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 8 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 9 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 10 MONTH), '%m')\n" +
                    "    UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 11 MONTH), '%m')\n" +
                    ") AS Months LEFT JOIN (\n" +
                    "\tSELECT DATE_FORMAT(PDP.NgayThue, \"%m\") AS Thang, LP.TenLoaiPhong, COUNT(PDP.IDPhong) AS SoLuong, SUM(PDP.TongTien) AS DoanhThu\n" +
                    "\tFROM Phong P JOIN (\n" +
                    "\t\tSELECT * FROM PhieuDatPhong PDP WHERE YEAR(PDP.NgayThue) = :year\n" +
                    "\t) AS PDP ON PDP.IDPhong = P.IDPhong JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong\n" +
                    "\tGROUP BY P.IDLoaiPhong, DATE_FORMAT(PDP.NgayThue, \"%m\")\n" +
                    "\tORDER BY PDP.NgayThue\n" +
                    ") AS Temp ON Months.Month = Temp.Thang GROUP BY Months.Month ORDER BY Months.Month";

            list = session.createNativeQuery(sql).setParameter("year", year).list();

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static List<Object[]> getDoanhThuTrong1Thang(int year, int month) {
        Session session = App.sessionFactory.getCurrentSession();
        List<Object[]> list = null;
        try {
            session.getTransaction().begin();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder date = new StringBuilder();
            date.append("01/");
            date.append(month);
            date.append("/");
            date.append(year);

            String sql = "SELECT DATE_FORMAT(Dates.Date, \"%d/%m/%Y\") AS Ngay, CAST(IFNULL(SUM(Temp.SoLuong), 0) AS CHAR(32)) AS SoLuotThue, CAST(IFNULL(SUM(Temp.DoanhThu), 0) AS CHAR(32)) AS DoanhThu\n" +
                    "FROM (\n" +
                    "    SELECT LAST_DAY(:date) - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY AS Date\n" +
                    "    FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a\n" +
                    "    CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b\n" +
                    "    CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c\n" +
                    ") AS Dates LEFT JOIN (\n" +
                    "\tSELECT PDP.NgayThue AS Ngay, LP.TenLoaiPhong, COUNT(PDP.IDPhong) AS SoLuong, SUM(PDP.TongTien) AS DoanhThu\n" +
                    "\tFROM Phong P JOIN (\n" +
                    "\t\tSELECT * \n" +
                    "        FROM PhieuDatPhong PDP \n" +
                    "        WHERE YEAR(PDP.NgayThue) = :year AND MONTH(PDP.NgayThue) = :month\n" +
                    "\t) AS PDP ON PDP.IDPhong = P.IDPhong JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong\n" +
                    "\tGROUP BY P.IDLoaiPhong, PDP.NgayThue\n" +
                    "\tORDER BY PDP.NgayThue\n" +
                    ") AS Temp ON Temp.Ngay = Dates.Date\n" +
                    "WHERE Dates.Date BETWEEN :date AND LAST_DAY(:date) \n" +
                    "GROUP BY Dates.Date ORDER BY Dates.Date";

            list = session.createNativeQuery(sql)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .setParameter("date", format.parse(date.toString()))
                    .list();

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            return list;
        }
    }

    public static List<Object[]> getDoanhThuGiuaCacNam(int fromYear, int toYear) {
        Session session = App.sessionFactory.getCurrentSession();
        List<Object[]> list = null;
        try {
            session.getTransaction().begin();

            int gap = toYear - fromYear;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT Years.Year AS Nam, CAST(IFNULL(SUM(Temp.SoLuong), 0) AS CHAR(32)) AS SoLuotThue, " +
                    "CAST(IFNULL(SUM(Temp.DoanhThu), 0) AS CHAR(32)) AS DoanhThu " +
                    "FROM (SELECT DATE_FORMAT(NOW(), '%Y') AS Year ");

            for (int i = 0; i < gap; i++) {
                sql.append("UNION SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL ");
                sql.append(i + 1);
                sql.append(" YEAR), '%Y')");
            }

            sql.append(") AS Years LEFT JOIN (SELECT YEAR(PDP.NgayThue) AS Nam, LP.TenLoaiPhong, " +
                    "COUNT(PDP.IDPhong) AS SoLuong, SUM(PDP.TongTien) AS DoanhThu " +
                    "FROM Phong P JOIN (SELECT * FROM PhieuDatPhong PDP) AS PDP " +
                    "ON PDP.IDPhong = P.IDPhong JOIN LoaiPhong LP ON P.IDLoaiPhong = LP.IDLoaiPhong " +
                    "GROUP BY P.IDLoaiPhong, YEAR(PDP.NgayThue) ORDER BY PDP.NgayThue) AS Temp ON " +
                    "Temp.Nam = Years.Year GROUP BY Years.Year ORDER BY Years.Year");

            list = session.createNativeQuery(sql.toString()).list();

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
