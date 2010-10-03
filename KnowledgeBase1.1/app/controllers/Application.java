package controllers;

import java.util.List;

import models.Question;
import models.User;
import play.Play;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {

		List<Question> questions = Question.find("order by postedAt desc")
				.fetch();
		render(questions);
	}

	public static void showQuestions(Long id) {
		Question question = Question.findById(id);
		render(question);
	}

	public static void questionAnswer(Long questionId, @Required String content) {
		User bob = User.find("byName", "Bob").first();
		Question question = Question.findById(questionId);
		if (validation.hasErrors()) {
			render("Application/showQuestion.hmtl", question);
		}
		question.addAnswer(bob, content);
		flash.success("Thanks for your answer %s", bob.name);
		showQuestions(questionId);
	}

	@Before
	public static void addDefaults() {
		renderArgs.put("kBTitle", Play.configuration.getProperty("kB.title"));
		renderArgs.put("kBBaseline", Play.configuration
				.getProperty("kB.baseline"));
	}

}