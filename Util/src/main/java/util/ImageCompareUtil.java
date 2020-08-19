package util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import util.ImageUtil;
import util.RobotUtil;

public class ImageCompareUtil {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Thread.currentThread().sleep(3000);
		List<String> imagePaths = new ArrayList<String>();
		imagePaths.add("D:\\Test\\20200213\\1.png");
		findImagesAndMouseLeftClick(imagePaths);
		
	}
	
	
	public static void findImagesAndMouseLeftClick(List<String> imagePaths) throws IOException {
		for (String partImagePath : imagePaths) {
			RobotUtil ru = new RobotUtil();
			BufferedImage screenshotFullBi = ru.screenshotFull();
			BufferedImage partBufferedImage = ImageUtil.getBufferedImage(new File(partImagePath));
			int[] xy = findPartImagePosInScreen(screenshotFullBi, partBufferedImage, 9);
			ru.mouseLeftClick(xy[0], xy[1]);
		}
	}
	
	/**
	 * 获取部分图像在当前屏幕的位置
	 * @param fullImageBi 全图像
	 * @param partImageBi 部分图像
	 * @param offsetFlag 允许颜色的差异量，rgb三个色量的差异量总和
	 * @return 最符合的图像所在位置的左上角坐标数组，第一个元素为x坐标，第二个为y坐标
	 * @throws IOException
	 */
	public static int[] findPartImagePosInScreen(BufferedImage fullImageBi, BufferedImage partImageBi, int offsetFlag) throws IOException {
		//4个标记点X,Y坐标数组，同时也是后续处理的坐标偏移量
		int[][] flagXYArray = {{-1,-1,},{-1,-1},{-1,-1},{-1,-1}};
		String[][] partPx = ImageUtil.getPX(partImageBi);
		String[][] fullPx = ImageUtil.getPX(fullImageBi);
		flagXYArray[0] = getLeftTopFlag(partPx, offsetFlag);
		flagXYArray[1] = getLeftDownFlag(partPx, offsetFlag);
		flagXYArray[2] = getRightTopFlag(partPx, offsetFlag);
		flagXYArray[3] = getRightDownFlag(partPx, offsetFlag);
		//进行4个标记点对比，获取全图符合的位置点x,y坐标列表
		List<int[]> posList = flagCompareAndGetFlagPosInFullPx(partPx, fullPx, flagXYArray ,offsetFlag);
		HashMap<int[] ,String[][]> partPxMap = getFullPxListByPosList(fullPx, posList, partPx.length, partPx[0].length);
		int[] mostFitPosXY = fullCompare(partPx, partPxMap);
		System.out.println(mostFitPosXY[0] + "\t" + mostFitPosXY[1]);
		return mostFitPosXY;
	}

	private static int[] fullCompare(String[][] partPx, HashMap<int[] ,String[][]> partPxMap) {
		int[] result = null;
		double lastSimilarPercentNum = 0;
		for (int[] key : partPxMap.keySet()) {
			String[][] partPxOfFullPx = partPxMap.get(key);
			double compareSimilarPercentNum = ImageUtil.compareTwoPXArray(partPx, partPxOfFullPx);
			if (compareSimilarPercentNum > lastSimilarPercentNum) {
				lastSimilarPercentNum = compareSimilarPercentNum;
				result = key;
			}
		}
		System.out.println(lastSimilarPercentNum);
		return result;
		
		
	}

	private static HashMap<int[], String[][]> getFullPxListByPosList(String[][] fullPx, List<int[]> posList, int xLength, int yLength) throws IOException {
		HashMap<int[], String[][]> result = new HashMap<int[], String[][]>();
		String[][] resultElement = new String[xLength][yLength];
		for(int[] pos : posList) {
			int xOffset = pos[0];
			int yOffset = pos[1];
			for(int x = 0; x < xLength; x++) {
				for(int y = 0; y < yLength; y++) {
					resultElement[x][y] = fullPx[x + xOffset][y + yOffset];
				}
			}
			result.put(pos, resultElement);
			int[][] changePixFormat = ImageUtil.changePixFormat(resultElement);
			ImageUtil.writePixMatrixToImage(changePixFormat, new File("D:\\Test\\20190102\\temp.png"), "png");
		}
		return result;
	}

	private static List<int[]> flagCompareAndGetFlagPosInFullPx(String[][] partPx, String[][] fullPx, int[][] flagXYArray, int flag) {
		List<int[]> result = new ArrayList<>();
		for (int x = 0; x < fullPx.length; x++) {
			loopxy:for (int y = 0; y < fullPx[x].length; y++) {
				
				//对比4个标记点
				for (int i = 0; i < flagXYArray.length; i++) {
					int xOffset = flagXYArray[i][0];
					int yOffset = flagXYArray[i][1];
					//如果标记点没找到（在部分图片进行标记点查找时，没找到的标记点xy坐标都为 -1）
					if(xOffset < 0 || yOffset < 0 ) {
						continue;
					}
					//如果当前标记点越界则跳过
					if( (x + 1 + xOffset) > fullPx.length - 1 || y + yOffset  > fullPx[x].length - 1) {
						continue loopxy;
					}
					int twoPxArrayRGBSumDiff1 = getTwoPxArrayRGBSumDiff(fullPx[x + xOffset][y + yOffset], partPx[xOffset][yOffset]);
					int twoPxArrayRGBSumDiff2 = getTwoPxArrayRGBSumDiff(fullPx[x + 1 + xOffset][y + yOffset], partPx[xOffset + 1][yOffset]);
					
					//任何一个标记点不符合跳过当前识别，进行下一次识别
					if(twoPxArrayRGBSumDiff1 > flag || twoPxArrayRGBSumDiff2 > flag) {
						continue loopxy;
					}
				}
				
				//4个标记点符合
				int[] xyArray = new int[2];
				xyArray[0] = x;
				xyArray[1] = y;
				result.add(xyArray);
			}
			
		}
		return result;
	}

	private static int[] getLeftTopFlag(String[][] partPx, int flag) {
		int[] result = {-1, -1};
		loop:for(int x = 0; x < (partPx.length/2); x++) {
			for (int y = 0; y < (partPx[x].length/2); y++) {

				int sumDiff = getTwoPxArrayRGBSumDiff(partPx[x][y], partPx[x+1][y]);
				if(sumDiff > flag) {
					System.out.println("getLeftTopFlag:" + x);
					System.out.println("getLeftTopFlag:" + y);
					System.out.println("getLeftTopFlag:" + partPx[x][y]);
					System.out.println("getLeftTopFlag:" + partPx[x+1][y]);
					result[0] = x;
					result[1] = y;
					break loop;
				}
			}
		}
		return result;
	}
	
	private static int[] getLeftDownFlag(String[][] partPx, int flag) {
		int[] result = {-1, -1};
		loop:for(int x = 0; x < (partPx.length/2); x++) {
			for (int y = partPx[x].length - 1; y > (partPx[x].length/2); y--) {
				
				int sumDiff = getTwoPxArrayRGBSumDiff(partPx[x][y], partPx[x+1][y]);
				if(sumDiff > flag) {
					System.out.println("getLeftDownFlag:" + x);
					System.out.println("getLeftDownFlag:" + y);
					System.out.println("getLeftDownFlag:" + partPx[x][y]);
					System.out.println("getLeftDownFlag:" + partPx[x+1][y]);
					result[0] = x;
					result[1] = y;
					break loop;
				}
			}
		}
		return result;
	}
	
	
	private static int[] getRightTopFlag(String[][] partPx, int flag) {
		int[] result = { -1, -1 };
		loop: for (int x = partPx.length - 1; x > (partPx.length / 2); x--) {
			for (int y = 0; y < (partPx[x].length / 2); y++) {

				int sumDiff = getTwoPxArrayRGBSumDiff(partPx[x][y], partPx[x - 1][y]);
				if (sumDiff > flag) {
					System.out.println("getRightTopFlag:" + (x - 1));
					System.out.println("getRightTopFlag:" + y);
					System.out.println("getRightTopFlag:" + partPx[x][y]);
					System.out.println("getRightTopFlag:" + partPx[x-1][y]);
					result[0] = x - 1;
					result[1] = y;
					break loop;
				}
			}
		}
		return result;
	}
	
	
	private static int[] getRightDownFlag(String[][] partPx, int flag) {
		int[] result = { -1, -1 };
		loop: for (int x = partPx.length - 1; x > (partPx.length / 2); x--) {
			for (int y = partPx[x].length - 1; y > (partPx[x].length / 2); y--) {

				int sumDiff = getTwoPxArrayRGBSumDiff(partPx[x][y], partPx[x - 1][y]);
				if (sumDiff > flag) {
					System.out.println("getRightDownFlag:" + (x - 1));
					System.out.println("getRightDownFlag:" + y);
					System.out.println("getRightDownFlag:" + partPx[x][y]);
					System.out.println("getRightDownFlag:" + partPx[x-1][y]);
					result[0] = x - 1;
					result[1] = y;
					break loop;
				}
			}
		}
		return result;
	}
	
	private static int getTwoPxArrayRGBSumDiff(String pxArrayStr1, String pxArrayStr2) {
		int[] rgbIntArray1 = ImageUtil.getRGBIntArray(pxArrayStr1);
		int[] rgbIntArray2 = ImageUtil.getRGBIntArray(pxArrayStr2);
		
		int redDiff = Math.abs(rgbIntArray1[0] - rgbIntArray2[0]);
		int greenDiff = Math.abs(rgbIntArray1[1] - rgbIntArray2[1]);
		int blueDiff = Math.abs(rgbIntArray1[2] - rgbIntArray2[2]);
		int sumDiff = redDiff + greenDiff + blueDiff;
		return sumDiff;
		
	}

	
	
	

}
