import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

public class SnakeCanvas extends Canvas implements ActionListener, KeyListener{ 
	private Timer t;
	public int ScoreCounter= 0;
	public int HighScoreCounter = 0;

	public SnakeCanvas(){
		snake = new LinkedList<Point>();
		PlaceFood();
		MakeNewSnake();
		direction = Direction.noDirection;
		Move();
		System.out.println("Starting direction: "+direction.toString());
		t = new Timer(55,this);
		t.start();
		this.addKeyListener(this);
		int timerVar= 55;
		
	}


	public void actionPerformed(ActionEvent e) {
		Move();
		repaint();
	}

	private final int heightBox = 15; 
	private final int widthBox = 15; 
	private final int widthGrid = 40; 
	private final int heightGrid = 25; 
	private Direction direction;
	private LinkedList<Point> snake;
	private Point food;
	

	public void paint(Graphics g)
	{
		drawGrid (g);
		drawSnake (g);
		drawFood (g);
		drawScore (g);
	}


	public void drawScore (Graphics g) {
		g.setColor(Color.WHITE);
		g.drawChars(("Score: "+ScoreCounter).toCharArray(), 0, ("Score: "+ScoreCounter).length(), 0, 400);
		g.drawChars(("High Score: "+HighScoreCounter).toCharArray(), 0, ("High Score: "+HighScoreCounter).length(), 0, 420);
	}


	public void drawGrid(Graphics g){
		// drawing the border rectangle
		g.setColor(Color.WHITE);
		g.drawRect(0,0, widthGrid * widthBox, heightGrid*heightBox);
		
		//drawing the vertical lines
	/*	for (int x = widthBox;x <widthGrid * widthBox; x+=widthBox){

			g.drawLine(x, 0, x, heightBox*heightGrid);			
		}

		for (int y= heightBox; y <heightGrid * heightBox; y+=heightBox){
			g.drawLine(0, y, widthGrid*widthBox, y);	  
		}*/
	}





	public void MakeNewSnake(){
		snake.clear();
		snake.add(new Point (13,13));
		snake.add(new Point (13,12));
		snake.add(new Point (13,11));
		snake.add(new Point (13,10));
		direction = direction.noDirection;
		if (ScoreCounter>HighScoreCounter){
			HighScoreCounter = ScoreCounter;
		}
		ScoreCounter = 0;
	}

	public void drawSnake(Graphics g){
		// looping every Point (p) in the snake
		for (Point a : snake){
			if (a == snake.peekFirst())
				g.setColor(Color.green);
			else
				g.setColor(Color.cyan);
			g.fillRect(a.x*widthBox, a.y*heightBox, widthBox, heightBox); 
		}

		// background is black
		g.setColor(Color.BLACK); 

	}




	public void drawFood(Graphics g)
	{
		g.setColor(Color.red);

		//making a red circle in a box in the grid
		g.fillRect(food.x*widthBox, food.y*heightBox, widthBox, heightBox);

	}

	public void PlaceFood(){
		Random d = new Random();
		int randomx = d.nextInt(widthGrid);
		int randomy = d.nextInt(heightGrid);
		Point dPoint = new Point(randomx,randomy);

		while (snake.contains(dPoint)){

			randomx = d.nextInt(widthGrid);
			randomy = d.nextInt(heightGrid);
			dPoint = new Point(randomx,randomy);
		}
		food = dPoint;

	}

	public void Move(){


		Point head = snake.peekFirst();

		Point newPoint = head;

		if (direction == direction.NORTH)
			newPoint = new Point(head.x, head.y - 1);

		if (direction == direction.SOUTH)
			newPoint = new Point(head.x, head.y + 1);

		if (direction == direction.WEST)
			newPoint = new Point(head.x-1, head.y);

		if (direction == direction.EAST)
			newPoint = new Point(head.x+1, head.y);

		//removing the last piece of snake
		snake.remove(snake.peekLast());

		if (newPoint.equals(food)){
			// the snake hit food
			Point headpoint = (Point) newPoint.clone();
			
			if (direction == direction.NORTH)
				newPoint = new Point(head.x, head.y - 1);

			if (direction == direction.SOUTH)
				newPoint = new Point(head.x, head.y + 1);

			if (direction == direction.WEST)
				newPoint = new Point(head.x-1, head.y);

			if (direction == direction.EAST)
				newPoint = new Point(head.x+1, head.y);

			snake.push(headpoint);
			PlaceFood();
			ScoreCounter++;
		}


		//checking if it is out of bounds
		else if(newPoint.x<0||newPoint.x>39 ){
			//we went oob, reset game
			MakeNewSnake();
			return;
			

		}
		else if(newPoint.y<0||newPoint.y>24){
			//we went oob, reset game
			MakeNewSnake();
			return;
		}

		else if (snake.contains(newPoint)){
			//we ran into ourselves, reset game
			MakeNewSnake();
			return;
		}

		//puts the newPoint in the first position of the snake
		snake.push(newPoint);
		return;
	}

	public void keyPressed(KeyEvent e){
		System.out.println("Key Pressed.");
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (direction != direction.SOUTH)
				direction = direction.NORTH;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			if (direction != direction.WEST)
				direction = direction.EAST;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (direction != direction.EAST)
				direction = direction.WEST;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (direction != direction.NORTH)
				direction = direction.SOUTH;
		}
		System.out.println("Direction: " + direction.toString());
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
}