package animals.service;

import animals.dto.animal.AnimalResponseDto;
import animals.dto.animal.AnimalSearchParamsRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {

    List<AnimalResponseDto> upload(MultipartFile file);

    AnimalResponseDto getById(Long id);

    List<AnimalResponseDto> search(AnimalSearchParamsRequestDto searchParams, Pageable pageable);
}
