package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileConnector<T> {
	public void writeToFile(String filePath, List<T> data) throws IOException {
		File fileName = new File(new File("").getAbsolutePath().concat(filePath));
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(data);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> readFromFile(String filePath) throws IOException, ClassNotFoundException {
		File file = new File(new File("").getAbsolutePath().concat(filePath));
		if (!file.exists() || file.length() == 0) {
			return new ArrayList<>();
		}
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
			return (List<T>) ois.readObject();
		}
	}

	public void appendObject(String fileName, T newObject) throws IOException, ClassNotFoundException {
		List<T> objects = readFromFile(fileName);
		objects.add(newObject);
		writeToFile(fileName, objects);
	}
}
