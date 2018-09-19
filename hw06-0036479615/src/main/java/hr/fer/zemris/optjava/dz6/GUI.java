package hr.fer.zemris.optjava.dz6;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hr.fer.zemris.optjava.dz6.algorithms.ant.Edge;
import hr.fer.zemris.optjava.dz6.algorithms.ant.EdgeData;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ICanvas;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;
import hr.fer.zemris.optjava.dz6.algorithms.ant.Point;

public class GUI extends JFrame implements ICanvas<int[]> {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 800;
	private static final int SCALING_SECURITY = 10;
	private static final int SCALING_OFFSET = 20;
	private static final int OVAL_SIZE = 10;
	
	private List<Point> points;
	private ISolution<int[]> bestSolution;
	private Map<Edge, EdgeData> pheromoneEdges;
	private boolean active;
	
	private JPanel pathPanel;
	private JPanel pheromonesPanel;
	
	private double minCoord;
	private double maxCoord;
	
	public GUI(List<Point> points, boolean active) {
		this.active = active;
		
		if (!active) {
			return;
		}
		
		this.points = points;
		initGUI();
	}


	private void initGUI() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(30, 30);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container cp = this.getContentPane();
		cp.setLayout(new GridLayout(1, 2));
		
		pathPanel = new JPanel();
		pheromonesPanel = new JPanel();
		
		cp.add(pathPanel);
		cp.add(pheromonesPanel);
		
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
	
	@Override
	public void showCanvas() {
		if (!active) {
			return;
		}
		
		this.setVisible(true);
	}
	
	@Override
	public void drawSolution(ISolution<int[]> solution) {
		if (!active) {
			return;
		}
		
		bestSolution = solution;
	
		Graphics2D pathGraphics = (Graphics2D) pathPanel.getGraphics();	
		initBoard(pathGraphics, pathPanel.getWidth(), pathPanel.getHeight());
		
		drawCities(pathGraphics);
	}
	
	private void drawCities(Graphics2D g2d) {
		int[] cities = bestSolution.getRepresentation();
		
		g2d.setColor(Color.BLACK);
		
		for (int i = 0; i < cities.length - 1; i++) {
			Point p1 = getPointById(cities[i]);
			Point p2 = getPointById(cities[i + 1]);
			
			drawPoint(g2d, p1);
			drawPoint(g2d, p2);
			drawLine(g2d, p1, p2);
		}
		
		Point p1 = getPointById(cities[0]);
		Point p2 = getPointById(cities[cities.length - 1]);
		
		drawPoint(g2d, p1);
		drawPoint(g2d, p2);
		drawLine(g2d, p1, p2);
	}

	private void drawPoint(Graphics2D g2d, Point p1) {
		int x1 = getX(p1, pathPanel.getWidth());
		int y1 = getY(p1, pathPanel.getHeight());
		
		g2d.fillOval(x1 - OVAL_SIZE / 2, y1 - OVAL_SIZE / 2, OVAL_SIZE, OVAL_SIZE);
	}


	private void drawLine(Graphics2D g2d, Point p1, Point p2) {
		int panelWidth = pathPanel.getWidth();
		int panelHeight = pathPanel.getHeight();
		
		int x1 = getX(p1, panelWidth);
		int x2 = getX(p2, panelWidth);
		int y1 = getY(p1, panelHeight);
		int y2 = getY(p2, panelHeight);
		
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public void drawPheromones(Map<Edge, EdgeData> pheromoneEdges) {
		if (!active) {
			return;
		}
		
		this.pheromoneEdges = pheromoneEdges;
		
		Graphics2D pheromonesGraphics = (Graphics2D) pheromonesPanel.getGraphics();
		initBoard(pheromonesGraphics, pheromonesPanel.getWidth(), pheromonesPanel.getHeight());
		
		drawPheromoneEdges(pheromonesGraphics);
	}
	
	private void initBoard(Graphics2D g2d, int width, int height) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
	}
	
	private void drawPheromoneEdges(Graphics2D g2d) {
		List<Double> values = getMinMaxPheromoneValues();
		
		double minValue = values.get(0);
		double maxValue = values.get(values.size() - 1);
		
		pheromoneEdges
				.entrySet()
				.stream()
				.sorted((en1, en2) -> {
						return Double.compare(en1.getValue().getPheromoneValue(), en2.getValue().getPheromoneValue());
				}).forEach(entry -> {
							drawEdge(
								g2d,
								getPointById(entry.getKey().getId1()),
								getPointById(entry.getKey().getId2()),
								entry.getValue().getPheromoneValue(),
								minValue,
								maxValue);
				});
	}


	private List<Double> getMinMaxPheromoneValues() {
		return pheromoneEdges
				.entrySet()
				.stream()
				.map(entry -> entry.getValue().getPheromoneValue())
				.sorted()
				.collect(Collectors.toList());
	}


	private void drawEdge(Graphics2D g2d, Point p1, Point p2,
			double pheromoneValue, double minValue, double maxValue) {
		
		int panelHeight = pheromonesPanel.getHeight();
		int panelWidth = pheromonesPanel.getWidth();
		
		int x1 = getX(p1, panelWidth);
		int x2 = getX(p2, panelWidth);
		int y1 = getY(p1, panelHeight);
		int y2 = getY(p2, panelHeight);
		
		double scale = ((pheromoneValue - minValue) / (maxValue - minValue));
		
		int color = (int) ((1- scale) * 255);
		color = color < 0 ? 0 : color;
		color = color > 255 ? 255 : color;
		
		g2d.setColor(new Color(255, color, color));
		g2d.drawLine(x1, y1, x2, y2);
	}

	private Point getPointById(int id) {
		for (Point point : points) {
			if (point.getId() == id) {
				return point;
			}
		}
		
		return null;
	}
	
	private int getX(Point p, int width) {
		return (int) ((p.getX() - minCoord) / (maxCoord - minCoord) *
				(width - width / SCALING_SECURITY) + width / SCALING_OFFSET);
	}
	
	private int getY(Point p, int height) {
		return (int) ((p.getY() - minCoord) / (maxCoord - minCoord) *
				(height - height / SCALING_SECURITY) + height / SCALING_OFFSET);
	}
	
	@Override
	public void paint(Graphics g) {
		if (bestSolution != null && pheromoneEdges != null) {
			drawSolution(bestSolution);
			drawPheromones(pheromoneEdges);
		}
	}
}
