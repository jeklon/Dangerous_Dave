package GameState;

import Main.GamePanel;

import java.util.ArrayList;

//Менеджер игровых состояний

public class GameStateManager {
	
	private GameState[] gameStates; // список, в котором содержаться игровые состояния
	private int currentState; //текущее состояние

	public static final int NUMSTATES = 2;
	public static final int MENUSTATE = 0; // 1е состояние меню
	public static final int LEVEL1STATE = 1; // 2е состояние меню
	
	public GameStateManager() { //конструктор
		
		gameStates = new GameState[NUMSTATES]; // инициализация списка
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) { //метод изменения состояний
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init(); // инициализация текущего состояния
	}
	
	public void update() { //метод обновления состояния
	//try{
		//gameStates[currentState].update();
	//}
	//catch (Exception e){
		//e.printStackTrace();
//}

		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		//try{
		//gameStates[currentState].draw(g);
	//}
		//catch (Exception e){
			//e.printStackTrace();
		//}

		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}

	}
	
	public void keyPressed(int k) {
	gameStates[currentState].keyPressed(k);

	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}









