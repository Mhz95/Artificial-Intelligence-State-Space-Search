import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {
	public static int min;
	public static int goal_i, goal_j;
	public static ArrayList<Pair> optimalPath;

	AStar() {
		min = Integer.MAX_VALUE;
		optimalPath = new ArrayList<Pair>();
	}

	void AStarSearch(int maze[][], boolean visited[][], int n, int m, int i, int j, int end_i, int end_j) {
		goal_i = end_i;
		goal_j = end_j;

		/**
		 * A* Fringe: Priority Queue
		 */
		Comparator<State> comparator = new AStarPairComparator(); // To order PQ based on cost f(n)
		PriorityQueue<State> priorityQueue = new PriorityQueue<State>(comparator);

		/**
		 * Enqueue state to Priority Queue
		 */
		visited[i][j] = true;
		State start = new State(i, j, 0, n, m, visited);
		priorityQueue.add(start);

		/**
		 * A* Algorithm
		 */
		while (!priorityQueue.isEmpty()) {
			State current = priorityQueue.remove();

			i = current.first;
			j = current.second;

			/**
			 * Goal test
			 */
			if (current.first == goal_i && current.second == goal_j) {
				if (current.cost < min) {
					min = current.cost;
					optimalPath = current.path;

					optimalPath.add(new Pair(i, j));
				}
			}

			/**
			 * Operators: LEFT, RIGHT, UP, DOWN
			 */

			if (j - 1 >= 0 && !current.visited[i][j - 1] && maze[i][j - 1] == 0) {
				current.visited[i][j - 1] = true;
				State temp = new State(i, j - 1, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				priorityQueue.add(temp);
			}
			if (j + 1 < m && !current.visited[i][j + 1] && maze[i][j + 1] == 0) {
				current.visited[i][j + 1] = true;
				State temp = new State(i, j + 1, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				priorityQueue.add(temp);
			}
			if (i - 1 >= 0) { // Within Maze
				if (!current.visited[i - 1][j]) { // Not visited
					if (maze[i - 1][j] == 0) { // Free path

						current.visited[i - 1][j] = true;
						State temp = new State(i - 1, j, current.cost + 1, n, m, current.visited);
						temp.AddPair(current.path, i, j);
						priorityQueue.add(temp);
					}
				}
			}
			if (i + 1 < n && !current.visited[i + 1][j] && maze[i + 1][j] == 0) {
				current.visited[i + 1][j] = true;
				State temp = new State(i + 1, j, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				priorityQueue.add(temp);
			}

		}
	}

	/**
	 * Calculates Manhattan distance
	 */
	public static int calculateManhattanDistance(int x1, int y1, int x2, int y2) {
		return (Math.abs(x1 - x2) + Math.abs(y1 - y2));
	}

	void logResult() {
		Maze.logs.append("\n\nA* Search:" + "\n");
		System.out.println("\n\nA* Search:");
		System.out.print("\nOptimal Path: {");
		Maze.logs.append("\nOptimal Path: {" + "\n");

		for (int i = 0; i < optimalPath.size(); i++) {
			System.out.print("(" + optimalPath.get(i).first + ", " + optimalPath.get(i).second + ")");
			Maze.logs.append("(" + optimalPath.get(i).first + ", " + optimalPath.get(i).second + ")");

			if (i != optimalPath.size() - 1) {
				System.out.print(" ");
				Maze.logs.append(" ");
			}
		}
		System.out.println("}, Cost: " + optimalPath.size());
		Maze.logs.append("}, Cost: " + optimalPath.size() + "\n");
	}

	ArrayList<Pair> getSolutionPath() {
		ArrayList<Pair> path = new ArrayList<Pair>();
		for (int i = 0; i < optimalPath.size(); i++) {
			path.add(new Pair(optimalPath.get(i).first, optimalPath.get(i).second));
			if (i == optimalPath.size() - 1) {
				break;
			}
		}
		return path;
	}
}

/**
 * Ordering Priority Queue based on f(n) cost
 */
class AStarPairComparator implements Comparator<State> {
	@Override
	public int compare(State a, State b) {
		int distance_a = AStar.calculateManhattanDistance(a.first, b.first, AStar.goal_i, AStar.goal_j);
		int distance_b = AStar.calculateManhattanDistance(b.first, b.second, AStar.goal_i, AStar.goal_j);

		/**
		 * f(n) = g(n) + h(n)
		 */
		if (distance_a + a.cost > distance_b + b.cost)
			return 1;
		else if (distance_a + a.cost < distance_b + b.cost)
			return -1;
		return 0;
	}
}