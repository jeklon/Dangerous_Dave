package TileMap;

import Main.Game;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;


public class TileMap {

//расположение
    private double x;
    private double y;

//границы
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween; //плавное прокручиваение камеры

//карта
    private int[][] map;
    private int tileSize; //размер плитки
    private int numRows; // количество рядов карты
    private int numCols; // количество столбцов карты
    private int width; // ширина карты в пикселях
    private int height; // длина карты в пикселях

//tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles; // в этот 2д массив помещаются импортированные изображения плиток(тайлов)

//отрисовка карты (отрисовывается не вся карта сразу, а только те плитки, которые видны на мониторе)
    private int rowOffset; //направление, смещение отрисовки по рядам
    private int colOffset; // направление, смещение отрисовки по столбцам
    private int numRowsToDraw; // количество рядов, которые нужно отрисовать
    private int numColsToDraw; // количество столбцов, которые нужно отрисовать

//конструктор
    public TileMap(int tileSize){
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize +2; // 240(высота окна) поделить на 30(размер плитки) и +2 ряда для случая прыжка персонажа = 10рядов
        numColsToDraw = GamePanel.WIDTH / tileSize +2;
        tween = 0.07;

    }

//загрузка файлов плиток
    public void loadTiles(String s){

        try{
            tileset = ImageIO.read(getClass().getResourceAsStream(s)); // считать изображение плитки
            numTilesAcross = tileset.getWidth() / tileSize; // получаем количество плиток
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            // импорт плиток
            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL); // норальная плитка
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED); //блоки
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


//загрузка файлов карт
public void loadMap(String s){

    try{

        InputStream in = getClass().getResourceAsStream(s);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        numCols = Integer.parseInt(br.readLine());
        numRows = Integer.parseInt(br.readLine());
        map = new int[numRows][numCols];
        width = numCols * tileSize;
        height = numRows * tileSize;

        xmin = GamePanel.WIDTH - width;
        xmax = 0;
        ymin = GamePanel.HEIGHT - height;
        ymax = 0;

        String delims = "\\s+"; //разделитель
        for (int row = 0; row < numRows; row++) {
            String line = br.readLine();
            String[] tokens = line.split(delims); //разделение строк на tokens исполизуя разделитель delims
            for (int col = 0; col < numCols; col++) {
                map[row][col] = Integer.parseInt(tokens[col]);
            }
        }

    }
    catch (Exception e){
        e.printStackTrace();
    }

}

public int getTileSize(){
    return tileSize;
}

public double getx(){
    return x;
}

public double gety(){
    return y;
}

public  int getWidth(){
    return width;
}

public  int getHeight(){
    return height;
}

public int getType(int row, int col){
    int rc = map[row][col];
    int r = rc / numTilesAcross;
    int c =  rc % numTilesAcross;
    return tiles[r][c].getType();
}

public void setTween(double d) { tween = d; }

public void setPosition(double x, double y){

//плавное движение камеры за игроком
    this.x += (x-this.x) * tween;
    this.y += (y-this.y) * tween;
    fixBounds();

    colOffset = (int) - this.x / tileSize;
    rowOffset = (int) - this.y / tileSize;
}

private void fixBounds(){
    if(x<xmin) x = xmin;
    if(y<ymin) y = ymin;
    if(x>xmax) x = xmax;
    if(y>ymax) y = ymax;
}

public void draw(Graphics2D g){

    for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

        if(row >= numRows) break;

        for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

            if(col >= numCols) break;

            if(map[row][col] == 0) continue;

            int rc = map[row][col];
            int r = rc / numTilesAcross;
            int c =  rc % numTilesAcross;

            g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize, (int)y + row * tileSize, null);

        }
    }
    
}

    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }

}
