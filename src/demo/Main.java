package demo;

import javax.swing.*;

/**
 * @author anqu
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("�ɻ���ս");
        //�������
        Plane plane = new Plane();
        //�������ӵ�����
        frame.add(plane);
        //���ò��ɸĴ�С
        frame.setResizable(false);
        //���ô��ڴ�С
        frame.setSize(700,900);
        //����ҳ����ʾλ��
        frame.setLocationRelativeTo(null);
        //�رմ��ڳ���Ҳ����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //���ÿɼ�
        frame.setVisible(true);

    }

}
