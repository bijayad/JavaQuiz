import java.io.IOException;
import java.util.Scanner;

public class ConsoleOperation {
	static Scanner sc = new Scanner(System.in);

	public static void displayMenu() throws IOException {
		System.out.println("");
		System.out.println("--------------MAIN MENU-----------------");
		System.out.println(" ► Enter 'S' to start the quiz");
		System.out.println(" ► Enter 'V' to view high score");
		System.out.println(" ► Enter 'H' for help");
		System.out.println(" ► Enter 'D' to go to developer menu");
		System.out.println(" ► Enter 'Q' to quit");
		System.out.println("----------------------------------------");
		System.out.println("");

		String key = sc.nextLine();

		if (key.length() != 0) {
			char inputtedKey = key.charAt(0);
			inputtedKey = Character.toUpperCase(inputtedKey);
			ConsoleOperation.selectMenu(inputtedKey);
		} else {

			System.out.println("Don't just press the ENTER key without selecting an option!");
			displayMenu();
		}

	}

	public static void selectMenu(char key) throws IOException {
		Cases forCase = new Cases();
		switch (key) {
		case 'S':
			forCase.startQuiz();
			break;

		case 'V':
			forCase.viewHighScore();
			break;

		case 'H':
			forCase.showHelp();
			break;

		case 'D':
			DeveloperMenu developerMenu = new DeveloperMenu();
			developerMenu.developerEntryPoint();
		case 'Q':
			forCase.doQuit();
			break;

		default:
			System.out.println("Please press the appropriate key!");
			ConsoleOperation.displayMenu();

		}

	}
}
