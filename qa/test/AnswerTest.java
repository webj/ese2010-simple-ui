import java.util.ArrayList;

import models.Answer;
import models.Question;
import models.User;

import org.junit.Test;

import play.test.UnitTest;

public class AnswerTest extends UnitTest {

	@Test
	public void shouldCreateAnAndDelteAnswer() {

		User user = User.createUser("Bob", "bob@bob.com", "hallo");
		Question question = Question.createQuestion(user, "mega", "giga");
		question.addAnswer(user, "hallo");

		Answer answer = Answer.findById(0);

		assertEquals(1, Answer.count());
		assertEquals(0, answer.id);
		assertEquals(user, answer.author);
		assertEquals(question, answer.question);

		user.delete();

		assertEquals(0, Answer.count());
	}

	@Test
	public void shouldAddAnVoteToAnAnswer() {

		User user = User.createUser("Bob", "bob@bob.com", "hallo");
		Question question = Question.createQuestion(user, "mega", "giga");
		question.addAnswer(user, "hallo");
		question.addAnswer(user, "biblbalb");

		ArrayList<Answer> answers = Answer.find(user.name);

		Answer answer = answers.get(1);

		assertEquals(1, answer.id);

		answer.addVote(user, true);

		assertEquals(1, answer.findDislikes() + answer.findLikes());
		assertEquals(1, answer.findLikes());

		user.delete();

	}

}
