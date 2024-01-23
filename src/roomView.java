
//Monday, April 10th 2023 Room View
import java.sql.*;
import java.util.*;

public class roomView {
	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		String username = "username"; // this is an arbitrary value that will be changed later, just used as a
										// placeholder

		while (true) { // Change to while view is room
			// Display chat room view
			// Prompt for user input
			Scanner scnr = new Scanner(System.in);
			String input = scnr.next(); // Read user input

			if (input.startsWith("/")) {
				// If input starts with '/', treat as command
				String[] command = input.split("\\s+"); // Parse command from input

				switch (command[0]) {
					case "/list":
						/*
						 * PER CHATGPT SUGGESTION:
						 * You can keep track of the users currently in the chat room using a data
						 * structure such as a list or a set.
						 * You can then print the list of users to display the users currently in the
						 * chat room.
						 */
						System.out.println("Here are the current users logged into this chat room: ");
						try {
							stmt = c.createStatement();
							ResultSet rs = stmt.executeQuery("select * from database;");
							while (rs.next()) { // checks if rs.next has a value
								String users = rs.getString("user"); // grabs the usernames of everyone logged in the
																		// chat room
								System.out.println("USERNAME: " + users);
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.err.println(e.getClass().getName() + ": " + e.getMessage());
							System.exit(0);
						}
						break;
					case "/leave":
						/*
						 * PER SUGGESTION:
						 * You can use a flag or a boolean variable to indicate that the user has left
						 * the chat room.
						 * You can then break out of the while loop or use another approach to exit the
						 * chat room gracefully.
						 */
						System.out.println("You have exited the chat room.");
						scnr.close();
						break;
					case "/history":
						/*
						 * PER SUGGESTION:
						 * You can store the chat messages in a data structure such as an array, a list,
						 * or a queue.
						 * You can then iterate through the data structure to print all the past
						 * messages for the chat room.
						 */
						System.out.println("Here are all of the past messages sent in this chat room: ");
						try {
							stmt = c.createStatement();
							ResultSet rs = stmt.executeQuery("SELECT user, message FROM usersdb;");
							while (rs.next()) { // checks if rs.next has a value
								String users = rs.getString("user"); // grabs the usernames
								String msg = rs.getString("message"); // grabs all the messages from the chat room
								System.out.println(users + ": " + msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.err.println(e.getClass().getName() + ": " + e.getMessage());
							System.exit(0);
						}
						break;
					case "/help":
						// Prints the list of available commands
						System.out.println("/list shows all users currently in the room.");
						System.out.println("/leave allows you to leave the chat room.");
						System.out.println("/history displays all past messages for the room.");
						System.out.println("/help displays all available commands.");
						break;
					default:
						System.out.println("Sorry! That's an invalid comand! Try again.");
						break;
				}
			} else {
				// Treat input as chat message
				System.out.println(username + ": " + input);

				// Store the message in the chat history
				try {
					stmt = c.createStatement();
					String sql = "INSERT INTO usersdb (user, message) " +
							"VALUES ('" + username + "', '" + input + "')";
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}
			}
			// Connect it to other's methods
			// Make a table for chat messages and each chat room
		}
	}
}
