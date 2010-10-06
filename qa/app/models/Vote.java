package models;

public class Vote {

	public static int id;
	public Boolean result;

	public User author;

	public Answer answer;

	public Question question;

	public Vote(Question quesiton, User author, boolean result) {

		this.question = quesiton;
		this.author = author;
		this.result = result;
		this.author.addVote(this);
		this.id = id++;

	}

	public Vote(Answer answer, User author, boolean result) {

		this.answer = answer;
		this.author = author;
		this.result = result;
		this.author.addVote(this);
		this.id = id++;
	}

	public static int count() {
		return User.votes.size();
	}

	public Vote delete() {
		return null;
	}
}
