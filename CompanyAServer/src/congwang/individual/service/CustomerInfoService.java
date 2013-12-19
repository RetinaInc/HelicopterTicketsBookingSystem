package congwang.individual.service;

import congwang.individual.model.CustomerInfo;
import congwang.individual.util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerInfoService {

	public void add(CustomerInfo c) {
		Connection conn = DB.createConnection();
		String sql = "insert into t_customerinfo values (?,?)";
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, c.getTicketNum());
			ps.setString(2, c.getPassport());
			ps.executeUpdate();
		} catch(SQLException e) {}
		DB.close(ps);
		DB.close(conn);
	}

	public void deleteByPassportAndTicketNum(String ticketNum, String passport) {
		Connection conn = DB.createConnection();
		String sql = "delete from t_customerinfo where ticketNum = ? and passport = ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, ticketNum);
			ps.setString(2, passport);
			ps.executeUpdate();
		} catch(SQLException e) {}
		DB.close(ps);
		DB.close(conn);
	}

	public void delete(CustomerInfo t) {
		deleteByPassportAndTicketNum(t.getTicketNum(), t.getPassport());
	}


	public CustomerInfo loadByPassportAndTicketNum(String ticketNum, String passport) {
		Connection conn = DB.createConnection();
		String sql = "select * from t_customerinfo where ticketNum = ?and passport = ?";
		PreparedStatement ps = DB.prepare(conn, sql);
		CustomerInfo c = null;
		try {
			ps.setString(1, ticketNum);
			ps.setString(2, passport);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				c = new CustomerInfo();
				c.setTicketNum(rs.getString("passport"));
				c.setTicketNum(rs.getString("ticketNum"));
			}
		} catch(SQLException e) {}
		DB.close(ps);
		DB.close(conn);
		return c;
	}
	public List < CustomerInfo > list() {
		Connection conn = DB.createConnection();
		String sql = "select * from t_CustomerInfo";
		PreparedStatement ps = DB.prepare(conn, sql);
		List < CustomerInfo > customerInfo = new ArrayList < > ();
		try {
			ResultSet rs = ps.executeQuery();
			CustomerInfo c = null;
			while(rs.next()) {
				c = new CustomerInfo();
				c.setTicketNum(rs.getString("ticketNum"));
				c.setPassport(rs.getString("passport"));
				customerInfo.add(c);
			}
		} catch(SQLException e) {}
		DB.close(ps);
		DB.close(conn);
		return customerInfo;
	}
}