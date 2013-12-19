package congwang.individual.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	public static Connection createConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/companyDatabase",
							"root", "w89620c");
		} catch (SQLException | ClassNotFoundException e) {
		}
		return conn;
	}

	public static PreparedStatement prepare(Connection conn, String sql) {
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
		} catch (SQLException e) {
		}
		return ps;
	}

	public static void close(Connection conn) {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
		}

	}

	public static void close(PreparedStatement stmt) {
		try {
			stmt.close();
			stmt = null;
		} catch (SQLException e) {
		}

	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
			rs = null;
		} catch (SQLException e) {
		}

	}

}