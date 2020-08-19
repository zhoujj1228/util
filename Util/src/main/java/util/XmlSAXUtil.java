package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
/**
 * rootNode��û����Ϣ�ģ�ֻ����Ϊ����������subNodes���Ǹ��ڵ�
 * @author Administrator
 *
 */
public class XmlSAXUtil {

	public static void main(String[] args) throws Exception {
		boolean isSaveData = true;
		File file = new File("D:\\Test\\xml\\test.xml");
		saxParse(file, isSaveData);
	}

	public static XmlNode saxParse(File file, boolean isSaveData) throws Exception {
		XmlNode rootNode = new XmlNode();
		// ����SAXParser�Ĺ���
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// ����SAXParser����������
		SAXParser parser = factory.newSAXParser();
		// ���xml�ĵ���������
		// InputStream is=Test.class.getClassLoader().getResourceAsStream("books.xml");
		InputStream is = new FileInputStream(file);
		// ����Handler���ĵ����н���
		parser.parse(is, new XmlSaxHandler(rootNode, isSaveData));
		System.out.println(rootNode);
		return rootNode;
	}
}

class XmlSaxHandler extends DefaultHandler {
	private LinkedList<XmlNode> nodeList = new LinkedList<>();
	XmlNode rootNode;
	boolean isSaveData;
	public XmlSaxHandler(XmlNode rootNode, boolean isSaveData) {
		this.rootNode = rootNode;
		this.isSaveData = isSaveData;
		nodeList.add(rootNode);
	}

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
		XmlNode supNode = nodeList.getLast();
		XmlNode node = new XmlNode();
		nodeList.add(node);
		node.setName(qName);
		if(isSaveData) {
			supNode.subNodes.add(node);
			node.setSupNode(supNode);
		}
		

		System.out.println("startElement:" + qName);
		// ��������
		for (int i = 0; i < attributes.getLength(); ++i) {
			String attrName = attributes.getQName(i);
			String attrValue = attributes.getValue(i);
			node.attributes.put(attrName, attrValue);
			System.out.println("���ԣ� " + attrName + "=" + attrValue);

		}

	}

	/**
	 * ����Ԫ�ؽ���Ԫ��ʱ����
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		nodeList.removeLast();// ��ʾ��Ԫ�ؽ�����ϣ���Ҫ��ջ�е�����ǩ

		System.out.println("endElement:" + qName);
	}

	/**
	 * �����ĵ��ڵ�ʱ����
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// ȡ����ǩ��
		XmlNode node = nodeList.getLast();
		String tagStringValue = new String(ch, start, length);
		node.setValue(tagStringValue);
		
		System.out.println("characters tagName:" + node.getName());
		System.out.println("StringValue:" + tagStringValue);

	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println(e.getMessage());
	}

}


class XmlNode{
	private String name;
	private String value;
	Map<String, String> attributes = new HashMap<>();
	List<XmlNode> subNodes = new ArrayList<>();
	private XmlNode supNode;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public XmlNode getSupNode() {
		return supNode;
	}
	public void setSupNode(XmlNode supNode) {
		this.supNode = supNode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map getAttributes() {
		return attributes;
	}
	public List<XmlNode> getSubNodes() {
		return subNodes;
	}
}
