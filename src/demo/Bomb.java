package demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ��ײ��
 * @author anqu
 */
public class Bomb {

    /**
     * ������ըͼƬ���Ͻ�����
     */
    private int bombX;
    private int bombY;
    /**
     * ��ըͼƬ���ִ������������
     */
    public int count;
    /**
     * ����ͼƬ
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
