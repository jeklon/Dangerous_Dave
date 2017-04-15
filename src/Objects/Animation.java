package Objects;

import java.awt.image.BufferedImage;


public class Animation { // класс обработки анимации

    private BufferedImage[] frames; //массив, который содержит кадры анимации
    private int currentFrame; //текущий кадр
    private int numFrames;

    private long startTime; // время между кадрами
    private long delay;

    private boolean playedOnce; //есть или нет анимации, была ли проиграна анимация


}
