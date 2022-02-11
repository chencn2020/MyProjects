/*
 * Name : GetPDF.java
 *
 * Function : To generate PDF files and send them to the player via email
 * 
 * Author : Chen Zewen
 * 
 * Student Number : 18301154
 * 
 * Date : 2019/12/23
 */

package tankGameServer;

import java.io.*;
import java.util.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.DocumentException;

/**
 * This class is used to generate PDF files and send them to the player via email
 * @author Zevin
 *
 */
public class GetPDF {

	/** Store the input content */
	private static String content;

	/**
	 * Generate PDF files
	 * @param content
	 * @throws IOException
	 * @throws DocumentException
	 */
	public GetPDF(String content) throws IOException, DocumentException {

		this.content = content;

		// Import PDF template
		String fileName = "example.pdf";
		
		// Generate new file path
		String newPDFPath = "certificate.pdf";

		try {
			
			// Open the PDF template
			PdfReader reader = new PdfReader(fileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			// Read the PDF template
			PdfStamper ps = new PdfStamper(reader, bos);
			PdfContentByte under = ps.getUnderContent(1);

			// Set the font
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
			fontList.add(bf);

			// Gets all the fields in the template
			AcroFields fields = ps.getAcroFields();
			fields.setSubstitutionFonts(fontList);
			
			// Make the PDF files
			fillData(fields, data()); 
			ps.setFormFlattening(true);
			ps.close();

			// Generate the new PDF file
			OutputStream fos = new FileOutputStream(newPDFPath);
			fos.write(bos.toByteArray());
			
			fos.flush();
			fos.close();
			bos.close();
		} catch (IOException e) {
			System.out.println("Something wrong, the reason: " + e.getLocalizedMessage());
		} catch (DocumentException e) {
			System.out.println("Something wrong, the reason: " + e.getLocalizedMessage());
		}
	}

	/**  Get the key and value in the PDF template */
	public static void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException {
		for (String key : data.keySet()) {
			
			// Get the key
			String value = data.get(key); 
			
			// Set the value
			fields.setField(key, value);
		}
	}

	/**
	 * Add the message to the fields in this template
	 * @return Map
	 */
	public static Map<String, String> data() {
		Map<String, String> data = new HashMap<String, String>();
		
		// Set the content into the key
		data.put("name", content.split(",")[0]); 
		data.put("rank", content.split(",")[1]);
		data.put("time", content.split(",")[2]);

		return data;
	}

}