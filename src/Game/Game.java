package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import GUIControls.Window;
import Models.Direction;
import Models.Pellet;
import Models.Snake;

public class Game extends JPanel {
	private Timer timer;
	private Snake snake;
	private Pellet pellet;
	private final int GRID_SIZE = 20;
	private Direction nextDirection = Direction.UP;
	private int numberOfRows;
	private int numberOfColumns;
	private final int MOVEMENT_DELAY = 22; // higher value == snake moves slower
	private int movementDelayCounter;

	private int score = 0; // NEW: score variable

	public Game() {
		Window.setTitle("Snake");
		setDoubleBuffered(true);

		snake = new Snake(180, 300, GRID_SIZE);

		pellet = new Pellet();

		// game loop
		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (movementDelayCounter >= MOVEMENT_DELAY) {

					if (nextDirection != null) {
						snake.setDirection(nextDirection);
					}

					snake.move();

					movementDelayCounter = 0;
					nextDirection = null;
				}

				// game over if snake hits wall
				if (snake.getXLocation() < 0 || snake.getXLocation() > getWidth()
						|| snake.getYLocation() < 0 || snake.getYLocation() > getHeight()) {
					System.exit(1);
				}

				// game over if snake hits its tail
				if (snake.hasCollidedWithTail()) {
					System.exit(1);
				}

				// snake eats pellet
				if (snake.getXLocation() == pellet.getXLocation() && snake.getYLocation() == pellet.getYLocation()) {
					Point newPelletLocation = getRandomGridCoords();
					pellet.setXLocation(newPelletLocation.x * GRID_SIZE);
					pellet.setYLocation(newPelletLocation.y * GRID_SIZE);
					snake.addSegment();

					score++; // NEW: increase score
				}

				movementDelayCounter++;

				repaint();
			}
		});

		// keyboard controls
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (nextDirection == null) {

					if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
						if (snake.getDirection() == Direction.UP || snake.getDirection() == Direction.DOWN) {
							nextDirection = Direction.LEFT;
						}
					}

					if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (snake.getDirection() == Direction.UP || snake.getDirection() == Direction.DOWN) {
							nextDirection = Direction.RIGHT;
						}
					}

					if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
						if (snake.getDirection() == Direction.LEFT || snake.getDirection() == Direction.RIGHT) {
							nextDirection = Direction.UP;
						}
					}

					if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
						if (snake.getDirection() == Direction.LEFT || snake.getDirection() == Direction.RIGHT) {
							nextDirection = Direction.DOWN;
						}
					}
				}
			}
		});

		this.setFocusable(true);
	}

	public void startGame() {

		numberOfRows = this.getHeight() / GRID_SIZE;
		numberOfColumns = this.getWidth() / GRID_SIZE;

		Point pelletStartLocation = getRandomGridCoords();
		pellet.setXLocation(pelletStartLocation.x * GRID_SIZE);
		pellet.setYLocation(pelletStartLocation.y * GRID_SIZE);

		timer.start();

		this.grabFocus();
	}

	private Point getRandomGridCoords() {
		Random random = new Random();
		return new Point(random.nextInt(numberOfColumns), random.nextInt(numberOfRows));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D brush = (Graphics2D) g;

		drawGrid(brush);
		snake.draw(brush);
		pellet.draw(brush);

		// NEW: draw score in top right
		brush.setColor(Color.white);
		brush.setFont(new Font("Arial", Font.BOLD, 18));
		brush.drawString("Score: " + score, getWidth() - 120, 20);

		// original credit text
		brush.setColor(Color.black);
		brush.setFont(new Font("Arial", 0, 10));
		brush.drawString("Created by: ARTech Industries", getWidth() - 146, getHeight() - 5);
	}

	private void drawGrid(Graphics2D g) {
		Color oldColor = g.getColor();
		int incrementTracker = 0;

		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {

				Color gridColor = j % 2 == incrementTracker ? new Color(0, 100, 0) : new Color(144, 238, 144);
				g.setColor(gridColor);
				g.fillRect(j * GRID_SIZE, i * GRID_SIZE, GRID_SIZE, GRID_SIZE);
			}

			incrementTracker = incrementTracker == 0 ? 1 : 0;
		}

		g.setColor(oldColor);
	}
}