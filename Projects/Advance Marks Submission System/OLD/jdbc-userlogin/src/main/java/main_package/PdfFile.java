package main_package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
	static HashMap<String, Subject> mergedHash = new HashMap<String, Subject>();

	public void wholePDF(ArrayList<MarksTO> marksList, String stream, String sem, String year, String course) {
		try {
			String path = "marksTable7.pdf";
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

			int mergedSubjectsSize = mergedSubjects.size();

			float[] ColumnWidth = new float[mergedSubjectsSize + 1];
			ColumnWidth[0] = 150f;
			for (int i = 1; i < ColumnWidth.length; i++) {
				ColumnWidth[i] = 135f;
			}

			float ColumnWidth2[] = new float[mergedSubjectsSize * 3 + 1];
			ColumnWidth2[0] = 150f;
			for (int i = 1; i < ColumnWidth2.length; i++) {
				ColumnWidth2[i] = 45f;
			}

			/*
			 * System.out.println("ColumnWidth array:"); for (int i = 0; i <
			 * ColumnWidth.length; i++) { System.out.println("ColumnWidth[" + i + "] = " +
			 * ColumnWidth[i]); }
			 * 
			 * System.out.println("\nColumnWidth2 array:"); for (int i = 0; i <
			 * ColumnWidth2.length; i++) { System.out.println("ColumnWidth2[" + i + "] = " +
			 * ColumnWidth2[i]); }
			 */

			float fullWidth[] = { 800f };

			Table tableDivider1 = new Table(fullWidth);
			DashedBorder dbg = new DashedBorder(Color.GRAY, 0.5f);

			Paragraph marksPara = new Paragraph("Marks Details");

			Table sevenColTable1 = new Table(ColumnWidth);
			Table sevenColTable2 = new Table(ColumnWidth2);
			Table sevenColTable3 = new Table(ColumnWidth2);
			Table sevenColTable4 = new Table(ColumnWidth2);

			sevenColTable1.setBackgroundColor(Color.BLACK, 0.7f);

			sevenColTable1.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
			sevenColTable2.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
			sevenColTable3.addCell(new Cell().add("Student Details").setTextAlignment(TextAlignment.CENTER));

			System.out.println("Contents of the HashMap:");
			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				String key = entry.getKey();
				Subject value = entry.getValue();
				if (value.getName() == null) {
					System.out.println("Key: " + key + ", Value: null");
				} else {
					System.out.println("Key: " + key + ", Value: " + value.getName());
				}

			}

			System.out.println("Contents of the Merged String:");
			for (int i = 0; i < mergedSubjects.size(); i++) {
				System.out.println("mergedSubjects::  " + mergedSubjects.get(i));
			}

			List<String> uniquePTValues = getUniquePTValues(marksList);
			getUniqueFMValues(marksList, mergedHash);
			System.out.println("Paper Type\tFull Marks");
			System.out.println("Contents of the HashMap:");
			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
			    String key = entry.getKey();
			    Subject value = entry.getValue();
			    if (value.getName() == null) {
			        System.out.println("Key: " + key + ", Value: null");
			    } else {
			        System.out.print("Key: " + key + ", Value: " + value.getName() + ", FullMarks = ");
			        String[] marks = value.getFullMarks();
			        if (marks != null) {
			            for (int i = 0; i < marks.length; i++) {
			                System.out.print(marks[i]);
			            }
			        } else {
			            System.out.print("No full marks available");
			        }
			        System.out.println(); // Add a new line after printing full marks
			    }
			}
			for (String subject : mergedSubjects) {
				sevenColTable1.addCell(new Cell().add(subject).setBold().setFontColor(Color.WHITE)
						.setTextAlignment(TextAlignment.CENTER));

				for (String ptValue : uniquePTValues) {
					sevenColTable2.addCell(new Cell().add(ptValue).setBold().setTextAlignment(TextAlignment.CENTER));
				}

//				for (String fmValue : uniqueFMValues) {
//					sevenColTable3.addCell(new Cell().add(fmValue).setBold().setTextAlignment(TextAlignment.CENTER));
//				}

				Set<String> rollNumberSet = new HashSet<>();
				for (int j = 0; j < marksList.size(); j++) {
					MarksTO markRoll = marksList.get(j);
					String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber();
					rollNumberSet.add(roll);
				}
				List<String> rollNumber = new ArrayList<>(rollNumberSet);
				Collections.sort(rollNumber);

				ArrayList<String> thMarks = new ArrayList<>();
				for (int j = 0; j < marksList.size(); j++) {
					MarksTO markRoll = marksList.get(j);
					if (markRoll.getPaperType().trim().equalsIgnoreCase("TH")) {
						String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber() + ":"
								+ markRoll.getMarksObt();
						thMarks.add(roll);
					}
				}
				Collections.sort(thMarks);

				ArrayList<String> iaMarks = new ArrayList<>();
				for (int j = 0; j < marksList.size(); j++) {
					MarksTO markRoll = marksList.get(j);
					if (markRoll.getPaperType().trim().equalsIgnoreCase("IA")) {
						String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber() + ":"
								+ markRoll.getMarksObt();
						iaMarks.add(roll);
					}
				}
				Collections.sort(iaMarks);

				ArrayList<String> prMarks = new ArrayList<>();
				for (int j = 0; j < marksList.size(); j++) {
					MarksTO markRoll = marksList.get(j);
					if (markRoll.getPaperType().trim().equalsIgnoreCase("PR")) {
						String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber() + ":"
								+ markRoll.getMarksObt();
						prMarks.add(roll);
					}
				}
				Collections.sort(prMarks);

				for (int j = 0; j < rollNumber.size(); j++) {
					sevenColTable4.addCell(rollNumber.get(j)).setTextAlignment(TextAlignment.CENTER);
					String[] parts = thMarks.get(j).split(":");
					sevenColTable4.addCell(parts[1]).setTextAlignment(TextAlignment.CENTER);
					String[] parts2 = iaMarks.get(j).split(":");
					sevenColTable4.addCell(parts2[1]).setTextAlignment(TextAlignment.CENTER);
					String[] parts3 = prMarks.get(j).split(":");
					sevenColTable4.addCell(parts3[1]).setTextAlignment(TextAlignment.CENTER);
				}

			}

			document.add(tableDivider1.setBorder(dbg));
			document.add(marksPara.setBold());
			document.add(sevenColTable1);
			document.add(sevenColTable2);
			document.add(sevenColTable3);
			document.add(sevenColTable4);

			document.close();

		} catch (Exception ex) {
		}
	}

	public static List<String> getUniquePTValues(List<MarksTO> marksList) {
		Set<String> uniquePTSet = new HashSet<>();
		for (MarksTO marks : marksList) {
			uniquePTSet.add(marks.getPaperType().trim());
		}

		Set<String> sortedSet = sortSet(uniquePTSet);
		reverseLastTwoElements(sortedSet);

		return new ArrayList<>(sortedSet);
	}

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

	public static void getUniqueFMValues(List<MarksTO> marksList, HashMap<String, Subject> mergedHash) {

		List<String> uniquePTValues = getUniquePTValues(marksList);
		String marks[] = new String[uniquePTValues.size()];
		for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
			String key = entry.getKey();
			Subject value = entry.getValue();
			int i = 0;

			for (String paperType : uniquePTValues) {
				if (!paperType.equals("PR")) {
					String fullMarks = getFullMarksByPaperType(marksList, key, paperType);
					marks[i] = fullMarks;
					i++;
				}
				else{
					System.out.print(value.getName());
					String fullMarks = getFullMarksByPaperType(marksList, value.getName(), paperType);
					marks[i] = fullMarks;
					System.out.println(i+marks[i]);
					i++;
				}
			}
			for(int j =0;j<3;j++)
			{
				System.out.println(marks[j]);
			}
			value.setFullMarks(marks);
			mergedHash.put(key, value);
			String marking[] = value.getFullMarks();
			for(int j =0;j<3;j++)
			{
				System.out.println(marking[j]);
			}
		}
	}

	private static String getFullMarksByPaperType(List<MarksTO> marksList, String key, String paperType) {
		for (MarksTO marks : marksList) {
			if (marks.getPaperTitle().equals(key)) {
				System.out.println(key);
				System.out.println(marks.getPaperType());
				System.out.println(paperType);
				if (paperType.equalsIgnoreCase(marks.getPaperType())) {
					System.out.println("inside"+marks.getFullMarks());
					return marks.getFullMarks();
				}
			}
		}
		return null; // or throw an exception if paper type is not found
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
		Set<String> mergedSubjectSet = new HashSet<>();
		for (String subject : subjects) {
			boolean isSubstring = false;
			for (String otherSubject : subjects) {
				if (!subject.equals(otherSubject) && subject.contains(otherSubject)) {
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
		return mergedSubjectList;
	}
}

class Subject {
	private String name;
	private String[] FullMarks = new String[3];

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFullMarks() {
		return FullMarks;
	}

	public void setFullMarks(String[] fullMarks) {
		FullMarks = fullMarks;
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
