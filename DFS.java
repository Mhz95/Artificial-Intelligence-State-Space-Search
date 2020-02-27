import java.util.ArrayList;
import java.util.Stack;

public class DFS {
	private int min;
	public ArrayList<Pair> solnPath;

	DFS() {
		min = Integer.MAX_VALUE;
		solnPath = new ArrayList<Pair>();
	}

	void depthFirstSearch(int maze[][], boolean visited[][], int n, int m, int startX, int startY, int end_i,
			int end_j) {

		/**
		 * DFS Fringe: Stack
		 */
		Stack<State> stack = new Stack<State>();
		int i = startX;
		int j = startY;

		/**
		 * Pushing State in Stack Fringe
		 */
		State start = new State(i, j, 0, n, m, visited);
		stack.push(start);

		/**
		 * DFS Algorithm
		 */
		while (!stack.isEmpty()) {
			State current = stack.pop();

			i = current.first;
			j = current.second;

			/**
			 * Goal test
			 */
			if (current.first == end_i && current.second == end_j) {
				if (current.cost < min) {
					min = current.cost;
					solnPath = current.path;

					solnPath.add(new Pair(i, j));
				}
			}

			/**
			 * Operators: LEFT, RIGHT, UP, DOWN
			 */
			if (j - 1 >= 0 && !current.visited[i][j - 1] && maze[i][j - 1] == 0) {
				current.visited[i][j - 1] = true;
				State temp = new State(i, j - 1, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				stack.add(temp);
			}
			if (j + 1 < m && !current.visited[i][j + 1] && maze[i][j + 1] == 0) {
				current.visited[i][j + 1] = true;
				State temp = new State(i, j + 1, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				stack.add(temp);
			}
			if (i - 1 >= 0) { // Within maze
				if (!current.visited[i - 1][j]) { // Not visited
					if (maze[i - 1][j] == 0) { // Free path
						current.visited[i - 1][j] = true;
						State temp = new State(i - 1, j, current.cost + 1, n, m, current.visited);
						temp.AddPair(current.path, i, j);
						stack.push(temp);
					}
				}
			}
			if (i + 1 < n && !current.visited[i + 1][j] && maze[i + 1][j] == 0) {
				current.visited[i + 1][j] = true;
				State temp = new State(i + 1, j, current.cost + 1, n, m, current.visited);
				temp.AddPair(current.path, i, j);
				stack.add(temp);
			}

		}
	}

	void logResult() {
		Maze.logs.append("\n\nDepth First Search:" + "\n");
		System.out.println("\n\nDepth First Search:");
		System.out.print("\nSolution Path: {");
		Maze.logs.append("\nSolution Path: {" + "\n");

		for (int i = 0; i < solnPath.size(); i++) {
			System.out.print("(" + solnPath.get(i).first + ", " + solnPath.get(i).second + ")");
			Maze.logs.append("(" + solnPath.get(i).first + ", " + solnPath.get(i).second + ")");

			if (i != solnPath.size() - 1) {
				System.out.print(" ");
				Maze.logs.append(" ");
			}
		}
		System.out.println("}, Cost: " + solnPath.size());
		Maze.logs.append("}, Cost: " + solnPath.size() + "\n");
	}

	ArrayList<Pair> getSolutionPath() {
		ArrayList<Pair> path = new ArrayList<Pair>();
		for (int i = 0; i < solnPath.size(); i++) {
			path.add(new Pair(solnPath.get(i).first, solnPath.get(i).second));
			if (i == solnPath.size() - 1) {
				break;
			}
		}
		return path;
	}

}
