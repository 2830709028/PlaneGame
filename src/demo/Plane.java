package demo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author anqu
 */
public class Plane extends JPanel implements KeyListener, ActionListener {

    /**
     * ��ǰ��Ϸ״̬
     */
    public static boolean isStart = false;
    public static boolean Over = false;

    /**
     * ����ͼƬ
     */
    public static BufferedImage backImage;
    public static BufferedImage hero;


    /**
     * ��ʼ���ɻ�����
     * ��ʼ���ӵ�����
     */
    protected int planeX = 200;
    protected int planeY = 500;

    /**
     * �����ӵ����л�����,��ը����
     */
    List<Bullet> bulletList = new ArrayList<>();
    List<Enemy> enemyList = new ArrayList<>();
    List<Bomb> bombList = new ArrayList<>();

    /**
     * �ɻ��ٶ�
     */
    public int speed = 10;

    /**
     * ����һ����ʱ��
     */
    Timer timer = new Timer(50,this);

    /**
     * ��ǰ��Ϸ����
     */
    public int score = 0;

    public Plane(){

        //�½��л�
        for (int i = 0; i < 10; i++) {
            enemyList.add(new Enemy());
        }
        //��ȡ�����¼�
        this.setFocusable(true);
        //��Ӽ���
        this.addKeyListener(this);
        //��ʱ��ʼ
        timer.start();

    }

    static {
        try {
            backImage = ImageIO.read(new File("Images/background.png"));
            hero = ImageIO.read(new File("Images/hero.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(backImage,0,0,null);
        g.drawImage(hero, planeX,planeY,null);
        //������Ϸ��ǰ״̬
        paintState(g);
        //�����ӵ�
        paintBullet(g);
        //���Ƶл�
        paintEnemy(g);
        //���Ʊ�ըͼƬ
        paintBomb(g);
        //���Ʒ���
        paintScore(g);
    }

    private void paintScore(Graphics g) {
        g.setColor(Color.yellow);
        g.setFont(new Font("����",Font.BOLD,30));
        g.drawString("��ǰ������"+score,10,30);
    }

    private void paintBomb(Graphics g) {
        for (int i = 0; i < bombList.size(); i++) {
            Bomb bomb = bombList.get(i);
            bomb.paintBomb(g);
            bomb.countBomb();
            if(bomb.getCount() > 5){
                bombList.remove(bomb);
            }
            //����ͼƬ��ʾʱ��
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        repaint();
    }


    private void paintEnemy(Graphics g) {
        if(isStart){
            for (int i = 0; i < enemyList.size(); i++) {
                Enemy enemys = enemyList.get(i);
                enemys.paintEnemy(g);
            }
        }
    }

    /**
     * �����ӵ�
     * @param g
     */
    private void paintBullet(Graphics g)  {
        if(isStart){
            for (int i = 0; i < bulletList.size(); i++) {
                if(i % 20 == 0){
                    Bullet bullets = bulletList.get(i);
                    bullets.paintImage(g);
                }
            }
        }
        repaint();
    }

    /**
     * ������Ϸ��ǰ״̬
     * @param g
     */
    private void paintState(Graphics g) {
        if(isStart == false &&  Over == false){
            g.setColor(Color.red);
            g.setFont(new Font("����",Font.BOLD,50));
            g.drawString("���ո����ʼ��Ϸ",80,300);
        }else if(isStart == false &&  Over == true){
            g.setColor(Color.red);
            g.setFont(new Font("����",Font.BOLD,60));
            g.drawString("��Ϸ����",200,300);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart){
            //����ӵ�
            bulletList.add(new Bullet(planeX + 44,planeY - 20));
            for (int i = 0; i < bulletList.size(); i++) {
                Bullet temBullet = bulletList.get(i);
                temBullet.move();
            }
            //���ӵ�y������С��0ɾ���ӵ��������ڴ�
            for (int i = 0; i < bulletList.size(); i++) {
                Bullet temBullet = bulletList.get(i);
                if(temBullet.getBulletY() < 0){
                    bulletList.remove(temBullet);
                }
            }

            for (int i = 0; i < enemyList.size(); i++) {
                Enemy temenemy = enemyList.get(i);
                temenemy.Move();
                if(temenemy.getEnemyY() > 900){
                    enemyList.add(new Enemy());
                    enemyList.remove(temenemy);
                }
            }

            //��ײ��ʧ
            for (int i = 0; i < enemyList.size(); i++) {
                Enemy enemys = enemyList.get(i);
                for (int j = 0; j < bulletList.size(); j++) {
                    Bullet bullets = bulletList.get(j);
                    if(isHit(enemys,bullets)){
                        //�������
                        score += 10;
                        enemyList.remove(enemys);
                        bulletList.remove(bullets);
                        enemyList.add(new Enemy());
                        Bomb bomb = new Bomb(enemys.getEnemyX(),enemys.getEnemyY());
                        bombList.add(bomb);
                    }
                }
            }

            for (int i = 0; i < enemyList.size(); i++) {
                Enemy enemys = enemyList.get(i);
                if(isGameOver(enemys)){
                    isStart = false;
                    Over = true;
                    break;
                }
            }
        }
        repaint();
        timer.start();
    }

    private boolean isGameOver(Enemy enemy) {
        //Ӣ�ۻ���Χ
        Rectangle rectangle1 = new Rectangle(planeX,planeY,90,120);
        //�л���Χ
        Rectangle rectangle2 = new Rectangle(enemy.getEnemyX(),enemy.getEnemyY(),40,30);
        return rectangle1.contains(rectangle2);
    }

    /**
     * �ж��Ƿ����
     * @param enemys
     * @param bullets
     * @return
     */
    private boolean isHit(Enemy enemys, Bullet bullets) {
        //�л���Χ
        Rectangle rectangle = new Rectangle(enemys.getEnemyX(),enemys.getEnemyY(),40,30);
        //�ӵ�����
        Point point = new Point(bullets.getBulletX(),bullets.getBulletY());
        //�ж��ӵ��Ƿ��ڵл������ڣ����򷵻�true
        return rectangle.contains(point);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //�жϣ����ո��ʱ����ֵΪ32
        if(keyCode == 32 && Over == false){
            isStart = !isStart;
            repaint();
        }
        if(isStart){
            if(keyCode == KeyEvent.VK_DOWN){
                if(planeY < 735){
                    planeY += speed;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    repaint();
                }
            }else if(keyCode == KeyEvent.VK_UP){
                if(planeY > 8){
                    planeY -= speed;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    repaint();
                }
            }else if(keyCode == KeyEvent.VK_LEFT){
                if(planeX > 0){
                    planeX -= speed;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    repaint();
                }
            }else if(keyCode == KeyEvent.VK_RIGHT){
                if(planeX < 598){
                    planeX += speed;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
