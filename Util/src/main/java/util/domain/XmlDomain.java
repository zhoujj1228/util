package util.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import util.XmlUtil;

public class XmlDomain{
	private String tagName;
	private HashMap<String, String> attrMap;
	private List<XmlDomain> subList = new ArrayList<>();
	
	public XmlDomain(Element element) throws DocumentException{
		attrMap = getAttrMapByElement(element);
		
		tagName = element.getName();
		
		List<Element> elements = element.elements();
		for(Element subElement : elements) {
			XmlDomain subXmlDomain = new XmlDomain(subElement);
			subList.add(subXmlDomain);
		}
	}
	
	public XmlDomain(File file) throws DocumentException {
		this(XmlUtil.getDocument(file).getRootElement());
	}
	
	
	
	private HashMap<String, String> getAttrMapByElement(Element element) {
		HashMap<String, String> result = new HashMap<>();
		List<Attribute> attributes = element.attributes();
		for (Attribute attribute : attributes) {
			String name = attribute.getName();
			String text = attribute.getText();
			result.put(name, text);
		}
		return result;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public HashMap<String, String> getAttrMap() {
		return attrMap;
	}
	public void setAttrMap(HashMap<String, String> attrMap) {
		this.attrMap = attrMap;
	}
	public List<XmlDomain> getSubList() {
		return subList;
	}
	public void setSubList(List<XmlDomain> subList) {
		this.subList = subList;
	}
	
	
}