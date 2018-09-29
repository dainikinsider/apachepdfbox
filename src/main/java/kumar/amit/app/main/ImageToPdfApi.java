package kumar.amit.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import kumar.amit.app.model.ImageToPdf;
import kumar.amit.app.model.exception.FatalException;

public class ImageToPdfApi {

	public static void main(String[] args) {

		ImageToPdf api=new ImageToPdf();
		try {
			api.prepare();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
		catch (FatalException e) {
			e.printStackTrace();
		}
		 catch (Exception e) {
				e.printStackTrace();
			}

	}

}
