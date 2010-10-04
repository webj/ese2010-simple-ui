import java.util.ArrayList;

import models.Question;
import models.User;

import org.junit.Test;

import play.test.UnitTest;

public class UserTest extends UnitTest {

	@Test
	public void shouldCreateAndDeleteUser() {

		// Create a new User
		User bob = User.createUser("Bob", "bob@bob.com", "hallo");
		User brayn = User.createUser("Brayn", "bob@bob.com", "velo");

		assertEquals(2, User.users.size());
		assertEquals("Bob", bob.name);
		assertEquals("hallo", bob.password);
		assertEquals(0, bob.id);
		assertEquals(1, brayn.id);

		assertEquals("Brayn", brayn.name);
		assertEquals("velo", brayn.password);

		bob.delete();
		assertEquals(1, User.users.size());
		brayn.delete();
		assertEquals(0, User.users.size());

	}

	@Test
	public void shouldFindAUser() {

		User.createUser("Bob", "bob@bob.com", "hallo");
		User.createUser("Brayn", "bob@bob.com", "velo");

		User bob = User.find("Bob");
		assertEquals("Bob", bob.name);
		assertEquals("bob@bob.com", bob.email);

		User brayn = User.find("Brayn");
		assertEquals("Brayn", brayn.name);

		bob.delete();
		brayn.delete();
	}

	@Test
	public void shouldAddAQuestionToAUserAndDelete() {

		User bob = User.createUser("Bob", "bob@bob.com", "hallo");
		User brayn = User.createUser("Brayn", "bob@bob.com", "velo");

		bob.addQuestion("none", "bla");
		bob.addQuestion("another question", "bla");
		brayn.addQuestion("balbal", "bla");

		User sepp = User.createUser("Sepp", "sepp@sepp.com", "mega");
		assertEquals(2, sepp.id);

		assertEquals(3, Question.count());

		assertEquals(bob, bob.questions.get(0).author);
		assertEquals(bob, bob.questions.get(1).author);

		ArrayList<Question> question = Question.find("Brayn");
		assertEquals(1, question.size());

		bob.delete();
		assertEquals(1, Question.count());

		brayn.delete();
		assertEquals(0, Question.count());

		sepp.delete();

	}

}
