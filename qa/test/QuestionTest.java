import java.util.ArrayList;

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
		assertEquals(0, firstQuestion.id);

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

		bob.delete();
	}

	@Test
	public void shouldAddAnVoteToAnQuestion() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");

		Question question = Question.createQuestion(bob, "blabbla", "foo");

		question.addVote(bob, true);

		assertEquals(1, question.findLikes() + question.findDislikes());

		bob.delete();

	}

	@Test
	public void shouldFindAllQuestonFromAAuthor() {
		User bob = User.createUser("Bob", "bob@bob.com", "hallo");

		Question question = Question.createQuestion(bob, "blabbla", "foo");
		bob.addQuestion("your mother", "more question");

		assertEquals(2, Question.find("Bob").size());

		bob.delete();
	}

	@Test
	public void shouldSortAGivenQuestionArray() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");

		bob.addQuestion("more question", "question");
		bob.addQuestion("your mother", "more question");
		Question question = Question.createQuestion(bob, "blabbla", "foo");

		question.addVote(bob, true);

		ArrayList<Question> questions = Question.sortByVotes();

		assertEquals("foo", questions.get(0).content);

		bob.delete();
	}
}
