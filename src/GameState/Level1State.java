package GameState;

import Main.GamePanel;
import Objects.Player;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;

    private Player player;


    public Level1State (GameStateManager gsm){

        this.gsm=gsm;
        init();

    }



    @Override
    public void init() {

        tileMap = new TileMap(30); //инициализация карты, размер плитки 30
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);

        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);

    }

    @Override
    public void update() {

        player.update();
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());

    }

    @Override
    public void draw(Graphics2D g) {
        //отрисовка фона уровня
        bg.draw(g);

        //отрисовка tileMap
        tileMap.draw(g);

        //отрисовка player
        player.draw(g);


    }

    @Override
    public void keyPressed(int k) {

        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_Z) player.setJumping(true);
        if(k == KeyEvent.VK_X) player.setFiring();

    }

    @Override
    public void keyReleased(int k) {

        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_Z) player.setJumping(false);



    }
}
