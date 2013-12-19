package congwang.individual.service;

import congwang.individual.model.deleteCustomer;
import congwang.individual.util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class deleteCustomerService {

	public void add(deleteCustomer delete) {
		Connection conn = DB.createConnection();
		String sql = "insert into t_deletecustomer values (?,?)";

		// save customerinfo into database
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, delete.getTicketNum());
			ps.setString(2, delete.getPassport());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);

	}

	public List<deleteCustomer> list() {
		Connection conn = DB.createConnection();
		String sql = "select * from t_deletecustomer";
		PreparedStatement ps = DB.prepare(conn, sql);
		List<deleteCustomer> deleteCustomer = new ArrayList<>();
		try {
			ResultSet rs = ps.executeQuery();
			deleteCustomer delete = null;
			while (rs.next()) {
				delete = new deleteCustomer();
				delete.setTicketNum(rs.getString("ticketNum"));
				delete.setPassport(rs.getString("passport"));
				deleteCustomer.add(delete);
			}
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
		return deleteCustomer;
	}

}