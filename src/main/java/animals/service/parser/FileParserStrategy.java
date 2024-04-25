package animals.service.parser;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileParserStrategy {
    private final List<FileParser> fileParsers;

    public FileParser getFileParser(String fileType) {
        return fileParsers
                .stream()
                .filter(parser -> parser.isApplicable(fileType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't parse file type " + fileType));
    }
}
