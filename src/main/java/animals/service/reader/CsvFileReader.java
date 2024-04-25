package animals.service.reader;

import static animals.constants.FilesRelatedConstantsHolder.CSV;
import static animals.constants.NumbersConstantsHolder.REQUIRED_ARRAY_SIZE;
import static animals.constants.OtherConstantsHolder.EMPTY_STRING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.util.csv.CSVReader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CsvFileReader implements FileReader {

    @Override
    public List<String[]> readFromFile(MultipartFile file) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                if (record.length != REQUIRED_ARRAY_SIZE) {
                    continue;
                }
                if (Arrays.stream(record).toList().contains(EMPTY_STRING)) {
                    continue;
                }
                records.add(record);
            }
            return records;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from CSV file", e);
        }
    }

    @Override
    public boolean isApplicable(String fileType) {
        return fileType.equalsIgnoreCase(CSV);
    }
}
