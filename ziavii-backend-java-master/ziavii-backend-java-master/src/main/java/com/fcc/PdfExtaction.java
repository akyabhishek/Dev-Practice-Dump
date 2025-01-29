package com.fcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fcc.domain.Order;
import com.fcc.repository.OrdersRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfExtaction {

	@Autowired
	static
	OrdersRepo ordersRepo;
	
 public static void main(String[] args) throws FileNotFoundException, DocumentException {
	 
	 Document my_pdf_report = new Document();
	  PdfWriter.getInstance(my_pdf_report, new FileOutputStream("pdf_report_from_sql_using_java.pdf"));
      my_pdf_report.open();            
      //we have four columns in our table
      PdfPTable my_first_table = new PdfPTable(4);
      //create a cell object
      PdfPCell table_cell;
      
      List<Order> orderList = ordersRepo.findAll();
      for(Order order : orderList) {
    	  table_cell=new PdfPCell(new Phrase(order.getOrderId()));
    	  my_first_table.addCell(table_cell);
    	  table_cell=new PdfPCell(new Phrase(order.getVendor().getName()));
    	  my_first_table.addCell(table_cell);
    	  table_cell=new PdfPCell(new Phrase(order.getTransactionId()));
    	  my_first_table.addCell(table_cell);
    	  table_cell=new PdfPCell(new Phrase(order.getCustomer().getName()));
    	  my_first_table.addCell(table_cell);
    	  table_cell=new PdfPCell(new Phrase(order.getCreatedDate().toString()));
    	  my_first_table.addCell(table_cell);
    	  
      }
      
      my_pdf_report.add(my_first_table);                       
      my_pdf_report.close();
      
      
	 
//	 PDDocument document = PDDocument.load(new File("test.pdf"));
//	 if (!document.isEncrypted()) {
//	     PDFTextStripper stripper = new PDFTextStripper();
//	     String text = stripper.getText(document);
//	     System.out.println("Text:" + text);
//	 }
//	 document.close();
	 
//	 File f = new File(filename);
//	 String parsedText;
//	 PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
//	 parser.parse();
  }
}
