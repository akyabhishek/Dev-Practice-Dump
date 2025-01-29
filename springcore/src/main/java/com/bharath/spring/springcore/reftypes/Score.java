package com.bharath.spring.springcore.reftypes;

public class Score {
	private double maths;
	private double physics;
	private double chemistry;

	public double getMaths() {
		return maths;
	}

	public void setMaths(double maths) {
		this.maths = maths;
	}

	public double getPhysics() {
		return physics;
	}

	public void setPhysics(double physics) {
		this.physics = physics;
	}

	public double getChemistry() {
		return chemistry;
	}

	public void setChemistry(double chemistry) {
		this.chemistry = chemistry;
	}

	@Override
	public String toString() {
		return String.format("Score [maths=%s, physics=%s, chemistry=%s]", maths, physics, chemistry);
	}

}
