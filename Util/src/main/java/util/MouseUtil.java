package util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * ��깤����
 * 
 * �Ƽ�ʹ��BUTTON1_DOWN_MASK ������ BUTTON1_MASK����Ȼ��;һ��
 * BUTTON1 ������
 * BUTTON2 ����м�
 * BUTTON3 ����Ҽ�
 * @author Administrator
 *
 */
public class MouseUtil{
	private Dimension dim;
	private Random rand;
	private Robot robot;

	
	public void mouseLeftClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void mouseRightClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	public void mouseMiddleClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
	}
	
	public MouseUtil() {
		//��ȡ��ǰ��Ļ
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		rand = new Random();
		try {
			robot = new Robot();
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}


	public static void main(String[] args) {
		MouseUtil mc = new MouseUtil();
		mc.mouseLeftClick(500, 750);
	}
}
