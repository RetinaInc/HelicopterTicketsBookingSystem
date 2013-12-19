package congwang.individual.service;

import congwang.individual.model.addCustomer;
import congwang.individual.util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class addCustomerService {

	public void add(addCustomer add) {
		Connection conn = DB.createConnection();
		String sql = "insert into t_addcustomer values (?,?)";

		// save customerinfo into database
		PreparedStatement ps = DB.prepare(conn, sql);
		try {
			ps.setString(1, add.getTicketNum());
			ps.setString(2, add.getPassport());
			ps.executeUpdate();
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);

	}

	public List<addCustomer> list() {
		Connection conn = DB.createConnection();
		String sql = "select * from t_addcustomer";
		PreparedStatement ps = DB.prepare(conn, sql);
		List<addCustomer> addCustomer = new ArrayList<>();
		try {
			ResultSet rs = ps.executeQuery();
			addCustomer add = null;
			while (rs.next()) {
				add = new addCustomer();
				add.setTicketNum(rs.getString("ticketNum"));
				add.setPassport(rs.getString("passport"));
				addCustomer.add(add);
			}
		} catch (SQLException e) {
		}
		DB.close(ps);
		DB.close(conn);
		return addCustomer;
	}

}