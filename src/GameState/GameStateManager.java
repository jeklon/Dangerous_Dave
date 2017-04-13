package GameState;

import java.util.ArrayList;

//Менеджер игровых состояний

public class GameStateManager {
	
	private ArrayList<GameState> gameStates; // список, в котором содержаться игровые состояния
	private int currentState; //текущее состояние
	
	public static final int MENUSTATE = 0; // 1е состояние меню
	public static final int LEVEL1STATE = 1; // 2е состояние меню
	
	public GameStateManager() { //конструктор
		
		gameStates = new ArrayList<GameState>(); // инициализация списка
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new Level1State(this)); //состояние перехода на 1й уровень игры
		
	}
	
	public void setState(int state) { //метод изменения состояний
		currentState = state;
		gameStates.get(currentState).init(); // инициализация текущего состояния
	}
	
	public void update() { //метод обновления состояния

		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
}









