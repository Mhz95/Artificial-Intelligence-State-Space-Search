import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	private int min;
	public ArrayList<Pair> optimalPath;

	BFS() {
		min = Integer.MAX_VALUE;
		optimalPath = new ArrayList<Pair>();
	}

	void breadthFirstSearch(int maze[][], boolean visited[][], int n, int m, int i, int j, int end_i, int end_j) {

		/**
		 * BFS Fringe: Queue
		 */
		Queue<State> queue = new LinkedList<State>();

		/**
		 * enqueue State in Queue Fringe
		 */
		State start = new State(i, j, 0, n, m, visited);
		queue.add(start);

		/**
		 * BFS Algorithm
		 */
		while (!queue.isEmpty()) {
			State current = queue.remove();

			i = current.first;
			j = current.second;

			/**
			 * Goal test
			 */
			if (current.first == end_i && current.second == end_j) {
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
				queue.add(temp);
			}
			if (j + 1 < m && !current.visited[i][j + 1] && maze[i][j + 1] == 0) {
				current.visited[i][j + 1] = true;
				State temp = new State(i, j + 1, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				queue.add(temp);
			}
			if (i - 1 >= 0) { // Within maze
				if (!current.visited[i - 1][j]) { // Not visited
					if (maze[i - 1][j] == 0) { // Free path
						current.visited[i - 1][j] = true;
						State temp = new State(i - 1, j, current.cost + 1, n, m, current.visited);
						temp.AddPair(current.path, i, j);
						queue.add(temp);
					}
				}
			}
			if (i + 1 < n && !current.visited[i + 1][j] && maze[i + 1][j] == 0) {
				current.visited[i + 1][j] = true;
				State temp = new State(i + 1, j, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				queue.add(temp);
			}

		}
	}

	void logResult() {
		Maze.logs.append("\n\nBreadth First Search:" + "\n");
		System.out.println("\n\nBreadth First Search:");
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
