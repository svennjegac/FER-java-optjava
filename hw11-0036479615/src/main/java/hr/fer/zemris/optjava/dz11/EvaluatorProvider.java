package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.IOException;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;

public class EvaluatorProvider {

	private static ThreadLocal<Evaluator> threadLocal = new ThreadLocal<>();
	
	public static Evaluator getEvaluator(String image) {
		Evaluator evaluator = threadLocal.get();
		
		if (evaluator == null) {
			try {
				evaluator = new Evaluator(GrayScaleImage.load(new File(image)));
			} catch (IOException e) {
			}
			
			threadLocal.set(evaluator);
		}
		
		return evaluator;
	}
}
