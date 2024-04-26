package zoo.service.reader;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileReader {

    List<String[]> readFromFile(MultipartFile file);

    boolean isApplicable(String fileType);
}
