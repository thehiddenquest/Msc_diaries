package Model.main_package;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation {
	private static final String DB = "user"; 
	private static final String DB_URL = "jdbc:mysql://localhost:****/"+DB;
	private static final String USER = "****";
	private static final String PASSWORD = "****";

	public static void main(String[] args) {
		try {
			// Connect to the database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("Connected to the database.");

			// Check if the tables exist
			if (!tableExists(conn, DB, "studentdb")) {
				createStudentTable(conn);
				System.out.println("Created studentdb table.");
			} else {
				System.out.println("StudentDb is present");
			}
			if (!tableExists(conn,DB,"subjectdb")) {
				createSubjectTable(conn);
				System.out.println("Created subjectdb table.");
			} else {
				System.out.println("subjectdb is present");
			}
			if (!tableExists(conn,DB,"marksdb")) {
				createMarksTable(conn);
				System.out.println("Created marksdb table.");
			} else {
				System.out.println("marksdb is present");
			}
			if (!tableExists(conn,DB,"examdb")) {
				createExamTable(conn);
				System.out.println("Created examdb table.");
			} else {
				System.out.println("examdb is present");
			}
			if (!tableExists(conn,DB,"userlogin")) {
				createUserLoginTable(conn);
				System.out.println("Created userlogin table.");
			} else {
				System.out.println("userlogin is present");
			}

			// Close the connection
			conn.close();
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static boolean tableExists(Connection conn, String databaseName, String tableName) throws SQLException {
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet result = meta.getTables(databaseName,null, tableName, null); 
//		while(result.next()) {
//			String table = result.getString("TABLE_NAME");
//			  System.out.println(table);
//		}
////		if (tableName.equals("userlogin")) {
////			Statement stmt = conn.createStatement();
////			String sql = "drop table userlogin";
////			stmt.executeUpdate(sql);
////		}
////		else
////		{
////			System.out.println("NONE");
////		}
		return result.next();
	}

	private static void createStudentTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE studentdb (" + "coll varchar(8) NOT NULL," + "cate varchar(8) NOT NULL,"
				+ "number1 varchar(8) NOT NULL," + "PRIMARY KEY (coll,cate,number1)" + ")";
		stmt.executeUpdate(sql);
	}

	private static void createSubjectTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE subjectdb (" + "paper_code varchar(20) NOT NULL,"
				+ "paper_name varchar(50) DEFAULT NULL," + "teachers_name varchar(50) DEFAULT NULL,"
				+ "PRIMARY KEY (paper_code)" + ")";
		stmt.executeUpdate(sql);
	}

	private static void createMarksTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE marksdb (" + "paper_type varchar(8) NOT NULL,"
				+ "full_marks varchar(3) DEFAULT NULL," + "marks_obt varchar(6) DEFAULT NULL,"
				+ "coll varchar(8) NOT NULL," + "cate varchar(8) NOT NULL," + "number1 varchar(8) NOT NULL,"
				+ "paper_code varchar(20) NOT NULL," + "PRIMARY KEY (coll,cate,number1,paper_type,paper_code),"
				+ "KEY marksdb_ibfk_1 (paper_code),"
				+ "CONSTRAINT marksdb_ibfk_1 FOREIGN KEY (paper_code) REFERENCES subjectdb (paper_code),"
				+ "CONSTRAINT marksdb_ibfk_2 FOREIGN KEY (coll, cate, number1) REFERENCES studentdb (coll, cate, number1)"
				+ ")";
		stmt.executeUpdate(sql);
	}

	private static void createExamTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE examdb (" + "stream varchar(20) DEFAULT NULL," + "sem varchar(5) DEFAULT NULL,"
				+ "year varchar(4) DEFAULT NULL," + "subject varchar(50) DEFAULT NULL," + "coll varchar(8) NOT NULL,"
				+ "cate varchar(8) NOT NULL," + "number1 varchar(8) NOT NULL," + "paper_type varchar(8) NOT NULL,"
				+ "PRIMARY KEY (coll,cate,number1,paper_type),"
				+ "CONSTRAINT examdb_ibfk_1 FOREIGN KEY (coll, cate, number1, paper_type) REFERENCES marksdb (coll, cate, number1, paper_type)"
				+ ")";
		stmt.executeUpdate(sql);
	}

	private static void createUserLoginTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE userlogin (" + "EmailAddress varchar(90) NOT NULL," + "Salt varchar(90) NOT NULL,"
				+ "HashedPassword varchar(90) NOT NULL," + "UserName varchar(70) NOT NULL," + "PRIMARY KEY (UserName)"
				+ ")";
		stmt.executeUpdate(sql);
	}
}
