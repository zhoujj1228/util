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
	 * �õ����һ���ڵ㣬�����������ͬ�ڵ���ʹ�õ�һ���ڵ�
	 * @param nodes	�����Ľڵ�
	 * @param root ���ڵ�
	 * @return ���Ľڵ�
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
	 * �����������ƺ�����ֵ�õ�root�ڵ��¶�Ӧ�ĵ�һ��ƥ����ӽڵ�
	 * @param attrName ���Ե�����
	 * @param attrValue ���Ե�ֵ
	 * @param e ���ڵ�
	 * @return ��Ӧ���ӽڵ�
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
	 * �����������ƺ�����ֵ�õ�root�ڵ��¶�Ӧ�������ӽڵ�
	 * @param attrName ���Ե�����
	 * @param attrValue ���Ե�ֵ
	 * @param e ���ڵ�
	 * @return ��Ӧ���ӽڵ�
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
	 * �����������ƺ�����ֵ�õ������ӽڵ�
	 * @param attrName ���Ե�����
	 * @param attrValue ���Ե�ֵ
	 * @param e ���ڵ�
	 * @return ��Ӧ���ӽڵ�
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
	 * �����ļ�����һ��Document����
	 * @param file �ļ�
	 * @return Document����
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
	 * ��ȡ�Զ��������ģ��(XmlDomain)
	 * @param element ����Ľڵ�
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
	 * ��ȡ�Զ��������ģ��(XmlDomain)
	 * @param file xml�ļ�
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
