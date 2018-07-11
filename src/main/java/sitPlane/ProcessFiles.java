package sitPlane;

import java.io.File;
import java.util.Scanner;

public class ProcessFiles {
	static File inFile;

	public ProcessFiles(String in) {
		try {
			setInFile(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	public static File getInFile() {
		return inFile;
	}

	public static void setInFile(String inFile) throws Exception {

		if (inFile == null || inFile.length() == 0)
			throw new Exception("File name is not valid");
		if(!new File(inFile).isFile())
			throw new Exception(inFile +" is not a File");
		ProcessFiles.inFile = new File(inFile);
	}

	public void readFile() {
//		File fichero = new File("fichero_leer.txt");
		Scanner s = null;

		try {
			// Leemos el contenido del fichero
			System.out.println("... Leemos el contenido del fichero ...");
			s = new Scanner(inFile);

			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				String linea = s.nextLine(); 	// Guardamos la linea en un String
				String[] unidades = linea.split(" ");
//				System.out.println(linea);      // Imprimimos la linea
				for (String string : unidades) {
					System.out.println(string);
				}
//				System.out.println(unidades);
			}

		} catch (Exception ex) {
			System.out.println("Mensaje: " + ex.getMessage());
		} finally {
			// Cerramos el fichero tanto si la lectura ha sido correcta o no
			try {
				if (s != null)
					s.close();
			} catch (Exception ex2) {
				System.out.println("Mensaje 2: " + ex2.getMessage());
			}
		}
	}

}
