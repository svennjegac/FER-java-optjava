package hr.fer.zemris.optjava.dz2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Util {

	public static void drawTrajectory(int edge, List<double[]> points, String pictureName) {
		BufferedImage bim = new BufferedImage(edge, edge, BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D g2d = bim.createGraphics();
		//draw background
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, edge, edge);

		//draw axis x and y
		g2d.setColor(Color.BLACK);
		g2d.drawLine(0, edge / 2, edge, edge / 2);
		g2d.drawLine(edge / 2, 0, edge / 2, edge);
		
		//find max absolute coordinate
		double max = -1;
		for (double[] point : points) {
			if (Math.abs(point[0]) > max) {
				max = Math.abs(point[0]);
			}
			
			if (Math.abs(point[1]) > max) {
				max = Math.abs(point[1]);
			}
		}
		
		//draw points
		for (int i = 0; i < points.size() - 1; i++) {
			double[] first = points.get(i);
			double[] second = points.get(i + 1);

			g2d.drawLine(
					scaleXCoord(edge, max, first[0]),
					scaleYCoord(edge, max, first[1]),
					scaleXCoord(edge, max, second[0]),
					scaleYCoord(edge, max, second[1])
			);
		}
		
		//output image to disk
		try (OutputStream os = Files.newOutputStream(Paths.get(pictureName + ".png"))) {
			ImageIO.write(bim, "png", os);
		} catch (IOException e) {
			System.out.println("Pisanje slike na disk nije uspjelo.");
		}
	}
	
	private static int scaleXCoord(int edge, double max, double value) {
		return (int) ((double) edge / (2 * max) * (value + max));
	}
	
	private static int scaleYCoord(int edge, double max, double value) {
		return (int) ((double) edge / (2 * max) * (max - value));
	}
	
	public static String getPointsAsText(List<double[]> points) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("###.###");
		
		for (int i = 0; i < points.size(); i++) {
			sb.append("" + i + ". => ");
			for (int j = 0; j < points.get(i).length; j++) {
				sb.append(df.format(points.get(i)[j]) + ", ");
			}
			
			sb.append("\r\n");
		}
		
		return sb.toString();
	}
	
	public static List<double[]> parseProblem(String fileName) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				Sustav.class.getResourceAsStream("/" + fileName), StandardCharsets.UTF_8))) {
			
			List<double[]> rows = new ArrayList<>();
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				
				line = line.trim();
				if (line.startsWith("#")) {
					continue;
				}
				
				String[] arguments = line.substring(1, line.length() - 1).split(",");
				double[] row = new double[arguments.length];
				
				for (int i = 0; i < arguments.length; i++) {
					row[i] = Double.parseDouble(arguments[i]);
				}
				
				rows.add(row);
			}
			
			return rows;
			
		} catch (IOException | NumberFormatException | NullPointerException e) {
			return null;
		}
	}
}
