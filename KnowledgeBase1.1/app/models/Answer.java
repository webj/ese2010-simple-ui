package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Answer extends Model {

	public Date postedAt;
	public User author;

	@Lob
	public String content;

	@ManyToOne
	public Question question;

	@OneToMany(mappedBy = "answer", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	List<Vote> votes;

	public Answer(Question question, User author, String content) {
		this.question = question;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
		this.votes = new ArrayList<Vote>();
		author.addAnswer(this);
	}

	public Answer addVote(User author, boolean result) {
		Vote vote = new Vote(this, author, result).save();
		this.votes.add(vote);
		this.save();
		return this;
	}

}
