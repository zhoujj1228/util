package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
/**
 * 测试读取pdf目录
 * @author Administrator
 *
 */
public class TestPdfRead {

	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\最近\\655290 《信息系统项目管理师教程》第三版.pdf");
		PDDocument doc = null;
		FileInputStream  fis = null;
		try {
			fis = new FileInputStream(file);
			PDFParser parser = new PDFParser(new RandomAccessBuffer(fis));
			parser.parse();
			doc = parser.getPDDocument();
			PDDocumentOutline outline = doc.getDocumentCatalog().getDocumentOutline();
			TestPdfRead pdf = new TestPdfRead();
			if (outline != null) {
				pdf.printBookmarks(outline, "");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public void printBookmarks(PDOutlineNode bookmark,String indentation) throws IOException{
		PDOutlineItem current = bookmark.getFirstChild();
		while(current != null){
			int pages = 0;
			if(current.getDestination() instanceof PDPageDestination){
				PDPageDestination pd = (PDPageDestination) current.getDestination();
				pages = pd.retrievePageNumber() + 1;
			}
			if (current.getAction()  instanceof PDActionGoTo) {
				PDActionGoTo gta = (PDActionGoTo) current.getAction();
				if (gta.getDestination() instanceof PDPageDestination) {
					PDPageDestination pd = (PDPageDestination) gta.getDestination();
					pages = pd.retrievePageNumber() + 1;
				}
			}
			if (pages == 0) {
				System.out.println(indentation+current.getTitle());
			}else{
				System.out.println(indentation+current.getTitle()+"  "+pages);
			}
			printBookmarks( current, indentation + "    " );
			current = current.getNextSibling();
		}
	}

}
