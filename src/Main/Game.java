package Main;

import javax.swing.JFrame;

public class Game {

	//Version1.1
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Dangerous Dave");
		window.setContentPane(new GamePanel());//область содержимого игры
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//операция закрытия панели
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
}
