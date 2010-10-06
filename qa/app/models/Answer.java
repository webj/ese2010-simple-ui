package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Answer {

	public int id;
	public static int check;
	public Date postedAt;
	public User author;
	public String content;
	public Question question;
	public static List<Vote> votes;

	private Answer(Question question, User author, String content, boolean check) {
		this.question = question;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
		if (check) {
			this.votes = new ArrayList<Vote>();
		}
		createId();
		author.addAnswer(this);
	}

	private void createId() {
		if (id == 0 || Answer.count() == 0) {
			this.id = Answer.count();

		} else {
			id = User.answers.get(User.answers.size() - 1).id + 1;
		}
	}

	// factory method
	public static Answer createAnswer(Question question, User author,
			String content) {

		if (check == 0) {
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

	/**
	 *find all answers by given author-name
	 * 
	 * @param name
	 * @return answers
	 */
	public static ArrayList<Answer> find(String name) {

		ArrayList<Answer> searchedAnswer = new ArrayList<Answer>();
		for (Answer answer : Question.answers) {
			if (answer.author.name.equals(name)) {
				searchedAnswer.add(answer);
			}
		}
		return searchedAnswer;
	}

	public static int count() {
		return Question.answers.size();
	}

	/**
	 * find answer by given id
	 * 
	 * @param id
	 * @return answer
	 */

	public static Answer findById(int id) {

		if (!User.answers.isEmpty()) {
			for (Answer answer : User.answers) {
				if (answer.id == id) {
					return answer;
				}
			}
		}
		return null;
	}

	/**
	 * finds all dislikes of an answer
	 * 
	 * @return number of dislike votes
	 */

	public int findDislikes() {

		int dislikes = 0;

		if (!votes.isEmpty()) {
			for (Vote vote : this.votes) {
				if (!vote.result && vote.answer.equals(this)) {
					dislikes++;
				}
			}
		}

		return dislikes;

	}

	/**
	 * finds all likes of an answer
	 * 
	 * @return number of like votes
	 */
	public int findLikes() {

		int like = 0;

		if (!votes.isEmpty()) {
			for (Vote vote : this.votes) {
				if (vote.result && vote.answer.equals(this)) {
					like++;
				}
			}
		}

		return like;

	}
}
