package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {
	
	public JFrame jframe;
	public RenderPanel renderPanel;
	public static Snake snake;
	public Timer timer = new Timer(20, this);
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	public int ticks = 0, direction = DOWN, score = 0, tailLength = 0;
	public Point head, cherry;
	public Random random;
	public Dimension dim;
	public boolean over = false, paused = false, controls = false, diff = false, wait = false;
	public static String difficulty = "Easy";
	public static int DIFF = 3;
	public static int POINTS = 5;
	
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setLocation(dim.width / 4, dim.height / 6);
		jframe.setSize(400, 400);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		startGame();
	}
	
	public void setGame() {
		over = false;
		paused = false;
		controls = false;
		score = 0;
		tailLength = 5;
		ticks = 0;
		direction = DOWN;
		head = new Point(0, 0);
		random = new Random();
		snakeParts.clear();
	}
	
	public void startGame() {
		setGame();
		cherry = new Point(random.nextInt(jframe.getWidth() / SCALE), random.nextInt(jframe.getHeight() / SCALE - 4));
		for (int i = 0; i < tailLength; i++) {
			snakeParts.add(new Point(head.x, head.y));
		}
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		renderPanel.repaint();
		if (!paused && !over) {
			ticks++;
		}
		
		if (ticks % DIFF == 0 && head != null && !over && !paused && !diff) {
			snakeParts.add(new Point(head.x, head.y));
			wait = false;
			if (direction == UP && noTailAt(head.x, head.y - 1)) {
				if (head.y - 1 >= 0) {
					head = (new Point(head.x, head.y - 1));
				} else {
					over = true;
				}
			}
			if (direction == DOWN) {
				if (head.y + 1 <= snake.jframe.getHeight() / SCALE - 4 && noTailAt(head.x, head.y + 1)) {
					head = (new Point(head.x, head.y + 1));
				} else {
					over = true;
				}
			}
			if (direction == LEFT) {
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) {
					head = (new Point(head.x - 1, head.y));
				} else {
					over = true;
				}
			}
			if (direction == RIGHT) {
				if (head.x + 1 < snake.jframe.getWidth() / SCALE && noTailAt(head.x + 1, head.y)) {
					head = (new Point(head.x + 1, head.y));
				} else {
					over = true;
				}
			}
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			if (cherry != null) {
				if (head.equals(cherry)) {
					score += (POINTS * (4 - DIFF));
					tailLength++;
					cherry.setLocation(random.nextInt(jframe.getWidth() / SCALE), random.nextInt(jframe.getHeight() / SCALE - 4));
				}
			}
		}
	}
	
	
	private boolean noTailAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (!over) {
			if (!paused) {
				if (i == KeyEvent.VK_A && direction != RIGHT && !wait) {
					direction = LEFT;
					wait = true;
				}
				if (i == KeyEvent.VK_D && direction != LEFT && !wait) {
					direction = RIGHT;
					wait = true;
				}
				if (i == KeyEvent.VK_W && direction != DOWN && !wait) {
					direction = UP;
					wait = true;
				}
				if (i == KeyEvent.VK_S && direction != UP && !wait) {
					direction = DOWN;
					wait = true;
				}
				if (i == KeyEvent.VK_SPACE) {
					paused = true;
				}
			} else if (paused) {
				if (i == KeyEvent.VK_SPACE) {
					paused = false;
					controls = false;
				}
				if (i == KeyEvent.VK_N) {
					startGame();
				}
				if (i == KeyEvent.VK_C) {
					controls = true;
				}
				if (i == KeyEvent.VK_D) {
					diff = true;
				}
				if (controls) {
					if (i == KeyEvent.VK_R) {
						controls = false;
					}
				}
				if (diff) {
					if (i == KeyEvent.VK_R) {
						diff = false;
					}
					if (i == KeyEvent.VK_H) {
						DIFF = 1;
						difficulty = "Hard";
					}
					if (i == KeyEvent.VK_M) {
						DIFF = 2;
						difficulty = "Medium";
					}
					if (i == KeyEvent.VK_E) {
						DIFF = 3;
						difficulty = "Easy";
					}
				}
			}
		} else {
			if (i == KeyEvent.VK_N) {
				startGame();
			}
		}
		if (i == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] arg) {
		snake = new Snake();
	}
}
