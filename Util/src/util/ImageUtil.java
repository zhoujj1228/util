package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageUtil {

	
	/**
	 * 去除白边
	 * @param pxMatrix
	 * @param start
	 * @param end
	 * @return
	 */
	public static int[][] removeNotUseWhitePix(int[][] pxMatrix) {
		int xStart = pxMatrix.length;
		int xEnd = 0;
		int yStart = pxMatrix[0].length;
		int yEnd = 0;
		for(int x = 0; x < pxMatrix.length; x++){
			for(int y = 0; y < pxMatrix[x].length; y++){
				if(pxMatrix[x][y] < -1 && x < xStart){
					xStart = x;
				}
				if(pxMatrix[x][y] < -1 && x > xEnd){
					xEnd = x;
				}
				if(pxMatrix[x][y] < -1 && y < yStart){
					yStart = y;
				}
				if(pxMatrix[x][y] < -1 && y > yEnd){
					yEnd = y;
				}
			}
		}
		int[][] result = new int[xEnd - xStart + 1][yEnd - yStart + 1];
		for(int i = 0; i < result.length; i++){
			result[i] = Arrays.copyOfRange(pxMatrix[i+xStart], yStart, yEnd + 1);
		}
		
		return result;
	}
	/**
	 * 去除不在范围内的无用像素
	 * @param pxMatrix
	 * @param start
	 * @param end
	 * @return
	 */
	public static int[][] removeNotInRangePix(int[][] pxMatrix, int start, int end) {
		int xStart = pxMatrix.length;
		int xEnd = 0;
		int yStart = pxMatrix[0].length;
		int yEnd = 0;
		for(int x = 0; x < pxMatrix.length; x++){
			for(int y = 0; y < pxMatrix[x].length; y++){
				if(pxMatrix[x][y] < end && pxMatrix[x][y] > start && x < xStart){
					xStart = x;
				}
				if(pxMatrix[x][y] < end && pxMatrix[x][y] > start && x > xEnd){
					xEnd = x;
				}
				if(pxMatrix[x][y] < end && pxMatrix[x][y] > start && y < yStart){
					yStart = y;
				}
				if(pxMatrix[x][y] < end && pxMatrix[x][y] > start && y > yEnd){
					yEnd = y;
				}
			}
		}
		int[][] result = new int[xEnd - xStart + 1][yEnd - yStart + 1];
		for(int i = 0; i < result.length; i++){
			result[i] = Arrays.copyOfRange(pxMatrix[i+xStart], yStart, yEnd + 1);
		}
		
		return result;
	}
	/**
	 * 放大缩小图像
	 * @param imageFile
	 * @param scaleImageFile
	 * @param targetWidth
	 * @param targetHeight
	 * @param formatName
	 * @param scaleType
	 * @return
	 * @throws IOException
	 */
	public static File scaleImage(File imageFile, File scaleImageFile, int targetWidth, int targetHeight, String formatName, int scaleType) throws IOException {
		BufferedImage bi = ImageIO.read(imageFile);
		int height = bi.getHeight();
		int width = bi.getWidth();
		if(targetWidth == 0 && targetHeight == 0){
			System.out.println("targetWidth和 targetHeight不能同时为0");
			return null;
		}
		if(targetWidth == 0){
			targetWidth = targetHeight * width / height;
		}
		if(targetHeight == 0){
			targetHeight = targetWidth * height / width; 
		}
		Image scaledImage = bi.getScaledInstance(targetWidth, targetHeight, scaleType);
		BufferedImage scaledImageBi = new BufferedImage(targetWidth, targetHeight, bi.getType());
		Graphics g = scaledImageBi.getGraphics();
		g.drawImage(scaledImage, 0, 0, null);
		g.dispose();
		ImageIO.write(scaledImageBi, formatName, scaleImageFile);
		return imageFile;
	}
	
	/**
	 * 将图像转为二维像素矩阵
	 * @param bi
	 * @return
	 * @throws IOException
	 */
	public static int[][] readImageToPixMatrix(BufferedImage bi) throws IOException {
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		int[][] result = new int[srcWidth][srcHeight];
		for(int i = 0; i < srcWidth; i++){
			for(int j = 0; j < srcHeight; j++){
				int rgb = bi.getRGB(i, j);
				result[i][j] = rgb;
			}
		}
		return result;
	}
	
	/**
	 * 将二维像素矩阵转为图像
	 * @param pixMatrix
	 * @param targetFile
	 * @param formatName 后缀名,其实是图片格式
	 * @throws IOException
	 */
	public static void writePixMatrixToImage(int[][] pixMatrix, File targetFile, String formatName) throws IOException {
		FileUtil.createFile(targetFile.getAbsolutePath());
		BufferedImage bi = new BufferedImage(pixMatrix.length, pixMatrix[0].length, BufferedImage.TYPE_INT_RGB);
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		for (int i = 0; i < srcWidth; i++) {
			for (int j = 0; j < srcHeight; j++) {
				/*if(pixMatrix[i][j] > -5777216 && pixMatrix[i][j] < -10){
					bi.setRGB(i, j, -1);
					continue;
				}*/
				bi.setRGB(i, j, pixMatrix[i][j]);
				/*if(pixMatrix[i][j] != -1){
					System.out.print("*" + " ");
				}else{
					System.out.print(" " + " ");
				}*/
				//System.out.println(pixMatrix[i][j]);
			}
			
		}
		ImageIO.write(bi, formatName, targetFile);
	}
	
	/**
	 * 对图像进行旋转
	 * @param imageFile 原图像
	 * @param angle 旋转角度 如：30
	 * @return 旋转后
	 * @throws IOException
	 */
	public static File ratateImage(File imageFile, double angle) throws IOException{
		//将角度转化为弧度
		double theta = Math.toRadians(angle);
		BufferedImage bi = ImageIO.read(imageFile);
		bi = grayImage(bi);
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		
		BufferedImage des = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
		
		
		//获取画图
		Graphics2D gs = (Graphics2D) des.getGraphics();
		//设置背景颜色
		gs.setColor(Color.white);
		//填充背景颜色
		gs.fillRect(0, 0, srcWidth, srcHeight);
		gs.rotate(theta);
		gs.drawImage(bi, null, null);
		/*
		AffineTransform aff = new AffineTransform();
		aff.rotate(angle, srcWidth/2, srcHeight/2);
		aff.translate(srcWidth, srcHeight);*/
		File desFile = new File(imageFile.getAbsolutePath() + ".1.png");
		ImageIO.write(des, "png", desFile);
		
		return desFile;
	}
	
	/**
	 * 进行灰度化
	 * @param bi 原图像
	 * @return 灰度化后图像
	 */
	public static BufferedImage grayImage(BufferedImage bi) {
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		BufferedImage grayImage = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_BYTE_GRAY);
		for(int i = 0; i < srcWidth; i++){
			for(int j = 0; j < srcHeight; j++){
				int rgb = bi.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		return grayImage;
	}
	
	/**
	 * 创建一个垂直居中的字体图像
	 * @param str
	 * @param font
	 * @param outFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void createMyFontImage(String str, Font font, File outFile, Integer width, Integer height)
			throws Exception {
		// 创建图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setClip(0, 0, width, height);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);// 先用白色填充整张图片,也就是背景
		g.setColor(Color.black);// 在换成黑色
		g.setFont(font);// 设置画笔字体
		/** 用于获得垂直居中y */
		//返回当前剪贴区域的边界矩形。
		Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		//获取字体基线到字符顶部的距离
		int ascent = fm.getAscent();
		//获取字体基线到字符底部的距离
		int descent = fm.getDescent();
		int leading = fm.getLeading();
		int height2 = fm.getHeight();
		
		int y = (clip.height - fm.getHeight()) / 2 + ascent;
		//首字符的基线位于用户空间中的 (x, y) 位置处 ,此处中文会自动添加行间距,不需要显式添加
		g.drawString(str, 0, y);
		System.out.println(ascent + descent);
		g.dispose();
		ImageIO.write(image, "png", outFile);// 输出png图片
	}
	
	/**
	 * 添加周围虚化
	 * @param source
	 * @param formatName
	 * @param output
	 * @param minRGB
	 * @throws IOException
	 */
	public static void addOffsetPix(File source, String formatName, File output, int minRGB) throws IOException {
		BufferedImage bi = ImageIO.read(source);
		int width = bi.getWidth();
		int height = bi.getHeight();
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				int rgb = bi.getRGB(x, y);
				if(rgb < minRGB){
					if(x-1 > 0 && bi.getRGB(x-1, y) == -1){
						bi.setRGB(x-1, y, minRGB/2);
					}
					if(x+1 < width && bi.getRGB(x+1, y) == -1){
						bi.setRGB(x+1, y, minRGB/2);
					}
					if(y-1 > 0 && bi.getRGB(x, y-1) == -1){
						bi.setRGB(x, y-1, minRGB/2);
					}
					if(y+1 < height && bi.getRGB(x, y+1) == -1){
						bi.setRGB(x, y+1, minRGB/2);
					}
				}
			}
		}
		
		
		ImageIO.write(bi, formatName, output);
	}
}
