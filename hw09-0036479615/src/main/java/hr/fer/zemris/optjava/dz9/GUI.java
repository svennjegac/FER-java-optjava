package hr.fer.zemris.optjava.dz9;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 800;
	private static final int SCALING_SECURITY = 10;
	private static final int SCALING_OFFSET = 20;
	private static final int OVAL_SIZE = 10;
	
	private List<Point> points;
	
	private JPanel pathPanel;
	
	private double minCoord;
	private double maxCoord;
	
	public GUI(List<Point> points) {
		this.points = points;
		initGUI();
	}


	private void initGUI() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(30, 30);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container cp = this.getContentPane();
		pathPanel = new JPanel();
		cp.add(pathPanel);
		
		initCoords();
	}


	private void initCoords() {
		minCoord = Double.MAX_VALUE;
		maxCoord = -Double.MAX_VALUE;
		
		for (Point point : points) {
			if (point.getX() > maxCoord) {
				maxCoord = point.getX();
			}
			
			if (point.getY() > maxCoord) {
				maxCoord = point.getY();
			}
			
			if (point.getX() < minCoord) {
				minCoord = point.getX();
			}
			
			if (point.getY() < minCoord) {
				minCoord = point.getY();
			}
		}
	}
	
	
	public void showCanvas() {
		this.setVisible(true);
	}
	
	public void draw() {
		Graphics2D pathGraphics = (Graphics2D) pathPanel.getGraphics();	
		initBoard(pathGraphics, pathPanel.getWidth(), pathPanel.getHeight());
		
		drawCities(pathGraphics);
	}
	
	private void drawCities(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		
		for (Point point : points) {
			drawPoint(g2d, point);
		}
	}

	private void drawPoint(Graphics2D g2d, Point p1) {
		int x1 = getX(p1, pathPanel.getWidth());
		int y1 = getY(p1, pathPanel.getHeight());
		
		g2d.fillOval(x1 - OVAL_SIZE / 2, y1 - OVAL_SIZE / 2, OVAL_SIZE, OVAL_SIZE);
	}

	
	private void initBoard(Graphics2D g2d, int width, int height) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
	}
	
	private int getX(Point p, int width) {
		return (int) ((p.getX() - minCoord) / (maxCoord - minCoord) *
				(width - width / SCALING_SECURITY) + width / SCALING_OFFSET);
	}
	
	private int getY(Point p, int height) {
		int y = (int) ((p.getY() - minCoord) / (maxCoord - minCoord) *
				(height - height / SCALING_SECURITY) + height / SCALING_OFFSET);
		
		return height - y;
	}
	
	@Override
	public void paint(Graphics g) {
		draw();
	}
}