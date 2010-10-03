import java.util.List;

import models.Answer;
import models.Question;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class QuestionTest extends UnitTest {

	@Test
	public void shouldCreateANewQuestion() {

		User bob = new User("Bob", "hallo").save();

		Question firstQuestion = new Question(bob, "I have an question",
				"Why do we write the I in great letters?").save();

		assertEquals(1, Question.count());
		assertEquals("I have an question", firstQuestion.title);
	}

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void shouldUseTheRelationAddQuestions() {

		User bob = new User("Bob", "hallo").save();

		// Test the addAnswer method
		Question firstQuestion = new Question(bob, "I have an question",
				"Why do we write the I in great letters?").save();
		firstQuestion.addAnswer(bob,
				"I know now the answer, the question is sloved").save();

		assertEquals(1, Answer.count());

		// find the answers from Bob's questions
		List<Answer> bobAnswer = Answer.find("byQuestion", firstQuestion)
				.fetch();
		assertEquals(1, bobAnswer.size());

		Answer answer = bobAnswer.get(0);
		assertEquals("I know now the answer, the question is sloved",
				answer.content);
	}

}
