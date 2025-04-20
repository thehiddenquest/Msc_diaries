package Model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Model.transfer_object.MarksTO;
import Model.transfer_object.UserTO;

public class MYSQLRepository<T> extends storageRepository<T> {

	private DatabaseConnection DC = null;
	private Connection connection = null;
	private ArrayList<MarksTO> rmarks = null;
	private Set<String> uniqueCombinations = null;

	@Override
	public boolean store(T item) {
		DC = DatabaseConnection.createInstance();
		connection = DC.createConnection();
		boolean flag = false;
		if (item instanceof UserTO) {
			UserTO user = (UserTO) item;
			System.out.println(user.getOperation());
			if (user.getOperation() == 1) {
				flag = insertUser(user.getEmailID(), user.getSalt(), user.getPassword(), user.getUsername());
			} else {
				flag = alterUser(user.getEmailID(), user.getSalt(), user.getPassword(), user.getUsername());
			}
			DC.stopConnection();
			return flag;
		}
		if (item instanceof ArrayList<?>) {
			ArrayList<?> itemList = (ArrayList<?>) item;
			for (Object obj : itemList) {
				if (obj instanceof MarksTO) {
					MarksTO marks = (MarksTO) obj;
					flag = insertMarks(marks);
					if (!flag) {
						DC.stopConnection();
						return false;
					}
				}
			}
			DC.stopConnection();
			return flag;
		} else {
			DC.stopConnection();
			return false;
		}
	}

	@Override
	public T retrive(T item) {
		DC = DatabaseConnection.createInstance();
		connection = DC.createConnection();
		if (item instanceof UserTO) {
			UserTO user = (UserTO) item;
			T returnUser = authenticateUser(user.getUsername());
			DC.stopConnection();
			return returnUser;
		}
		if (item instanceof String[]) {
			String[] operation = (String[]) item;
			T returnmarks = retrieveMarks(operation);
			DC.stopConnection();
			return returnmarks;
		}
		DC.stopConnection();
		return null;
	}

	@Override
	public boolean remove(T item) {
		DC = DatabaseConnection.createInstance();
		connection = DC.createConnection();
		if (item instanceof UserTO) {
			UserTO user = (UserTO) item;
			boolean flag = deleteUser(user.getUsername());
			DC.stopConnection();
			return flag;
		}
		DC.stopConnection();
		return false;
	}

	private boolean insertUser(String... user) {
		String query = "INSERT INTO userlogin (EmailAddress,Salt, HashedPassword, UserName) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user[0]);
			preparedStatement.setString(2, user[1]);
			preparedStatement.setString(3, user[2]);
			preparedStatement.setString(4, user[3]);

			int rowsAffected = 0;
			try {
				rowsAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Primary key overlap");
				e.printStackTrace();
			}

			if (rowsAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private T authenticateUser(String userName) {
		String query = "SELECT * FROM userlogin WHERE UserName = ?";
		UserTO user = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String emailAddress = resultSet.getString("EmailAddress");
				String salt = resultSet.getString("Salt");
				String hashedPassword = resultSet.getString("HashedPassword");
				userName = resultSet.getString("UserName");
				user = new UserTO(userName, hashedPassword, salt, emailAddress);
			}
			return (T) user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean deleteUser(String userName) {
		String query = "DELETE FROM userlogin WHERE UserName = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			int rowsAffected = 0;
			try {
				rowsAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Primary key overlap");
				e.printStackTrace();
			}

			if (rowsAffected == 1) {
				// System.out.println("Data uploaded successfully.");
				return true;
			} else {
				// System.out.println("Failed to upload data.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean alterUser(String... user) {
		String query = "UPDATE userlogin SET HashedPassword = ?,Salt = ? WHERE UserName = ?;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user[2]);
			preparedStatement.setString(2, user[1]);
			preparedStatement.setString(3, user[3]);
			int rowsAffected = 0;
			try {
				rowsAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Primary key overlap");
				e.printStackTrace();
			}

			if (rowsAffected == 1) {
				// System.out.println("Data uploaded successfully.");
				return true;
			} else {
				// System.out.println("Failed to upload data.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean insertMarks(MarksTO marks) {
		try {
			uniqueCombinations = new HashSet<>();
			uniqueCombinations.clear();
			PreparedStatement ps1 = connection
					.prepareStatement("INSERT INTO subjectdb (paper_code, paper_name, teachers_name) VALUES (?, ?, ?)");
			PreparedStatement ps2 = connection
					.prepareStatement("INSERT INTO studentdb (coll, cate, number1) VALUES (?, ?, ?)");
			PreparedStatement ps3 = connection.prepareStatement(
					"INSERT INTO marksdb (paper_type, full_marks, marks_obt, coll, cate, number1, paper_code) VALUES (?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement ps4 = connection.prepareStatement(
					"INSERT INTO examdb (stream, sem, year, subject, coll, cate, number1, paper_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

			String paper_code, paper_title, coll, cate, number, paper_type, full_marks, marks_obt, stream, sem, year,
					subject, teacherName;
			paper_code = marks.getPaperCode();
			paper_title = marks.getPaperTitle();
			coll = marks.getColl();
			cate = marks.getCate();
			number = marks.getNumber();
			paper_type = marks.getPaperType();
			full_marks = marks.getFullMarks();
			marks_obt = marks.getMarksObt();
			stream = marks.getStream();
			sem = marks.getSem();
			year = marks.getYear();
			subject = marks.getSubject();
			teacherName = marks.getTeachersName();

			String pc_pt = paper_code + paper_title;
			String number_com = coll + cate + number;
			String paper_type_com = paper_type + coll + cate + number + paper_code;
			String exam_com = stream + sem + year + subject + coll + cate + number + paper_type + paper_code;

			if (!uniqueCombinations.contains(pc_pt)) {
				ps1.setString(1, paper_code);
				ps1.setString(2, paper_title);
				ps1.setString(3, teacherName);

				try {
					ps1.executeUpdate();
				} catch (SQLException e) {
				}

				uniqueCombinations.add(pc_pt);
			}

			if (!uniqueCombinations.contains(number_com)) {
				ps2.setString(1, coll);
				ps2.setString(2, cate);
				ps2.setString(3, number);
				try {
					ps2.executeUpdate();
				} catch (SQLException e) {
				}

				uniqueCombinations.add(number_com);

			}

			if (!uniqueCombinations.contains(paper_type_com)) {
				ps3.setString(1, paper_type);
				ps3.setString(2, full_marks);
				ps3.setString(3, marks_obt);
				ps3.setString(4, coll);
				ps3.setString(5, cate);
				ps3.setString(6, number);
				ps3.setString(7, paper_code);
				try {
					ps3.executeUpdate();
				} catch (SQLException e) {
				}

			}

			if (!uniqueCombinations.contains(exam_com)) {
				ps4.setString(1, stream);
				ps4.setString(2, sem);
				ps4.setString(3, year);
				ps4.setString(4, subject);
				ps4.setString(5, coll);
				ps4.setString(6, cate);
				ps4.setString(7, number);
				ps4.setString(8, paper_type);
				try {
					ps4.executeUpdate();
				} catch (SQLException e) {
				}

				uniqueCombinations.add(number_com);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private T retrieveMarks(String[] operation) {

		if (operation[0].equals("2")) {
			String query = "SELECT e.coll, e.cate, e.number1, e.paper_type, e.stream, e.sem,  e.year, e.subject, m.full_marks,"
					+ " m.marks_obt, s.paper_code, s.paper_name, s.teachers_name FROM examDB e "
					+ "INNER JOIN marksDB m ON e.coll = m.coll AND e.cate = m.cate AND e.number1 = m.number1 AND e.paper_type = m.paper_type "
					+ "INNER JOIN subjectDB s ON m.paper_code = s.paper_code WHERE \n" + "    e.stream = ? \n"
					+ "    AND e.sem = ? \n" + "    AND e.year = ? \n" + "    AND e.subject = ?;";
			try {
				rmarks = new ArrayList<>();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, operation[1]);
				preparedStatement.setString(2, operation[2]);
				preparedStatement.setString(3, operation[3]);
				preparedStatement.setString(4, operation[4]);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next()) {
					MarksTO marksTO = new MarksTO();
					marksTO.setColl(result.getString("coll"));
					marksTO.setCate(result.getString("cate"));
					marksTO.setNumber(result.getString("number1"));
					marksTO.setPaperType(result.getString("paper_type"));
					marksTO.setStream(result.getString("stream"));
					marksTO.setSem(result.getString("sem"));
					marksTO.setFullMarks(result.getString("full_marks"));
					marksTO.setMarksObt(result.getString("marks_obt"));
					marksTO.setPaperCode(result.getString("paper_code"));
					marksTO.setPaperTitle(result.getString("paper_name"));
					marksTO.setTeachersName(result.getString("teachers_name"));
					marksTO.setYear(result.getString("year"));
					marksTO.setSubject(result.getString("subject"));
					rmarks.add(marksTO);
				}
				return (T) rmarks;
			} catch (Exception e) {

			}
			return null;
		}

		// highest marks in a subject
		if (operation[0].equals("1")) {
			String query = "SELECT coll, cate, number1, highest_total_marks\n"
					+ "FROM (\n"
					+ "    SELECT e.coll, e.cate, e.number1, SUM(m.marks_obt) AS total_marks\n"
					+ "    FROM examdb e\n"
					+ "    INNER JOIN marksdb m ON e.coll = m.coll \n"
					+ "        AND e.cate = m.cate \n"
					+ "        AND e.number1 = m.number1 \n"
					+ "        AND e.paper_type = m.paper_type \n"
					+ "    INNER JOIN subjectdb s ON m.paper_code = s.paper_code \n"
					+ "    WHERE e.stream = ? \n"
					+ "        AND e.sem = ? \n"
					+ "        AND e.year = ? \n"
					+ "        AND e.subject = ? \n"
					+ "        AND s.paper_name = ? \n"
					+ "    GROUP BY e.coll, e.cate, e.number1\n"
					+ ") AS marks_sum\n"
					+ "JOIN (\n"
					+ "    SELECT MAX(total_marks) AS highest_total_marks\n"
					+ "    FROM (\n"
					+ "        SELECT SUM(m.marks_obt) AS total_marks\n"
					+ "        FROM examdb e\n"
					+ "        INNER JOIN marksdb m ON e.coll = m.coll \n"
					+ "            AND e.cate = m.cate \n"
					+ "            AND e.number1 = m.number1 \n"
					+ "            AND e.paper_type = m.paper_type \n"
					+ "        INNER JOIN subjectdb s ON m.paper_code = s.paper_code \n"
					+ "        WHERE e.stream = ? \n"
					+ "            AND e.sem = ? \n"
					+ "            AND e.year = ? \n"
					+ "            AND e.subject = ? \n"
					+ "            AND s.paper_name = ? \n"
					+ "        GROUP BY e.coll, e.cate, e.number1\n"
					+ "    ) AS max_marks\n"
					+ ") AS max_total_marks ON marks_sum.total_marks = max_total_marks.highest_total_marks;\n"
					+ "";
			try {
				rmarks = new ArrayList<>();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, operation[1]);
				preparedStatement.setString(2, operation[2]);
				preparedStatement.setString(3, operation[3]);
				preparedStatement.setString(4, operation[4]);
				preparedStatement.setString(5, operation[5]);
				preparedStatement.setString(6, operation[1]);
				preparedStatement.setString(7, operation[2]);
				preparedStatement.setString(8, operation[3]);
				preparedStatement.setString(9, operation[4]);
				preparedStatement.setString(10, operation[5]);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next()) {
					String coll = result.getString("coll");
				    String cate = result.getString("cate");
				    String number1 = result.getString("number1");
				    String marksObt = result.getString("highest_total_marks");
				    MarksTO marksTO = new MarksTO();
				    marksTO.setColl(coll);
				    marksTO.setCate(cate);
				    marksTO.setNumber(number1);
				    marksTO.setMarksObt(marksObt);

				    rmarks.add(marksTO);
				}
				
				return (T) rmarks;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		// highest marks in a sem
		if (operation[0].equals("22")) {
			String query = "SELECT coll, cate, number1, highest_total_marks\n" + "FROM (\n"
					+ "    SELECT e.coll, e.cate, e.number1, SUM(m.marks_obt) AS total_marks\n" + "    FROM examdb e\n"
					+ "    INNER JOIN marksdb m ON e.coll = m.coll \n" + "        AND e.cate = m.cate \n"
					+ "        AND e.number1 = m.number1 \n" + "        AND e.paper_type = m.paper_type \n"
					+ "    INNER JOIN subjectdb s ON m.paper_code = s.paper_code \n" + "    WHERE e.stream = ? \n"
					+ "        AND e.sem = ? \n" + "        AND e.year = ? \n" + "        AND e.subject = ? \n"
				    + "    GROUP BY e.coll, e.cate, e.number1\n"
					+ ") AS marks_sum\n" + "JOIN (\n" + "    SELECT MAX(total_marks) AS highest_total_marks\n"
					+ "    FROM (\n" + "        SELECT SUM(m.marks_obt) AS total_marks\n" + "        FROM examdb e\n"
					+ "        INNER JOIN marksdb m ON e.coll = m.coll \n" + "            AND e.cate = m.cate \n"
					+ "            AND e.number1 = m.number1 \n" + "            AND e.paper_type = m.paper_type \n"
					+ "        INNER JOIN subjectdb s ON m.paper_code = s.paper_code \n"
					+ "        WHERE e.stream = ? \n" + "            AND e.sem = ? \n" + "            AND e.year = ? \n"
					+ "            AND e.subject = ? \n" 
					+ "        GROUP BY e.coll, e.cate, e.number1\n" + "    ) AS max_marks\n"
					+ ") AS max_total_marks ON marks_sum.total_marks = max_total_marks.highest_total_marks;";
			try {
				rmarks = new ArrayList<>();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, operation[1]);
				preparedStatement.setString(2, operation[2]);
				preparedStatement.setString(3, operation[3]);
				preparedStatement.setString(4, operation[4]);
				preparedStatement.setString(5, operation[1]);
				preparedStatement.setString(6, operation[2]);
				preparedStatement.setString(7, operation[3]);
				preparedStatement.setString(8, operation[4]);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next()) {
					MarksTO marksTO = new MarksTO();
					marksTO.setColl(result.getString("coll"));
					marksTO.setCate(result.getString("cate"));
					marksTO.setNumber(result.getString("number1"));
					marksTO.setMarksObt(result.getString("highest_total_marks"));
					rmarks.add(marksTO);
				}
				return (T) rmarks;
			} catch (Exception e) {
				e.printStackTrace();

			}
			return null;
		}

		// average marks in a subject
		if (operation[0].equals("3")) {
			String query = "SELECT AVG(total_marks) AS average_total_marks\n" + "FROM (\n"
					+ "    SELECT SUM(marks_obt) AS total_marks\n" + "    FROM examdb e\n"
					+ "    INNER JOIN marksdb m ON e.coll = m.coll \n" + "        AND e.cate = m.cate \n"
					+ "        AND e.number1 = m.number1 \n" + "        AND e.paper_type = m.paper_type \n"
					+ "    INNER JOIN subjectdb s ON m.paper_code = s.paper_code \n" + "    WHERE e.stream = ? \n"
					+ "        AND e.sem = ? \n" + "        AND e.year = ?  AND e.subject = ? \n"
					+ "        AND s.paper_name = ? \n" + "    GROUP BY e.coll, e.cate, e.number1\n"
					+ ") AS marks_sum;";
			try {
				rmarks = new ArrayList<>();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, operation[1]);
				preparedStatement.setString(2, operation[2]);
				preparedStatement.setString(3, operation[3]);
				preparedStatement.setString(4, operation[4]);
				preparedStatement.setString(5, operation[5]);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next()) {
					MarksTO marksTO = new MarksTO();
					marksTO.setMarksObt(result.getString("average_total_marks"));
					rmarks.add(marksTO);
				}
				return (T) rmarks;
			} catch (Exception e) {

			}
			return null;
		}

		// average marks in a sem
		if (operation[0].equals("4")) {
			String query = "SELECT AVG(total_marks) AS average_total_marks\n"
					+ "FROM (\n"
					+ "    SELECT SUM(marks_obt) AS total_marks\n"
					+ "    FROM examdb e\n"
					+ "    INNER JOIN marksdb m ON e.coll = m.coll \n"
					+ "        AND e.cate = m.cate \n"
					+ "        AND e.number1 = m.number1 \n"
					+ "        AND e.paper_type = m.paper_type \n"
					+ "    INNER JOIN subjectdb s ON m.paper_code = s.paper_code \n"
					+ "    WHERE e.stream = ? \n"
					+ "        AND e.sem = ? \n"
					+ "        AND e.year = ? AND e.subject = ? \n"
					+ "    GROUP BY e.coll, e.cate, e.number1\n"
					+ ") AS marks_sum;";
			try {
				rmarks = new ArrayList<>();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, operation[1]);
				preparedStatement.setString(2, operation[2]);
				preparedStatement.setString(3, operation[3]);
				preparedStatement.setString(4, operation[4]);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next()) {
					MarksTO marksTO = new MarksTO();
					marksTO.setMarksObt(result.getString("average_total_marks"));
					rmarks.add(marksTO);
				}
				return (T) rmarks;
			} catch (Exception e) {

			}
			return null;
		}
		return null;
	}
}