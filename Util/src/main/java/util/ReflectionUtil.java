package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * getDeclaredFields 和 getFields 的区别
 * getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
 * getFields()获得某个类的所有的公共（public）的字段，包括父类。
 * 
 * 
 * @author Jay
 * @date 2018年6月27日
 */
public class ReflectionUtil {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		
	}
	
	
	
	
	//test
	public static void test(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException {
		//---test getMethod
		Method method = getMethod(ReflectionUtil.class, "getClass", Object.class);
		System.out.println(method);
		
		//---test setInstanceField getInstanceField
		ReflectionUtil ru = new ReflectionUtil();
		setInstanceField(ReflectionUtil.class, "str", ru, "123");
		ReflectionUtil ru1 = new ReflectionUtil();
		/*String str1 = (String) field.get(ru1);
		String str = (String) field.get(ru);*/
		Object str = getInstanceField(ReflectionUtil.class, "str", ru);
		Object str1 = getInstanceField(ReflectionUtil.class, "str", ru1);
		System.out.println(str);
		System.out.println(str1);
		
		//---test getClass
		ru = new ReflectionUtil();
		Class<? extends Object> cls = getClass(ru);
		if(cls.equals(ReflectionUtil.class)){
			System.out.println("--1");
		}
		if(cls.equals(Object.class)){
			System.out.println("--2");
		}
	
	}
	
	/**
	 * 获取对象的类
	 * @param obj
	 * @return 传入对象的类
	 */
	public static Class<? extends Object> getClass(Object obj){
		return obj.getClass();
	}
	
	/**
	 * 新建对象
	 * @param cl
	 * @return 新建的对象
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object getClass(Class<?> cl) throws InstantiationException, IllegalAccessException{
		return cl.newInstance();
	}
	
	/**
	 * 设置对象的属性值
	 * @param cl
	 * @param fieldName
	 * @param instance
	 * @param fieldValue
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setInstanceField(Class<?> cl, String fieldName, Object instance, Object fieldValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = cl.getDeclaredField(fieldName);
		//由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的 
		field.setAccessible(true);
		field.set(instance, fieldValue);
	}
	
	/**
	 * 获取某对象的属性值
	 * @param cl
	 * @param fieldName
	 * @param instance
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getInstanceField(Class<?> cl, String fieldName, Object instance) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = cl.getDeclaredField(fieldName);
		//由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的 
		field.setAccessible(true);
		Object object = field.get(instance);
		return object;
	}
	
	/**
	 * 获取method
	 * @param cl
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getMethod(Class<?> cl, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException{
		Method method = cl.getDeclaredMethod(methodName, parameterTypes);
		return method;
	}
	
	/**
	 * 获取接口
	 * @param cl
	 * @return
	 */
	public static Class<?>[] getInterfaces(Class<?> cl){
		Class<?>[] interfaces = cl.getInterfaces();
		return interfaces;
	}
	
	/**
	 * 获取父类
	 * @param cl
	 * @return
	 */
	public static Class<?> getSupClass(Class<?> cl){
		Class<?> superclass = cl.getSuperclass();
		return superclass;
	}
	
	/**
	 * 获取所有的属性, getFields()只能获取到public的属性
	 * @param cl
	 * @return
	 */
	public static Field[] getAllField(Class<?> cl){
		Field[] fields = cl.getDeclaredFields();
		return fields;
	}
	
}
