import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @Author Munerah H. Alzaidan
 * @instructor Dr. Mohammad Alsulmi
 * @Course KSU CCIS CSC562 AI 
 * @Date February 2020
 * Dev env: Mac OS, user may need to update image paths if compiled in Windows
 * 
 * Inspired by
 * {@link https://github.com/danz1ka19/Artificial-Intelligence-State-Space-Search}
 */
public class Maze extends JFrame {

	/**
	 * Default Maze config
	 */
	int[][] maze = new int[][] { 
			{ 0, 0, 0, 0 }, 
			{ 0, 0, 1, 0 }, 
			{ 0, 1, 1, 0 }, 
			{ 0, 0, 1, 0 } };

	boolean[][] visited = new boolean[][] { 
			{ false, false, false, false }, 
			{ false, false, false, false }, 
			{ false, false, false, false },
			{ false, false, false, false } };

	int n = 4, m = 4;
	int end_i = 3, end_j = 1;
	int i = 0, j = 3;

	/**
	 * Solution Path
	 */
	ArrayList<Pair> solution;

	/**
	 * GUI Components
	 */
	JButton searchDFS;
	JButton searchBFS;
	JButton searchAStar;
	JButton clear;
	JButton exit;
	JButton readUserMaze;
	JProgressBar progressBar;
	JLabel filepath;
	JLabel author;
	JTextField textfilepath;
	static JTextArea logs;

	String text = "Welcome to CSC562 Homework solver !\nPlease enter the maze file path then select the desired search algorithm to see the solution.\n\nExample on Maze file: \n3 3 \n2 1 0\n0 1 0 \n0 0 9 \n\n2 is the start point\n9 is the goal";

	public Maze() {

		/**
		 * Setup JFrame
		 */
		setTitle("CSC562 Homework 1 Q2 - Maze Traversal"); 
		setSize(960, 530); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLayout(null); 

		/**
		 * Add Components to JFrame
		 */
		filepath = new JLabel("Maze file path :");
		author = new JLabel("© Munerah H. Alzaidan");
		textfilepath = new JTextField();
		logs = new JTextArea();

		logs.setText(text);
		logs.setLineWrap(true);
		logs.setWrapStyleWord(true);

		JScrollPane pane = new JScrollPane(logs);
		pane.setPreferredSize(new Dimension(500, 200));
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		searchDFS = new JButton("DFS");
		searchBFS = new JButton("BFS");
		searchAStar = new JButton("A*");
		clear = new JButton("Clear");
		readUserMaze = new JButton("Draw Maze from file");
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);

		add(searchDFS);
		add(searchBFS);
		add(searchAStar);
		add(clear);
		add(filepath);
		add(author);
		add(textfilepath);
		add(readUserMaze);
		add(pane);
		add(progressBar);

		setVisible(true);

		/**
		 * JFrame Components Positions
		 * 
		 * @param (x,y,width,height)
		 */
		searchDFS.setBounds(500, 100, 100, 40);
		searchBFS.setBounds(600, 100, 100, 40);
		searchAStar.setBounds(700, 100, 100, 40);
		progressBar.setBounds(500, 135, 250, 30);
		clear.setBounds(800, 100, 100, 40);
		filepath.setBounds(500, 30, 100, 40);
		readUserMaze.setBounds(760, 55, 160, 40);
		pane.setBounds(500, 160, 300, 250);
		author.setBounds(500, 410, 200, 40);
		textfilepath.setBounds(500, 60, 250, 30);	
		
		/**
		 * onClick on Draw Maze from file
		 */
		readUserMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solution = new ArrayList<Pair>();
				try {
					ReadMaze();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				repaint(); 
			}
		});

		/**
		 * onClick on Clear Button
		 */
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				logs.setText(text);

				solution = null;

				repaint();

			}
		});

		/**
		 * onClick on Search DFS Button
		 */
		searchDFS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logs.setText("");

					new Thread(new Runnable() {
						@Override
						public void run() {
							DFS dfs = new DFS();

							progressBar.setVisible(true);
							solution = new ArrayList<Pair>();

							// Depth First Search
							dfs.depthFirstSearch(maze, visited, n, m, i, j, end_i, end_j);
							dfs.logResult();
							
							solution = dfs.getSolutionPath();
							repaint();

							progressBar.setVisible(false);
						}
					}).start();

			}
		});

		/**
		 * onClick on Search BFS Button
		 */
		searchBFS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logs.setText("");

					new Thread(new Runnable() {
						@Override
						public void run() {
							BFS bfs = new BFS();

							progressBar.setVisible(true);
							solution = new ArrayList<Pair>();

							// Breadth First Search
							bfs.breadthFirstSearch(maze, visited, n, m, i, j, end_i, end_j);
							bfs.logResult();
							
							solution = bfs.getSolutionPath();
							repaint();

							progressBar.setVisible(false);
						}
					}).start();

			}
		});
		
		/**
		 * onClick on Search A* Button
		 */
		searchAStar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logs.setText("");

					new Thread(new Runnable() {
						@Override
						public void run() {
							AStar astar = new AStar();	

							progressBar.setVisible(true);
							solution = new ArrayList<Pair>();

							// Astar Search
							astar.AStarSearch(maze, visited, n, m, i, j, end_i, end_j);
							astar.logResult();
							
							solution = astar.getSolutionPath();
							repaint();

							progressBar.setVisible(false);
						}
					}).start();

			}
		});

	}

	/**
	 * Read an input Maze from user
	 * @return int [][] representation of the Maze
	 */
	public void ReadMaze() throws FileNotFoundException {

		String graphName = textfilepath.getText();
		File file = new File(graphName);

		/*
		 * 2 represent START
		 * 9 represent END
		 * Example File Format:
		 * 
		 * 4 4 
		 * 0 0 0 0 
		 * 0 1 0 1 
		 * 0 2 0 9 
		 * 0 0 0 0
		 *
		 */

		Scanner sc = new Scanner(file);

		// Maze Dimensions
		n = sc.nextInt();
		m = sc.nextInt();

		maze = new int[n][m];
		visited = new boolean[n][m];

		System.out.println("Maze "+ n +" x "+ m);
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				visited[x][y] = false;
				int tmp = sc.nextInt();
				if (tmp == 2) {
					i = x;
					j = y;
					System.out.print("S ");
				} else if (tmp == 9) {
					end_i = x;
					end_j = y;
					System.out.print("D ");
				} else {
					maze[x][y] = tmp;
					System.out.print(maze[x][y] + " ");
				}

			}
			System.out.println();
		}

	}

	/**
	 * Paint the maze on the JFrame
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.translate(70, 70); 

		// Read End Icon
		BufferedImage endIcon;
		BufferedImage endIconResized = null;
		// Read Start Icon
		BufferedImage startIcon;
		BufferedImage startIconResized = null;
		// Read Arrows
		BufferedImage up, down, right, left;
		BufferedImage upResized = null, downResized = null, rightResized = null, leftResized = null;
		try {
			endIcon = ImageIO.read(new File("./images/end.png"));
			endIconResized = resize(endIcon, 35, 35);

			startIcon = ImageIO.read(new File("./images/start.png"));
			startIconResized = resize(startIcon, 35, 35);

			up = ImageIO.read(new File("./images/arrow_up.png"));
			down = ImageIO.read(new File("./images/arrow_down.png"));
			right = ImageIO.read(new File("./images/arrow_right.png"));
			left = ImageIO.read(new File("./images/arrow_left.png"));

			upResized = resize(up, 20, 20);
			downResized = resize(down, 20, 20);
			rightResized = resize(right, 15, 15);
			leftResized = resize(left, 20, 20);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[0].length; col++) {
				Color color;
				boolean setGoalIcon = false;
				boolean setStartIcon = false;

				if (row == i && col == j) {
					color = Color.WHITE; 
					setStartIcon = true;
				} else if (row == end_i && col == end_j) {
					color = Color.WHITE; 
					setGoalIcon = true;
				} else if (maze[row][col] == 1) {
					color = Color.darkGray; 
				} else {
					color = Color.WHITE; 
				}

				g.setColor(color);
				g.fillRect(40 * col, 40 * row, 40, 40); 
				g.setColor(Color.BLACK);
				g.drawRect(40 * col, 40 * row, 40, 40); 

				if (setGoalIcon) {
					g.drawImage(endIconResized, (40 * col) + 2, (40 * row) + 2, null);

				} else if (setStartIcon) {
					g.drawImage(startIconResized, (40 * col) + 2, (40 * row) + 2, null);

				}

			}
		}

		if(solution != null) {
			Pair prev = null;
			
			for (int p = 0; p < solution.size(); p++) {

				int row = solution.get(p).first;
				int col = solution.get(p).second;

				g.setColor(Color.decode("#69e89f"));
				g.fillRect(40 * col, 40 * row, 40, 40);

				if (prev != null) {
					if (prev.first < row) { // Down
						g.drawImage(downResized, (40 * col) + 10, (40 * row) + 10, null);
					} else if (prev.first > row) { // Up
						g.drawImage(upResized, (40 * col) + 10, (40 * row) + 10, null);
					} else if (prev.second < col) { // Right
						g.drawImage(rightResized, (40 * col) + 10, (40 * row) + 10, null);
					} else if (prev.second > col) { // Left
						g.drawImage(leftResized, (40 * col) + 10, (40 * row) + 10, null);
					}
				}

				prev = new Pair(row, col);

				if (p == solution.size() - 1) {
					break;
				}
			}
		}


	}

	/**
	 * Resize Images to fit in Maze cells
	 */
	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Maze maze = new Maze();

			}
		});

	}

}
