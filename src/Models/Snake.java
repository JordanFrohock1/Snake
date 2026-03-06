package Models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Random;

public class Snake {
	private LinkedList<Segment> segments = new LinkedList<Segment>();
	private Direction direction;
	private int movementAmount;

	public Snake(int startXLocation, int startYLocation, int movementAmount) {
		direction = Direction.UP;
		segments.add(new Segment(startXLocation, startYLocation, Color.green));
		this.movementAmount = movementAmount;
	}

	public int getXLocation() {
		return segments.get(0).getXLocation();
	}

	public int getYLocation() {
		return segments.get(0).getYLocation();
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void addSegment() {
		Segment tail = segments.get(segments.size() - 1);

		Random random = new Random();
		int red = random.nextInt(255);
		int green = random.nextInt(255);
		int blue = random.nextInt(255);
		 
		switch (direction) {
			case LEFT:
				segments.add(new Segment(tail.getXLocation() + movementAmount, tail.getYLocation(), new Color(red, green, blue)));
				break;
			case RIGHT:
				segments.add(new Segment(tail.getXLocation() - movementAmount, tail.getYLocation(), new Color(red, green, blue)));
				break;
			case UP:
				segments.add(new Segment(tail.getXLocation(), tail.getYLocation() + movementAmount, new Color(red, green, blue)));
				break;
			case DOWN:
				segments.add(new Segment(tail.getXLocation(), tail.getYLocation() - movementAmount, new Color(red, green, blue)));
				break;
		}
	}
	
	public boolean hasCollidedWithTail() {
		Segment head = segments.get(0);
		for (int i = 1; i < segments.size(); i++) {
			Segment next = segments.get(i);
			if (head.getXLocation() == next.getXLocation() && head.getYLocation() == next.getYLocation()) {
				return true;
			}
		}
		return false;
	}

	public void move() {


		Random random = new Random();
		int red = random.nextInt(255);
		int green = random.nextInt(255);
		int blue = random.nextInt(255);
		System.out.println(String.format("%s, %s, %s", red, green, blue));

		Segment head = segments.get(0);
		head.setColor(new Color(red, green, blue));
		segments.remove(segments.size() - 1);
		int newXLocation = head.getXLocation();
		int newYLocation = head.getYLocation();
		switch (direction) {
			case LEFT:
				newXLocation -= movementAmount;
				break;
			case RIGHT:
				newXLocation += movementAmount;
				break;
			case UP:
				newYLocation -= movementAmount;
				break;
			case DOWN:
				newYLocation += movementAmount;
				break;
		}
		segments.add(0, new Segment(newXLocation, newYLocation, Color.green));
	}

	public void draw(Graphics2D g) {
		for (Segment segment : segments) {
			segment.draw(g);
		}
	}
}
