package demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 碰撞类
 * @author anqu
 */
public class Bomb {

    /**
     * 声明爆炸图片左上角坐标
     */
    private int bombX;
    private int bombY;
    /**
     * 爆炸图片出现次数，计算分数
     */
    public int count;
    /**
     * 加载图片
     */
    public static BufferedImage bombImage;

    public int getBombX() {
        return bombX;
    }

    public void setBombX(int bombX) {
        this.bombX = bombX;
    }

    public int getBombY() {
        return bombY;
    }

    public void setBombY(int bombY) {
        this.bombY = bombY;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    static {
        try {
            bombImage = ImageIO.read(new File("Images/bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintBomb(Graphics g){
        g.drawImage(bombImage,getBombX(),getBombY(),null);
    }

    public Bomb(int bombX, int bombY) {
        this.bombX = bombX;
        this.bombY = bombY;
    }

    public Bomb() {
    }

    public void countBomb(){
        count++;
    }
}
