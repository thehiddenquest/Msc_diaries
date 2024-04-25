package ems_package;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileRepository implements StorageRepository {
	public void store(BaseEntity entiy, String Filename) {

		try {

			FileOutputStream fileOut = new FileOutputStream(Filename);

			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

			objectOut.writeObject(entiy);

			objectOut.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	@Override
	public Object getData(String filename) {
		Object obj = null;
		try
	       {
	          FileInputStream fileIn =new FileInputStream(filename);
	          ObjectInputStream in = new ObjectInputStream(fileIn);
	          try {
				obj = in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          in.close();
	          fileIn.close();
	       }catch(IOException i)
	       {
	          i.printStackTrace();
	          return null;
	       }
		return obj;
	}
}
