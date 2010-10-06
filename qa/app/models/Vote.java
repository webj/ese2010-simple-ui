package models;

public class Vote {

	public static int id;

	// check if answer or question
	public int checkKind;
	public Boolean result;

	public User author;

	public Answer answer;

	public Question question;

	public Vote(Question quesiton, User author, boolean result) {

		this.question = quesiton;
		this.author = author;
		this.result = result;
		this.author.addVote(this);
		this.checkKind = 1;
		this.id = id++;

	}

	public Vote(Answer answer, User author, boolean result) {

		this.answer = answer;
		this.author = author;
		this.result = result;
		this.author.addVote(this);
		this.checkKind = 0;
		this.id = id++;
	}

	public static int count() {
		return User.votes.size();
	}

	public Vote delete() {
		return null;
	}
}
