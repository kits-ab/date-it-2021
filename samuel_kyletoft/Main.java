import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Main {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(
				"Usage: java Main <municipalities> <schools>\n"
				+ "Prints to stdout so to store to a file redirect like this:\n"
				+ "java Main <municipalities> <schools> > <output>");
			return;
		}
		var municipalities_path = Paths.get(args[0]);
		var schools_path        = Paths.get(args[1]);

		// Treat the key as a string because it's not worth the performance
		// cost to parse the number since we're mostly just paying the cache
		// miss to access it anyway
		Map<String, String> municipalities;
		List<String> schools;
		try {
			municipalities = Files
				.readAllLines(municipalities_path)
				.stream()
				.map(line -> line.split(";"))
				.collect(Collectors.toMap(
					arr -> arr[0],
					arr -> arr[1],
					(prev, next) -> next, HashMap::new));
			schools = Files.readAllLines(schools_path);
		} catch (IOException e) {
			System.err.println("Error reading municipalities file: " + e);
			return;
		}

		for (var line : schools) {
			// Codes are padded out to 4 digits, so it's fine to hardcode
			// instead of actually finding the semicolon
			var code         = line.substring(0, 4);
			var municipality = municipalities.get(code);
			String out;

			if (municipality == null) {
				System.err.println("No municipality found for code " + code);
				out = line;
			} else {
				out = code + ";" + municipality + line.substring(4);
			}
			System.out.println(out);
		}
	}
}
