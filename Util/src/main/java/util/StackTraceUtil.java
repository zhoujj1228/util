package util;

public class StackTraceUtil {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		test1();
	}
	
	public static void test1() {
		System.out.println("test1 in");
		test2();
		System.out.println("test1 out");
	}

	public static void test2() {
		System.out.println("test2 in");
		test3();
		System.out.println("test2 out");}
	
	public static void test3() {
		System.out.println("test3 in");
		System.out.println(getLastMethodName());
		System.out.println("test3 out");
	}
	
	
	/**
	 * ��ȡ��ǰִ�з�����
	 * stackTrace[2]  ��ǰִ�з�������һ����ã�Ҳ���ǵ�����������ķ���
	 * stackTrace[1]  ��ǰִ�з�����Ҳ����getCurrentMethodName()
	 * stackTrace[0]  ΪgetStackTrace()
	 * @return
	 */
	public static String getCurrentMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return stackTrace[2].getMethodName();
	}
	
	/**
	 * ��ȡ��һ����õķ�����
	 * @return
	 */
	public static String getLastMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return stackTrace[3].getMethodName();
	}
}
