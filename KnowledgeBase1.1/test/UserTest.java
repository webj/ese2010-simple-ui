import java.util.List;

import models.Answer;
import models.Question;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class UserTest extends UnitTest {

	@Test
	public void shouldCreateAndRetrieveUser() {

		// Create a new User
		new User("Bob", "hallo", "bob@bob.com").save();
		new User("Brayn", "velo", "brayn@brayn.com").save();

		// Retrieve the user with some keywords
		User bob = User.find("byPassword", "hallo").first();
		User brayn = User.find("byName", "Brayn").first();
		User zero = User.find("byPassword", "Housi").first();

		assertEquals("Bob", bob.name);
		assertEquals("hallo", bob.password);

		assertEquals("Brayn", brayn.name);
		assertEquals("velo", brayn.password);

		assertNull(zero);
	}

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}

	@Test
	public void shouldDeleteUser() {

		User bob = new User("Bob", "hallo", "bob@bob.com").save();
		bob.delete();
		assertEquals(0, User.count());
	}

	@Test
	public void shouldUseTheQuestionRelation() {
		User bob = new User("Bob", "hallo", "bob@bob.com").save();
		User brayn = new User("Brayn", "velo", "brayn@brayn.com").save();

		bob.addQuestion("What's going on?", "Hey guys, What's going on?")
				.save();
		bob.addQuestion("Why rise the sun every mornig?",
				"The question is above").save();

		// all Questions
		assertEquals(2, Question.count());

		// Retrieves the questions from bob
		List<Question> bobQuestion = Question.find("byAuthor", bob).fetch();
		assertEquals(2, bobQuestion.size());

		// take the second question. Check
		Question question = bobQuestion.get(1);
		assertEquals("Why rise the sun every mornig?", question.title);
		assertEquals("The question is above", question.content);
		assertEquals(bob, question.author);

		List<Question> braynQuestion = Question.find("byAuthor", brayn).fetch();
		assertEquals(0, braynQuestion.size());
	}

	@Test
	public void shouldDeleteUserAndEveryDependencies() {

		// create user
		User bob = new User("Bob", "hallo", "bob@bob.com").save();
		User brayn = new User("Brayn", "velo", "brayn@brayn.com").save();

		// create questions
		bob.addQuestion("What's going on?", "Hey guys, What's going on?")
				.save();
		brayn
				.addQuestion(
						"Why doesen't snow smell?",
						"Hey, yesterday I was in the mountains. "
								+ "I was very confused when I detected that snow doesen't smell. Pleas help me!")
				.save();

		List<Question> listbobQuestion = Question.find("byAuthor", bob).fetch();
		Question bobQuestion = listbobQuestion.get(0);

		assertEquals("What's going on?", bobQuestion.title);

		List<Question> listbraynQuestion = Question.find("byAuthor", brayn)
				.fetch();
		Question braynQuestion = listbraynQuestion.get(0);

		bobQuestion.addAnswer(brayn, "A lot").save();
		braynQuestion.addAnswer(bob, "Brayn, you are an idiot").save();
		bobQuestion.addAnswer(bob, "Oh, ok").save();

		// check if the number of questions/users/answers are the right
		assertEquals(2, Question.count());
		assertEquals(2, User.count());
		assertEquals(3, Answer.count());

		// delete bob
		bob.delete();

		// All dependencies from bob should be deleted.
		assertEquals(1, User.count());
		assertEquals(0, Answer.count());
		assertEquals(1, Question.count());

		// Check if the right answers were deleted
		List<Answer> braynanswer = Answer.find("byQuestion", braynQuestion)
				.fetch();
		assertEquals(0, braynanswer.size());

		// Check if the remaining question is the right
		List<Question> braynquestion = Question.find("byAuthor", brayn).fetch();
		Question question = braynquestion.get(0);

		assertEquals("Why doesen't snow smell?", question.title);

	}

	@Test
	public void shouldfullTest() {
		Fixtures.load("data.yml");

		assertEquals(2, User.count());
		assertEquals(3, Question.count());
		assertEquals(3, Answer.count());

		User bob = User.find("byName", "Bob").first();
		assertEquals("Bob", bob.name);
		assertEquals(2, bob.questions.size());

		Question bobQuestion = bob.questions.get(0);
		assertEquals("What's going on?", bobQuestion.title);
	}
}
