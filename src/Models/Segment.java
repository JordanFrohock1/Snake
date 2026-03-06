package Models;

import java.awt.Color;
import java.awt.Graphics2D;

import SwingShapes.Ellipse;

public class Segment {
	private Ellipse segment;
	
	public Segment(int startXLocation, int startYLocation, Color color) {
		segment = new Ellipse();
		segment.setColor(color);
		segment.setSize(20,  20);
		segment.setLocation(startXLocation, startYLocation);
	}

	public int getXLocation() {
		return segment.getXLocation();
	}

	public int getYLocation() {
		return segment.getYLocation();
	}

	public Color getColor() {
		return segment.getFillColor();
	}
	
	public void setColor(Color color) {
		segment.setColor(color);
	}
	
	public void draw(Graphics2D g) {
		segment.paint(g);
	}
}
