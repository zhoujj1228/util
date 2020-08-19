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
	 * 获取当前执行方法名
	 * stackTrace[2]  当前执行方法的上一层调用，也就是调用这个方法的方法
	 * stackTrace[1]  当前执行方法，也就是getCurrentMethodName()
	 * stackTrace[0]  为getStackTrace()
	 * @return
	 */
	public static String getCurrentMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return stackTrace[2].getMethodName();
	}
	
	/**
	 * 获取上一层调用的方法名
	 * @return
	 */
	public static String getLastMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return stackTrace[3].getMethodName();
	}
}
