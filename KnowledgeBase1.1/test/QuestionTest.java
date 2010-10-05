import models.Answer;
import models.Question;
import models.User;

import org.junit.Test;

import play.test.UnitTest;

public class QuestionTest extends UnitTest {

	@Test
	public void shouldCreateANewQuestion() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");

		Question firstQuestion = Question
				.createQuestion(bob, "I have an question",
						"Why do we write the I in great letters?");

		assertEquals(1, Question.count());
		assertEquals("I have an question", firstQuestion.title);

		bob.delete();
	}

	@Test
	public void shouldUseTheRelationAddQuestions() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");
		User brayn = User.createUser("Brayn", "brayn@brayn.com", "velo");

		assertEquals(2, User.count());

		// Test the addAnswer method
		Question firstQuestion = Question
				.createQuestion(bob, "I have an question",
						"Why do we write the I in great letters?");
		firstQuestion.addAnswer(bob,
				"I know now the answer, the question is sloved");
		Question secondQuestion = Question
				.createQuestion(brayn, "blub", "blub");
		secondQuestion.addAnswer(bob, "Answer is good");

		assertEquals(2, Answer.count());

		assertEquals(1, Question.findAnswers(firstQuestion.id).size());

		bob.delete();
		assertEquals(1, Question.count());
		assertEquals(1, Answer.count());
		brayn.delete();
	}

	@Test
	public void shouldCheckTheIDofAQuestion() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");

		Question firstQuestion = Question.createQuestion(bob, "bla", "blabla");
		bob.addQuestion("blabalba", "blabalbaabal");

		assertEquals(0, firstQuestion.id);
		assertEquals(1, bob.questions.get(1).id);
	}
}
