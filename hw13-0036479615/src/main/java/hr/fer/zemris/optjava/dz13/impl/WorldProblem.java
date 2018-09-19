package hr.fer.zemris.optjava.dz13.impl;

import java.util.List;

import hr.fer.zemris.optjava.dz13.game.Action;
import hr.fer.zemris.optjava.dz13.game.World;
import hr.fer.zemris.optjava.dz13.genetic.IProblem;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class WorldProblem implements IProblem<Tree> {

	private static final int MAX_ACTIONS = 600;
	
	private World initialWorld;
	
	public WorldProblem(World initialWorld) {
		this.initialWorld = initialWorld;
	}

	@Override
	public double getFitness(ISolution<Tree> solution) {
		Tree tree = solution.getRepresentation();
		World world = initialWorld.copy();
		
		int actionsCounter = 0;
		
		while (true) {
			List<Action> actions = tree.evaluate(world);
			boolean finished = false;
			
			for (Action action : actions) {
				world.makeAction(action);
				actionsCounter++;
				
				if (actionsCounter > MAX_ACTIONS) {
					finished = true;
					break;
				}
			}
			
			if (finished) {
				break;
			}
		}
		
		return world.getFoodEaten();
	}

	@Override
	public double getValue(ISolution<Tree> solution) {
		return getFitness(solution);
	}	
}
