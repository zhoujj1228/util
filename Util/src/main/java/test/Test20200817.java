package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import util.FileUtil;
import util.PatternUtil;
import util.XmlUtil;

public class Test20200817 {
	public static void main(String[] args) throws Exception {
		new Test20200817().call();
		
	}

	private void call() throws Exception {
		String path = "D:\\Test\\20200817\\q2exam.xml";
		/*Document document = XmlUtil.getDocument(new File(path));
		Element rootElement = document.getRootElement();
		List<Element> allSubChildNode = new ArrayList<>();
		XmlUtil.getAllSubChildNodeByAttr("class", "rich-text content", rootElement, allSubChildNode);*/
		
		
		String str = FileUtil.readByFileWithEncodingNolineBreak(new File(path), "UTF-8");
		List<List<String>> patternList = PatternUtil.getPatternList(str, "rich-text content\">(.*?)</div>|class=\"seq\">(.*?)</span>|data-v-b0206cc8=\"\">(.*?)</span>|\"el-radio__label\">(.*?)<");
		for (List<String> list : patternList) {
			for (int i = 1; i < list.size(); i++) {
				if(list.get(i) != null) {
					if(list.get(i).matches("A.|B.|C.|D.")) {
						System.out.print(list.get(i));
						continue;
					}
					System.out.println(list.get(i));
				}
			}
			
		}
		
	}
}
