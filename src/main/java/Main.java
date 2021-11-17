import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean elementary = true;
        boolean preschool = true;
        boolean afterSchoolCentre = true;

        Map<String, String> municipalities = parseMunicipalities("./kommuner.csv");
        Set<CSVRecord> schools = sortSchools("./skolverksamhet.csv", elementary, preschool, afterSchoolCentre);

        String[] HEADERS = {"Kod", "Skolenhetsnamn", "Grund-skola", "Förskole-klass", "Fritids-hem"};

        FileWriter out = new FileWriter("skolverksamhet_kommuner.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS).withDelimiter(';'))) {

            for (CSVRecord school : schools) {
                printer.printRecord(school.get(0),
                        municipalities.get(school.get(0)),
                        school.get(1),
                        school.get(2),
                        school.get(3),
                        school.get(4));
            }
        }
    }

    public static Set<CSVRecord> sortSchools(String fileName,
                                             boolean elementary,
                                             boolean preschool,
                                             boolean afterSchoolCentre) throws IOException {
        Reader in = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().withDelimiter(';').parse(in);
        Set<CSVRecord> hasElementary = new HashSet<>();
        Set<CSVRecord> hasPreschool = new HashSet<>();
        Set<CSVRecord> hasAfterSchoolCentre = new HashSet<>();
        Set<CSVRecord> result = new HashSet<>();

        for (CSVRecord record : records) {

            if (elementary && record.get("Grund-skola").equals("Ja")) {
                hasElementary.add(record);
            }

            if (preschool && record.get("Förskole-klass").equals("Ja")) {
                hasPreschool.add(record);
            }

            if (afterSchoolCentre && record.get("Fritids-hem").equals("Ja")) {
                hasAfterSchoolCentre.add(record);
            }

            result.add(record);
        }

        if (elementary) {
            result.retainAll(hasElementary);
        }

        if (preschool) {
            result.retainAll(hasPreschool);
        }

        if (afterSchoolCentre) {
            result.retainAll(hasAfterSchoolCentre);
        }

        return result;
    }

    public static Map<String, String> parseMunicipalities(String fileName) throws IOException {
        Map<String, String> codeToMunicipality = new HashMap<>();
        Reader in = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().withDelimiter(';').parse(in);

        while (records.iterator().hasNext()) {
            CSVRecord current = records.iterator().next();
            codeToMunicipality.put(current.get(0), current.get(1));
        }

        return codeToMunicipality;
    }

}
