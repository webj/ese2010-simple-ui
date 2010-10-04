package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Vote extends Model {

	public Boolean result;

	@ManyToOne
	public User author;

	@ManyToOne
	public Answer answer;

	@ManyToOne
	public Question question;

	public Vote(Question quesiton, User author, boolean result) {

		this.question = quesiton;
		this.author = author;
		this.result = result;
		this.author.addVote(this);

	}

	public Vote(Answer answer, User author, boolean result) {

		this.answer = answer;
		this.author = author;
		this.result = result;
		this.author.addVote(this);
	}

}
