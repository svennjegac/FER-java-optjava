package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RNG {

	private static final String PROPERTIES_FILE = "rng-config.properties";
	private static final String RNG_PROVIDER = "rng-provider";
	
	private static IRNGProvider rngProvider;
	
	static {
		Properties properties = new Properties();
		InputStream is = RNG.class.getResourceAsStream("/" + PROPERTIES_FILE);
		
		try {
			properties.load(is);
		} catch (IOException e) {
		}
		
		String rngProviderName = properties.getProperty(RNG_PROVIDER);
		Class<?> referenceToClass = null;
		
		try {
			referenceToClass = RNG.class.getClassLoader().loadClass(rngProviderName);
		} catch (ClassNotFoundException e) {
		}
		
		try {
			rngProvider = (IRNGProvider) referenceToClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
		}
	}
	
	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}