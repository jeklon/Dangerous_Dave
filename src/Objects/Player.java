package Objects;

import TileMap.Tile;
import TileMap.TileMap;
import com.sun.xml.internal.bind.v2.model.core.ID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching; //мегание персонажа
    private long flinchTimer;

    //fire(выстрел)
    private boolean firing;
    private int fireCost;
    private int fireDamage;
    //private ArrayList<Bullet> bullets;

    //анимация
    private ArrayList<BufferedImage[]> sprites;

    //хранение количества кадров анимации под определенное действие
    private final int[] numFrames = {1, 4, 3, 1, 2};

    //анимация действий
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int BULLET = 4;

    //конструктор
    public Player(TileMap tm){

        super(tm);

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 2;
        fire = maxFire = 2500;

        fireCost = 200;
        fireDamage = 5;
        //bullets = new ArrayList<Bullet>();

        //загрузка спрайтов
        try{

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
                    "/Sprites/Player/s_dave2.gif"));

            sprites = new ArrayList<BufferedImage[]>();

            //проход по действиям персонажа
            for (int i = 0; i < 5 ; i++) {

                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                //проход по кадрам действия
                for (int j = 0; j < numFrames[i] ; j++) {

                    //если размер отличается от 30 на 30 то if(i !=4) и потом в else меняешь щирину и длину

                    bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);

                    /*if(i != 4) {
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * width * 2,
                                i * height,
                                width,
                                height
                        );
                    }*/

                }
                sprites.add(bi);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);

    }

    public int getHealth(){ return health; }
    public int getMaxHealth(){ return maxHealth; }
    public int getFire(){ return fire; }
    public int getMaxFire(){ return maxFire; }

    public void setFiring(){
        firing=true;
    }

    private void getNextPosition(){ //определяет следующую позицию после нажатия на клавишу
        //движение
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }else if(right){
            dx += moveSpeed;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
        }else {
            if(dx > 0){
                dx -= stopSpeed;
                if(dx < 0){
                    dx=0;
                }
            }
            else if(dx < 0){
                dx += stopSpeed;
                if(dx > 0){
                    dx = 0;
                }
            }
        }

        //атака при движении невозможна:
        if((currentAction == BULLET) && !(jumping || falling)){
            dx = 0;
        }

        //прыжок
        if(jumping && !falling){
            dy = jumpStart;
            falling = true;
        }

        //падение
        if(falling){
            //
            dy += fallSpeed;
            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed; //чем дольше зажата клавиша прыжка - тем выше прыгает player

            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }


    }

    //основная функция позиционирования
    public void update(){

        //обновление позиции
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //проверка набора анимаций
        if(firing){

            if(currentAction != BULLET){
                currentAction = BULLET;
                animation.setFrames(sprites.get(BULLET));
                animation.setDelay(100);
                width = 30;
            }}else if(dy > 0) {if(currentAction != FALLING){

                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;

            }}else if(dy < 0){

                if(currentAction != JUMPING){

                    currentAction = JUMPING;
                    animation.setFrames(sprites.get(JUMPING));
                    animation.setDelay(-1);
                    width = 30;

                }

            }else if(left || right){

            if(currentAction != WALKING){

                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(120);
                width = 30;

            }

        }else {
            if(currentAction !=IDLE){
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }

        animation.update();

        if(currentAction != BULLET){
            if(right) facingRight = true;
            if(left) facingRight = false;
        }

        }

        public void draw(Graphics2D g){

            setMapPosition();

            //отрисовка player
            if(flinching){
                long elapsed = (System.nanoTime() - flinchTimer)/1000000;
                if(elapsed / 100 % 2 == 0){ //мигание player какждые 100 милисекунды
                    return;
                }
            }
            if(facingRight){
                g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
            }else {
                g.drawImage(animation.getImage(), (int)(x+xmap-width/2 +width), (int)(y+ymap-height/2), -width, height, null);
            }

        }

    }





