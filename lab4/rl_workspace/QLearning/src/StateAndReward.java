public class StateAndReward {

	static int angle_states 	= 	8;
	static int num_vx_states 	= 	4;
	static int num_vy_states 	= 	4;
	static double max_vx 		= 	1;
	static double max_vy 		=	1.5;
	// static int min_vx = 
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		int disc_angle = discretize(angle, angle_states, -Math.PI, Math.PI);
		String state = "";
		// switch (disc_angle) {
		// 	case 0:
		// 		state = "southsouthwest";
		// 		break;
		// 	case 1:
		// 		state = "southwestwest";
		// 		break;
		// 	case 2:
		// 		state = "northwestwest";
		// 		break;
		// 	case 3:
		// 		state = "northnorthwest";3
		// 		break;	
		// 	case 4:
		// 		state = "northnortheast";
		// 		break;
		// 	case 5:
		// 		state = "northeasteast";
		// 		break;	
		// 	case 6:
		// 		state = "southeastheast";
		// 		break;
		// 	case 7:
		// 		state = "southsoutheast";
		// 		break;			
		// 	default:
		// 		break;
		// }
		state = state + disc_angle;

		
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;
		int disc_angle = discretize(Math.abs(angle), angle_states, 0, Math.PI);

		reward = 1500/Math.pow(disc_angle,2);


		// switch (disc_angle) {
		// 	case 0:
		// 		reward = 0;
		// 		break;
		// 	case 1:
		// 		reward = 3;
		// 		break;
		// 	case 2:
		// 		reward = 7;
		// 		break;
		// 	case 3:
		// 		reward = 20;
		// 		break;	
		// 	case 4:
		// 		reward = 20;
		// 		break;
		// 	case 5:
		// 		reward = 7;
		// 		break;		
		// 	case 6:
		// 		reward = 3;
		// 		break;		if(disc_vy != 1)
		// 		reward = 0;
		// 		break;		
		// 	default:
		// 		break;
		// }
		//reward = 10*disc_angle

		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		double disc_vx = discretize(vx, num_vx_states, -max_vx, max_vx);
		double disc_vy = discretize(vy, num_vy_states, -max_vy, max_vy);
		//System.out.println("Disc_vx: " + disc_vx + " Disc_vy: " + disc_vy);
		String state = getStateAngle(angle, vx, vy) + disc_vx + disc_vy;
		// if (vx > 1 && vy > 1) 
		// {
		// 	state = "UpRight";
		// }

		// // else if (vx > 1 && vy <= 1 && vy >= -1) 
		// // {
		// // 	state = "Right";
		// // }

		// else if (vx > 1 && vy < -1) {
		// 	state = "DownRight";
		// }

		// // else if (vx <= 1 && vx >= -1 && vy > 1) 
		// // {
		// // 	state = "Up";
		// // }

		// // else if (vx <= 1 && vx >= -1 && vy <= 1 && vy >= -1) 
		// // {
		// // 	state = "Hovering";
		// // }

		// // else if (vx <= 1 && vx >= -1 && vy < -1) 
		// // {
		// // 	state = "Down";
		// // }

		// else if (vx < -1 && vy > 1) 
		// {
		// 	state = "UpLeft";
		// }

		// // else if (vx < -1 && vy <= 1 && vy >= -1) 
		// // {
		// // 	state = "Left";
		// // }

		// else if (vx < -1 && vy < -1) 
		// {
		// 	state = "DownLeft";
		// }

		//state = state + getStateAngle(angle, vx, vy);

		
		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double disc_vx 	= 	discretize(Math.abs(vx), num_vx_states, 0, max_vx);
		double disc_vy 	= 	discretize(Math.abs(vy), num_vy_states, 0, max_vy);
		double reward 	= 	0;
		// if (disc_vx == (int)max_vx/2 && disc_vx < max_vx/2 && disc_vy > max_vy/2 && max_vy/2 < disc_vy) 
		// {
		// 	reward = 15;
		// }
		// else if(disc_vx > 1 && disc_vx < 4 || disc_vy > 1 && disc_vy < 4) 
		// {
		// 	reward = 5;
		// }
		// else if (vx < maxSpeed && vx > minSpeed || vy < maxSpeed && vy > minSpeed) 
		// {
		// 	reward = 40;
		// }
		// else if (vx < maxSpeed && vy > minSpeed) 
		// {
		// 	reward = 30;
		// }
		// else if (vx < maxSpeed && vy > maxSpeed) 
		// {
		// 	reward = 30;
		// }
		// if(disc_vx == 1)
		// {
		// 	reward += 100;
		// }
		// if(disc_vy == 1)
		// 	reward += 100;
		double angle_reward = getRewardAngle(angle, vx, vy);
		// System.out.println(angle_reward);
		 //System.out.println(disc_vx);
		// System.out.println(disc_vy);
		reward += angle_reward + 1000/Math.pow(disc_vx,2) + 1000/Math.pow(disc_vy,5);

		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than max, nrValues-1 is returned
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
	// If the value is higher than max, nrValues-1 is returned
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
