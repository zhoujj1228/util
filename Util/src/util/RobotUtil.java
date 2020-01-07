package util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RobotUtil {
	private Dimension dim;
	private Robot robot;

	public void mouseLeftClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public void mouseRightClick() {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}

	public void mouseMiddleClick() {
		robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
	}
	
	public void mouseMove(int x, int y) {
		robot.mouseMove(x, y);
	}
	
	public void mouseLeftPress() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void mouseLeftRelease() {
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void mouseRightPress() {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	public void mouseRightRelease() {
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	
	
	/**
	 * 输入一个key,需要keyRelease的，如果不进行
	 * @param key 例如KeyEvent.VK_Q, KeyEvent.VK_SHIFT
	 */
	public void oneKeyClick(int key) {
		robot.keyPress(key);
		robot.keyRelease(key);
	}

	public File screenshot(int x1, int y1, int x2, int y2, String fileSuffix, File outputFile) {
		BufferedImage bi = robot.createScreenCapture(new Rectangle(x1, y1, x2, y2));
		try {
			ImageIO.write(bi, fileSuffix, outputFile);
			return outputFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BufferedImage screenshot(int x1, int y1, int x2, int y2) {
		BufferedImage bi = robot.createScreenCapture(new Rectangle(x1, y1, x2, y2));
		return bi;
	}
	
	public double getScreenWidth() {
		return dim.getWidth();
	}
	
	public double getScreenHeight() {
		return dim.getHeight();
	}

	public RobotUtil() {
		// 获取当前屏幕
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			robot = new Robot();
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}
	
	public BufferedImage screenshotFull() {
		BufferedImage bi = screenshot(0, 0, (int)getScreenWidth(), (int)getScreenHeight());
		return bi;
	}

	public static void main(String[] args) throws InterruptedException {
		RobotUtil mc = new RobotUtil();
		//mc.screenshot(0, 0, 1000, 700, "png", new File("D:/Test/a.png"));
		Thread.currentThread().sleep(1000);
		mc.oneKeyClick(KeyEvent.VK_Q);
		mc.oneKeyClick(KeyEvent.VK_Q);
		mc.oneKeyClick(KeyEvent.VK_Q);
		mc.oneKeyClick(KeyEvent.VK_Q);
		Thread.currentThread().sleep(3000);
	}
}
