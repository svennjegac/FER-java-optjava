package test;

import java.io.File;
import java.io.IOException;

import hr.fer.zemris.art.GrayScaleImage;

public class Test3 {

	public static void main(String[] args) {
		try {
			GrayScaleImage.load(new File("src/main/resources/11-kuca-200-133.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
