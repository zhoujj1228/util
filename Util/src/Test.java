import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import util.DateUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		Date date = new Date();
		long time = date.getTime();
		System.out.println(time);
		
		Date parseDate = DateUtil.parseDate("20200304180400", "yyyyMMddhhmmss");
		long time1 = parseDate.getTime();
		System.out.println(time1 - time);
		
		/*String s = "#MXSD_r:2#esbsplit#MXSD_q:C1#esbsplit#MXSD_REPLY_TAG:true#esbsplit#MXSD_n:1511265540859#esbsplit#MXSD_m:1511265540858#esbsplit#MXSD_l:1511265540743#esbsplit#MXSD_k:1511265540740#esbsplit#MXSD_j";
		String[] split = s.split("#esbsplit#");
		for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}*/
		
	}

	public void testRetrieve(File file) throws Exception {
		// 创建SAXParser的工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 创建SAXParser解析器对象
		SAXParser parser = factory.newSAXParser();
		// 获得xml文档的输入流
		// InputStream is=Test.class.getClassLoader().getResourceAsStream("books.xml");
		InputStream is = new FileInputStream(file);
		// 利用Handler对文档进行解析
		parser.parse(is, new TechDefaultHandler());
		// 获得文档的读对象
		XMLReader reader = parser.getXMLReader();
	}
}

class TechDefaultHandler extends DefaultHandler {
	// 使用栈这个数据结构来保存
	private Stack<String> stack = new Stack<String>();

	/**
	 * 遇到文档开始元素时调用
	 */
	@Override
	public void startDocument() throws SAXException {
		System.out.println("startDocument");

	}

	/**
	 * 遇到文档结束元素时调用
	 */
	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument");
	}

	/**
	 * 遇到元素开始元素时调用 获取属性为web的book元素的price元素文本
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 将标签名压入栈
		stack.push(qName);

		System.out.println("startElement:" + qName);
		// 处理属性
		for (int i = 0; i < attributes.getLength(); ++i) {
			String attrName = attributes.getQName(i);
			String attrValue = attributes.getValue(i);

			System.out.println("属性： " + attrName + "=" + attrValue);

		}

	}

	/**
	 * 遇到元素结束元素时调用
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		stack.pop();// 表示该元素解析完毕，需要从栈中弹出标签

		System.out.println("endElement:" + qName);
	}

	/**
	 * 遇到文档节点时调用
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// 取出标签名
		String tagName = stack.peek();
		String tagStringValue = new String(ch, start, length);

		System.out.println("characters tagName:" + tagName);
		System.out.println("StringValue:" + tagStringValue);

	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println(e.getMessage());
	}

}