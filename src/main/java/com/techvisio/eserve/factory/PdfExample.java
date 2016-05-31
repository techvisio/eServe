package com.techvisio.eserve.factory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfExample {

	public static void main(String[] args) throws MalformedURLException, IOException
	{



		//special font sizes
		Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
		Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 
		Document document = new Document();
		Font GRAY_NORMAL = new Font(FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.GRAY);
		float fntSize = 6.7f;
		try
		{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("invoice.pdf"));
			document.open();

			//Add Image
			Image image1 = Image.getInstance("E:/TECHVISIO/workspace/eServe/localRepo/src/main/webapp/static/images/Honda.jpg");
			//Fixed Positioning
			image1.setAbsolutePosition(35f, 780f);
			//Scale to new height and new width of image
			image1.scaleAbsolute(50, 50);
			//Add to document
			document.add(image1);


			PdfPTable table = new PdfPTable(1);

			PdfPCell cell1 = new PdfPCell(new Paragraph("INVOICE", GRAY_NORMAL));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1.setVerticalAlignment(Element.ALIGN_RIGHT);

			table.addCell(cell1);
			document.add(table);


			Paragraph clientName = new Paragraph("Techvisio Solutions Pvt. Ltd.", FontFactory.getFont(FontFactory.COURIER, fntSize));
			clientName.setAlignment(Element.ALIGN_LEFT);
			document.add(clientName);

			Paragraph address = new Paragraph("5/1608 Vasundhra", FontFactory.getFont(FontFactory.COURIER, fntSize));
			address.setAlignment(Element.ALIGN_LEFT);
			document.add(address);

			Paragraph city = new Paragraph("Ghaziabad, 201012", FontFactory.getFont(FontFactory.COURIER, fntSize));
			city.setAlignment(Element.ALIGN_LEFT);
			document.add(city);

			Paragraph state = new Paragraph("Uttar Pradesh", FontFactory.getFont(FontFactory.COURIER, fntSize));
			state.setAlignment(Element.ALIGN_LEFT);
			document.add(state);


			//specify column widths
			float[] columnWidths = {2f, 8f, 3f};
			//create PDF table with the given widths
			PdfPTable unitTable = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			
			unitTable.setWidthPercentage(100f);

			//insert column headings
			
			
			insertCell(unitTable, " ", Element.ALIGN_LEFT, 4, bfBold12, Rectangle.NO_BORDER);
			insertCell(unitTable, "S. No.", Element.ALIGN_LEFT, 1, bf12,Rectangle.BOX);
			insertCell(unitTable, "Product Description", Element.ALIGN_LEFT, 1, bf12,Rectangle.BOX);
			insertCell(unitTable, "Price", Element.ALIGN_LEFT, 1, bf12,Rectangle.BOX);
			insertCell(unitTable, " ", Element.ALIGN_LEFT, 4, bfBold12, Rectangle.BOX);
			insertCell(unitTable, "", Element.ALIGN_LEFT, 1, bf12,Rectangle.BOX);
			insertCell(unitTable, "unitId 2 Machine Serial No : HKID812K", Element.ALIGN_LEFT, 1, bf12,Rectangle.BOX);
			insertCell(unitTable, "80000", Element.ALIGN_LEFT, 1, bf12,Rectangle.BOX);
			document.add(unitTable);


			document.close();
			writer.close();
		} catch (DocumentException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private static void insertCell(PdfPTable table, String text, int align, int colspan, Font font, int border){

		//create a new cell with the specified Text and Font
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		//set the cell alignment
		cell.setHorizontalAlignment(align);
		//set the cell column span in case you want to merge two or more cells
		cell.setColspan(colspan);

		cell.setBorder(border);
		//in case there is no text and you wan to create an empty row
		if(text.trim().equalsIgnoreCase("")){
			cell.setMinimumHeight(10f);
		}
		//add the call to the table
		table.addCell(cell);

	}

}
