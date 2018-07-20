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
	// static List<String> alonesNotWindow = new ArrayList<String>();
	static List<String> alones = new ArrayList<String>();

	static List<String[]> groups = new ArrayList<String[]>();

	static String[][] plane;

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
					plane = new String[rows][seatsPerRow];
				} // queda por implementar que los datos obtenidos aqui sean validos.

				else if (plane != null) {

					// pasajeros solos
					if (data.length == 1) {
						alones.add(data[0]);
						System.out.println("pasajero solitario -" + data[0]);
						// boolean windowTrue = windowOrNot(data[0]);
						// clasified(data[0], windowTrue);
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

	// private static void clasified(String data, boolean windowTrue) {
	// if (windowTrue) {
	// alonesWindow.add(extractPassergerForWindow(data));
	// } else {
	// alonesNotWindow.add(data);
	// }
	// }

	private static String extractPassergerForWindow(String data) {
		return data.substring(0, data.length() - 1);
	}

	private static boolean windowOrNot(String data) {
		return (data.endsWith(windowValue)) ? true : false;

	}

	private static void putPassengersInPlane() {
		// TODO Auto-generated method stub
		// primero procesamos los grupos.
		putGroupsInPlane();
		/// vamos a mover los pasajeros que quieren ventanilla
		relocateRows();
		//////
		System.out.println("relocate");
		///// vamos a poner los solitarios
		int numberOfAlone = 0;
		while (alones.size() > 0) {
			String passengerForPosition = alones.remove(0);
			for (int x = 0; x < plane.length && passengerForPosition != null; x++) {
				String[] row = plane[x];

				for (int y = 0; y < row.length && passengerForPosition != null; y++) {

					if (row[y] == null) {
						if (passengerForPosition.endsWith(windowValue)) {
							if (!row[0].endsWith(windowValue)) {
								String passengerMove = row[0];
								row[0] = passengerForPosition;
								row[y] = passengerMove;
								passengerForPosition = null;

							}
						} else {
							row[y] = passengerForPosition;
							passengerForPosition = null;
						}

						numberOfAlone++;
					}
				}
			}
		}

		/// vamos a mover los pasajeros que quieren ventanilla
		relocateRows();
		//////

		verFilas();
	}

	private static void putGroupsInPlane() {
		int groupForPosition = 0;
		for (int x = 0; x < plane.length; x++) {
			String[] row = plane[x];
			String[] grupo = groups.get(groupForPosition);
			int occupiedseats = 0;
			int asiento;
			boolean otroGrupo = false;
			for (int passenger = 0; passenger < grupo.length; passenger++) {
				asiento = passenger + occupiedseats;
				row[asiento] = grupo[passenger];
				if (((groupForPosition + 1) < groups.size())
						&& (grupo.length + groups.get(groupForPosition + 1).length) <= row.length
						&& passenger + 1 == grupo.length) {

					occupiedseats = grupo.length;
					groupForPosition++;
					passenger = -1;
					grupo = groups.get(groupForPosition);
				}
			}
			groupForPosition++;
		}
	}

	private static void relocateRows() {
		String pasajeroActual = null;
		String pasajeroForRelocate = null;
		for (String[] row : plane) {
			for (int x = 0; x < row.length; x++) {
				pasajeroActual = row[x];
				if (pasajeroActual != null && pasajeroActual.endsWith(windowValue)) {
					pasajeroForRelocate = pasajeroActual;
					row[x] = null;
					while (pasajeroForRelocate != null) {
						if (row[0] == null) {
							row[0] = pasajeroForRelocate;
							System.out.println("primera ventanilla vacia colocado pasajero");
							pasajeroForRelocate = null;
						} else if (!row[0].endsWith(windowValue)) {
							row[x] = row[0];
							row[0] = pasajeroForRelocate;
							System.out.println("ultima ventanilla vacia colocado pasajero");
							pasajeroForRelocate = null;
						} else {
							if (row[row.length - 1] == null) {
								row[row.length - 1] = pasajeroForRelocate;
								pasajeroForRelocate = null;
							} else if (row[row.length - 1] != null && !row[row.length - 1].endsWith(windowValue)) {
								pasajeroActual = row[row.length - 1];
								row[row.length - 1] = pasajeroForRelocate;
								row[x] = pasajeroActual;
								pasajeroForRelocate = null;
							}
						}

					}

				}
			}
		}
	}

	private static void verFilas() {
		int x = 1;
		for (String[] row : plane) {
			System.out.println("fila " + x);
			for (String seatNumber : row) {
				System.out.print(seatNumber + "-");
			}
			System.out.println(" libre-" + row.length);
			x++;
		}
		System.out.println();
	}

	private static Object sitWithWindow(String k, String v) {
		// TODO Auto-generated method stub
		boolean lineIsOcuppied = true;
		Integer freeRow = null;
		for (int x = 0; x < plane.length && lineIsOcuppied; x++) {
			String[] row = plane[x];
			System.out.println("huecos-" + row.length);
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
