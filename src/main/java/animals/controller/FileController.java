package animals.controller;

import animals.dto.animal.AnimalResponseDto;
import animals.service.AnimalService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final AnimalService animalService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AnimalResponseDto> create(@RequestParam MultipartFile file) {
        return animalService.create(file);
    }
}
