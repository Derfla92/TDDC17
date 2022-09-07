package tddc17;

import aima.core.environment.liuvacuum.*;
import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.AgentProgram;
import aima.core.agent.Percept;
import aima.core.agent.impl.*;

import java.lang.reflect.Array;
import java.util.*;

class Vector2 {
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	};

	public int x;
	public int y;

	Vector2 addVector2(Vector2 add) {
		Vector2 result = new Vector2(this.x + add.x, this.y + add.y);
		return result;
	}
}



class MyAgentState {
	public int[][] world = new int[30][30];
	public int initialized = 0;
	final int UNKNOWN = 0;
	final int WALL = 1;
	final int CLEAR = 2;
	final int DIRT = 3;
	final int HOME = 4;
	final int ACTION_NONE = 0;
	final int ACTION_MOVE_FORWARD = 1;
	final int ACTION_TURN_RIGHT = 2;
	final int ACTION_TURN_LEFT = 3;
	final int ACTION_SUCK = 4;

	public int agent_x_position = 1;
	public int agent_y_position = 1;
	public int agent_last_action = ACTION_NONE;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public int agent_direction = EAST;

	MyAgentState() {
		for (int i = 0; i < world.length; i++)
			for (int j = 0; j < world[i].length; j++)
				world[i][j] = UNKNOWN;
		world[1][1] = HOME;
		agent_last_action = ACTION_NONE;
	}

	// Based on the last action and the received percept updates the x & y agent
	// position
	public void updatePosition(DynamicPercept p) {
		Boolean bump = (Boolean) p.getAttribute("bump");

		if (agent_last_action == ACTION_MOVE_FORWARD && !bump) {
			switch (agent_direction) {
				case MyAgentState.NORTH:
					agent_y_position--;
					break;
				case MyAgentState.EAST:
					agent_x_position++;
					break;
				case MyAgentState.SOUTH:
					agent_y_position++;
					break;
				case MyAgentState.WEST:
					agent_x_position--;
					break;
			}
		}

	}

	public void updateWorld(int x_position, int y_position, int info) {
		world[x_position][y_position] = info;
	}

	public void printWorldDebug() {
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if (world[j][i] == UNKNOWN)
					System.out.print(" ? ");
				if (world[j][i] == WALL)
					System.out.print(" # ");
				if (world[j][i] == CLEAR)
					System.out.print(" . ");
				if (world[j][i] == DIRT)
					System.out.print(" D ");
				if (world[j][i] == HOME)
					System.out.print(" H ");
			}
			System.out.println("");
		}
	}

}

class Pair {
	Pair(Action action, Integer i) {
		this.action = action;
		this.i = i;
	}

	Action action;
	Integer i;
}


class MyAgentProgram implements AgentProgram {

	private int initnialRandomActions = 10;
	private Random random_generator = new Random();

	// Here you can define your variables!
	public int iterationCounter = 30*30*2;
	public MyAgentState state = new MyAgentState();
	public ArrayList<Pair> actionQueue = new ArrayList<Pair>();
	public ArrayList<Vector2> path = new ArrayList<Vector2>();
	boolean go_home;
	public int fake_direction = state.agent_direction;

	// moves the Agent to a random start position
	// uses percepts to update the Agent position - only the position, other
	// percepts are ignored
	// returns a random action
	private Action moveToRandomStartPosition(DynamicPercept percept) {
		int action = random_generator.nextInt(6);
		initnialRandomActions--;
		state.updatePosition(percept);
		if (action == 0) {
			state.agent_direction = ((state.agent_direction - 1) % 4);
			if (state.agent_direction < 0)
				state.agent_direction += 4;
			state.agent_last_action = state.ACTION_TURN_LEFT;
			return LIUVacuumEnvironment.ACTION_TURN_LEFT;
		} else if (action == 1) {
			state.agent_direction = ((state.agent_direction + 1) % 4);
			state.agent_last_action = state.ACTION_TURN_RIGHT;
			return LIUVacuumEnvironment.ACTION_TURN_RIGHT;
		}
		state.agent_last_action = state.ACTION_MOVE_FORWARD;
		return LIUVacuumEnvironment.ACTION_MOVE_FORWARD;
	}

	public void turn_Right() {
		// state.agent_last_action = state.ACTION_TURN_RIGHT;
		state.agent_direction++;
		if (state.agent_direction > MyAgentState.WEST)
			state.agent_direction = MyAgentState.NORTH;
	}

	public void turn_Left() {
		// state.agent_last_action = state.ACTION_TURN_LEFT;
		state.agent_direction--;
		if (state.agent_direction < MyAgentState.NORTH)
			state.agent_direction = MyAgentState.WEST;
	}

	@Override
	public Action execute(Percept percept) {

		// DO NOT REMOVE this if condition!!!
		if (initnialRandomActions > 0) {
			return moveToRandomStartPosition((DynamicPercept) percept);
		} else if (initnialRandomActions == 0) {
			// process percept for the last step of the initial random actions
			initnialRandomActions--;
			state.updatePosition((DynamicPercept) percept);
			System.out.println("Processing percepts after the last execution of moveToRandomStartPosition()");
			state.agent_last_action = state.ACTION_SUCK;
			return LIUVacuumEnvironment.ACTION_SUCK;
		}

		// This example agent program will update the internal agent state while only
		// moving forward.
		// START HERE - code below should be modified!

		System.out.println("x=" + state.agent_x_position);
		System.out.println("y=" + state.agent_y_position);
		System.out.println("dir=" + state.agent_direction);

		iterationCounter--;

		if (iterationCounter == 0)
			return NoOpAction.NO_OP;

		DynamicPercept p = (DynamicPercept) percept;
		Boolean bump = (Boolean) p.getAttribute("bump");
		Boolean dirt = (Boolean) p.getAttribute("dirt");
		Boolean home = (Boolean) p.getAttribute("home");
		System.out.println("percept: " + p);

		// State update based on the percept value and the last action
		state.updatePosition((DynamicPercept) percept);

		if (bump) {
			switch (state.agent_direction) {
				case MyAgentState.NORTH:
					state.updateWorld(state.agent_x_position, state.agent_y_position - 1, state.WALL);
					break;
				case MyAgentState.EAST:
					state.updateWorld(state.agent_x_position + 1, state.agent_y_position, state.WALL);
					break;
				case MyAgentState.SOUTH:
					state.updateWorld(state.agent_x_position, state.agent_y_position + 1, state.WALL);
					break;
				case MyAgentState.WEST:
					state.updateWorld(state.agent_x_position - 1, state.agent_y_position, state.WALL);
					break;
			}
		}
		if (dirt)
			state.updateWorld(state.agent_x_position, state.agent_y_position, state.DIRT);
		else if (!home)
			state.updateWorld(state.agent_x_position, state.agent_y_position, state.CLEAR);

		state.printWorldDebug();

		if(dirt)
		{
			state.agent_last_action = state.ACTION_SUCK;
			return LIUVacuumEnvironment.ACTION_SUCK;
		}
		
		if(go_home && home)
			return NoOpAction.NO_OP;

		if (actionQueue.size() == 0 ) {
			
			path = findPath(state.agent_x_position, state.agent_y_position);

			 Vector2 fakepos = new Vector2(state.agent_x_position, state.agent_y_position);
			 fake_direction = state.agent_direction;
			
			 while(path.size() > 0)
			 {
				 
				 Vector2 pos = path.remove(path.size()-1);

				 if(pos.x > fakepos.x)
				 {
					 AgentGoEast(fake_direction);
					 fake_direction = MyAgentState.EAST;
				 }
					 
				 else if(pos.x < fakepos.x)
				 {
					 AgentGoWest(fake_direction);
					 fake_direction = MyAgentState.WEST;
				 }
					 
				 else if(pos.y > fakepos.y)
				 {
					 AgentGoSouth(fake_direction);
					 fake_direction = MyAgentState.SOUTH;
				 }
					 
				 else if(pos.y < fakepos.y)
				 {
					 AgentGoNorth(fake_direction);
					 fake_direction = MyAgentState.NORTH;
				 }
				 fakepos = new Vector2(pos.x, pos.y);
					 
			 }
		}
		
		

	
		state.agent_last_action = actionQueue.get(0).i;
		if (state.agent_last_action == state.ACTION_TURN_LEFT) {
			turn_Left();
		} else if (state.agent_last_action == state.ACTION_TURN_RIGHT) {

			turn_Right();
		}

		return actionQueue.remove(0).action;
	}

	public ArrayList<Vector2> findPath(int x, int y)
	{
		ArrayList<Vector2> neighbours = new ArrayList<Vector2>();
		neighbours.add(new Vector2(state.agent_x_position, state.agent_y_position));
		boolean[][] visited = new boolean[25][25];
		Vector2[][] prev = new Vector2[27][27];
		
		Vector2 temp = neighbours.get(0);
		prev[temp.x][temp.y] = null;
		while(neighbours.size() > 0)
		{
			
			
			temp = neighbours.remove(0);
			if(state.world[temp.x][temp.y] == state.UNKNOWN)
				break;
			if(go_home && state.world[temp.x][temp.y] == state.HOME && visited[temp.x][temp.y])
				break;
			
			if(!visited[temp.x][temp.y])
			{
				visited[temp.x][temp.y] = true;
				
				if (!visited[temp.x + 1][temp.y] && state.world[temp.x + 1][temp.y] != state.WALL) {	
					prev[temp.x+1][temp.y] = temp;
					neighbours.add(new Vector2(temp.x+1, temp.y));
					if(state.world[temp.x+1][temp.y] == state.UNKNOWN)
					{
						temp = neighbours.get(neighbours.size()-1);
						break;
					}
						
				} 
				if (!visited[temp.x][temp.y + 1] && state.world[temp.x][temp.y + 1] != state.WALL) {
	
					prev[temp.x][temp.y+1] = temp;
					neighbours.add(new Vector2(temp.x, temp.y+1));
					if(state.world[temp.x][temp.y+1] == state.UNKNOWN)
					{
						temp = neighbours.get(neighbours.size()-1);
						break;
					}
				} 
				if(temp.x-1 >= 0)
				{
					if (!visited[temp.x-1][temp.y] && state.world[temp.x - 1][temp.y] != state.WALL) 
					{
						prev[temp.x-1][temp.y] = temp;
						neighbours.add(new Vector2(temp.x-1, temp.y));
						if(state.world[temp.x-1][temp.y] == state.UNKNOWN)
					{
						temp = neighbours.get(neighbours.size()-1);
						break;
					}
					} 
				}
				if(temp.y - 1 >= 0)
				{
					if (!visited[temp.x][temp.y - 1]&& state.world[temp.x][temp.y - 1] != state.WALL) {
						prev[temp.x][temp.y-1] = temp;
						neighbours.add(new Vector2(temp.x, temp.y-1));
						if(state.world[temp.x][temp.y-1] == state.UNKNOWN)
						{
							temp = neighbours.get(neighbours.size()-1);
							break;
						}
					}
				} 
				
				
				
			}
			
		}
	
		ArrayList<Vector2> path = new ArrayList<Vector2>();

		while(prev[temp.x][temp.y] != null)
		{
			path.add(temp);
			temp = prev[temp.x][temp.y];
		}
		if(state.world[path.get(0).x][path.get(0).y] != state.UNKNOWN)
			go_home = true;
		return path;
	}


	public void AgentGoEast(int direction) {
		switch (direction) {
			case MyAgentState.NORTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.EAST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.SOUTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_LEFT, state.ACTION_TURN_LEFT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;
			case MyAgentState.WEST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;

			default:
				break;
		}
	}

	public void AgentGoWest(int direction) {
		switch (direction) {
			case MyAgentState.NORTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_LEFT, state.ACTION_TURN_LEFT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.WEST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.SOUTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;
			case MyAgentState.EAST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;

			default:
				break;
		}
	}

	public void AgentGoSouth(int direction) {
		switch (direction) {
			case MyAgentState.EAST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.SOUTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.WEST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_LEFT, state.ACTION_TURN_LEFT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;
			case MyAgentState.NORTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;

			default:
				break;
		}
	}

	public void AgentGoNorth(int direction) {
		switch (direction) {
			case MyAgentState.WEST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.NORTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));
				break;
			case MyAgentState.EAST:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_LEFT, state.ACTION_TURN_LEFT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;
			case MyAgentState.SOUTH:
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_TURN_RIGHT, state.ACTION_TURN_RIGHT));
				actionQueue.add(new Pair(LIUVacuumEnvironment.ACTION_MOVE_FORWARD, state.ACTION_MOVE_FORWARD));

				break;

			default:
				break;
		}
	}
}

public class MyVacuumAgent extends AbstractAgent {
	public MyVacuumAgent() {
		super(new MyAgentProgram());
	}
}
