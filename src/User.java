import java.sql.*;

public class User {
    private String username;
    private String password;
    private String port = "100.75.220.21";

    public User(String usename, String password) {
        this.username = usename;
        this.password = password;
    }

    public Connection makeConnection(String database) throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:postgresql://" + port + ":5432/" + database,
                username, password);

        return c;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
