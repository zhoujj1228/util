package util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.domain.Book;
import demo.domain.Person;
 
/**
 * bean转json格式或者json转bean格式, 项目中我们通常使用这个工具类进行json---java互相转化
 */
public class JacksonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	/*public static String bean2Json(Object obj) throws IOException {
		StringWriter sw = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
		mapper.writeValue(gen, obj);
		gen.close();
		return sw.toString();
	}*/
	
	public static String obj2json(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }
 
	public static <T> T json2Bean(String jsonStr, Class<T> objClass)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(jsonStr, objClass);
	}
	
	public static void main(String[] args) throws Exception {
		Book book = new Book();
		book.setAuthor("海明威");
		book.setBookId(123);
		book.setName("老人与海");
		book.setPrice(30);
		Person authorData = new Person();
		List<String> books = new ArrayList<>();
		books.add("books1");
		books.add("books2");
		authorData.setBooks(books);
		authorData.setId("海明威id");
		authorData.setName("海明威name");
		book.setAuthorData(authorData);
		
		
		String obj2json = obj2json(book);
		System.out.println(obj2json);
		
		Book json2Bean = json2Bean(obj2json, Book.class);
		System.out.println(json2Bean);
	}
}

