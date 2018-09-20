package hr.fer.zemris.optjava.dz13;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class WorldGUI extends JFrame {

	private static final long serialVersionUID = 141421421421421L;

	private static final int MAX_ACTIONS = 600;
	
	private static final String ACTIONS_LABEL = "Actions: ";
	private static final String FOOD_EATEN = "Food eaten: ";
	private static final String ORIENTATION = "Orientation: ";
	
	private World world;
	private Tree tree;
	private int actionsCounter;
	private List<Action> actions;
	
	public WorldGUI(World world, ISolution<Tree> solution) {
		setSize(500, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(300, 200);
		
		this.world = world;
		tree = solution.getRepresentation();
		actionsCounter = 0;
		actions = new ArrayList<>();
		
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel navigation = new JPanel();
		JButton nextButton = new JButton("Next");
		
		JLabel actions = new JLabel();
		JLabel foodEaten = new JLabel();
		JLabel orientation = new JLabel();
		
		setActions(actions);
		setOrientation(orientation);
		setFoodEaten(foodEaten);
		
		navigation.add(nextButton);
		navigation.add(actions);
		navigation.add(foodEaten);
		navigation.add(orientation);
		
		WorldPanel worldPanel = new WorldPanel(world);
		
		cp.add(navigation, BorderLayout.NORTH);
		cp.add(worldPanel, BorderLayout.CENTER);
		
		nextButton.addActionListener(e -> {
			if (this.actions.isEmpty()) {
				this.actions = tree.evaluate(world);
			}
			
			if (actionsCounter >= MAX_ACTIONS) {
				return;
			}
			
			Action action = this.actions.remove(0);
			world.makeAction(action);
			
			actionsCounter++;
			
			setActions(actions);
			setOrientation(orientation);
			setFoodEaten(foodEaten);
			
			this.repaint();
		});
	}
	
	private void setActions(JLabel label) {
		label.setText(ACTIONS_LABEL + actionsCounter);
	}
	
	private void setFoodEaten(JLabel label) {
		label.setText(FOOD_EATEN + world.getFoodEaten());
	}
	
	private void setOrientation(JLabel label) {
		label.setText(ORIENTATION + world.getOrientation());
	}
}
