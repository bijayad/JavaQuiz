import java.io.IOException;
import java.util.Scanner;

public class DeveloperMenu {
	FileManipulation doManipulation = new FileManipulation();
	Scanner sc = new Scanner(System.in);

	public void displayDeveloperMenu() {
		System.out.println("");
		System.out.println("--------------DEVELOPER MENU--------------------");
		System.out.println("► A. Add new Question");
		System.out.println("► B. Display all questions with correct answers & their index number");
		System.out.println("► C. Delete specific question with its index ");
		System.out.println("► D. Delete all questions");
		System.out.println("► P. Change pin");
		System.out.println("► E. Exit developer menu");
		System.out.println("------------------------------------------------");
		System.out.println("");

		String key = sc.nextLine();

		if (key.length() != 0) {
			char inputtedKey = key.charAt(0);
			inputtedKey = Character.toUpperCase(inputtedKey);
			try {
				selectDeveloperMenu(inputtedKey);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			System.out.println("Don't just press the ENTER key without selecting an option!");
			displayDeveloperMenu();
		}

	}

	public void selectDeveloperMenu(char key) throws IOException {
		switch (key) {
		case 'A':
			inputQuestion();
			break;

		case 'B':
			displayQuestionsWithIndex();
			break;

		case 'C':
			deleteWithIndexMenu();
			break;

		case 'D':
			deleteAllQuestionsMenu();
			break;

		case 'E':
			ConsoleOperation.displayMenu();

		case 'P':
			changePin();
		default:
			System.out.println("Please press the appropriate key!");
			displayDeveloperMenu();

		}

	}

	public void pinChecker() throws IOException {
		if (!doManipulation.DatabaseFolderExists()) {
			System.out.println("Creating database folder at " + doManipulation.databaseFolderPath.getAbsolutePath());
			doManipulation.createDatabaseFolder();
		}

		if (doManipulation.pinFileExists()) {
			setPinTaker();

		} else {
			newPinTaker();

		}

	}

	public void setPinTaker() throws IOException {
		doManipulation.pinFileEnableRead();
		System.out.println("Enter the 6 digit pin that you set previously!");
		String rawInputtedPin = sc.nextLine();
		int inputtedPin = -1;
		if (rawInputtedPin.length() == 0) {
			System.out.println("Please write the pin before hitting enter key!");
			setPinTaker();
		}

		try {
			inputtedPin = Integer.parseInt(rawInputtedPin);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Enter number only!");
			setPinTaker();
		}
		if (rawInputtedPin.length() == 6) {
			int filePin = Integer.parseInt(doManipulation.brPin.readLine());
			if (inputtedPin == filePin)
				displayDeveloperMenu();
			else {
				System.out.println("The pin is incorrect, Can't go into Developer Menu!");
				ConsoleOperation.displayMenu();
			}

		} else {
			System.out.println("The pin is 6 digit long.");
			setPinTaker();
		}
	}

	public void newPinTaker() throws IOException {

		System.out.println("");
		System.out.println("Pin not set previously. Enter new 6 digit pin to add...");
		String rawToSetPin = sc.nextLine();
		int inputtedtoSetPin = -1;
		if (rawToSetPin.length() == 0) {
			System.out.println("Please write the pin before hitting enter key!");
			newPinTaker();
		}
		try {
			inputtedtoSetPin = Integer.parseInt(rawToSetPin);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Pin must be numeric!");
			newPinTaker();
		}
		if (rawToSetPin.length() == 6) {
			doManipulation.pinFileEnableWrite();
			doManipulation.bwPin.write(Integer.toString(inputtedtoSetPin));
			doManipulation.bwPin.close();
			System.out.println("New pin set successfully!");
			ConsoleOperation.displayMenu();

		} else {
			System.out.println("The pin must be 6 digit long.");
			newPinTaker();
		}

	}

	public void inputQuestion() {
		if (!doManipulation.questionFileExists()) {
			doManipulation.writeQuestionsToFile();
		}

		doManipulation.readQuestionsFromFile();
		QuestionsDatabase newData = new QuestionsDatabase();
		System.out.println("Enter the question to add...");
		newData.setQuestion(questionChecker());
		System.out.println("Enter option A.");
		newData.setOptionA(optionChecker());
		System.out.println("Enter option B.");
		newData.setOptionB(optionChecker());
		System.out.println("Enter option C.");
		newData.setOptionC(optionChecker());
		System.out.println("Enter option D.");
		newData.setOptionD(optionChecker());
		System.out.println("Amongst all these which option is the correct one?");
		newData.setCorrectOption(Character.toUpperCase(correctChecker().charAt(0)));

		QuestionsDatabase.updatedDatabase.add(newData);
		System.out.println("Question added successfully!");
		System.out.println("Total questions in the database is " + QuestionsDatabase.updatedDatabase.size());
		doManipulation.writeQuestionsToFile();
		System.out.println("Do you want to add more questions? press 'Y'");

		String key = sc.nextLine();

		if (key.length() != 0) {
			if (Character.toUpperCase(key.charAt(0)) == 'Y')
				inputQuestion();
			else
				displayDeveloperMenu();
		} else
			displayDeveloperMenu();
	}

	public void developerEntryPoint() {
		try {
			pinChecker();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void displayQuestionsWithIndex() {
		doManipulation.readQuestionsFromFile();
		boolean isDatabaseEmpty = QuestionsDatabase.updatedDatabase.isEmpty() ? true : false;
		if (isDatabaseEmpty) {
			System.out.println("No questions to show. Consider adding some...");
			displayDeveloperMenu();
		} else {
			System.out.println("");
			System.out.println("►►►►►►►►►►►►►►►►►►►QUESTIONS►►►►►►►►►►►►►►►►►►");
			for (int i = 0; i < QuestionsDatabase.updatedDatabase.size(); i++) {
				System.out.println(QuestionsDatabase.updatedDatabase.get(i).getQuestion());
				System.out.println("Correct Option: " + QuestionsDatabase.updatedDatabase.get(i).getCorrectOption());
				System.out.println("Index: " + i);
				System.out.println("");
			}
			System.out.println("►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►►");
			displayDeveloperMenu();
		}

	}

	public void deleteWithIndexMenu() {
		doManipulation.readQuestionsFromFile();

		if (!QuestionsDatabase.updatedDatabase.isEmpty()) {
			int index = -1;
			System.out.println("Enter the index number to delete!");
			while (true) {
				String rawIndex = sc.nextLine();
				if (rawIndex.length() == 0) {
					System.out.println("Don't press the enter key without entering anything!");
					System.out.println("Enter again!");
					continue;
				}

				try {
					index = Integer.parseInt(rawIndex);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					System.out.println("The index should be number not the character!");
					System.out.println("Enter again!");
					continue;
				}

				if (QuestionsDatabase.updatedDatabase.size() - 1 < index) {

					System.out.println("Index out of bound. There is no such index yet!");
					displayDeveloperMenu();
				}
				break;
			}
			deleteWithIndex(index);
		} else {
			System.out.println("There is no question in the database to delete!");
			displayDeveloperMenu();
		}

	}

	public void deleteWithIndex(int index) {
		doManipulation.readQuestionsFromFile();
		QuestionsDatabase.updatedDatabase.remove(index);
		System.out.println("Question at index " + index + " is removed successfully.");
		doManipulation.writeQuestionsToFile();
		System.out.println("Delete more? Press 'Y' | Press any key to go to menu...");
		String key = sc.nextLine();

		if (key.length() != 0) {
			if (Character.toUpperCase(key.charAt(0)) == 'Y')
				deleteWithIndexMenu();
			else
				displayDeveloperMenu();
		} else
			displayDeveloperMenu();

	}

	public void deleteAllQuestionsMenu() {
		doManipulation.readQuestionsFromFile();
		if (!QuestionsDatabase.updatedDatabase.isEmpty()) {
			System.out.println("Do you really want to delete all the questions? This cannot be undone.");
			System.out.println("Press 'Y' to continue | Press any key to abort operation...");
			String key = sc.nextLine();

			if (key.length() != 0) {
				if (Character.toUpperCase(key.charAt(0)) == 'Y')
					deleteAllQuestions();
			} else {
				displayDeveloperMenu();
			}
		} else {
			System.out.println("There is no question in the database to delete!");
			displayDeveloperMenu();
		}

	}

	public void deleteAllQuestions() {
		doManipulation.readQuestionsFromFile();
		QuestionsDatabase.updatedDatabase.clear();
		System.out.println("Removing...");
		System.out.println("Removed all questions successfully!");
		doManipulation.writeQuestionsToFile();
		displayDeveloperMenu();

	}

	public void changePin() throws IOException {
		System.out.println("Enter your new pin...");

		String rawToSetPin = sc.nextLine();
		int inputtedNewPin = -1;
		if (rawToSetPin.length() == 0) {
			System.out.println("Please write the pin before hitting enter key!");
			changePin();
		}
		try {
			inputtedNewPin = Integer.parseInt(rawToSetPin);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Pin must be numeric!");
			changePin();
		}
		if (rawToSetPin.length() == 6) {
			doManipulation.pinFileEnableWrite();
			doManipulation.bwPin.write(Integer.toString(inputtedNewPin));
			doManipulation.bwPin.close();
			System.out.println("Pin changed successfully!");
			System.out.println("Remember! Your new pin is " + inputtedNewPin);
			ConsoleOperation.displayMenu();

		} else {
			System.out.println("The pin must be 6 digit long.");
			changePin();
		}

	}

	public String questionChecker() {
		String input = null;
		while (true) {
			input = sc.nextLine();

			if (input.length() == 0) {
				System.out.println("Please write the question before hitting enter key!");
				continue;
			} else if (input.length() < 5) {
				System.out.println("Question is too short, enter again");
				continue;

			}

			break;
		}
		return input;
	}

	public String optionChecker() {
		String input = null;
		while (true) {
			input = sc.nextLine();
			if (input.length() == 0) {
				System.out.println("Please write the option before hitting enter key!");
				continue;
			}
			break;
		}

		return input;
	}

	public String correctChecker() {
		char[] appropriateKeys = { 'A', 'B', 'C', 'D' };
		String input = null;
		while (true) {
			input = sc.nextLine();

			boolean isValid = false;

			if (input.length() != 0) {
				char inputtedKey = Character.toUpperCase(input.charAt(0));
				for (char validKey : appropriateKeys) {

					if (inputtedKey == validKey) {
						isValid = true;
						break;

					}

				}
				if (!isValid) {
					System.out.println("Please enter either A,B,C,D not any other!");
					continue;
				}

			} else {

				System.out.println("Please write the option before hitting enter key!");
				continue;
			}
			break;
		}
		return input;
	}
}
