package animals.controller;

import animals.dto.animal.AnimalResponseDto;
import animals.dto.animal.AnimalSearchParamsRequestDto;
import animals.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Animals controller", description = "Endpoints for managing animals")
@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;

    @Operation(summary = "Get animal by id")
    @GetMapping("/{id}")
    public AnimalResponseDto getById(@PathVariable Long id) {
        return animalService.getById(id);
    }

    @Operation(summary = "Search animal by params")
    @GetMapping("/search")
    public List<AnimalResponseDto> search(
            @RequestBody AnimalSearchParamsRequestDto searchParams,
            Pageable pageable) {
        return animalService.search(searchParams, pageable);
    }
}
