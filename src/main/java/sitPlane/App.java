package sitPlane;

import java.io.File;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String fileName = args[0];
		try {
			File file = existFile("src\\main\\resources\\demo");
			readFile(file);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static File existFile(String fileName) throws Exception {
		File file = new File(fileName);
		if (file == null || file.length() == 0)
			throw new Exception("File name " + fileName + " is not valid");
		if (!file.isFile())
			throw new Exception(fileName + " is not a File");
		return file;
	}

	public static void readFile(File file) {
		// File fichero = new File("fichero_leer.txt");
		Scanner s = null;
		boolean itFirsLine = true;
		Integer rows = null;
		Integer seatsPerRow = null;
		Plane newPlane;
		try {
			// Leemos el contenido del fichero
			System.out.println("... Leemos el contenido del fichero ...");
			s = new Scanner(file);
			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				String linea = s.nextLine(); // Guardamos la linea en un String
				String[] data = linea.split(" ");
				// vamos a procesar linea a linea.
				if (itFirsLine) {
					itFirsLine = false;
					seatsPerRow = Integer.parseInt(data[0]);
					System.out.println("seatsPerRow " + seatsPerRow);
					rows = Integer.parseInt(data[1]);
					System.out.println("rows " + rows);
				} // queda por implementar que los datos obtenidos aqui sean validos.
					// create de plane
				else if (seatsPerRow != null && rows != null) {
					newPlane = new Plane(rows, seatsPerRow);
					///
					if (data.length == 1) {
						System.out.println("pasajero solitario");
						leerFila(data);
					} else {
						System.out.println("grupo");
						leerFila(data);
					}
				}

				// System.out.println(linea); // Imprimimos la linea
				// leerFila(data);
				// System.out.println(unidades);
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

	private static void leerFila(String[] data) {
		for (String string : data) {
			System.out.print(string);
			System.out.print("-");
		}
		System.out.println();
	}
}
