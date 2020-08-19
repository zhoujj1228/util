package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtil {
	public static void main(String[] args) {
		System.out.println(changeHexToInt("A000"));
		System.out.println(changeIntToHex(170));
	}
	
	public static BufferedImage getBufferedImage(File imageFile) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}
	
	/**
	 * 
	 * @param fileSuffix 文件后缀，如：png
	 * @param outputFile
	 * @param bi
	 * @return
	 */
	public static File writeBufferedImageToFile(String fileSuffix, File outputFile, BufferedImage bi) {
		try {
			ImageIO.write(bi, fileSuffix, outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputFile;
	}
	
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
	 * 获取图片的rgb信息像素矩阵
	 * @param imagePath 图片路径
	 * @return 包含rgb信息的二维数组，每个元素以'r,g,b'这样的形式保存
	 */
	public static String[][] getPX(String imagePath) {
		File file = new File(imagePath);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[][] rgbPixArray = getPX(bi);
		return rgbPixArray;
	}
	
	/**
	 * 将图像转为二维像素矩阵
	 * @param bi
	 * @return 包含rgb信息的二维数组，每个元素以'r,g,b'这样的形式保存
	 */
	public static String[][] getPX(BufferedImage bi) {
		int[] rgb = new int[3];
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		String[][] rgbPixArray = new String[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				rgbPixArray[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
			}
		}
		return rgbPixArray;
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
				bi.setRGB(i, j, pixMatrix[i][j]);
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
	
	public static int getRed(int rgb) {
		int red = (rgb >> 16) & 255;
		return red;
	}
	
	public static int getGreen(int rgb) {
		int green = (rgb >> 8) & 255;
		return green;
	}
	
	public static int getBlue(int rgb) {
		int blue = (rgb) & 255;
		return blue;
	}
	
	/**
	 * 将16进制转十进制，例如num为0000AA，则返回结果为170 = 16*10 + 10
	 * @param num 十六进制数
	 * @return
	 */
	public static int changeHexToInt(String num) {
		int intNum = Integer.parseInt(num, 16);
		return intNum;
	}
	
	public static String changeIntToHex(int num) {
		StringBuffer s = new StringBuffer();
		String hexNum;
		char[] b = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		while (num != 0) {
			s = s.append(b[num % 16]);
			num = num / 16;
		}
		hexNum = s.reverse().toString();
		return hexNum;
	}
	
	public static int[][] changePixFormat(String[][] pixArray) {
		int[][] result = new int[pixArray.length][pixArray[0].length];
		for (int i = 0; i < pixArray.length; i++) {
			for (int j = 0; j < pixArray[i].length; j++) {
				String rgbStr = pixArray[i][j];
				int[] rgbIntArray = getRGBIntArray(rgbStr);
				//int rgb = (1 << 17) + (rgbIntArray[0] << 16) + (rgbIntArray[0] << 8) + (rgbIntArray[0]);
				result[i][j] = getRGB(rgbIntArray[0], rgbIntArray[1], rgbIntArray[2]);
			}
			
		}
		return result;
	}
	
	
	public static int getRGB(int r, int g, int b) {
		Color color = new Color(r, g, b);
		int rgb = color.getRGB();
		return rgb;
	}

	
	/**
	 * 对比两个像素矩阵，要求两个矩阵长度一致，
	 * @param array1
	 * @param array2
	 * @return 相似程度，最高为1
	 */
	public static double compareTwoPXArray(String[][] array1, String[][] array2) {
		int xiangsi = 0;
		int busi = 0;
		for (int i = 0; i < array1.length; i++) {
			
			for (int j = 0; j < array1[i].length; j++) {
				try {
					String[] value1 = array1[i][j].toString().split(",");
					String[] value2 = array2[i][j].toString().split(",");
					for (int k = 0; k < value1.length; k++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 15) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					//越界直接不对比
					continue;
				}
			}
		}
		
		double baifen = 0;

		try {
			baifen = (double)xiangsi / (double)(busi + xiangsi);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + (int)(baifen * 100) + "%");
//		System.out.println("baifen=" + baifen);
		return baifen;
	}
	

	/**
	 * 比较两张图片是否相同，要求图片大小一致
	 * @param imagePath1
	 * @param imagePath2
	 */
	public static void compareImage(String imagePath1, String imagePath2) {
		String[] images = { imagePath1, imagePath2 };
		if (images.length == 0) {
			System.out.println("Usage >java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		// 分析图片相似度 begin
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		
		compareTwoPXArray(list1, list2);
	}
	

	public static int[] getRGBIntArray(String pxRgbStr) {
		String[] rgbArray = pxRgbStr.split(",");
		int[] result = new int[rgbArray.length];
		for (int i = 0; i < rgbArray.length; i++) {
			int parseInt = Integer.parseInt(rgbArray[i]);
			result[i] = parseInt;
		}
		return result;
	}
	
}
