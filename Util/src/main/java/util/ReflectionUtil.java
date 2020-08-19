package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ���乤����
 * 
 * getDeclaredFields �� getFields ������
 * getDeclaredFields()���ĳ����������������ֶΣ�������public��private��proteced�����ǲ���������������ֶΡ�
 * getFields()���ĳ��������еĹ�����public�����ֶΣ��������ࡣ
 * 
 * 
 * @author Jay
 * @date 2018��6��27��
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
	 * ��ȡ�������
	 * @param obj
	 * @return ����������
	 */
	public static Class<? extends Object> getClass(Object obj){
		return obj.getClass();
	}
	
	/**
	 * �½�����
	 * @param cl
	 * @return �½��Ķ���
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object getClass(Class<?> cl) throws InstantiationException, IllegalAccessException{
		return cl.newInstance();
	}
	
	/**
	 * ���ö��������ֵ
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
		//����JDK�İ�ȫ����ʱ�϶�.����ͨ��setAccessible(true)�ķ�ʽ�رհ�ȫ���Ϳ��Դﵽ���������ٶȵ�Ŀ�� 
		field.setAccessible(true);
		field.set(instance, fieldValue);
	}
	
	/**
	 * ��ȡĳ���������ֵ
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
		//����JDK�İ�ȫ����ʱ�϶�.����ͨ��setAccessible(true)�ķ�ʽ�رհ�ȫ���Ϳ��Դﵽ���������ٶȵ�Ŀ�� 
		field.setAccessible(true);
		Object object = field.get(instance);
		return object;
	}
	
	/**
	 * ��ȡmethod
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
	 * ��ȡ�ӿ�
	 * @param cl
	 * @return
	 */
	public static Class<?>[] getInterfaces(Class<?> cl){
		Class<?>[] interfaces = cl.getInterfaces();
		return interfaces;
	}
	
	/**
	 * ��ȡ����
	 * @param cl
	 * @return
	 */
	public static Class<?> getSupClass(Class<?> cl){
		Class<?> superclass = cl.getSuperclass();
		return superclass;
	}
	
	/**
	 * ��ȡ���е�����, getFields()ֻ�ܻ�ȡ��public������
	 * @param cl
	 * @return
	 */
	public static Field[] getAllField(Class<?> cl){
		Field[] fields = cl.getDeclaredFields();
		return fields;
	}
	
}
