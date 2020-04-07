package util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

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
	
	public File screenshotFullToFile(String fileSuffix, File outputFile) {
		BufferedImage bi = screenshotFull();
		try {
			ImageIO.write(bi, fileSuffix, outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputFile;
	}
	
	/**
	 * 将内容复制入剪贴板
	 * @param writeMe
	 */
	public static void setSysClipboardText(String writeMe) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tText = new StringSelection(writeMe);
		clip.setContents(tText, null);
	}
	
	/**
	 * 在谷歌浏览器打开新打开一个连接,前提已经调用openSoftware方法打开了浏览器
	 * @param url
	 * @param robot
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
	public static void openChromeUrl(String url, Robot robot) throws AWTException, InterruptedException {
		setSysClipboardText(url);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_T);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * 打开任务栏软件
	 * @param softwareTagIndex KeyEvent.VK_N 代表软件是任务栏第N个标签
	 * @param robot
	 * @throws AWTException 
	 */
	public static void openSoftware(int softwareTagIndex, Robot robot) throws AWTException{
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyPress(softwareTagIndex);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.keyRelease(softwareTagIndex);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().sleep(3000);
		RobotUtil ru = new RobotUtil();
		ru.screenshotFullToFile("png", new File("D:\\Test\\20200213\\3.png"));
	}
	
	
}
