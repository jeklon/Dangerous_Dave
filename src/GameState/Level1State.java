package GameState;

import Main.GamePanel;
import Objects.Enemies.Zombie;
import Objects.Enemy;
import Objects.Explosion;
import Objects.HUD;
import Objects.Player;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;

    private Player player;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private HUD hud;


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
        tileMap.setTween(1);

        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);

        populateEnemies();

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

    }

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        Zombie s;
        Point[] points = new Point[] {
                new Point(200, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200)
        };
        for(int i = 0; i < points.length; i++) {
            s = new Zombie(tileMap);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);
        }

    }

    @Override
    public void update() {

        // обновление player
        player.update();
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());

        // обновление фона
        bg.setPosition(tileMap.getx(), tileMap.gety());

        //проверка на атаку
        player.checkAttack(enemies);

        //обновление всех врагов
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if(e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }

        // обновление взрывов
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }
        }



    @Override
    public void draw(Graphics2D g) {
        //отрисовка фона уровня
        bg.draw(g);

        //отрисовка tileMap
        tileMap.draw(g);

        //отрисовка player
        player.draw(g);

        //отрисовка врагов
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // отрисовка взрывов
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
            explosions.get(i).draw(g);
        }

        //отрисовка панели HUD
        hud.draw(g);


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
