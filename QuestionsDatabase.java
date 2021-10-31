import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsDatabase implements Serializable {
	static ArrayList<QuestionsDatabase> updatedDatabase = new ArrayList<>();

	private static final long serialVersionUID = 1L;
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private char correctOption;

	public String getQuestion() {
		return question;
	}

	public char getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(char correctOption) {
		this.correctOption = correctOption;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

}