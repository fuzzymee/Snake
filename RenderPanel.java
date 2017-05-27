package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public Font over = new Font("default", Font.BOLD, 20);
	public Font inGame = new Font("default", Font.PLAIN + Font.BOLD, 14);
	public Font pause = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	public Font controls = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	public int padding_right = 300;
	public int padding_left = 275;
	public boolean clear = true;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Snake snake = Snake.snake;
		
		//paint current game
		paintGame(snake, g);
		
		//paint game over
		if (snake.over) {
			paintGameOver(snake, g);
			clear = false;
		}
		
		//paint pause
		if (snake.paused && (!snake.controls || !snake.diff)) {
			if (!clear) {
				g.clearRect(0, 0, getWidth(), getHeight());
			} else {
				clear = false;
			}
			paintGame(snake, g);
			paintPaused(snake, g);
		}
		
		//paint controls
		if (snake.controls && !snake.diff) {
			if (!clear) {
				g.clearRect(0, 0, getWidth(), getHeight());
			} else {
				clear = false;
			}
			paintGame(snake, g);
			paintControls(snake, g);
		}
		
		//print difficulty
		if (snake.diff && !snake.controls) {
			if (!clear) {
				g.clearRect(0, 0, getWidth(), getHeight());
			} else {
				clear = false;
			}
			paintGame(snake, g);
			paintDifficulty(snake, g);
		}
	}
	
	public void paintGame(Snake snake, Graphics g) {
		g.setFont(inGame);
		FontMetrics fm = g.getFontMetrics();
		clear = true;
		//paint background
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 800);
				
		//paint snake and cherries
		paintSnake(snake, g);
		
		int newline = fm.getHeight();
		int h = newline;
		int x = 4;
		
		//paint scoreboard
		String score = "Score: " + snake.score;
		String length = "Length: " + snake.tailLength;
		String time = "Time: " + snake.ticks / 40;
		String diff = "Difficulty: " + Snake.difficulty;
		
		g.setColor(Color.white);
		g.drawString(score, x, h);
		h += newline;
		g.drawString(length, x, h);
		h += newline;
		g.drawString(time, x, h);
		h += newline;
		g.drawString(diff, x, h);
	}
	
	public void paintSnake(Snake snake, Graphics g) {
		//paint snake
		g.setColor(Color.RED);
		for (Point point : snake.snakeParts) {
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		}
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		//paint cherries
		g.setColor(Color.GREEN);
		g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
	}
	
	public void paintGameOver(Snake snake, Graphics g) {
		g.setFont(over);
		g.setColor(Color.RED);
		FontMetrics fm = g.getFontMetrics();
		String gameOver = "Game Over";
		String score = "Score: " + snake.score;
		String length = "Length: " + snake.tailLength;
		String time = "Time: " + snake.ticks / 40;
		int h = snake.jframe.getHeight() / 3;
		int newline = fm.getHeight();
		int line = 0;
		g.drawString(gameOver, getCenter(snake, fm, gameOver), h);
		underline(snake, fm, gameOver, g);
		line++;
		g.drawString(score, getCenter(snake, fm, score), h + newline * line);
		line++;
		g.drawString(length, getCenter(snake, fm, length), h + newline * line);
		line++;
		g.drawString(time, getCenter(snake, fm, time), h + newline * line);
	}
	
	public void paintPaused(Snake snake, Graphics g) {
		g.setFont(pause);
		g.setColor(Color.yellow);
		FontMetrics fm = g.getFontMetrics();
		
		String paused = "Game Paused";
		String menuControl = "Controls";
		String menuC = "[C]";
		String menuExit = "Exit Game";
		String menuESC = "[ESC]";
		String menuResume = "Resume";
		String menuSpace = "[Space]";
		String difficulty = "Difficulty";
		String diff = "[D]";
		
		int total_width_right = snake.jframe.getWidth() - padding_right;
		int h = snake.jframe.getHeight() / 3;
		int newline = fm.getHeight();
		int line = 0;
		
		g.drawString(paused, getCenter(snake, fm, paused), h);
		underline(snake, fm, paused, g);
		line++;
		line++;
		g.drawString(menuResume, padding_left, h + newline * line);
		g.drawString(menuSpace, total_width_right - g.getFontMetrics().stringWidth(menuSpace), h + newline * line);
		line++;
		g.drawString(difficulty, padding_left, h + newline * line);
		g.drawString(diff, total_width_right - g.getFontMetrics().stringWidth(diff), h + newline * line);
		line++;
		g.drawString(menuControl, padding_left, h + newline * line);
		g.drawString(menuC, total_width_right - g.getFontMetrics().stringWidth(menuC), h + newline * line);
		line++;
		g.drawString(menuExit, padding_left, h + newline * line);
		g.drawString(menuESC, total_width_right - g.getFontMetrics().stringWidth(menuESC), h + newline * line);
	}
	
	public void paintControls(Snake snake, Graphics g) {
		g.setFont(controls);
		g.setColor(Color.orange);
		FontMetrics fm = g.getFontMetrics();
		
		String controls = "Controls";
		String moveLeft = "Turn Left";
		String conA = "[A]";
		String moveRight = "Turn Right"; 
		String conD = "[D]";
		String moveUp = "Turn Up";
		String conW = "[W]";
		String moveDown = "Turn Down";
		String conS = "[S]";
		String pause = "Resume"; 
		String conSpace = "[Space]";
		String returnMenu = "Return";
		String conR = "[R]";
		String exitGame = "Exit Game";
		String conESC = "[ESC]";
		String newGame = "New Game";
		String conN = "[N]";
		
		int total_width_right = snake.jframe.getWidth() - padding_right;
		int h = snake.jframe.getHeight() / 3;
		int newline = fm.getHeight();
		int line = 0;
		
		g.drawString(controls, getCenter(snake, fm, controls), h);
		underline(snake, fm, controls, g);
		line++;
		g.drawString(moveLeft, padding_left, h + newline * line);
		g.drawString(conA, total_width_right - g.getFontMetrics().stringWidth(conA), h + newline * line);
		line++;
		g.drawString(moveRight, padding_left, h + newline * line);
		g.drawString(conD, total_width_right - g.getFontMetrics().stringWidth(conD), h + newline * line);
		line++;
		g.drawString(moveUp, padding_left, h + newline * line);
		g.drawString(conW, total_width_right - g.getFontMetrics().stringWidth(conW), h + newline * line);
		line++;
		g.drawString(moveDown, padding_left, h + newline * line);
		g.drawString(conS, total_width_right - g.getFontMetrics().stringWidth(conS), h + newline * line);
		line++;
		g.drawString(returnMenu, padding_left, h + newline * line);
		g.drawString(conR, total_width_right - g.getFontMetrics().stringWidth(conR), h + newline * line);
		line++;
		g.drawString(pause, padding_left, h + newline * line);
		g.drawString(conSpace, total_width_right - g.getFontMetrics().stringWidth(conSpace), h + newline * line);
		line++;
		g.drawString(exitGame, padding_left, h + newline * line);
		g.drawString(conESC, total_width_right - g.getFontMetrics().stringWidth(conESC), h + newline * line);
		line++;
		g.drawString(newGame, padding_left, h + newline * line);
		g.drawString(conN, total_width_right - g.getFontMetrics().stringWidth(conN), h + newline * line);
	}
	
	public void paintDifficulty(Snake snake, Graphics g) {
		g.setFont(controls);
		g.setColor(Color.blue);
		FontMetrics fm = g.getFontMetrics();
		
		int total_width_right = snake.jframe.getWidth() - padding_right;
		int h = snake.jframe.getHeight() / 3;
		int newline = fm.getHeight();
		int line = 0;
		
		String diff = "Difficulty Settings";
		String easy = "Easy";
		String eKey = "[E]";
		String med = "Medium";
		String mKey = "[M]";
		String hard = "Hard";
		String hKey = "[H]";
		
		g.drawString(diff, getCenter(snake, fm, diff), h);
		underline(snake, fm, diff, g);
		line++;
		line++;
		g.drawString(easy, padding_left, h + newline * line);
		g.drawString(eKey, total_width_right - g.getFontMetrics().stringWidth(eKey), h + newline * line);
		line++;
		g.drawString(med, padding_left, h + newline * line);
		g.drawString(mKey, total_width_right - g.getFontMetrics().stringWidth(mKey), h + newline * line);
		line++;
		g.drawString(hard, padding_left, h + newline * line);
		g.drawString(hKey, total_width_right - g.getFontMetrics().stringWidth(hKey), h + newline * line);
	}
	
	public void underline(Snake snake, FontMetrics fm, String s, Graphics g) {
		int h = snake.jframe.getHeight() / 3;
		g.drawLine(getCenter(snake, fm, s), h + 2, getCenter(snake, fm, s) + fm.stringWidth(s), h + 2);
	}
	
	public int getCenter(Snake snake, FontMetrics fm, String s) {
		return snake.jframe.getWidth() / 2 - fm.stringWidth(s) / 2;
	}
}
