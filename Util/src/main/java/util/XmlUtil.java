package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import util.domain.XmlDomain;

public class XmlUtil {
	
	public static void main(String[] args) {
		Document document = createDocument();
		Element createRootElement = createRootElement(document, "Test");
		createAttribute(createRootElement, "name", "rootName");
		Element createElementByName = createElementByName("Test1", createRootElement);
		createAttribute(createElementByName, "name", "test1name");
		System.out.println(document.getRootElement().asXML());
		//XmlUtil.createXmlFile(document, file, "UTF-8");
		Attribute object = (Attribute) createRootElement.attributes().get(0);
		System.out.println(object.getName());
		System.out.println(object.getText());
		System.out.println(createElementByName.attributes());
		XmlDomain xmlDomain = getXmlDomain(createRootElement);
		System.out.println(xmlDomain.getTagName() + xmlDomain.getAttrMap() + xmlDomain.getSubList().get(0).getAttrMap());
	
		

		String path = "D:\\Test\\xml\\test1.xml";
		File file = new File(path);
		XmlDomain xmlDomain1 = getXmlDomain(file);
		System.out.println(xmlDomain1.getTagName() + xmlDomain1.getAttrMap() + xmlDomain1.getSubList().get(0).getAttrMap());
	}
	
	public static Document createDocument(){
		return DocumentHelper.createDocument();
	}
	
	
	public static Element createRootElement(Document document, String rootElementName){
		return document.addElement(rootElementName);
	}
	
	
	/**
	 * 得到最后一个节点，但是如果有相同节点是使用第一个节点
	 * @param nodes	下属的节点
	 * @param root 父节点
	 * @return 最后的节点
	 */
	public static Element getLastNodeByNodes(ArrayList<String> nodes, Element root){
		List<Element> list = root.elements();
		Element result = null;
		for(String s : nodes){
			for(Element e : list){
				if(e.getName().equals(s)){
					list = e.elements();
					result = e;
				}
			}
		}
		return result;
	}
	/**
	 * 根据属性名称和属性值得到root节点下对应的第一个匹配的子节点
	 * @param attrName 属性的名称
	 * @param attrValue 属性的值
	 * @param e 父节点
	 * @return 对应的子节点
	 */
	public static Element getChildNodeByAttr(String attrName, String attrValue, Element root){
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		//System.out.println(list.size());
		for(Element element : list){
			String value = element.attributeValue(attrName);
			if(attrValue.equals(value)){
				return element;
			}
		}
		return null;
	}
	
	/**
	 * 根据属性名称和属性值得到root节点下对应的所有子节点
	 * @param attrName 属性的名称
	 * @param attrValue 属性的值
	 * @param e 父节点
	 * @return 对应的子节点
	 */
	public static List<Element> getAllChildNodeByAttr(String attrName, String attrValue, Element root){
		@SuppressWarnings("unchecked")
		List<Element> result = new ArrayList<>();
		List<Element> list = root.elements();
		//System.out.println(list.size());
		for(Element element : list){
			String value = element.attributeValue(attrName);
			if(attrValue.equals(value)){
				result.add(element);
			}
		}
		return result;
	}
	
	/**
	 * 根据属性名称和属性值得到所有子节点
	 * @param attrName 属性的名称
	 * @param attrValue 属性的值
	 * @param e 父节点
	 * @return 对应的子节点
	 */
	public static List<Element> getAllSubChildNodeByAttr(String attrName, String attrValue, Element root, List<Element> result){
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		String value = root.attributeValue(attrName);
		if(attrValue.equals(value)){
			result.add(root);
		}
		//System.out.println(list.size());
		for(Element element : list){
			getAllSubChildNodeByAttr(attrName, attrValue, element, result);
		}
		return result;
	}
	/**
	 * 根据文件返回一个Document对象
	 * @param file 文件
	 * @return Document对象
	 * @throws DocumentException 
	 */
	public static Document getDocument(File file) throws DocumentException{
		SAXReader sd = new SAXReader();
		Document doc = sd.read(file);
		return doc;
	}
	
	public static Element createElementByName(String name ,Element ele){
		return ele.addElement(name);
	}
	public static void createAttribute(Element ownElement, String name, String value){
		ownElement.addAttribute(name, value);
	}
	public static boolean createXmlFile(Document document, File xmlFile,String encode){
		XMLWriter writer = null;
		try {
			if(!xmlFile.exists()){
				xmlFile.createNewFile();
			}else{
				xmlFile.delete();
				xmlFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			Writer osWriter = new OutputStreamWriter(new FileOutputStream(xmlFile));
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(encode);
			format.setIndent("\t");
			format.setNewLineAfterDeclaration(false);
			writer = new XMLWriter(osWriter, format);
			writer.write(document);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	/**
	 * 获取自定义的数据模型(XmlDomain)
	 * @param element 传入的节点
	 * @return XmlDomain
	 */
	public static XmlDomain getXmlDomain(Element element) {
		try {
			return new XmlDomain(element);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取自定义的数据模型(XmlDomain)
	 * @param file xml文件
	 * @return XmlDomain
	 */
	public static XmlDomain getXmlDomain(File file) {
		try {
			return new XmlDomain(file);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
