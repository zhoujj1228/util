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
	 * @param fileSuffix �ļ���׺���磺png
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
	 * ȥ���ױ�
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
	 * ȥ�����ڷ�Χ�ڵ���������
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
	 * �Ŵ���Сͼ��
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
			System.out.println("targetWidth�� targetHeight����ͬʱΪ0");
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
	 * ��ͼ��תΪ��ά���ؾ���
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
	 * ��ȡͼƬ��rgb��Ϣ���ؾ���
	 * @param imagePath ͼƬ·��
	 * @return ����rgb��Ϣ�Ķ�ά���飬ÿ��Ԫ����'r,g,b'��������ʽ����
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
	 * ��ͼ��תΪ��ά���ؾ���
	 * @param bi
	 * @return ����rgb��Ϣ�Ķ�ά���飬ÿ��Ԫ����'r,g,b'��������ʽ����
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
	 * ����ά���ؾ���תΪͼ��
	 * @param pixMatrix
	 * @param targetFile
	 * @param formatName ��׺��,��ʵ��ͼƬ��ʽ
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
	 * ��ͼ�������ת
	 * @param imageFile ԭͼ��
	 * @param angle ��ת�Ƕ� �磺30
	 * @return ��ת��
	 * @throws IOException
	 */
	public static File ratateImage(File imageFile, double angle) throws IOException{
		//���Ƕ�ת��Ϊ����
		double theta = Math.toRadians(angle);
		BufferedImage bi = ImageIO.read(imageFile);
		bi = grayImage(bi);
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		
		BufferedImage des = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
		
		
		//��ȡ��ͼ
		Graphics2D gs = (Graphics2D) des.getGraphics();
		//���ñ�����ɫ
		gs.setColor(Color.white);
		//��䱳����ɫ
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
	 * ���лҶȻ�
	 * @param bi ԭͼ��
	 * @return �ҶȻ���ͼ��
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
	 * ����һ����ֱ���е�����ͼ��
	 * @param str
	 * @param font
	 * @param outFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void createMyFontImage(String str, Font font, File outFile, Integer width, Integer height)
			throws Exception {
		// ����ͼƬ
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setClip(0, 0, width, height);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);// ���ð�ɫ�������ͼƬ,Ҳ���Ǳ���
		g.setColor(Color.black);// �ڻ��ɺ�ɫ
		g.setFont(font);// ���û�������
		/** ���ڻ�ô�ֱ����y */
		//���ص�ǰ��������ı߽���Ρ�
		Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		//��ȡ������ߵ��ַ������ľ���
		int ascent = fm.getAscent();
		//��ȡ������ߵ��ַ��ײ��ľ���
		int descent = fm.getDescent();
		int leading = fm.getLeading();
		int height2 = fm.getHeight();
		
		int y = (clip.height - fm.getHeight()) / 2 + ascent;
		//���ַ��Ļ���λ���û��ռ��е� (x, y) λ�ô� ,�˴����Ļ��Զ�����м��,����Ҫ��ʽ���
		g.drawString(str, 0, y);
		System.out.println(ascent + descent);
		g.dispose();
		ImageIO.write(image, "png", outFile);// ���pngͼƬ
	}
	
	/**
	 * �����Χ�黯
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
	 * ��16����תʮ���ƣ�����numΪ0000AA���򷵻ؽ��Ϊ170 = 16*10 + 10
	 * @param num ʮ��������
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
	 * �Ա��������ؾ���Ҫ���������󳤶�һ�£�
	 * @param array1
	 * @param array2
	 * @return ���Ƴ̶ȣ����Ϊ1
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
					//Խ��ֱ�Ӳ��Ա�
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
//		System.out.println("��������������" + xiangsi + " ����������������" + busi + " �����ʣ�" + (int)(baifen * 100) + "%");
//		System.out.println("baifen=" + baifen);
		return baifen;
	}
	

	/**
	 * �Ƚ�����ͼƬ�Ƿ���ͬ��Ҫ��ͼƬ��Сһ��
	 * @param imagePath1
	 * @param imagePath2
	 */
	public static void compareImage(String imagePath1, String imagePath2) {
		String[] images = { imagePath1, imagePath2 };
		if (images.length == 0) {
			System.out.println("Usage >java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		// ����ͼƬ���ƶ� begin
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
