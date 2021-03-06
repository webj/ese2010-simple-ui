import models.Question;
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {

		User bob = User.createUser("bob", "bob@bob.com", "hallo");
		User brayn = User.createUser("brayn", "brayn@brayn.com", "velo");

		bob.addQuestion("hallo", "ist hallo mein Password");
		brayn.addQuestion("velo", "ist velo mein passwort?");

		Question question = Question.findById(0);
		question.addAnswer(bob, "hallo");
	}

}