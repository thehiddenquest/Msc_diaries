package Model.main_package;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import Model.transfer_object.MarksTO;

public class PdfFile {

	TreeMap<String, Subject> mergedHash = new TreeMap<String, Subject>();

	public void specificPDF(ArrayList<MarksTO> marksList,String path, String stream, String sem, String year, String course,
			String subject) {
		try {
			// PDF Creation done here.
			PdfWriter pdfWriter = new PdfWriter(path);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(PageSize.A4);
			Document document = new Document(pdfDocument);

			// All Paper Title
			Set<String> paperSet = new HashSet<>();
			Set<String> rollNumberSet = new HashSet<>();

			for (MarksTO marksTO : marksList) {
				paperSet.add(marksTO.getPaperTitle());
			}
			ArrayList<String> paperList = new ArrayList<>(paperSet);
			ArrayList<String> mergedSubjects = mergeSubjects(paperList);
			ArrayList<String> paperName = new ArrayList<>();
			ArrayList<String> paperFullMarks = new ArrayList<>();
			ArrayList<String> uniqueFMValues = new ArrayList<>();
			List<String> uniquePTValues = getUniquePTValues();
			List<String> rollSort = new ArrayList<>();
			List<String> thMarksList = new ArrayList<>();
			List<String> iaMarksList = new ArrayList<>();
			List<String> prMarksList = new ArrayList<>();
			List<String> subjectTeacherList = new ArrayList<>();

			float ColumnWidth[] = { 200f, 300f };
			float ColumnWidth2[] = { 200f, 100f, 100f, 100f };
			float twoColumn[] = {350f, 350f};
			float fullWidth[] = { 800f };

			// Table is generated from the previous generated Row and Column Sizes
			Table tableDivider1 = new Table(fullWidth);
			DashedBorder dbg = new DashedBorder(Color.GRAY, 0.5f);
			Paragraph TitlePara = new Paragraph("University Of Calcutta");
			Paragraph marksPara = new Paragraph("Marks Details: ");
			Paragraph emptygraph = new Paragraph(" ");
			Paragraph marksPara1 = new Paragraph(stream.toUpperCase() + " ( " + course.toUpperCase() + " ) SEMESTER: "+ sem.toUpperCase() + " " + year + " ");
			Table sevenColTable1 = new Table(ColumnWidth);
			Table sevenColTable2 = new Table(ColumnWidth2);
			Table sevenColTable3 = new Table(ColumnWidth2);
			Table sevenColTable4 = new Table(twoColumn);

			sevenColTable1.setBackgroundColor(Color.BLACK, 0.7f);

			sevenColTable1.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));
			sevenColTable2.addCell(
					new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER));

			// Mapping One Paper Type with its full marks for individual Subjects.
			uniqueFMValues = getUniqueFMValues(marksList, mergedHash);
			Collections.sort(uniqueFMValues);

			// Paper Name and Full Marks are splitted here
			for (String paper : uniqueFMValues) {
				String[] parts = paper.split(":");
				paperName.add(parts[0]);
			}
			for (String paper : uniqueFMValues) {
				String[] parts = paper.split(":");
				paperFullMarks.add(parts[2]);
			}

//			Subjects and PT values for PDF printed here.			
			for (String subject1 : mergedSubjects) {
				if (subject.contains(subject1)) {
					sevenColTable1.addCell(new Cell().add(subject1).setBold().setFontColor(Color.WHITE)
							.setTextAlignment(TextAlignment.CENTER));
					for (String paper : uniquePTValues) {
						sevenColTable2.addCell(new Cell().add(paper).setBold().setTextAlignment(TextAlignment.CENTER));
					}

				}
			}
			sevenColTable2.addCell(new Cell().add("Student Details").setTextAlignment(TextAlignment.CENTER));

			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				String key = entry.getKey();
				Subject value = entry.getValue();
				String[] marks = value.getMarks();
				if (subject.contains(key)) {
					for (int i = 0; i < marks.length; i++) {
						sevenColTable2
								.addCell(new Cell().add(marks[i]).setBold().setTextAlignment(TextAlignment.CENTER));
					}
				}
			}

			for (int j = 0; j < marksList.size(); j++) {
				MarksTO markRoll = marksList.get(j);
				String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber();
				rollNumberSet.add(roll);
			}

			List<String> rollNumber = new ArrayList<>(rollNumberSet);
			HashSet<String> subjectTeacherSet = new HashSet<>();
			// Sorted Roll number List created here.
			for (MarksTO markRoll : marksList) {
				String rollStr = markRoll.getPaperTitle() + ":" + markRoll.getColl() + "/" + markRoll.getCate() + "/"
						+ markRoll.getNumber() + ":" + markRoll.getPaperType() + ":" + markRoll.getMarksObt();
				rollSort.add(rollStr);
				String subjectTeacher = markRoll.getPaperTitle() + ":" + markRoll.getTeachersName();
				subjectTeacherSet.add(subjectTeacher);
			}
			Collections.sort(rollSort);
			System.out.println(subjectTeacherSet);
			
			// Sorted Roll Number list is splitted into three List to store three types of
			// Paper type values by splitting.
			String rollStr = "";
			for (String markRoll : rollSort) {
				String[] parts = markRoll.split(":");
				rollStr = parts[1];
				String paperTitle = parts[0];
				String paperType = parts[2];
				String marksObt = parts[3];
				if (subject.contains(paperTitle)) {
					if (paperType.equals("PR")) {
						prMarksList.add(rollStr + ":" + paperType + ":" + paperTitle + ":" + marksObt);
					} else if (paperType.equals("TH")) {
						thMarksList.add(rollStr + ":" + paperType + ":" + paperTitle + ":" + marksObt);
					} else if (paperType.equals("IA")) {
						iaMarksList.add(rollStr + ":" + paperType + ":" + paperTitle + ":" + marksObt);
					}
				}
			}
			
			for(String subjectTeacherStr : subjectTeacherSet) {
				String[] parts = subjectTeacherStr.split(":");
				String paperTitle = parts[0];
				String teacherName = parts[1];
				if (subject.contains(paperTitle)) {
					subjectTeacherList.add(teacherName);
				}
			}

			// If a certain Paper Type exists or not for a certain paper name of a student
			// is checked here.
			String compStrForPR = "";
			String compStrForIA = "";
			String compStrForTH = "";
			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				String key = entry.getKey();
				Subject value = entry.getValue();
				String[] marks = value.getMarks();
				if (subject.contains(entry.getKey())) {
					System.out.println(
							"Inside the LOOP : key : " + entry.getKey() + " value : " + entry.getValue().getName());
					if (value.getName() != null) {
						compStrForPR = value.getName();
						System.out.println("PR Component : " + value.getName());
						if (!marks[1].equals("N/A")) {
							System.out.println("TH Component : " + key);
							compStrForTH = key;
						} else {
							compStrForTH = "9999";
						}
						if (!marks[2].equals("N/A")) {
							System.out.println("IA Component : " + key);
							compStrForIA = key;
						} else {
							compStrForIA = "9999";
						}

					} else if (value.getName() == null) {
						if (!marks[0].equals("N/A")) {
							System.out.println("Practical Component : " + key);
							compStrForPR = key;
							compStrForIA = "9999";
							compStrForTH = "9999";
						} else if (!marks[2].equals("N/A") && !marks[1].equals("N/A")) {
							System.out.println("TH Component : " + key);
							System.out.println("IA Component : " + key);
							compStrForPR = "9999";
							compStrForIA = key;
							compStrForTH = key;
						} else if (!marks[2].equals("N/A")) {
							System.out.println("IA Component : " + key);
							compStrForIA = key;
							compStrForPR = "9999";
							compStrForTH = "9999";
						} else {
							System.out.println("TH Component : " + key);
							compStrForIA = "9999";
							compStrForPR = "9999";
							compStrForTH = key;
						}
					}

					boolean PRF;
					boolean THF;
					boolean IAF;
// 				Whether the student has given exam of a unique paper type of an unique paper name is check here. If he/she didn't appeared The "N/A" is stored in the List 
					for (String markRoll : rollSort) {
						String[] parts = markRoll.split(":");
						rollStr = parts[1];
						PRF = false;
						if (!compStrForPR.equals("9999")) {
							for (String pr : prMarksList) {
								if (pr.contains(rollStr + ":" + "PR" + ":" + compStrForPR)) {
									PRF = true;
									break;
								}
							}
							if (PRF == false) {
								prMarksList.add(rollStr + ":" + "PR" + ":" + compStrForPR + ":" + "N/A");
							}
						}
						THF = false;
						if (!compStrForTH.equals("9999")) {
							for (String th : thMarksList) {
								if (th.contains(rollStr + ":" + "TH" + ":" + key)) {
									THF = true;
									break;
								}
							}
							if (THF == false) {
								thMarksList.add(rollStr + ":" + "TH" + ":" + key + ":" + "N/A");
							}
						}
						IAF = false;
						if (!compStrForIA.equals("9999")) {
							for (String ia : iaMarksList) {
								if (ia.contains(rollStr + ":" + "IA" + ":" + compStrForIA)) {
									IAF = true;
									break;
								}
							}
							if (IAF == false) {
								iaMarksList.add(rollStr + ":" + "IA" + ":" + compStrForIA + ":" + "N/A");
							}
						}
					}
				}
			}

			Collections.sort(rollNumber);
			Collections.sort(thMarksList);
			Collections.sort(iaMarksList);
			Collections.sort(prMarksList);


			List<String> thMarksList2 = new ArrayList<>();
			List<String> iaMarksList2 = new ArrayList<>();
			List<String> prMarksList2 = new ArrayList<>();

			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				String key = entry.getKey();
				if (subject.contains(key)) {
					for (String mark : thMarksList) {
						String[] parts = mark.split(":");
						thMarksList2.add(parts[3]);
					}

					for (String mark : iaMarksList) {
						String[] parts = mark.split(":");
						iaMarksList2.add(parts[3]);
					}

					for (String mark : prMarksList) {
						String[] parts = mark.split(":");
						prMarksList2.add(parts[3]);
					}
				}
			}
//			The Table is generated here with respect to student size and number of Subject. 
			int p = 0, q = 0, r = 0;
			for (int j = 0; j < rollNumber.size(); j++) {
				sevenColTable3.addCell(rollNumber.get(j)).setTextAlignment(TextAlignment.CENTER);
				for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
					if (subject.contains(entry.getKey())) {
						if (p == rollNumber.size() * mergedSubjects.size()) {
							break;
						}
						Subject value = entry.getValue();
						String[] marks = value.getMarks();
						if (value.getName() == null && marks[0].equals("N/A")) {
							sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
						} else {
							String prMarksStr = prMarksList2.get(p);
							p++;
							String nullString = "N/A";
							if (prMarksStr.equals(nullString)) {
								sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
							} else {
								sevenColTable3.addCell(prMarksStr).setTextAlignment(TextAlignment.CENTER);
							}
						}

						if (!marks[1].equalsIgnoreCase("N/A")) {
							sevenColTable3.addCell(thMarksList2.get(q)).setTextAlignment(TextAlignment.CENTER);
							q++;
						} else {
							sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
						}
						if (!marks[2].equalsIgnoreCase("N/A")) {
							sevenColTable3.addCell(iaMarksList2.get(r)).setTextAlignment(TextAlignment.CENTER);
							r++;
						} else {
							sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
						}
					}
				}
			}

			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				Subject value = entry.getValue();
				if(subject.contains(entry.getKey())) {
					if (value.getName() == null) {
						sevenColTable4.addCell(new Cell().add("_____________________").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add(subjectTeacherList.get(0)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
					}
					else {
						sevenColTable4.addCell(new Cell().add("_____________________").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add("_____________________").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
						sevenColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
						sevenColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
						sevenColTable4.addCell(new Cell().add(subjectTeacherList.get(0)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));			
						sevenColTable4.addCell(new Cell().add(subjectTeacherList.get(1)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
					}	
				}
			}
			
			
			
//			The tables are added into the PDF file through .add method.
			document.add(tableDivider1.setBorder(dbg));
			document.add(TitlePara.setBold().setFontSize(28).setUnderline().setTextAlignment(TextAlignment.CENTER));
			document.add(emptygraph);
			document.add(marksPara1.setBold().setFontSize(16).setUnderline().setTextAlignment(TextAlignment.CENTER));
			document.add(emptygraph);
			document.add(marksPara.setBold().setFontSize(14));
			document.add(emptygraph);
			document.add(sevenColTable1);
			document.add(sevenColTable2);
			document.add(sevenColTable3);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(emptygraph);
			document.add(sevenColTable4);
			
			document.close();

		}
		// Exception is Handled here.
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	public void wholePDF(ArrayList<MarksTO> marksList,String path, String stream, String sem, String year, String course) {
		try {
			// PDF Creation done here.
			
			PdfWriter pdfWriter = new PdfWriter(path);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(PageSize.A3.rotate());
			Document document = new Document(pdfDocument);

			// All Paper Title
			Set<String> paperSet = new HashSet<>();
			Set<String> rollNumberSet = new HashSet<>();

			for (MarksTO marksTO : marksList) {
				paperSet.add(marksTO.getPaperTitle());
			}
			ArrayList<String> paperList = new ArrayList<>(paperSet);
			ArrayList<String> mergedSubjects = mergeSubjects(paperList);
			ArrayList<String> paperName = new ArrayList<>();
			ArrayList<String> paperFullMarks = new ArrayList<>();
			ArrayList<String> uniqueFMValues = new ArrayList<>();
			List<String> uniquePTValues = getUniquePTValues();
			List<String> rollSort = new ArrayList<>();
			List<String> thMarksList = new ArrayList<>();
			List<String> iaMarksList = new ArrayList<>();
			List<String> prMarksList = new ArrayList<>();

			// Table Row and Column Size declared here.
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
			float fullWidth[] = { 1100f };

			// Table is generated from the previous generated Row and Column Sizes
			Table tableDivider1 = new Table(fullWidth);
			DashedBorder dbg = new DashedBorder(Color.GRAY, 0.5f);
			Paragraph TitlePara = new Paragraph("University Of Calcutta");
			Paragraph marksPara = new Paragraph("Marks Details: ");
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

			// Mapping One Paper Type with its full marks for individual Subjects.
			uniqueFMValues = getUniqueFMValues(marksList, mergedHash);
			Collections.sort(uniqueFMValues);

			// Paper Name and Full Marks are splitted here
			for (String paper : uniqueFMValues) {
				String[] parts = paper.split(":");
				paperName.add(parts[0]);
			}
			for (String paper : uniqueFMValues) {
				String[] parts = paper.split(":");
				paperFullMarks.add(parts[2]);
			}
//			Subjects and PT values for PDF printed here.			
			for (String subject : mergedSubjects) {
				sevenColTable1.addCell(new Cell().add(subject).setBold().setFontColor(Color.WHITE)
						.setTextAlignment(TextAlignment.CENTER));

				for (String paper : uniquePTValues) {
					sevenColTable2.addCell(new Cell().add(paper).setBold().setTextAlignment(TextAlignment.CENTER));
				}
			}
			sevenColTable2.addCell(new Cell().add("Student Details").setTextAlignment(TextAlignment.CENTER));

			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				Subject value = entry.getValue();
				String[] marks = value.getMarks();
				for (int i = 0; i < marks.length; i++) {
					sevenColTable2.addCell(new Cell().add(marks[i]).setBold().setTextAlignment(TextAlignment.CENTER));
				}
			}

			for (int j = 0; j < marksList.size(); j++) {
				MarksTO markRoll = marksList.get(j);
				String roll = markRoll.getColl() + "/" + markRoll.getCate() + "/" + markRoll.getNumber();
				rollNumberSet.add(roll);
			}

			List<String> rollNumber = new ArrayList<>(rollNumberSet);

			// Sorted Roll number List created here.
			for (MarksTO markRoll : marksList) {
				String rollStr = markRoll.getPaperTitle() + ":" + markRoll.getColl() + "/" + markRoll.getCate() + "/"
						+ markRoll.getNumber() + ":" + markRoll.getPaperType() + ":" + markRoll.getMarksObt();
				rollSort.add(rollStr);
			}
			Collections.sort(rollSort);

			// Sorted Roll Number list is splitted into three List to store three types of
			// Paper type values by splitting.
			String rollStr = "";
			for (String markRoll : rollSort) {
				String[] parts = markRoll.split(":");
				rollStr = parts[1];
				String paperTitle = parts[0];
				String paperType = parts[2];
				String marksObt = parts[3];
				if (paperType.equals("PR")) {
					prMarksList.add(rollStr + ":" + paperType + ":" + paperTitle + ":" + marksObt);
				} else if (paperType.equals("TH")) {
					thMarksList.add(rollStr + ":" + paperType + ":" + paperTitle + ":" + marksObt);
				} else if (paperType.equals("IA")) {
					iaMarksList.add(rollStr + ":" + paperType + ":" + paperTitle + ":" + marksObt);
				}
			}

			// If a certain Paper Type exists or not for a certain paper name of a student
			// is checked here.
			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				System.out.println(
						"Inside the LOOP : key : " + entry.getKey() + " value : " + entry.getValue().getName());
				String key = entry.getKey();
				Subject value = entry.getValue();
				String[] marks = value.getMarks();
				String compStrForPR = "";
				String compStrForIA = "";
				String compStrForTH = "";
				if (value.getName() != null) {
					compStrForPR = value.getName();
					System.out.println("PR Component : " + value.getName());
					if (!marks[1].equals("N/A")) {
						System.out.println("TH Component : " + key);
						compStrForTH = key;
					} else {
						compStrForTH = "9999";
					}
					if (!marks[2].equals("N/A")) {
						System.out.println("IA Component : " + key);
						compStrForIA = key;
					} else {
						compStrForIA = "9999";
					}

				} else if (value.getName() == null) {
					if (!marks[0].equals("N/A")) {
						System.out.println("Practical Component : " + key);
						compStrForPR = key;
						compStrForIA = "9999";
						compStrForTH = "9999";
					} else if (!marks[2].equals("N/A") && !marks[1].equals("N/A")) {
						System.out.println("TH Component : " + key);
						System.out.println("IA Component : " + key);
						compStrForPR = "9999";
						compStrForIA = key;
						compStrForTH = key;
					} else if (!marks[2].equals("N/A")) {
						System.out.println("IA Component : " + key);
						compStrForIA = key;
						compStrForPR = "9999";
						compStrForTH = "9999";
					} else {
						System.out.println("TH Component : " + key);
						compStrForIA = "9999";
						compStrForPR = "9999";
						compStrForTH = key;
					}

				}

				boolean PRF;
				boolean THF;
				boolean IAF;
// 				Whether the student has given exam of a unique paper type of an unique paper name is check here. If he/she didn't appeared The "N/A" is stored in the List 
				for (String markRoll : rollSort) {
					String[] parts = markRoll.split(":");
					rollStr = parts[1];
					PRF = false;
					if (!compStrForPR.equals("9999")) {
						for (String pr : prMarksList) {
							if (pr.contains(rollStr + ":" + "PR" + ":" + compStrForPR)) {
								PRF = true;
								break;
							}
						}
						if (PRF == false) {
							prMarksList.add(rollStr + ":" + "PR" + ":" + compStrForPR + ":" + "N/A");
						}
					}
					THF = false;
					if (!compStrForTH.equals("9999")) {
						for (String th : thMarksList) {
							if (th.contains(rollStr + ":" + "TH" + ":" + key)) {
								THF = true;
								break;
							}
						}
						if (THF == false) {
							thMarksList.add(rollStr + ":" + "TH" + ":" + key + ":" + "N/A");
						}
					}
					IAF = false;
					if (!compStrForIA.equals("9999")) {
						for (String ia : iaMarksList) {
							if (ia.contains(rollStr + ":" + "IA" + ":" + compStrForIA)) {
								IAF = true;
								break;
							}
						}
						if (IAF == false) {
							iaMarksList.add(rollStr + ":" + "IA" + ":" + compStrForIA + ":" + "N/A");
						}
					}
				}
			}

			Collections.sort(rollNumber);
			Collections.sort(thMarksList);
			Collections.sort(iaMarksList);
			Collections.sort(prMarksList);

			List<String> thMarksList2 = new ArrayList<>();
			List<String> iaMarksList2 = new ArrayList<>();
			List<String> prMarksList2 = new ArrayList<>();

			for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
				for (String mark : thMarksList) {
					String[] parts = mark.split(":");
					thMarksList2.add(parts[3]);
				}

				for (String mark : iaMarksList) {
					String[] parts = mark.split(":");
					iaMarksList2.add(parts[3]);
				}

				for (String mark : prMarksList) {
					String[] parts = mark.split(":");
					prMarksList2.add(parts[3]);
				}
			}

//			The Table is generated here with respect to student size and number of Subject. 
			int p = 0, q = 0, r = 0;
			for (int j = 0; j < rollNumber.size(); j++) {
				sevenColTable3.addCell(rollNumber.get(j)).setTextAlignment(TextAlignment.CENTER);
				for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
					if (p == rollNumber.size() * mergedSubjects.size()) {
						break;
					}
					Subject value = entry.getValue();
					String[] marks = value.getMarks();
					if (value.getName() == null && marks[0].equals("N/A")) {
						sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
					} else {
						String prMarksStr = prMarksList2.get(p);
						p++;
						String nullString = "N/A";
						if (prMarksStr.equals(nullString)) {
							sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
						} else {
							sevenColTable3.addCell(prMarksStr).setTextAlignment(TextAlignment.CENTER);
						}
					}

					if (!marks[1].equalsIgnoreCase("N/A")) {
						sevenColTable3.addCell(thMarksList2.get(q)).setTextAlignment(TextAlignment.CENTER);
						q++;
					} else {
						sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
					}
					if (!marks[2].equalsIgnoreCase("N/A")) {
						sevenColTable3.addCell(iaMarksList2.get(r)).setTextAlignment(TextAlignment.CENTER);
						r++;
					} else {
						sevenColTable3.addCell("N/A").setTextAlignment(TextAlignment.CENTER);
					}
				}
			}

//			The tables are added into the PDF file through .add method.
			document.add(tableDivider1.setBorder(dbg));
			document.add(TitlePara.setBold().setFontSize(32).setUnderline().setTextAlignment(TextAlignment.CENTER));
			document.add(emptygraph);
			document.add(marksPara1.setBold().setFontSize(18).setUnderline().setTextAlignment(TextAlignment.CENTER));
			document.add(emptygraph);
			document.add(marksPara.setBold().setFontSize(16));
			document.add(emptygraph);
			document.add(sevenColTable1);
			document.add(sevenColTable2);
			document.add(sevenColTable3);
			document.close();

		}
		// Exception is Handled here.
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<String> getUniquePTValues() {
		ArrayList<String> sortedSet = new ArrayList<>();
		sortedSet.add("PR");
		sortedSet.add("TH");
		sortedSet.add("IA");
		return sortedSet;
	}

// This funtion generates unique FM Values
	public ArrayList<String> getUniqueFMValues(List<MarksTO> marksList, TreeMap<String, Subject> mergedHash) {
		ArrayList<String> paperTypeToFullMarksMap = new ArrayList<String>();
		List<String> uniquePTValues = getUniquePTValues();
		for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
			String subject = entry.getKey();
			Subject Lab = entry.getValue();

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
		}
		return paperTypeToFullMarksMap;

	}

//  Maps paper type with its full marks as given.
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

	// All subject name is present in this mergeSubjects arrayList.
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

//----------------------------------------------------------------------------------------------------------------
//Printing HashMap
//System.out.println("Contents of the HashMap:");
//for (Map.Entry<String, Subject> entry : mergedHash.entrySet()) {
//	String key = entry.getKey();
//	Subject value = entry.getValue();
//	if (value.getName() == null) {
//		System.out.println("Key: " + key + ", Value: null");
//	} else {
//		System.out.println("Key: " + key + ", Value: " + value.getName() + ", FUll marks");
//		String[] marks = value.getMarks();
//		for (int i = 0; i < marks.length; i++) {
//			System.out.println(marks[i]);
//
//		}
//	}
//}

//--------

//System.out.println("Paper Type\tFull Marks");
//for (String paper : uniqueFMValues) {
//	System.out.println(paper);
//}

//----------

//for (String pr : prMarksList) {
//	System.out.println("PR List: " + pr);
//}
//for (String th : thMarksList) {
//	System.out.println("TH List: " + th);
//}
//for (String ia : iaMarksList) {
//	System.out.println("IA List: " + ia);
//}

//---------------
