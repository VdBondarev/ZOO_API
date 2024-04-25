package animals.service;

import animals.dto.animal.AnimalResponseDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {
    List<AnimalResponseDto> create(MultipartFile file);
}
