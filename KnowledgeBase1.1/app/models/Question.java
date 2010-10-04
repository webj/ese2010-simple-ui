package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Question extends Model {

	@Required
	public Date postedAt;
	
	@Required
	public String title;

	@Lob
	@Required
    @MaxSize(10000)
	public String content;

	@Required
    @ManyToOne
	public User author;

	@OneToMany(mappedBy = "question", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Answer> answers;

	@OneToMany(mappedBy = "question", cascade = { CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH })
	public List<Vote> votes;

	public Question(User user, String title, String content) {
		this.author = user;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
		this.answers = new ArrayList<Answer>();
		this.votes = new ArrayList<Vote>();

	}

	public Question addAnswer(User author, String content) {
		Answer answer = new Answer(this, author, content).save();
		this.answers.add(answer);
		this.save();
		return this;
	}

	public Question addVote(User author, boolean result) {
		Vote vote = new Vote(this, author, result).save();
		this.votes.add(vote);
		this.save();
		return this;
	}

	public Question previous() {
		return Question.find("postedAt < ? order by postedAt desc", postedAt)
				.first();
	}

	public Question next() {
		return Question.find("postedAt > ? order by postedAt asc", postedAt)
				.first();
	}
	
	public String toString(){
		return title;
	}

}