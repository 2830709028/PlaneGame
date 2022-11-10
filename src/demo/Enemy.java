package demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * µÐ»úÀà
 * @author anqu
 */
public class Enemy {
    private int enemyX;
    private int enemyY;
    public static BufferedImage enemy;

    public int getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(int enemyX) {
        this.enemyX = enemyX;
    }

    public Enemy() {
        this.enemyX = new Random().nextInt(650);
        this.enemyY -= new Random().nextInt(250);
    }

    public int getEnemyY() {
        return enemyY;
    }

    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY;
    }

    static {
        try {
            enemy = ImageIO.read(new File("Images/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Move(){
        enemyY += 7;
    }

    public void paintEnemy(Graphics g){
        g.drawImage(enemy,getEnemyX(),getEnemyY(),null);
    }

}
