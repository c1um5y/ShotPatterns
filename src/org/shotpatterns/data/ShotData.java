package org.shotpatterns.data;

public class ShotData {
	public static final double OVER_THE_SHOULDER = 0; // ansnitt
	public static final double EXTREME_CLOSE_UP = 1; // superkozeli
	public static final double CLOSE_UP = 2; // premierplan
	public static final double MEDIUM_CLOSE_UP = 3; // szekond
	public static final double MEDIUM_SHOT = 4; // amerikai
	public static final double MEDIUM_LONG_SHOT = 5; // kistotal
	public static final double LONG_SHOT = 6; // total
	public static final double EXTREME_LONG_SHOT = 7; // nagytotal
	public static final double INSERT = 8; // inzert

	public static final double NR_OF_SHOT_TYPES = 9;

	private double shotType;
	private double percentage;

	public ShotData(double shotType, double percentage) {
		this.shotType = shotType;
		this.percentage = percentage;
	}

	public void setShotTypeAndPercentage(double shotType, double percentage) {
		this.shotType = shotType;
		this.percentage = percentage;
	}

	public double getPercentage() {
		return percentage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return ((ShotData) obj).shotType == shotType && ((ShotData) obj).percentage == percentage;
	}

	@Override
	public String toString() {
		return Double.toString(percentage);
	}

	public boolean isNear(ShotData data, int percent) {
		return Math.abs(this.percentage - data.percentage) <= percent;
	}
}
