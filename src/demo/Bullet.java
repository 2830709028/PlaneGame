package demo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 子弹类
 * @author anqu
 */

public class Bullet{
    /**
     * 声明子弹坐标
     */
    private int bulletX;
    private int bulletY;
    /**
     * 添加图片
     */
    public static BufferedImage bulletImage;

    static {
        try {
            bulletImage = ImageIO.read(new File("Images/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBulletX() {
        return bulletX;
    }

    public void setBulletX(int bulletX) {
        this.bulletX = bulletX;
    }

    public int getBulletY() {
        return bulletY;
    }

    public void setBulletY(int bulletY) {
        this.bulletY = bulletY;
    }

    public Bullet(int bulletX, int bulletY) {
        this.bulletX = bulletX;
        this.bulletY = bulletY;
    }

    public void move() {
        bulletY -= 3;
    }

    public void paintImage(Graphics g){
        g.drawImage(bulletImage, bulletX, bulletY, null);
    }
}