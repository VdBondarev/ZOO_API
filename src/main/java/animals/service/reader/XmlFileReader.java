package animals.service.reader;

import static animals.constants.ConstantsHolder.SPLIT_XML_LINE_REGEX;
import static animals.constants.ConstantsHolder.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class XmlFileReader implements FileReader {

    @Override
    public List<String[]> readFromFile(MultipartFile file) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("<animal>")) {
                    List<String> record = new ArrayList<>();
                    while (!(line = reader.readLine().trim()).equalsIgnoreCase("</animal>")) {
                        String value = line.split(SPLIT_XML_LINE_REGEX)[1];
                        record.add(value);
                    }
                    if (record.size() == 5) {
                        records.add(record.toArray(new String[0]));
                    }
                }
            }
            return records;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from XML file", e);
        }
    }

    @Override
    public boolean isApplicable(String fileType) {
        return fileType.equalsIgnoreCase(XML);
    }
}
