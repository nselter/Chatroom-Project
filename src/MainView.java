import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainView {

	public static void mainView(Scanner scnr) throws Exception {
		// initial output options
		System.out.println("Please select from the following options: ");
		System.out.println("(J)oin, (C)reate, (A)ccount, (L)ogout");

		char command = scnr.nextLine().toLowerCase().charAt(0);

		if (command == 'j') {
			System.out.println("Please input chatroom name to join: ");
			Chatroom.joinChatroom(scnr.nextLine());

		} else if (command == 'c') {
			System.out.println("Please input chatroom name to create: ");
			Chatroom.createChatroom(scnr.nextLine());

		} else if (command == 'a') {
			updateAcct();

		} else if (command == 'l') {
			logout();

		} else {
			System.out.println("ERROR: Invalid Input");
			mainView(scnr);
		}
	}

	private static void extracted() {
		throw new NoSuchElementException();
	}

	public static void updateAcct() {
		// TODO: coordinate w group
	}

	public static void logout() {
		// TODO: coordinate w group
	}

	public static void main(String[] args) throws Exception {
		mainView(new Scanner(System.in));
	}
}
