package animals.controller;

import animals.dto.animal.AnimalResponseDto;
import animals.service.animal.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Files controller",
        description = "An endpoint for uploading a file with animal entity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final AnimalService animalService;

    @Operation(summary = "Upload endpoint",
            description = """
                    Upload a file with animal entities for saving them to a database.
                    If some animals do not have all required params, they will not be saved.
                    If non-valid params for animals are passed, they will not be saved as well
                    Allowed for admins only
                    """)
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AnimalResponseDto> upload(@RequestParam MultipartFile file) {
        return animalService.upload(file);
    }
}
