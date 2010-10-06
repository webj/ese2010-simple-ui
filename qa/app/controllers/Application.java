package controllers;

import java.util.ArrayList;

import models.Answer;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Application extends Controller {

	public static void mainpage() {

		ArrayList<Question> renderquestions = Question.sortByVotes();

		render(renderquestions);
	}

	public static void createQuestion() {
		render();
	}

	public static void createAnswer(int id) {
		render(id);
	}

	public static void addAnswer(int questionid, String content) {

		String failure;

		if (content.isEmpty()) {
			failure = "you wrote now answer! Idiot....";
		}

		else {

			User user = User.find(Security.connected());
			Question question = Question.findById(questionid);
			question.addAnswer(user, content);
			failure = "answer succsesfull created";
		}

		render(failure, questionid);

	}

	public static void addQuestion(String title, String content) {

		String failure;

		if (title.isEmpty() || content.isEmpty()) {
			failure = "you forgot something";
		}

		else {
			User user = User.find(Security.connected());
			Question.createQuestion(user, title, content);
			failure = "question succsesfull created";
		}

		render(failure);

	}

	public static void showQuestion(int id) {
		Question question = Question.findById(id);

		ArrayList<Answer> answers = Question.findAnswers(id);
		render(question, answers);
	}

	public static void voteAnswer(int id, boolean result, int questionid) {

		String failure;
		User user = User.find(Security.connected());
		System.out.println(id);
		Answer answer = Answer.findById(id);

		if (!user.findUserVoteAnswer(answer)
				&& !answer.author.name.equals(Security.connected())) {
			answer.addVote(user, result);
			failure = "thank's for voting";
		}

		else if (answer.author.name.equals(Security.connected())) {
			failure = "you can't vote your own answer";
		}

		else {
			failure = "you already voted this question";
		}

		render(failure, questionid);
	}

	public static void voteQuestion(int id, boolean result) {

		String failure;
		User user = User.find(Security.connected());
		Question question = Question.findById(id);

		if (!user.findUserVoteQuestion(question)
				&& !question.author.name.equals(Security.connected())) {
			question.addVote(user, result);
			failure = "thank's for voting";
		}

		else if (question.author.name.equals(Security.connected())) {
			failure = "you can't vote your own answer";
		}

		else {
			failure = "you already voted this question";
		}

		render(failure, id);
	}
}
