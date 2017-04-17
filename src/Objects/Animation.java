package Objects;

import java.awt.image.BufferedImage;


public class Animation { // класс обработки анимации

    private BufferedImage[] frames; //массив, который содержит кадры анимации
    private int currentFrame; //текущий кадр
    private int numFrames;

    private long startTime; // время между кадрами
    private long delay;

    private boolean playedOnce; //есть или нет анимации, была ли проиграна анимация

//конструктор
    public void Animation(){
        playedOnce = false;
    }

    public void setFrames(BufferedImage[] frames) {

        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long d){ delay=d; }
    public void setFrame(int i) { currentFrame=i; }

//логика перехода к следующему кадру
    public void update(){

        if(delay == -1) return;

        long elapsed = (System.nanoTime() - startTime)/1000000;
        if(elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }

        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }

    }

    public int getFrame(){ return currentFrame; }
    public BufferedImage getImage(){ return frames[currentFrame]; }
    public boolean hasPlayedOnce(){ return playedOnce; }


}
