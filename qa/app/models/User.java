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

	/**
	 * factory to crate user
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @return user
	 */
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

	/**
	 * delete user and all his dependencies
	 * 
	 * @return null
	 */

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

	/**
	 * find user by name
	 * 
	 * @param name
	 * @return user
	 */

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

	/**
	 * check if an user is connectet.
	 * 
	 * This method is necessary for the login.
	 * 
	 * @param username
	 * @param password
	 * @return user
	 */

	public static User connect(String username, String password) {

		for (User user : User.users) {
			if (user.name.equals(username) && user.password.equals(password))
				return user;

		}
		return null;

	}

	/**
	 * check if an user exists.
	 * 
	 * @param name
	 * @param email
	 * @return boolean
	 */

	public static boolean userExists(String name, String email) {

		if (!User.users.isEmpty()) {
			for (User user : users) {

				if (user.email.equals(email) && user.name.equals(name)) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * check if an user has alredy voted for an question.
	 * 
	 * 
	 * @param question
	 * @return boolean
	 */

	public boolean findUserVoteQuestion(Question question) {

		if (votes.isEmpty()) {
			return false;
		} else {
			for (Vote vote : votes) {
				if (vote.checkKind == 1 && vote.author.name.equals(this.name)
						&& vote.question.equals(question)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * check if an user has already voted for an answer.
	 * 
	 * @param answer
	 * @return boolean
	 */

	public boolean findUserVoteAnswer(Answer answer) {

		if (votes.isEmpty()) {
			return false;
		} else {
			for (Vote vote : votes) {

				if (vote.checkKind == 0 && vote.author.name.equals(this.name)
						&& vote.answer.equals(answer)) {
					return true;
				}
			}
		}
		return false;
	}
}
