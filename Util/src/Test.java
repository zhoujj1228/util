import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class Test {

	public static void main(String[] args) throws Exception {

		File file = new File("D:\\Test\\xml\\UPIDGWL\\ENTITY.XML");
		new Test().testRetrieve(file);
	}

	public void testRetrieve(File file) throws Exception {
		// ����SAXParser�Ĺ���
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// ����SAXParser����������
		SAXParser parser = factory.newSAXParser();
		// ���xml�ĵ���������
		// InputStream is=Test.class.getClassLoader().getResourceAsStream("books.xml");
		InputStream is = new FileInputStream(file);
		// ����Handler���ĵ����н���
		parser.parse(is, new TechDefaultHandler());
		// ����ĵ��Ķ�����
		XMLReader reader = parser.getXMLReader();
	}
}

class TechDefaultHandler extends DefaultHandler {
	// ʹ��ջ������ݽṹ������
	private Stack<String> stack = new Stack<String>();

	/**
	 * �����ĵ���ʼԪ��ʱ����
	 */
	@Override
	public void startDocument() throws SAXException {
		System.out.println("startDocument");

	}

	/**
	 * �����ĵ�����Ԫ��ʱ����
	 */
	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument");
	}

	/**
	 * ����Ԫ�ؿ�ʼԪ��ʱ���� ��ȡ����Ϊweb��bookԪ�ص�priceԪ���ı�
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// ����ǩ��ѹ��ջ
		stack.push(qName);

		System.out.println("startElement:" + qName);
		// ��������
		for (int i = 0; i < attributes.getLength(); ++i) {
			String attrName = attributes.getQName(i);
			String attrValue = attributes.getValue(i);

			System.out.println("���ԣ� " + attrName + "=" + attrValue);

		}

	}

	/**
	 * ����Ԫ�ؽ���Ԫ��ʱ����
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		stack.pop();// ��ʾ��Ԫ�ؽ�����ϣ���Ҫ��ջ�е�����ǩ

		System.out.println("endElement:" + qName);
	}

	/**
	 * �����ĵ��ڵ�ʱ����
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// ȡ����ǩ��
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