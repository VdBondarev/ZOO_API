package zoo.service.animal;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import zoo.dto.animal.AnimalResponseDto;
import zoo.dto.animal.AnimalSearchParamsRequestDto;

public interface AnimalService {

    List<AnimalResponseDto> upload(MultipartFile file);

    AnimalResponseDto getById(Long id);

    List<AnimalResponseDto> search(AnimalSearchParamsRequestDto searchParams, Pageable pageable);
}
