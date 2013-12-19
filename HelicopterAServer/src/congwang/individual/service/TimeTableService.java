package congwang.individual.service;

import congwang.individual.model.TimeTable;
import congwang.individual.util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeTableService {

	public void add(TimeTable t) {
		Connection conn = DB.createConnection();
		String sql = "insert into h_timetable values (?,?,?,?,?,?,?)";
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, t.getFlightNum());
			ps.setString(2, t.getLeftTime());
			ps.setString(3, t.getArriveTime());
			ps.setString(4, t.getDestination());
			ps.setString(5, t.getCompany());
			ps.setString(6, t.getLeftDate());
			ps.setInt(7, t.getLeftSeat());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
	}

	public List<TimeTable> list() {
		Connection conn = DB.createConnection();
		String sql = "select * from h_timetable";
		PreparedStatement ps = DB.prepare(conn, sql);
		List<TimeTable> timetable = new ArrayList<TimeTable>();
		try {
			ResultSet rs = ps.executeQuery();
			TimeTable t = null;
			while (rs.next()) {
				t = new TimeTable();
				t.setFlightNum(rs.getString("flightNum"));
				t.setLeftTime(rs.getString("leftTime"));
				t.setArriveTime(rs.getString("arriveTime"));
				t.setDestination(rs.getString("destination"));
				t.setCompany(rs.getString("company"));
				t.setLeftDate(rs.getString("leftDate"));
				t.setLeftSeat(rs.getInt("leftSeat"));
				timetable.add(t);
			}
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
		return timetable;
	}

	public List<TimeTable> listByDateAndDest(String s1, String s2) {
		Connection conn = DB.createConnection();
		String sql = "select * from h_timetable where leftDate = ? and destination = ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		List<TimeTable> timetable = new ArrayList<>();
		try {
			ps.setString(1, s1);
			ps.setString(2, s2);
			ResultSet rs = ps.executeQuery();
			TimeTable t = null;
			while (rs.next()) {
				t = new TimeTable();
				t.setFlightNum(rs.getString("flightNum"));
				t.setLeftTime(rs.getString("leftTime"));
				t.setArriveTime(rs.getString("arriveTime"));
				t.setDestination(rs.getString("destination"));
				t.setCompany(rs.getString("company"));
				t.setLeftDate(rs.getString("leftDate"));
				t.setLeftSeat(rs.getInt("leftSeat"));
				timetable.add(t);
			}
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
		return timetable;
	}

	public void deleteByFlightNum(String flightNum) {
		Connection conn = DB.createConnection();
		String sql = "delete from h_timetable where flightNum = ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, flightNum);
			ps.executeUpdate();
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
	}

	public void delete(TimeTable t) {
		deleteByFlightNum(t.getFlightNum());
	}

	public void update(TimeTable t) {
		Connection conn = DB.createConnection();
		String sql = "update h_timetable set leftTime = ?,arriveTime = ?,destination = ?,company= ?"
				+ ",leftSeat= ? where flightNum = ?and leftDate= ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, t.getLeftTime());
			ps.setString(2, t.getArriveTime());
			ps.setString(3, t.getDestination());
			ps.setString(4, t.getCompany());
			ps.setInt(5, t.getLeftSeat());
			ps.setString(6, t.getFlightNum());
			ps.setString(7, t.getLeftDate());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
	}

	public TimeTable loadById(String flightNum) {
		Connection conn = DB.createConnection();
		String sql = "select * from h_timetable where flightNum = ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		TimeTable t = null;
		try {
			ps.setString(1, flightNum);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				t = new TimeTable();
				t.setFlightNum(rs.getString("flightNum"));
				t.setLeftTime(rs.getString("leftTime"));
				t.setArriveTime(rs.getString("arriveTime"));
				t.setDestination(rs.getString("destination"));
				t.setCompany(rs.getString("company"));
				t.setLeftDate(rs.getString("leftDate"));
				t.setLeftSeat(rs.getInt("leftSeat"));
			}
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
		return t;
	}

	public TimeTable loadByFlightNoAndDate(String flightNum, String leftDate) {
		Connection conn = DB.createConnection();
		String sql = "select * from h_timetable where flightNum = ? and leftDate = ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		TimeTable t = null;
		try {
			ps.setString(1, flightNum);
			ps.setString(2, leftDate);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				t = new TimeTable();
				t.setFlightNum(rs.getString("flightNum"));
				t.setLeftTime(rs.getString("leftTime"));
				t.setArriveTime(rs.getString("arriveTime"));
				t.setDestination(rs.getString("destination"));
				t.setCompany(rs.getString("company"));
				t.setLeftDate(rs.getString("leftDate"));
				t.setLeftSeat(rs.getInt("leftSeat"));
			}
		} catch (SQLException e) {
			System.out.println("wrong data.");
		}
		DB.close(ps);
		DB.close(conn);
		return t;
	}
}