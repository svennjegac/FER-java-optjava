package hr.fer.zemris.optjava.dz13;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;

import hr.fer.zemris.optjava.dz13.game.Position;
import hr.fer.zemris.optjava.dz13.game.World;

public class WorldPanel extends JPanel {

	private static final Color BACKGRD = Color.BLACK;
	private static final Color AGENT = Color.RED;
	private static final Color FOOD = Color.BLUE;
	private static final Color NOT_FOOD = Color.YELLOW;
	
	private static final int OFFSET = 2;
	
	private JPanel[][] fields;
	private int height;
	private int width;
	private World world;
	
	public WorldPanel(World world) {
		this.world = world;
		initPanel();
		setOpaque(true);
	}
	
	private void initPanel() {
		height = world.getHeight();
		width = world.getWidth();
		
		fields = new JPanel[height][width];
		setLayout(new GridLayout(height, width, 2, 2));
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				JPanel field = new JPanel();
				fields[i][j] = field;
				this.add(field);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		initFood();
	}

	private void initFood() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				JPanel field = fields[i][j];
				//field.setBackground(BACKGRD);
				Position position = new Position(j, i);
				
				if (world.foodAtPosition(position)) {
					field.setBackground(Color.BLACK);
					drawField(field, FOOD);
				} else if (world.agentPosition(position)) {
					field.setBackground(AGENT);
				} else {
					field.setBackground(NOT_FOOD);
				}
			}
		}
	}

	private void drawField(JPanel field, Color color) {
		Graphics2D g2d = (Graphics2D) field.getGraphics();
		
		g2d.setColor(color);
		g2d.fillRect(OFFSET, OFFSET, field.getWidth() - 2 * OFFSET, field.getHeight() - 2 * OFFSET);
	}
}
