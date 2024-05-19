package DAO;

import java.util.ArrayList;

import repository.MYSQLRepository;
import repository.marksRepository;
import transfer_object.MarksTO;

public class MarksDAO {
	private MYSQLRepository<Object> mysqlrepository = new MYSQLRepository<>();
	private marksRepository marksrepository = new marksRepository(mysqlrepository);
	private ArrayList<MarksTO> marksList = new ArrayList<>();
	private ArrayList<String> cells = null;

	public boolean insertDataBase(String[][] data) {
		for (String[] row : data) {
			cells = new ArrayList<String>();
			for (String cell : row) {
				cells.add(cell);
			}
			MarksTO marksTO = new MarksTO(cells.get(0), cells.get(1), cells.get(2), cells.get(3), cells.get(4),
					cells.get(5), cells.get(6), cells.get(7), cells.get(8), cells.get(9), cells.get(10), cells.get(11));
			marksList.add(marksTO);
		}
		boolean flag = marksrepository.save(marksList);
		return flag;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<MarksTO> retriveDataBase(String[] operation) {
		ArrayList<MarksTO> marks = (ArrayList<MarksTO>) marksrepository.load(operation);
		return marks;
	}
}
