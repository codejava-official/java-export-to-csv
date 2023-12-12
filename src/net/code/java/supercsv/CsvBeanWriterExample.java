package net.codejava.supercsv;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * This program demonstrates how to write fields of POJOs to a CSV file 
 * using SuperCSV library. Each JavaBean's fields are written to a row
 * in the CSV file.
 * @author www.codejava.net
 *
 */
public class CsvBeanWriterExample {

	static void writeCSVFile(String csvFileName, List<Book> listBooks) {
		ICsvBeanWriter beanWriter = null;
		CellProcessor[] processors = new CellProcessor[] { 
				new NotNull(), // ISBN
				new NotNull(), // title
				new NotNull(), // author
				new NotNull(), // publisher
				new FmtDate("MM/dd/yyyy"), // published date
				new ParseDouble() // price
		};

		try {
			beanWriter = new CsvBeanWriter(new FileWriter(csvFileName),
					CsvPreference.STANDARD_PREFERENCE);
			String[] header = {"isbn", "title", "author", "publisher", "published", "price"};
			beanWriter.writeHeader(header);
			
			for (Book aBook : listBooks) {
				beanWriter.write(aBook, header, processors);
			}
			
		} catch (IOException ex) {
			System.err.println("Error writing the CSV file: " + ex);
		} finally {
			if (beanWriter != null) {
				try {
					beanWriter.close();
				} catch (IOException ex) {
					System.err.println("Error closing the writer: " + ex);
				}
			}
		}
	}

	public static void main(String[] args) throws ParseException {
		// creates some dummy data
		DateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
		
		Book book1 = new Book("0321356683", "Effective Java", "Joshua Bloch", 
				"Addision-Wesley", dateFormater.parse("05/08/2008"), 38.00);
		Book book2 = new Book("0321356683", "Head First Java", "Kathy Sierra & Bert Bates", 
				"O'Reilly Media", dateFormater.parse("02/09/2005"), 30.00);
		Book book3 = new Book("0131872486", "Thinking in Java", "Bruce Eckel", 
				"Prentice Hall", dateFormater.parse("02/26/06"), 45.00);
		Book book4 = new Book("0596527756", "", "Naftalin & Philip Wadler", 
				"O'Reilly Media", dateFormater.parse("10/24/2006"), 27.00);
		
		List<Book> listBooks = Arrays.asList(book1, book2, book3, book4);
		
		
		String csvFileName = "Java_Books.csv";
		writeCSVFile(csvFileName, listBooks);
	}
}