package demo;

import javax.swing.*;

/**
 * @author anqu
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("飞机大战");
        //创建面板
        Plane plane = new Plane();
        //将面板添加到窗口
        frame.add(plane);
        //设置不可改大小
        frame.setResizable(false);
        //设置窗口大小
        frame.setSize(700,900);
        //设置页面显示位置
        frame.setLocationRelativeTo(null);
        //关闭窗口程序也结束
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置可见
        frame.setVisible(true);

    }

}
