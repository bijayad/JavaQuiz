import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManipulation {
	public FileWriter fwScore;
	public BufferedWriter bwScore;
	public FileReader frScore;
	public BufferedReader brScore;

	public FileWriter fwPin;
	public BufferedWriter bwPin;
	public FileReader frPin;
	public BufferedReader brPin;

	File databaseFolderPath = new File("C:/Quiz-Database");
	File pin = new File("C:/Quiz-Database/pin.txt");
	File score = new File("C:/Quiz-Database/score.txt");
	File questionsList = new File("C:/Quiz-Database/questions-list.txt");

	public void createDatabaseFolder() {
		databaseFolderPath.mkdir();
	}

	public void pinFileEnableRead() throws IOException {
		frPin = new FileReader(pin);
		brPin = new BufferedReader(frPin);
	}

	public void pinFileEnableWrite() throws IOException {
		fwPin = new FileWriter(pin);
		bwPin = new BufferedWriter(fwPin);
	}

	public void scoreFileEnableRead() throws IOException {
		frScore = new FileReader(score);
		brScore = new BufferedReader(frScore);
	}

	public void scoreFileEnableWrite() throws IOException {
		fwScore = new FileWriter(score);
		bwScore = new BufferedWriter(fwScore);
	}

	public boolean DatabaseFolderExists() {
		return databaseFolderPath.exists();
	}

	public boolean questionFileExists() {

		return questionsList.length() == 0 ? false : true;
	}

	public boolean pinFileExists() {

		return pin.length() == 0 ? false : true;
	}

	public boolean scoreFileExists() {

		return score.length() == 0 ? false : true;
	}

	public void writePlayerData() {

	}

	public void writeQuestionsToFile() {
		try {
			FileOutputStream fos = new FileOutputStream(questionsList);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(QuestionsDatabase.updatedDatabase);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void readQuestionsFromFile() {

		try {
			FileInputStream fis = new FileInputStream(questionsList);
			ObjectInputStream ois = new ObjectInputStream(fis);
			QuestionsDatabase.updatedDatabase = ((ArrayList<QuestionsDatabase>) ois.readObject());
			ois.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
