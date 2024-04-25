//package DAO;
//
//import java.io.FileNotFoundException;
//import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.ArrayList;
//import java.util.List;
//
////import com.itextpdf.kernel.color.Color;
////import com.itextpdf.kernel.geom.PageSize;
////import com.itextpdf.kernel.pdf.PdfDocument;
////import com.itextpdf.kernel.pdf.PdfWriter;
////import com.itextpdf.layout.Document;
////import com.itextpdf.layout.border.DashedBorder;
////import com.itextpdf.layout.element.Paragraph;
////import com.itextpdf.layout.element.Table;
////import com.itextpdf.layout.property.TextAlignment;
//
//import transfer_object.MarksTO;
//
//public class DBtoPDF {
//	public void genaratePDF() throws FileNotFoundException {
//		try {
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Marks", "arijit", "arijit2112");
//
//			// Step 2: Construct a 'Statement' object called 'stmt' inside the Connection
//			// created
//			PreparedStatement stmt = conn.prepareStatement("select * from marksDB");
//			ResultSet rst = stmt.executeQuery();
//			ResultSetMetaData rm = rst.getMetaData();
//			System.out.print(rm.getColumnName(1));
//			String path = " marksTable.pdf";
//			PdfWriter pdfWriter = new PdfWriter(path);
//			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//			pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
//			Document document = new Document(pdfDocument);
//			float onecol = 85f;
//			float sevenColumnWidth[] = { onecol, onecol, onecol, onecol, onecol, onecol, onecol };
//			float fullWidth[] = { onecol * 7 };
//
//			Table tableDivider1 = new Table(fullWidth);
//			DashedBorder dbg = new DashedBorder(Color.GRAY, 0.5f);
//			document.add(tableDivider1.setBorder(dbg));
//			Paragraph marksPara = new Paragraph("Marks Details");
//
//			document.add(marksPara.setBold());
//			Table sevenColTable1 = new Table(sevenColumnWidth);
//			sevenColTable1.setBackgroundColor(Color.BLACK, 0.7f);
//
//			List<MarksTO> marksList = new ArrayList<>();
//
//			ResultSet rset = stmt.executeQuery();
//			System.out.println("The records selected are:");
//			int rowCount = 0;
//			System.out.print(rm.getColumnName(1));
//			while (rset.next()) { // Repeatedly process each row
//				String pt = rset.getString("paper_type");
//				String fm = rset.getString("full_marks");
//				String mo = rset.getString("marks_obt");
//				String co = rset.getString("coll");
//				String ca = rset.getString("cate");
//				String nu = rset.getString("number1");
//				String pc = rset.getString("paper_code");
//				System.out.println(pt + " : " + fm + " : " + mo + " : " + co + " : " + ca + " : " + nu + " : " + pc);
//				marksList.add(new MarksTO(pt, fm, mo, co, ca, nu, pc));
//				++rowCount;
//			}
//			
//			Table sevenColTable2 = new Table(sevenColumnWidth);
//
//			for (MarksTO mark : marksList) {
//				sevenColTable2.addCell(mark.getPt()).setTextAlignment(TextAlignment.CENTER);
//				sevenColTable2.addCell(mark.getFm());
//				sevenColTable2.addCell(mark.getMo());
//				sevenColTable2.addCell(mark.getCo());
//				sevenColTable2.addCell(mark.getCa());
//				sevenColTable2.addCell(mark.getNu());
//				sevenColTable2.addCell(mark.getPc());
//			}
//
//			document.add(sevenColTable2);
//
//			System.out.println("Total number of records = " + rowCount);
//
//			document.close();
//
//		} catch (SQLException ex) {
//		}
//	}
//}