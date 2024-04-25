package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import transfer_object.MarksTO;
import transfer_object.UserTO;

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
		if (item instanceof String) {
			String operation = (String) item;
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
					.prepareStatement("INSERT INTO subjectDB (paper_code, paper_name, teachers_name) VALUES (?, ?, ?)");
			PreparedStatement ps2 = connection
					.prepareStatement("INSERT INTO studentDB (coll, cate, number1) VALUES (?, ?, ?)");
			PreparedStatement ps3 = connection.prepareStatement(
					"INSERT INTO marksDB (paper_type, full_marks, marks_obt, coll, cate, number1, paper_code) VALUES (?, ?, ?, ?, ?, ?, ?)");
			PreparedStatement ps4 = connection.prepareStatement(
					"INSERT INTO examDB (stream, sem, year, subject, coll, cate, number1, paper_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

			String paper_code, paper_title, coll, cate, number, paper_type, full_marks, marks_obt, stream, sem, year,
					subject;
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

			String pc_pt = paper_code + paper_title;
			String number_com = coll + cate + number;
			String paper_type_com = paper_type + coll + cate + number;
			String exam_com = stream + sem + year + subject + coll + cate + number + paper_type;

			if (!uniqueCombinations.contains(pc_pt)) {
				ps1.setString(1, paper_code);
				ps1.setString(2, paper_title);
				ps1.setString(3, "Teacher's Name as given.");

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

	private T retrieveMarks(String operation) {
		
		if (operation.equals("2")) {
			String query = "SELECT e.coll, e.cate, e.number1, e.paper_type, e.stream, e.sem, m.full_marks,"
					+ " m.marks_obt, s.paper_code, s.paper_name, s.teachers_name FROM examDB e "
					+ "INNER JOIN marksDB m ON e.coll = m.coll AND e.cate = m.cate AND e.number1 = m.number1 AND e.paper_type = m.paper_type "
					+ "INNER JOIN subjectDB s ON m.paper_code = s.paper_code";
			try {
				rmarks = new ArrayList<>();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet result = preparedStatement.executeQuery();
				while(result.next()) {
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
					rmarks.add(marksTO);
				}
				return (T)rmarks;
			} catch (Exception e) {

			}
			return null;
		}
		return null;
	}
}