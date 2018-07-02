package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
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
	 * 根据属性名称和属性值得到对应的子节点
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
	
}
