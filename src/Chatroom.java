import java.sql.*;

public class Chatroom {

	public static void joinChatroom(String name) {
		Connection c = null;
		Statement stmt = null;
		boolean found = false;

		try {
			// connect to database
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbcpostgresql://localhost:5432/usersdb",
					"postgres", "12345");

			// check that room name exists
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from database");
			while (rs.next()) {
				String index = rs.getString("NAME");
				if (index.equals(name)) {
					found = true;
					break;
				}
			}
			stmt.close();
			c.close();

			if (found) {

			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static void createChatroom(String name) throws Exception {

		Connection c = null;
		Statement stmt = null;

		// Check that name is valid
		for (int i = 0; i < name.length(); ++i) {
			if (!Character.isLowerCase(name.charAt(i)) && !Character.isDigit(name.charAt(i))) {
				throw new Exception();
			}
		}

		try {
			// connect to database
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbcpostgresql://localhost:5432/usersdb",
					"postgres", "12345");
			// or replace password

			// Insert new chatroom
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "INSERT INTO CHATROOM( "
					+ "NAME) "
					+ "VALUES(name)";
			stmt.executeLargeUpdate(sql);

			// ask? how to set name variable

			// close and finish
			stmt.close();
			c.close();
			System.out.println("Chatroom \"" + name + "\" Created");
			joinChatroom(name);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

}
