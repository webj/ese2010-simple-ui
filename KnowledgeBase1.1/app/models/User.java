package models;

import java.util.ArrayList;
import java.util.List;

public class User {

	public int id;
	public static int check;
	public String name;
	public String email;
	public String password;
	public static List<Question> questions;
	public static List<Answer> answers;
	public static List<Vote> votes;
	public static List<User> users;

	private User(String name, String email, String password, boolean check) {

		this.name = name;
		this.email = email;
		this.password = password;
		if (check) {
			this.votes = new ArrayList<Vote>();
			this.questions = new ArrayList<Question>();
			this.answers = new ArrayList<Answer>();
			this.users = new ArrayList<User>();
		}
		createId();
		this.users.add(this);

	}

	public static User createUser(String name, String email, String password) {

		if (check == 0) {
			check++;
			return new User(name, email, password, true);
		} else
			return new User(name, email, password, false);
	}

	private void createId() {
		if (id == 0 || users.size() == 0) {
			this.id = users.size();
		} else {
			id = users.get(users.size() - 1).id + 1;
		}
	}

	public User addQuestion(String title, String content) {
		Question question = Question.createQuestion(this, title, content);
		return this;
	}

	public User addAnswer(Answer answer) {

		this.answers.add(answer);
		return this;

	}

	public User addVote(Vote vote) {
		this.votes.add(vote);
		return this;
	}

	public User delete() {

		ArrayList<Question> deletetQuestions = new ArrayList<Question>();
		ArrayList<Vote> deletetVote = new ArrayList<Vote>();

		for (Question question : questions) {
			if (question.author.name.equals(this.name)) {
				deletetQuestions.add(question);
			}
		}

		for (Question question : deletetQuestions) {
			question.delete();
			questions.remove(question);

		}

		for (Vote vote : votes) {
			if (vote.author.equals(this)) {
				votes.remove(vote);
				vote.delete();
			}
		}

		for (Vote vote : deletetVote) {
			votes.remove(vote);
			vote.delete();
		}

		this.users.remove(this);
		return null;
	}

	public static Question findById(Long id) {

		for (Question question : questions) {
			if (question.id == id) {
				return question;
			}
		}
		return null;
	}

	public static User find(String name) {

		for (User user : users) {
			if (user.name.equals(name)) {
				return user;
			}
		}
		return null;
	}

	public static int count() {
		return users.size();
	}
}
