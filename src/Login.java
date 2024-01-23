import java.sql.*;
import java.util.Scanner;

public class Login {
    static String port = "localhost";
    static String database = "userdb";
    static String adminPassword = "Canes1034";

    public static boolean validName(Connection c, String name) {
        try {
            boolean ans = true;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pg_user;");

            while (rs.next()) {
                if (name.equals(rs.getString("usename"))) {
                    System.out.println("Username not valid\n");
                    ans = false;
                    break;
                }
            }

            rs.close();
            stmt.close();
            return ans;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public static String register(Scanner scnr) throws Exception {
        Connection c = null;
        Statement stmt = null;
        String username, password;

        // Connnect to the dataBase
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection(
                "jdbc:postgresql://" + port + ":5432/" + database,
                "postgres", adminPassword);

        // Get a valid username
        System.out.print("Create Username: ");
        username = scnr.nextLine();

        // Get a password
        System.out.print("Create Password: ");
        password = scnr.nextLine();
        System.out.println();

        if (!validName(c, username)) {
            throw new Exception();
        }

        // Add username password to logins
        stmt = c.createStatement();
        String sql = "INSERT INTO LOGINS (username, password) "
                + "VALUES('" + username + "', '" + password + "')";
        stmt.execute(sql);
        stmt.close();

        login(c, username);
        c.close();

        return username;

    }

    public static String login(Scanner scnr) throws Exception {
        String username, password;

        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:postgresql://" + port + ":5432/" + database,
                "postgres", adminPassword);

        // Get a valid username
        System.out.print("Enter Username: ");
        username = scnr.nextLine();

        // Get a password
        System.out.print("Enter Password: ");
        password = scnr.nextLine();
        System.out.println();

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM logins;");

        while (rs.next()) {
            if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                login(c, username);

                rs.close();
                stmt.close();
                c.close();
                return username;
            }
        }

        rs.close();
        stmt.close();
        c.close();
        throw new Exception();
    }

    private static void login(Connection c, String username) throws Exception {
        Statement stmt = null;

        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection(
                "jdbc:postgresql://" + port + ":5432/" + database,
                "postgres", adminPassword);
        System.out.println("Welcome " + username + "!");

        stmt = c.createStatement();
        String sql = "INSERT INTO ACTIVE (username) "
                + "VALUES('" + username + "')";
        stmt.execute(sql);
        stmt.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to the Chatroom!\n");
        System.out.println("Select form one of the following options:");
        System.out.println("(R)egister, (L)ogin, (Q)uit\n");

        System.out.print("-");
        char input = scnr.nextLine().toLowerCase().charAt(0);

        String name;

        while (input != 'q') {
            System.out.println();
            if (input == 'r') {
                try {
                    name = register(scnr);
                    MainView.mainView(scnr);
                } catch (Exception e) {
                    System.out.println("ERROR! Username already exists.\n");
                }
            } else if (input == 'l') {
                try {
                    name = login(scnr);
                    MainView.mainView(scnr);
                } catch (Exception e) {
                    System.out.println("ERROR! Wrong username/password.\n");
                }
            } else {
                System.out.println("Invalid input\n");
            }

            System.out.println("Select form one of the following options:");
            System.out.println("(R)egister, (L)ogin, (Q)uit\n");
            System.out.print("-");
            input = scnr.nextLine().toLowerCase().charAt(0);
        }

    }
}