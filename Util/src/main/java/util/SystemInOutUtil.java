package util;

import java.util.Scanner;

public class SystemInOutUtil {
	public static void main(String[] args) {
		if(isContinue("1")) {
			System.out.println("continue");
		}
	}
	
	public static boolean isContinue(String continueInputFlag) {
		System.out.println("ȷ���Ƿ����, ����������:" + continueInputFlag);
		System.out.println("�����룺");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		if(input != null && input.contentEquals(continueInputFlag)) {
			return true;
		}
		return false;
		
	}
}
