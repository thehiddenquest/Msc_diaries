package main_package;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import transfer_object.MarksTO;

public class PdfFile {
	public void wholePDF(ArrayList<MarksTO> marks) {
		try{
			List<MarksTO> marksList = new ArrayList<>();

			String path = "marksTable.pdf";
			PdfWriter pdfWriter = new PdfWriter(path);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
			Document document = new Document(pdfDocument);
			float onecol = 60f;
			int rowCount = 0;

			marksList = marks;

			ArrayList<String> paperList = new ArrayList<>();
			for (MarksTO marksTO : marksList) {
			    paperList.add(marksTO.getPaperTitle()); // Assuming getPaperTitle() method retrieves the paper name
			}
			
			ArrayList<String> mergedSubjects = mergeSubjects(paperList);

			float ColumnWidth2[] = { 150f, 45f, 45f, 45f };
			float ColumnWidth[] = { 150f, 135f };
			float fullWidth[] = { 150f + 2 * onecol };

			Table tableDivider1 = new Table(fullWidth);
			DashedBorder dbg = new DashedBorder(Color.GRAY, 0.5f);
			document.add(tableDivider1.setBorder(dbg));
			Paragraph marksPara = new Paragraph("Marks Details");

			document.add(marksPara.setBold());

			Table sevenColTable1 = new Table(ColumnWidth);
			sevenColTable1.setBackgroundColor(Color.BLACK, 0.7f);
			Table sevenColTable2 = new Table(ColumnWidth2);
			Table sevenColTable3 = new Table(ColumnWidth2);
			Table sevenColTable4 = new Table(ColumnWidth2);

			sevenColTable1.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
			sevenColTable2.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
			sevenColTable3.addCell(new Cell().add("Student Details").setTextAlignment(TextAlignment.CENTER));
			for (String subject : mergedSubjects) {

				sevenColTable1.addCell(new Cell().add(subject).setBold().setFontColor(Color.WHITE)
						.setTextAlignment(TextAlignment.CENTER));

				List<String> uniquePTValues = getUniquePTValues(marksList);
				for (String ptValue : uniquePTValues) {
					sevenColTable2.addCell(new Cell().add(ptValue).setBold().setTextAlignment(TextAlignment.CENTER));
				}
				List<String> uniqueFMValues = getUniqueFMValues(marksList);
				for (String fmValue : uniqueFMValues) {
					sevenColTable3.addCell(new Cell().add(fmValue).setBold().setTextAlignment(TextAlignment.CENTER));
				}

				for (int j = 0; j < marksList.size() / 3; j++) {
					MarksTO markRoll = marksList.get(j * 3);
					sevenColTable4.addCell(markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber()).setTextAlignment(TextAlignment.CENTER);
					MarksTO mark0 = marksList.get(j * 3);
					MarksTO mark1 = marksList.get(j * 3 + 1);
					MarksTO mark2 = marksList.get(j * 3 + 2);
					sevenColTable4.addCell(mark2.getMarksObt()).setTextAlignment(TextAlignment.CENTER);
					sevenColTable4.addCell(mark0.getMarksObt()).setTextAlignment(TextAlignment.CENTER);
					sevenColTable4.addCell(mark1.getMarksObt()).setTextAlignment(TextAlignment.CENTER);

				}
			}

			document.add(sevenColTable1);
			document.add(sevenColTable2);
			document.add(sevenColTable3);
			document.add(sevenColTable4);

			System.out.println("Total number of records = " + rowCount);

			document.close();

		} catch (Exception ex) {
		}
	}

	public static List<String> getUniquePTValues(List<MarksTO> marksList) {
		Set<String> uniquePTSet = new HashSet<>();
		for (MarksTO marks : marksList) {
			uniquePTSet.add(marks.getPaperType());
		}
		Set<String> sortedSet = sortSet(uniquePTSet);
		reverseLastTwoElements(sortedSet);

		return new ArrayList<>(sortedSet);
	}

	public static List<String> getUniqueFMValues(List<MarksTO> marksList) {
		Set<String> uniqueFMSet = new HashSet<>();
		for (MarksTO marks : marksList) {
			uniqueFMSet.add(marks.getFullMarks());
		}
		Set<String> sortedSet = sortSet(uniqueFMSet);
		return new ArrayList<>(sortedSet);
	}

	public static Set<String> sortSet(Set<String> set) {
		List<String> list = new ArrayList<>(set);
		Collections.sort(list);
		Collections.reverse(list);

		return new LinkedHashSet<>(list);
	}

	public static void reverseLastTwoElements(Set<String> set) {
		if (set.size() >= 2) {
			List<String> list = new ArrayList<>(set);
			Collections.swap(list, list.size() - 1, list.size() - 2);
			set.clear();
			set.addAll(list);
		}
	}

	public static ArrayList<String> mergeSubjects(ArrayList<String> subjects) {
		ArrayList<String> mergedSubjects = new ArrayList<>();
		for (String subject : subjects) {
			boolean isSubstring = false;
			for (String otherSubject : subjects) {
				if (!subject.equals(otherSubject) && subject.contains(otherSubject)) {
					isSubstring = true;
					break;
				}
			}
			if (!isSubstring) {
				mergedSubjects.add(subject);
			}
		}
		return mergedSubjects;
	}
}


/*
 * ResultSetMetaData rsmd=rset.getMetaData();
 * System.out.println("Total columns: "+rsmd.getColumnCount());
 * System.out.println("Column Name of 1st column: "+rsmd.getColumnName(1));
 * System.out.println("Column Type Name of 1st column: "+rsmd.getColumnTypeName(
 * 1));
 * System.out.println("Column Type Name of 1st Table: "+rsmd.getTableName(1));
 */

/*
 * DatabaseMetaData dbmd=conn.getMetaData(); String table[]={"TABLE"}; ResultSet
 * rs=dbmd.getTables(null,null,null,table);
 * 
 * while(rs.next()){ System.out.println(rs.getString(3)); }
 */
