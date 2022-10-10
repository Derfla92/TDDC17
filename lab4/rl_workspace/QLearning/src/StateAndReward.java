public class StateAndReward {

	static int angle_states = 10;
	static int vx_states = 6;
	static int vy_states = 4;
	static double max_vx = 1;
	static double max_vy = 0.5;
	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		int disc_angle = discretize(angle, angle_states, -Math.PI, Math.PI);
		String state = "" + disc_angle;
		
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;
		int disc_angle = discretize(Math.abs(angle), angle_states, 0, Math.PI);
		reward = 10/disc_angle;
		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		String state = "" + getStateAngle(angle, vx, vy);

		double disc_vx = discretize(vx, vx_states, -max_vx, max_vx);
		double disc_vy = discretize(vy, vy_states, -max_vy, max_vy);

		state = state + disc_vx + disc_vy;
		
		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		double disc_vx = discretize(Math.abs(vx), vx_states, 0, max_vx);
		double disc_vy = discretize(Math.abs(vy), vy_states, 0, max_vy);

		double reward = getRewardAngle(angle, vx, vy) + 10/disc_vx + 10/disc_vy;

		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
