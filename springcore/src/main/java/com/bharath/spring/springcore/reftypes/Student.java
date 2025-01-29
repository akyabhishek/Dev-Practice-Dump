package com.bharath.spring.springcore.reftypes;

public class Student {
	private Score scores;

	public Score getScores() {
		return scores;
	}

	public void setScores(Score scores) {
		this.scores = scores;
	}

	@Override
	public String toString() {
		return String.format("Student [scores=%s]", scores);
	}
	
	
}
