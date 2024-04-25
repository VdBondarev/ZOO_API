package animals.service.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileReaderStrategy {
    private final List<FileReader> fileReaders;

    public FileReader getFileReader(String fileType) {
        return fileReaders
                .stream()
                .filter(reader -> reader.isApplicable(fileType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't read file " + fileType));
    }
}
