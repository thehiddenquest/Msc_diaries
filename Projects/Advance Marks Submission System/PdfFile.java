package Model.main_package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import Model.transfer_object.MarksTO;

public class PdfFile {
	TreeMap<String, Subject> mergedHash = new TreeMap<String, Subject>();

	public void wholePDF(ArrayList<MarksTO> marksList, String stream, String sem, String year, String course) {
		try {
			String path = "marksTable10.pdf";
			PdfWriter pdfWriter = new PdfWriter(path);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
			Document document = new Document(pdfDocument);
			Set<String> paperSet = new HashSet<>();
			for (MarksTO marksTO : marksList) {
				paperSet.add(marksTO.getPaperTitle());
			}

			ArrayList<String> paperList = new ArrayList<>(paperSet);

			/*
			 * paperList.add("Operating System"); paperList.add("Data Stucture");
			 * paperList.add("Module B Operating System Lab");
			 * paperList.add("Module A Data Stucture"); paperList.add("Operating System");
			 */

			ArrayList<String> mergedSubjects = mergeSubjects(paperList);
			List<String> uniquePTValues = getUniquePTValues(marksList);
			int mergedSubjectsSize = mergedSubjects.size();

			float[] ColumnWidth = new float[mergedSubjectsSize + 1];
			ColumnWidth[0] = 150f;
			for (int i = 1; i < ColumnWidth.length; i++) {
				ColumnWidth[i] = 135f;
			}

			float ColumnWidth2[] = new float[mergedSubjectsSize * uniquePTValues.size() + 1];
			ColumnWidth2[0] = 150f;
			for (int i = 1; i < ColumnWidth2.length; i++) {
				ColumnWidth2[i] = 45f;
			}

			float fullWidth[] = { 800f };

			Table tableDivider1 = new Table(fullWidth);
			DashedBorder dbg = new DashedBorder(Color.GRAY, 0.5f);

			Paragraph marksPara = new Paragraph("Marks Details");
			Paragraph emptygraph = new Paragraph(" ");
			Paragraph marksPara1 = new Paragraph(stream.toUpperCase() + " ( " + course.toUpperCase() + " ) SEMESTER: "
					+ sem.toUpperCase() + " " + year + " ");
			Table sevenColTable1 = new Table(ColumnWidth);
			Table sevenColTable2 = new Table(ColumnWidth2);
			Table sevenColTable3 = new Table(ColumnWidth2);

			sevenColTable1.setBackgroundColor(Color.BLACK, 0.7f);

			sevenColTable1.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
			sevenColTable2.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));

//			System.out.println("Contents of the Merged String:");
//			for (int i = 0; i < mergedSubjects.size(); i++) {
//				System.out.println("mergedSubjects::  " + mergedSubjects.get(i));
//			}

			ArrayList<String> uniqueFMValues = getUniqueFMValues(marksList, mergedHash);
			Collections.sort(uniqueFMValues);
			System.out.println("Paper Type\tFull Marks");
			for (String paper : uniqueFMValues) {
				System.out.println(paper);
			}

			System.out.println("Contents of the HashMap:");
			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				String key = entry.getKey();
				Subject value = entry.getValue();
				if (value.getName() == null) {
					System.out.println("Key: " + key + ", Value: null");
				} else {
					System.out.println("Key: " + key + ", Value: " + value.getName() + ", FUll marks");
					String[] marks = value.getMarks();
					for (int i = 0; i < marks.length; i++) {
						System.out.println(marks[i]);

					}
				}
			}
			ArrayList<String> paperName = new ArrayList<>();
			ArrayList<String> paperFullMarks = new ArrayList<>();

			for (String paper : uniqueFMValues) {
				String[] parts = paper.split(":");
				paperName.add(parts[0]);
			}
			for (String paper : uniqueFMValues) {
				String[] parts = paper.split(":");
				paperFullMarks.add(parts[2]);
			}

			for (String subject : mergedSubjects) {
				sevenColTable1.addCell(new Cell().add(subject).setBold().setFontColor(Color.WHITE)
						.setTextAlignment(TextAlignment.CENTER));

				for (String paper : uniquePTValues) {
					sevenColTable2.addCell(new Cell().add(paper).setBold().setTextAlignment(TextAlignment.CENTER));
				}
			}
			sevenColTable2.addCell(new Cell().add("Student Details").setTextAlignment(TextAlignment.CENTER));

			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
//				for (String paper : uniqueFMValues) {in
				Subject value = entry.getValue();
				String[] marks = value.getMarks();
				for (int i = 0; i < marks.length; i++) {
					sevenColTable2.addCell(new Cell().add(marks[i]).setBold().setTextAlignment(TextAlignment.CENTER));
				}
//			}
			}

			Set<String> rollNumberSet = new HashSet<>();
			for (int j = 0; j < marksList.size(); j++) {
				MarksTO markRoll = marksList.get(j);
				String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber();
				rollNumberSet.add(roll);
			}

			List<String> rollNumber = new ArrayList<>(rollNumberSet);
			ArrayList<String> thMarks = new ArrayList<>();
			ArrayList<String> iaMarks = new ArrayList<>();
			ArrayList<String> prMarks = new ArrayList<>();
//			ArrayList<>

			String rollStr = "";

			for (MarksTO markRoll : marksList) {
				rollStr = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber();
				if (markRoll.getPaperType().trim().equalsIgnoreCase("TH")) {
					thMarks.add(rollStr + ":" + markRoll.getPaperTitle() + ":" + markRoll.getMarksObt());
				} else if (markRoll.getPaperType().trim().equalsIgnoreCase("IA")) {
					iaMarks.add(rollStr + ":" + markRoll.getPaperTitle() + ":" + markRoll.getMarksObt());
				} else if (markRoll.getPaperType().trim().equalsIgnoreCase("PR")) {
					prMarks.add(rollStr + ":" + markRoll.getPaperTitle() + ":" + markRoll.getMarksObt());
				}
			}

			Collections.sort(rollNumber);
			Collections.sort(thMarks);
			Collections.sort(iaMarks);
			Collections.sort(prMarks);
			for (String roll : prMarks) {
				System.out.println(roll);
			}
			List<String> thMarksList = new ArrayList<>();
			List<String> iaMarksList = new ArrayList<>();
			List<String> prMarksList = new ArrayList<>();

			for (String mark : thMarks) {
				String[] parts = mark.split(":");
				thMarksList.add(parts[2]);
			}
			for (String mark : iaMarks) {
				String[] parts = mark.split(":");
				iaMarksList.add(parts[2]);
			}
			for (String mark : prMarks) {
				String[] parts = mark.split(":");
				prMarksList.add(parts[2]);
			}

			int p = 0, q = 0, r = 0;
			for (int j = 0; j < rollNumber.size(); j++) {
				sevenColTable3.addCell(rollNumber.get(j)).setTextAlignment(TextAlignment.CENTER);
				for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
					if (p == rollNumber.size() * mergedSubjects.size()) {
						break;
					}
					String key = entry.getKey();
					Subject value = entry.getValue();
					String[] marks = value.getMarks();
					if (value.getName() == null && marks[0].equals("N/A")) {
						sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);

					} else {
						sevenColTable3.addCell(prMarksList.get(p++)).setTextAlignment(TextAlignment.CENTER);
					}
					if (marks[1].equalsIgnoreCase("N/A")) {
						sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
						sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
					} else {
						sevenColTable3.addCell(thMarksList.get(q++)).setTextAlignment(TextAlignment.CENTER);
						sevenColTable3.addCell(iaMarksList.get(r++)).setTextAlignment(TextAlignment.CENTER);
					}

				}
			}

			document.add(tableDivider1.setBorder(dbg));
			document.add(marksPara1.setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));
			document.add(emptygraph);
			document.add(marksPara.setBold());
			document.add(emptygraph);
			document.add(sevenColTable1);
			document.add(sevenColTable2);
			document.add(sevenColTable3);
			document.close();

		} catch (

		Exception ex) {
		}
	}

	public List<String> getUniquePTValues(List<MarksTO> marksList) {
		// Set<String> uniquePTSet = new TreeSet<>();
//		for (MarksTO marks : marksList) {
//			uniquePTSet.add(marks.getPaperType());
//		}
//		boolean containsPRorPractice = false;
//		for (String paperType : uniquePTSet) {
//			if (paperType.equalsIgnoreCase("PR") || paperType.equalsIgnoreCase("Practical")) {
//				containsPRorPractice = true;
//				break; // No need to continue checking if found
//			}
//		}
//		if (containsPRorPractice == false) {
//			uniquePTSet.add("PR");
//		}

//		System.out.println("Unique Paper Types:");
//		for (String paperType : sortedSet) {
//			System.out.println("pt set: " + paperType);
//		}
//		uniquePTSet.add("PR");
//		uniquePTSet.add("TH");
//		uniquePTSet.add("IA");
//		Set<String> sortedSet = sortSet(uniquePTSet);
//		reverseLastTwoElements(sortedSet);
		ArrayList<String> sortedSet = new ArrayList<>();
		sortedSet.add("PR");
		sortedSet.add("TH");
		sortedSet.add("IA");
		return sortedSet;
	}

//	for (int j = 0, sizeI = 0; j < rollNumber.size(); j++) {
//		if(false) {
//			break;
//		}
//		sevenColTable4.addCell(rollNumber.get(j)).setTextAlignment(TextAlignment.CENTER);
//		String[] parts = thMarks.get(j).split(":");
//		String[] parts2 = iaMarks.get(j).split(":");
//		String[] parts3 = prMarks.get(j).split(":");
//		sevenColTable4.addCell(parts[1]).setTextAlignment(TextAlignment.CENTER);
//		sevenColTable4.addCell(parts2[1]).setTextAlignment(TextAlignment.CENTER);
//		sevenColTable4.addCell(parts3[1]).setTextAlignment(TextAlignment.CENTER);
//	
//		for(int i = 0; i < 3; i ++) {
//			sevenColTable4.addCell("").setTextAlignment(TextAlignment.CENTER);
//		}
//		
//	}
//	public static List<String> getUniqueFMValues(List<MarksTO> marksList) {
//		Set<String> uniqueFMSet = new HashSet<>();
//		for (MarksTO marks : marksList) {
//			uniqueFMSet.add(marks.getFullMarks());
//		
//		}
//		Set<String> sortedSet = sortSet(uniqueFMSet);
//		System.out.println("FUll");
//		for (String paper : sortedSet) {
//			System.out.println(paper);
//		}
//		return new ArrayList<>(sortedSet);
//	}

	public ArrayList<String> getUniqueFMValues(List<MarksTO> marksList, TreeMap<String, Subject> mergedHash) {
		ArrayList<String> paperTypeToFullMarksMap = new ArrayList<String>();
		List<String> uniquePTValues = getUniquePTValues(marksList);
		for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
			String subject = entry.getKey();
			Subject Lab = entry.getValue();
//			for (String paperType : uniquePTValues) {
//				System.out.println("pt: " + paperType);
//			}
			ArrayList<String> arrayList = new ArrayList<>();
			for (String paperType : uniquePTValues) {
				if (!paperType.equalsIgnoreCase("PR")) {
					String fullMarks = getFullMarksByPaperType(marksList, subject, paperType);
					arrayList.add(fullMarks);
				} else {
					String fullMarks = getFullMarksByPaperType(marksList, Lab.getName(), paperType);
					if (fullMarks.equalsIgnoreCase("N/A")) {
						fullMarks = getFullMarksByPaperType(marksList, subject, paperType);
					}
					arrayList.add(fullMarks);
				}

			}
			String[] array = arrayList.toArray(new String[arrayList.size()]);
			Lab.setMarks(array);
//			String[] marks = Lab.getMarks();
//			for (int i = 0; i < 3; i++) {
//				System.out.println(marks[i]);
//			}
		}
		return paperTypeToFullMarksMap;

	}

	private String getFullMarksByPaperType(List<MarksTO> marksList, String paperName, String paperType) {
		for (MarksTO marks : marksList) {
			if (marks.getPaperTitle().equals(paperName)) {
				if (marks.getPaperType().equals(paperType)) {
					return marks.getFullMarks();
				}
			}
		}
		return "N/A"; // or throw an exception if paper type is not found
	}

	public Set<String> sortSet(Set<String> set) {
		List<String> list = new ArrayList<>(set);
		Collections.sort(list);
		Collections.reverse(list);

		return new LinkedHashSet<>(list);
	}

	public void reverseLastTwoElements(Set<String> set) {
		if (set.size() >= 2) {
			List<String> list = new ArrayList<>(set);
			Collections.swap(list, list.size() - 1, list.size() - 2);
			set.clear();
			set.addAll(list);
		}
	}

	public ArrayList<String> mergeSubjects(ArrayList<String> subjects) {
		Set<String> mergedSubjectSet = new HashSet<>();

		for (String subject : subjects) {
			boolean isSubstring = false;
			for (String otherSubject : subjects) {
				if (!subject.equalsIgnoreCase(otherSubject)
						&& subject.toUpperCase().contains(otherSubject.toUpperCase())) {
					isSubstring = true;
					if (!mergedHash.containsKey(otherSubject)) {
						Subject mergedSubject = new Subject();
						mergedSubject.setName(subject);
						mergedHash.put(otherSubject, mergedSubject);
					}
					break;
				}
			}
			if (!isSubstring) {
				mergedSubjectSet.add(subject);
			}
		}
		for (String subject : mergedSubjectSet) {
			if (!mergedHash.containsKey(subject)) {
				System.out.println(subject);
				Subject mergedSubject = new Subject();
				mergedHash.put(subject, mergedSubject);
			}
		}
		ArrayList<String> mergedSubjectList = new ArrayList<>(mergedSubjectSet);
		Collections.sort(mergedSubjectList);
		return mergedSubjectList;
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
