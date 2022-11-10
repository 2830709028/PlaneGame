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
     * 当前游戏状态
     */
    public static boolean isStart = false;
    public static boolean Over = false;

    /**
     * 加载图片
     */
    public static BufferedImage backImage;
    public static BufferedImage hero;


    /**
     * 初始化飞机坐标
     * 初始化子弹坐标
     */
    protected int planeX = 200;
    protected int planeY = 500;

    /**
     * 声明子弹，敌机数组,爆炸数组
     */
    List<Bullet> bulletList = new ArrayList<>();
    List<Enemy> enemyList = new ArrayList<>();
    List<Bomb> bombList = new ArrayList<>();

    /**
     * 飞机速度
     */
    public int speed = 10;

    /**
     * 设置一个计时器
     */
    Timer timer = new Timer(50,this);

    /**
     * 当前游戏分数
     */
    public int score = 0;

    public Plane(){

        //新建敌机
        for (int i = 0; i < 10; i++) {
            enemyList.add(new Enemy());
        }
        //获取键盘事件
        this.setFocusable(true);
        //添加监听
        this.addKeyListener(this);
        //计时开始
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
        //绘制游戏当前状态
        paintState(g);
        //绘制子弹
        paintBullet(g);
        //绘制敌机
        paintEnemy(g);
        //绘制爆炸图片
        paintBomb(g);
        //绘制分数
        paintScore(g);
    }

    private void paintScore(Graphics g) {
        g.setColor(Color.yellow);
        g.setFont(new Font("宋体",Font.BOLD,30));
        g.drawString("当前分数："+score,10,30);
    }

    private void paintBomb(Graphics g) {
        for (int i = 0; i < bombList.size(); i++) {
            Bomb bomb = bombList.get(i);
            bomb.paintBomb(g);
            bomb.countBomb();
            if(bomb.getCount() > 5){
                bombList.remove(bomb);
            }
            //增加图片显示时间
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
     * 绘制子弹
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
     * 绘制游戏当前状态
     * @param g
     */
    private void paintState(Graphics g) {
        if(isStart == false &&  Over == false){
            g.setColor(Color.red);
            g.setFont(new Font("宋体",Font.BOLD,50));
            g.drawString("按空格键开始游戏",80,300);
        }else if(isStart == false &&  Over == true){
            g.setColor(Color.red);
            g.setFont(new Font("宋体",Font.BOLD,60));
            g.drawString("游戏结束",200,300);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart){
            //添加子弹
            bulletList.add(new Bullet(planeX + 44,planeY - 20));
            for (int i = 0; i < bulletList.size(); i++) {
                Bullet temBullet = bulletList.get(i);
                temBullet.move();
            }
            //当子弹y轴坐标小于0删除子弹，减少内存
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

            //碰撞消失
            for (int i = 0; i < enemyList.size(); i++) {
                Enemy enemys = enemyList.get(i);
                for (int j = 0; j < bulletList.size(); j++) {
                    Bullet bullets = bulletList.get(j);
                    if(isHit(enemys,bullets)){
                        //计算分数
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
        //英雄机范围
        Rectangle rectangle1 = new Rectangle(planeX,planeY,90,120);
        //敌机范围
        Rectangle rectangle2 = new Rectangle(enemy.getEnemyX(),enemy.getEnemyY(),40,30);
        return rectangle1.contains(rectangle2);
    }

    /**
     * 判断是否击中
     * @param enemys
     * @param bullets
     * @return
     */
    private boolean isHit(Enemy enemys, Bullet bullets) {
        //敌机范围
        Rectangle rectangle = new Rectangle(enemys.getEnemyX(),enemys.getEnemyY(),40,30);
        //子弹坐标
        Point point = new Point(bullets.getBulletX(),bullets.getBulletY());
        //判断子弹是否在敌机区域内，是则返回true
        return rectangle.contains(point);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //判断，当空格键时数字值为32
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
