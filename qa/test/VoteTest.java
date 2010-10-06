import java.util.ArrayList;

import models.Answer;
import models.Question;
import models.User;
import models.Vote;

import org.junit.Test;

import play.test.UnitTest;

public class VoteTest extends UnitTest {

	@Test
	public void shouldAddAndDeleteAnVoteToAnswer() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");
		User brayn = User.createUser("Brayn", "bob@bob.com", "velo");

		bob.addQuestion("bla", "blbalab");
		Question question = User.questions.get(0);

		question.addVote(bob, false);
		question.addVote(brayn, true);

		question.addAnswer(brayn, "good question");

		ArrayList<Answer> answer = Question.findAnswers(0);
		answer.get(0).addVote(bob, true);

		assertEquals(3, Vote.count());
		assertEquals(1, answer.get(0).findLikes());
		assertEquals(2, question.findDislikes() + question.findLikes());

		bob.delete();
		assertEquals(0, Vote.count());
		brayn.delete();
	}

}
