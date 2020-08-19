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
 * rootNode是没有信息的，只是作为容器，它的subNodes才是根节点
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
		// 创建SAXParser的工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 创建SAXParser解析器对象
		SAXParser parser = factory.newSAXParser();
		// 获得xml文档的输入流
		// InputStream is=Test.class.getClassLoader().getResourceAsStream("books.xml");
		InputStream is = new FileInputStream(file);
		// 利用Handler对文档进行解析
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
		XmlNode supNode = nodeList.getLast();
		XmlNode node = new XmlNode();
		nodeList.add(node);
		node.setName(qName);
		if(isSaveData) {
			supNode.subNodes.add(node);
			node.setSupNode(supNode);
		}
		

		System.out.println("startElement:" + qName);
		// 处理属性
		for (int i = 0; i < attributes.getLength(); ++i) {
			String attrName = attributes.getQName(i);
			String attrValue = attributes.getValue(i);
			node.attributes.put(attrName, attrValue);
			System.out.println("属性： " + attrName + "=" + attrValue);

		}

	}

	/**
	 * 遇到元素结束元素时调用
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		nodeList.removeLast();// 表示该元素解析完毕，需要从栈中弹出标签

		System.out.println("endElement:" + qName);
	}

	/**
	 * 遇到文档节点时调用
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// 取出标签名
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
