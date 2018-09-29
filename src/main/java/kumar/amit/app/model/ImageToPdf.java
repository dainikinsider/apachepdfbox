package kumar.amit.app.model;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import kumar.amit.app.model.util.PropHandler;


public class ImageToPdf {
	
	
	
	public void prepare() throws IOException {
		PDDocument pdf=new PDDocument();
		Properties prop=PropHandler.loadPropertiesFile();
		
		final String sourceDirectory=PropHandler.getRequiredProperties(PropHandler.SOURCE_DIR);
		final String destinationDirectory=PropHandler.getRequiredProperties(PropHandler.DESTINATION_DIR);
		final String authorName=PropHandler.getRequiredProperties(PropHandler.AUTHOR_NAME);
		final String title=PropHandler.getRequiredProperties(PropHandler.PDF_TITLE);
		final String creator=PropHandler.getRequiredProperties(PropHandler.PDF_CREATOR);
		final String imageFormats=PropHandler.getRequiredProperties(PropHandler.ACCEPTABLE_IMAGE_FORMAT);
		
		setDocumentproperties(pdf, authorName, title, creator);
		
		File imageDirectory=new File(sourceDirectory);
		
		File[] images=imageDirectory.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {

				return pathname.isFile() && isAcceptableFormat(imageFormats,pathname.getAbsolutePath());
						
			}
		});
		
		
		for(int i=0;i<images.length;i++) {
			PDPage page=new PDPage();
			pdf.addPage(page);
			PDImageXObject pdImage = PDImageXObject.createFromFile(images[i].getAbsolutePath(), pdf);
			PDPageContentStream contentStream = new PDPageContentStream(pdf, page);
			contentStream.drawImage(pdImage, 70, 250);
			contentStream.close();
		}
		
		Calendar calendar=Calendar.getInstance();
		
		Timestamp t=new Timestamp(System.currentTimeMillis());
		calendar.setTime(new Date());
		String outputFile=destinationDirectory.concat("/"+t.toLocaleString()+".pdf");
	pdf.save(outputFile);
	pdf.close();
	}

	private void setDocumentproperties(PDDocument pdf, String authorName, String title, String creator) {
		PDDocumentInformation pdfMeta=pdf.getDocumentInformation();
		pdfMeta.setAuthor(authorName);
		pdfMeta.setCreator(creator);
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		if(pdfMeta.getCreationDate() == null) {
			pdfMeta.setCreationDate(c);
		}
		
		pdfMeta.setModificationDate(c);
		pdfMeta.setTitle(title);
		
		pdf.setDocumentInformation(pdfMeta);
	}
	
	private boolean isAcceptableFormat(String format,String pathName) {
		
		boolean status=true;
		String[] formats=format.split(",");
		for(String s:formats) {
			if(pathName.toLowerCase().trim().endsWith(s.toLowerCase().trim())){
				status=true;
				break;
			}
			else {
				status=false;
			}
		}
		if(status==false) {
			System.out.println("INAVLID FILE : "+pathName+" not processed...");
		}
		return status;
	}

}
