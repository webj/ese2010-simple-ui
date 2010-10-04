package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Answer {

	public static int id;
	public static int check;
	public Date postedAt;
	public User author;
	public String content;
	public static Question question;
	public static List<Vote> votes;

	private Answer(Question question, User author, String content, boolean check) {
		this.question = question;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
		if (check) {
			this.votes = new ArrayList<Vote>();
		}
		this.id = id++;
		author.addAnswer(this);
	}

	// factory method
	public static Answer createAnswer(Question question, User author,
			String content) {

		if (check == 0) {
			System.out.println("geht hier vorbei");
			check++;
			return new Answer(question, author, content, true);
		} else
			return new Answer(question, author, content, false);
	}

	public Answer addVote(User author, boolean result) {
		Vote vote = new Vote(this, author, result);
		this.votes.add(vote);
		return this;
	}

	public Answer delete() {

		for (Vote vote : this.votes) {
			User.votes.remove(vote);
			vote.delete();
		}

		return this;
	}

	public static ArrayList<Answer> find(String name) {

		ArrayList<Answer> searchedAnswer = new ArrayList<Answer>();
		for (Answer answer : Question.answers) {
			if (answer.author.equals(name)) {
				searchedAnswer.add(answer);
			}
		}
		return searchedAnswer;
	}

	public static int count() {
		return Question.answers.size();
	}

}
