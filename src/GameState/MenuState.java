package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

//состояние меню

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0; // отслеживание выбраной опции
	private String[] options = {
		"Start",
		"Help",
		"Quit"
	};
	
	private Color titleColor; // цвет заголовка
	private Font titleFont; // шрифт заголовка
	
	private Font font; // шрифт опций
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menubg.gif");
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					28); // размер шрифта 28
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {

	}
	
	public void draw(Graphics2D g) {
		
		// draw bg отрисовка заднего фона
		bg.draw(g);
		
		// draw title отрисовка заголовка
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Dangerous", 80, 30);
		g.drawString("Dave", 120, 55);
		
		// draw menu options отрисовка опций меню
		g.setFont(font);
		for(int i = 0; i < options.length; i++) { // цикл для прохода по опциям
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 160 + i * 15); // отступ 15 пикселей для каждой опции
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE); // 1й пункт опций запускает 1й уровень игры
		}
		if(currentChoice == 1) {
			// help
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) { // переход с первой опции на последнюю
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) { // переход с последней опции на первую
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
}










