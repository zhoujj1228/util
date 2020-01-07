package example;

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


public class MouseUtilExample implements Runnable {
	private Dimension dim;
	private Random rand;
	private Robot robot;
	private volatile boolean stop = false;

	public MouseUtilExample() {
		//获取当前屏幕
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		rand = new Random();
		try {
			robot = new Robot();
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}

	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		while (!stop) {

			//doGetPDFImages();

		}
	}

	private void doGetPDFImages() {
		long time = new Date().getTime();
		BufferedImage bi = robot.createScreenCapture(new Rectangle(45, 80, 1300, 650));
		try {
			ImageIO.write(bi, "png", new File(""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 165%
		int x = 1358;
		int y = 710;
		robot.mouseMove(x, y);
		// robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(300);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
	}

	public synchronized void stop() {
		stop = true;
	}

	public static void main(String[] args) {
		MouseUtilExample mc = new MouseUtilExample();
		Thread mcThread = new Thread(mc);
		System.out.println("Mouse Controller start");
		mcThread.start();
		try {
			Thread.sleep(60000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		mc.stop();
		System.out.println("Mouse Controller stoped");
	}
}
