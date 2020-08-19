package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 
 
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.domain.Book;
 
public class JackSonDemo {
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private Book book = null;
 
	/**
	 * Junit�ķ��������ڸ�ÿ����Ԫ�������ǰ�������ͽ�������
	 */
	@Before
	public void init() {
		// ����һ��Bookʵ�����󲢸�ֵ
		book = new Book();
		book.setAuthor("������");
		book.setBookId(123);
		book.setName("�����뺣");
		book.setPrice(30);
		objectMapper = new ObjectMapper();
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	@After
	public void destory() {
		try {
			if (jsonGenerator != null) {
				jsonGenerator.flush();
			}
			if (!jsonGenerator.isClosed()) {
				jsonGenerator.close();
			}
			jsonGenerator = null;
			objectMapper = null;
			book = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/********************** java������������תJSON ****************************/
	/**
	 * 1.javaBeanת����json---���ַ���writeObject/writeValue����
	 * jsonGenerator������ObjectMapper����
	 */
	@Test
	public void javaBeanToJson() {
 
		try {
			System.out.println("jsonGenerator");
			// ����һ
			jsonGenerator.writeObject(book);
			System.out.println();
 
			System.out.println("ObjectMapper");
			// ������
			objectMapper.writeValue(System.out, book);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * Listת����JSON�����ַ�ʽ
	 */
	@Test
	public void listToJson() {
		try {
			List<Book> list = new ArrayList<Book>();
			Book bookOne = new Book();
			bookOne.setAuthor("��ͽ��");
			bookOne.setBookId(456);
			bookOne.setName("��ͽ��ͯ��");
			bookOne.setPrice(55);
			Book bookTwo = new Book();
			bookTwo.setAuthor("��ͽ��");
			bookTwo.setBookId(456);
			bookTwo.setName("��ͽ��ͯ��");
			bookTwo.setPrice(55);
			list.add(bookOne);
			list.add(bookTwo);
			// ��ʽһ
			System.out.println("��ʽһjsonGenerator");
			jsonGenerator.writeObject(list);
			System.out.println();
			System.out.println("��ʽ��ObjectMapper");
			// ��ʽ��
			System.out.println(objectMapper.writeValueAsString(list));
			// ��ʽ��
			System.out.println("��ʽ��ֱ��ͨ��objectMapper��writeValue����:");
			objectMapper.writeValue(System.out, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * mapת����JSON,���ַ�ʽ
	 */
	@Test
	public void mapToJSON() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", book.getName());
			map.put("book", book);
			Book newBook = new Book();
			newBook.setAuthor("��ͽ��");
			newBook.setBookId(456);
			newBook.setName("��ͽ��ͯ��");
			newBook.setPrice(55);
			map.put("newBook", newBook);
 
			System.out.println("��һ�ַ�ʽjsonGenerator");
			jsonGenerator.writeObject(map);
			System.out.println("");
 
			System.out.println("�ڶ��ַ�ʽobjectMapper");
			objectMapper.writeValue(System.out, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/*********************** JSON��������תjava���� ********************************/
	/**
	 * json'����'����ת����javaBean
	 */
	@Test
	public void jsonToJavaBean() {
		String json = "{\"bookId\":\"11111\",\"author\":\"³Ѹ\",\"name\":\"����Ϧʰ\",\"price\":\"45\"}";
		try {
			Book book = objectMapper.readValue(json, Book.class);
			System.out.println(book);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * json'����'����ת��ΪArrayList
	 */
	@Test
	public void jsonToArrayList() {
		String json = "[{\"bookId\":\"11111\",\"author\":\"³Ѹ\",\"name\":\"����Ϧʰ\",\"price\":\"45\"},"
				+ "{\"bookId\":\"11111\",\"author\":\"³Ѹ\",\"name\":\"����Ϧʰ\",\"price\":\"45\"}]";
		try {
			Book[] book = objectMapper.readValue(json, Book[].class);
			for (int i = 0; i < book.length; i++) {
				// ע��book[i]���������飬��Ҫͨ��Arrays.asList()����תΪArrayList
				List<Book> list = Arrays.asList(book[i]);
				System.out.println(list);
 
			}
 
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * jsonת����map
	 */
	@Test
	public void JsonToMap() {
		String json = "{\"name\":\"book\",\"number\":\"12138\",\"book1\":{\"bookId\":\"11111\",\"author\":\"³Ѹ\",\"name\":\"����Ϧʰ\",\"price\":\"45\"},"
				+ "\"book2\":{\"bookId\":\"22222\",\"author\":\"������\",\"name\":\"����\",\"price\":\"25\"}}";
		try {
			Map<String, Map<String, Object>> maps = objectMapper.readValue(
					json, Map.class);
			Set<String> key = maps.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String field = iter.next();
				System.out.println(field + ":" + maps.get(field));
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

