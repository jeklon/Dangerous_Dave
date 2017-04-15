package TileMap;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;

	private double x;
	private double y;
	private double moveScale;


	public Background(String s) {
		
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public Background(String s, double ms) {

		try {
			image = ImageIO.read(
					getClass().getResourceAsStream(s)
			);
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}




	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x, (int)y, null);

	}
	
}







