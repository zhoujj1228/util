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
 * 鼠标工具类
 * 
 * 推荐使用BUTTON1_DOWN_MASK 而不是 BUTTON1_MASK，虽然用途一样
 * BUTTON1 鼠标左键
 * BUTTON2 鼠标中键
 * BUTTON3 鼠标右键
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
		//获取当前屏幕
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
