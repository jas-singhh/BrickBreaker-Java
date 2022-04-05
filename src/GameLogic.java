package game.brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

public class GameLogic extends JPanel implements ActionListener, KeyListener {
	
	private final int DELAY = 5;
	private final int PADDLE_SPEED = 10;
	
	private Timer timer;
	private boolean isStarted;
	private boolean isGameWon;
	private Random rnd;
	
	// Paddle
	private int paddleWidth;
	private int paddleHeight;
	private int paddleX;
	private int paddleY;
	
	// Ball
	private int ballWidth;
	private int ballX;
	private int ballY;
	private int ballSpeedX;
	private int ballSpeedY;
	
	// Brick
	private int brickWidth;
	private int brickHeight;
	private int brickX;
	private int brickY;
	private int brickSpeedX;

	GameLogic() {
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(true);
		timer = new Timer(DELAY, this);
		timer.start();
		rnd = new Random();
		isStarted = false;
		isGameWon = false;
				
		paddleWidth = 80;
		paddleHeight = 15;
		paddleX = (GameFrame.WIDTH / 2) - (paddleWidth / 2);// center the paddle
		paddleY = GameFrame.HEIGHT - (paddleHeight * 4);
		
		ballWidth = 15;
		ballX = (GameFrame.WIDTH / 2) - (ballWidth / 2);
		ballY = paddleY - (ballWidth * 20);
		ballSpeedX = 3;
		ballSpeedY = 3;
		
		brickWidth = 50;
		brickHeight = 10;
		brickX = paddleX;
		brickY = (brickHeight * 4);
		brickSpeedX = rnd.nextInt(4) + 1;
	}
	
	@Override
	public void paint(Graphics g) {
		// Canvas
		g.setColor(Color.black);
		g.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
		
		// Start text
		if (!isStarted && !isGameWon) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.ITALIC, 20));
			g.drawString("Press Space to start", (GameFrame.WIDTH / 2) - 80, GameFrame.HEIGHT / 2);
		}
		
		// Paddle
		g.setColor(Color.orange);
		g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
		
		// Ball
		g.setColor(Color.white);
		g.fillOval(ballX, ballY, ballWidth, ballWidth);
		
		// Brick
		g.setColor(Color.red);
		g.fillRect(brickX, brickY, brickWidth, brickHeight);
		
		if (isGameWon && !isStarted) {
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("You won. Press space to restart", (GameFrame.WIDTH / 2) - 80, GameFrame.HEIGHT / 2);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Space key pressed, so start the game
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			isStarted = true;
			isGameWon = false;
		}
		
		if (isStarted) {// game started, so allow the player to move the paddle
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				moveRight();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				moveLeft();
			}
		}
		
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isStarted) {
			moveBrick();
			moveBall();
			checkPaddleBounce();
			checkIfGameOver();
			checkIfGameWon();
		}
		repaint();
	}
	
	private void checkPaddleBounce() {
		Rectangle paddleRect = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
		Rectangle ballRect = new Rectangle(ballX, ballY, ballWidth, ballWidth);
		
		if (paddleRect.intersects(ballRect)) {
			ballSpeedY *= -1;
		}
	}
	
	private void moveBall() {
		if (ballX <= 0 || ballX >= (GameFrame.WIDTH - 30)) {
			ballSpeedX *= -1;
		}
		
		if (ballY <= 0) {
			ballSpeedY *= -1;
		}
		
		ballX += ballSpeedX;
		ballY += ballSpeedY;
	}
	
	private void moveBrick() {
		if (brickX <= 0 || brickX >= (GameFrame.WIDTH - brickWidth)) {
			brickSpeedX *= -1;
		}
		brickX += brickSpeedX;
	}
	
	private void checkIfGameOver() {
		if (ballY >= (GameFrame.HEIGHT - 50)) {
			onGameRestarted();
		}
	}
	
	private void checkIfGameWon() {
		Rectangle ballRect = new Rectangle(ballX, ballY, ballWidth, ballWidth);
		Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
		
		if (ballRect.intersects(brickRect)) {
			onGameRestarted();
			isGameWon = true;
		}
	}
	
	// Reset the game when it is restarted
	private void onGameRestarted() {
		isStarted = false;
		
		paddleX = (GameFrame.WIDTH / 2) - (paddleWidth / 2);
		paddleY = GameFrame.HEIGHT - (paddleHeight * 4);
		
		ballX = (GameFrame.WIDTH / 2) - (ballWidth / 2);
		ballY = paddleY - (ballWidth * 20);
	}
	
	private void moveRight() {
		if (paddleX >= (GameFrame.WIDTH - paddleWidth)) {
			paddleX = GameFrame.WIDTH - paddleWidth;
		}
		paddleX += PADDLE_SPEED;
	}
	
	private void moveLeft() {
		if (paddleX <= 0) {
			paddleX = 0;
		}
		paddleX -= PADDLE_SPEED;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
}
