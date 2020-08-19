package util;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import util.XmlUtil;
import util.domain.ConfigDomain;

public class ConfigUtil {
	public static ConfigDomain configContainer = new ConfigDomain();
	
	public static void main(String[] args) {
		String configPath = "D:\\นคื๗\\EclipseWorkspace\\SkyLightTest\\src\\configs\\common\\config.xml";
		ConfigUtil.init(new File(configPath));
		System.out.println(configContainer.getMapConfig("DBConn").getConfig("poolName"));
	}

	private static void init(File file) {
		Document doc = null;
		try {
			doc = XmlUtil.getDocument(file);
		} catch (DocumentException e) {
			e.printStackTrace();
			return;
		}
		Element root = doc.getRootElement();
		parseConfigXml(configContainer, root);
	}

	private static void parseConfigXml(ConfigDomain container, Element element) {
		String id = element.attributeValue("id");
		String value = element.attributeValue("value");
		//System.out.println(id);
		container.setId(id);
		container.setValue(value);
		List<Element> subList = element.elements();
		if(subList == null || subList.size() == 0){
			return;
		}
		for(Element ele : subList){
			String eleId = ele.attributeValue("id");
			ConfigDomain subContainer = new ConfigDomain();
			parseConfigXml(subContainer, ele);
			container.getMap().put(eleId, subContainer);
		}
	}
	
}
