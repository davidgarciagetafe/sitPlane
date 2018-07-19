package sitPlane;

import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class App {

	private final static String windowValue = "W";
	static List<String> alonesNotWindow = new ArrayList<String>();
	static List<String> alonesWindow = new ArrayList<String>();

	static List<String[]> groups = new ArrayList<String[]>();

	static int[][] plane;

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
		// Map <Integer, String []> plane = new HashMap<Integer, String[]>();
		boolean itFirsLine = true;
		Integer rows = null;
		Integer seatsPerRow = null;
		// Plane newPlane;
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
					plane = new int[rows][seatsPerRow];
				} // queda por implementar que los datos obtenidos aqui sean validos.

				else if (plane != null) {

					// pasajeros solos
					if (data.length == 1) {
						System.out.println("pasajero solitario -" + data[0]);
						boolean windowTrue = windowOrNot(data[0]);
						clasified(data[0], windowTrue);
					} else {
						// procesar grupos
						groups.add(data);

					}
				}

			}
			System.out.println(groups.size());
			System.out.println("recopilados pasajeros");

			putPassengersInPlane();

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

	private static void clasified(String data, boolean windowTrue) {
		if (windowTrue) {
			alonesWindow.add(extractPassergerForWindow(data));
		} else {
			alonesNotWindow.add(data);
		}
	}

	private static String extractPassergerForWindow(String data) {
		return data.substring(0, data.length() - 1);
	}

	private static boolean windowOrNot(String data) {
		return (data.endsWith(windowValue)) ? true : false;

	}

	private static void putPassengersInPlane() {
		// TODO Auto-generated method stub
		// primero procesamos los grupos.
		for (String[] group : groups) {
			Map<String, String> clasified = new HashMap<String, String>();
			for (String passenger : group) {
				if (windowOrNot(passenger)) {
					clasified.put(extractPassergerForWindow(passenger), windowValue);
				} else {
					clasified.put(passenger, "N");
				}

			}
			System.out.println(clasified.values());
			clasified.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));
			if (clasified.containsValue(windowValue)) {
				clasified.forEach((k, v) -> sitWithWindow(k, v));
			} else {
				clasified.forEach((k, v) -> sit(k, v));
			}
		}
	}

	private static Object sitWithWindow(String k, String v) {
		// TODO Auto-generated method stub
boolean lineIsOcuppied=true;
Integer freeRow = null;
for (int x=0;x<plane.length && lineIsOcuppied;x++) {
	int[] row = plane[x];
	System.out.println("huecos-"+row.length);
}
		if (v.equals(windowValue)) {

		} else {

		}
		return null;
	}
	private static Object sit(String k, String v) {
		// TODO Auto-generated method stub

		return null;
	}

	private static void leerFila(String[] data) {
		for (String string : data) {
			System.out.print(string);
			System.out.print("-");
		}
		System.out.println();
	}
}
