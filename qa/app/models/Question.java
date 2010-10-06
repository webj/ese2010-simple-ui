package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question {

	public int id;
	public static int check;
	public Date postedAt;
	public String title;
	public String content;
	public User author;
	public static List<Answer> answers;
	public static List<Vote> votes;

	private Question(User user, String title, String content, boolean check) {
		this.author = user;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
		if (check) {
			this.answers = new ArrayList<Answer>();
			this.votes = new ArrayList<Vote>();
		}
		createId();
		User.questions.add(this);

	}

	private void createId() {
		if (id == 0 || Question.count() == 0) {
			this.id = Question.count();

		} else {
			id = User.questions.get(User.questions.size() - 1).id + 1;
		}
	}

	// factory method
	public static Question createQuestion(User user, String title,
			String content) {

		if (check == 0) {
			check++;
			return new Question(user, title, content, true);
		} else
			return new Question(user, title, content, false);
	}

	public Question addAnswer(User author, String content) {
		Answer answer = Answer.createAnswer(this, author, content);
		this.answers.add(answer);
		return this;
	}

	public Question addVote(User author, boolean result) {
		Vote vote = new Vote(this, author, result);
		this.votes.add(vote);
		;
		return this;
	}

	public Question delete() {

		ArrayList<Answer> deletetAnswer = new ArrayList<Answer>();
		for (Answer answer : answers) {
			if (answer.question.equals(this)) {
				deletetAnswer.add(answer);
			}

		}
		for (Answer answer : deletetAnswer) {
			answer.delete();
			this.answers.remove(answer);

		}

		for (Vote vote : this.votes) {
			User.votes.remove(vote);
			vote.delete();
		}

		return this;
	}

	public static ArrayList<Question> find(String name) {

		ArrayList<Question> searchedQuestion = new ArrayList<Question>();
		for (Question question : User.questions) {
			if (question.author.name.equals(name)) {
				searchedQuestion.add(question);
			}
		}
		return searchedQuestion;
	}

	public static int count() {
		return User.questions.size();
	}

	public static Question findById(int id) {
		return User.questions.get(id);
	}

	public static ArrayList<Answer> findAnswers(int id) {
		ArrayList<Answer> search = new ArrayList<Answer>();
		if (!answers.isEmpty()) {

			for (Answer answer : User.answers) {
				if (answer.question.id == id) {
					search.add(answer);
				}
			}
			return search;
		}
		return null;
	}

	public int findDislikes() {

		int dislikes = 0;

		if (!votes.isEmpty()) {
			for (Vote vote : this.votes) {
				if (!vote.result && vote.question.equals(this)) {
					dislikes++;
				}
			}
		}

		return dislikes;

	}

	public int findLikes() {

		int like = 0;

		if (!votes.isEmpty()) {
			for (Vote vote : votes) {
				if (vote.result && vote.question.equals(this)) {
					like++;
				}
			}
		}

		return like;

	}

	public static ArrayList<Question> sortByVotes() {

		ArrayList<Question> sortquestions = new ArrayList<Question>();
		Question compare;
		int place = 0;

		for (Question question : User.questions) {
			sortquestions.add(question);
		}
		if (sortquestions.size() <= 1) {
			return sortquestions;
		}

		else {
			int j;

			for (int i = 1; i < sortquestions.size(); i++) {
				j = i;
				compare = sortquestions.get(j);

				while (j > 0
						&& sortquestions.get(j - 1).findAllVotes() < compare
								.findAllVotes()) {
					sortquestions.add(j, sortquestions.get(j - 1));
					j--;
				}
				sortquestions.add(j, compare);
			}

			return sortquestions;
		}

	}

	private int findAllVotes() {

		return (this.findDislikes() + this.findLikes());

	}
}