
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Scanner;

public class Cases {
	int currentPlayerScore = 0;
	FileManipulation doManipulation = new FileManipulation();

	Scanner sc = new Scanner(System.in);

	public void startQuiz() throws IOException {
		if (doManipulation.questionFileExists())
			doManipulation.readQuestionsFromFile();
		System.out.println("");
		if (QuestionsDatabase.updatedDatabase.isEmpty()) {
			System.out.println("No questions in the database. Go to developer menu and consider adding some.");
			ConsoleOperation.displayMenu();
		}
		if (QuestionsDatabase.updatedDatabase.size() < 5) {
			System.out.println("There should be at least 5 questions added before starting the Quiz!");
			ConsoleOperation.displayMenu();
		}
		System.out.println("Enter your name to continue...");
		String playerName = sc.nextLine();
		if (playerName.length() == 0) {
			System.out.println("Please provide the name!!!");
			startQuiz();
		} else {
			System.out.println("");
			System.out.println("Okay let's start this ultimate Quiz " + playerName + "!");
			System.out.println("Fetching Questions...");
			System.out.println("");
			Collections.shuffle(QuestionsDatabase.updatedDatabase);
			mainQuizPortion();
		}

		try {
			System.out.println("Your total score is " + currentPlayerScore + " .You answered " + currentPlayerScore / 5
					+ " question/s correctly!");

			if (doManipulation.scoreFileExists()) {
				doManipulation.scoreFileEnableRead();

				doManipulation.brScore.readLine();
				int savedHighScore = Integer.parseInt(doManipulation.brScore.readLine());

				if (currentPlayerScore >= savedHighScore) {
					doManipulation.scoreFileEnableWrite();
					LocalDateTime playedTime = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
					String formattedDateTime = playedTime.format(formatter);

					doManipulation.bwScore.write(playerName);
					doManipulation.bwScore.newLine();
					doManipulation.bwScore.append(Integer.toString(currentPlayerScore));
					doManipulation.bwScore.newLine();
					doManipulation.bwScore.append(formattedDateTime);
					System.out.println("You have a new high score!");
					doManipulation.bwScore.close();
				}

			}

			else if (currentPlayerScore != 0) {
				doManipulation.scoreFileEnableWrite();
				LocalDateTime playedTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
				String formattedDateTime = playedTime.format(formatter);

				doManipulation.bwScore.write(playerName);
				doManipulation.bwScore.newLine();
				doManipulation.bwScore.append(Integer.toString(currentPlayerScore));
				doManipulation.bwScore.newLine();
				doManipulation.bwScore.append(formattedDateTime);
				doManipulation.bwScore.close();

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentPlayerScore = 0;
		System.out.println("Press 'Y' to play again | Press any key to go to menu...");
		String key = sc.nextLine();

		if (key.length() != 0) {
			if (Character.toUpperCase(key.charAt(0)) == 'Y')
				startQuiz();
			else
				ConsoleOperation.displayMenu();
		} else
			ConsoleOperation.displayMenu();
	}

	public void mainQuizPortion() {

		for (int i = 0; i < 5; i++) {
			String question = QuestionsDatabase.updatedDatabase.get(i).getQuestion();
			String optionA = QuestionsDatabase.updatedDatabase.get(i).getOptionA();
			String optionB = QuestionsDatabase.updatedDatabase.get(i).getOptionB();
			String optionC = QuestionsDatabase.updatedDatabase.get(i).getOptionC();
			String optionD = QuestionsDatabase.updatedDatabase.get(i).getOptionD();
		
			System.out.println("");
			System.out.println(question);
			System.out.printf("A. %-40s", optionA);
			System.out.printf("B. %s %n", optionB);
			System.out.printf("C. %-40s", optionC);
			System.out.printf("D. %s %n", optionD);

			String input = sc.nextLine();
			char[] appropriateKeys = { 'A', 'B', 'C', 'D' };
			boolean isValid = false;

			if (input.length() != 0) {
				char inputtedKey = Character.toUpperCase(input.charAt(0));
				for (char validKey : appropriateKeys) {

					if (inputtedKey == validKey) {
						isValid = true;
						break;

					}
				}
				if (isValid) {
					char correctOption = QuestionsDatabase.updatedDatabase.get(i).getCorrectOption();
					if (inputtedKey == correctOption) {
						System.out.println("Correct!");
						currentPlayerScore += 5;

					} else {
						System.out.println("Wrong! The right option is " + correctOption);
					}
				} else {
					System.out.println("Enter the appropriate keys i.e. either A,B,C,D");
					i--;

				}

			} else {
				System.out.println("Don't press enter without selecting an option!");
				i--;
			}

		}

	}

	public void viewHighScore() throws IOException {

		if (doManipulation.scoreFileExists()) {
			doManipulation.scoreFileEnableRead();
			System.out.println(doManipulation.brScore.readLine() + " has scored " + doManipulation.brScore.readLine()
					+ " on " + doManipulation.brScore.readLine());
			doManipulation.brScore.close();
		} else
			System.out.println("No saved highscore to show!");

		System.out.println("Enter any key to go to menu...");
		String key = sc.nextLine();
		if (key != null)
			ConsoleOperation.displayMenu();

	}

	public void showHelp() {
		System.out.println(
				"--------------------------------ULTIMATE QUIZ APPLICATION-------------------------------------");
		System.out.println("►To start playing Quiz, first the Developer should add some questions.");
		System.out.println(
				"►If you are a developer go to the developer menu, set the pin(if first time) and then add questions easily.");
		System.out
				.println("►Everything will be stored as file whether it is added quiz questions, highscore or the pin");
		System.out.println(
				"►Every possible exception is handled properly, so no unexpected event might occur, program won't get terminate.");
		System.out.println(
				"►Developer can easily customize and manipulate the quiz data and every option is provided for the ease");
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("►After adding questions, one can play the quiz easily");
		System.out.println("►The quiz database will be shuffled randomly everytime one try to play it");
		System.out.println(
				"►Total 5 questions will be asked for each individual and based on the number of correct questions answered, the score is calculated");
		System.out.println(
				"►The total score for answering one question correctly is 5 and if it's highscore the data will be replaced by the new player's data");
		System.out.println("►High score will be stored in a separate file with the player name, score and played time");

		System.out.println("");
		System.out.println("Enter any key to go to menu...");
		String key = sc.nextLine();
		if (key != null)
			try {
				ConsoleOperation.displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public void doQuit() {
		System.out.println("Exiting...");
		System.out.println("Successfully exited!");
		System.exit(0);
	}

}
