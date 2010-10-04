package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String name;
	
	@Required
	public String password;
	
	@Email
	@Required
	public String email;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Question> questions;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Answer> answers;

	@OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Vote> votes;

	public User(String name, String password, String email) {

		this.name = name;
		this.password = password;
		this.email = email;		
		this.votes = new ArrayList<Vote>();
		this.questions = new ArrayList<Question>();
		this.answers = new ArrayList<Answer>();
		
	}

	public static User connect(String name, String password) {
		return find("byNameAndPassword", name, password).first();
	}

	public User addQuestion(String title, String content) {
		Question question = new Question(this, title, content).save();
		this.questions.add(question);
		this.save();
		return this;
	}

	public User addAnswer(Answer answer) {

		this.answers.add(answer);
		this.save();
		return this;

	}

	public User addVote(Vote vote) {
		this.votes.add(vote);
		this.save();
		return this;
	}
	
	public String toString(){
		return name;
	}
}
