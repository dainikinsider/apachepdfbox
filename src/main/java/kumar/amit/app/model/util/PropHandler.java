package kumar.amit.app.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import kumar.amit.app.model.exception.FatalException;

public class PropHandler {
	
	public static final String SOURCE_DIR="kumar.amit.app.imagetopdf.images.files.source.dir";
	public static final String DESTINATION_DIR="kumar.amit.app.imagetopdf.images.files.destination.dir";
	public static final String AUTHOR_NAME="kumar.amit.app.imagetopdf.images.meta.author";
	public static final String PDF_TITLE="kumar.amit.app.imagetopdf.images.pdf.meta.title";
	public static final String PDF_CREATOR="kumar.amit.app.imagetopdf.images.pdf.meta.creator";
	public static final String NO_OF_IMAGE_PER_PAGE="kumar.amit.app.imagetopdf.images.pdf.meta.no.of.image.per.page";
	public static final String ACCEPTABLE_IMAGE_FORMAT="kumar.amit.app.imagetopdf.images.files.acceptable.image.format";
	
	
private static Properties prop = new Properties();
private static File file=new File("ImageToPdf.properties");
	public static Properties loadPropertiesFile() throws FileNotFoundException {
		

		try {
			InputStream fis =  PropHandler.class.getClassLoader().getResourceAsStream("ImageToPdf.properties");
			prop.load(fis);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileNotFoundException(
					"SORRY! I cant't find the properties file, please make sure its in classpath");

		} catch (Exception e) {
			throw new FatalException(
					"So SORRY! This operation is not permitted, make sure you do follow the insruction provided...");
		}

	}
	
	public static String getRequiredProperties(String propName) throws FileNotFoundException {
		String value=null;
		if(prop==null) {
			loadPropertiesFile();
		}
		//NOT POSSIBLE BUT STILL IT HAS O(1)
		if(prop!=null) {
			value= prop.getProperty(propName);
			if(value==null || value.trim().equals("")) {
				throw new FatalException("FATAL! Make sure you have provided the proper values in your properties file available at - "+file.getAbsolutePath().toString());
			}
			else {
				return value;
			}
		}
		return null;
	}

}
