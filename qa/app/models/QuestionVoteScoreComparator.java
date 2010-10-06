package models;

import java.util.Comparator;

public class QuestionVoteScoreComparator implements Comparator<Question> {

	public int compare(Question x, Question y) {

		if (x.findLikes() > y.findLikes()) {
			return -1;
		} else if (x.findLikes() < y.findLikes()) {
			return 1;
		}
		return 0;

	}

}