package zoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.dto.animal.AnimalResponseDto;
import zoo.dto.animal.AnimalSearchParamsRequestDto;
import zoo.service.animal.AnimalService;

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
            @RequestBody @Valid AnimalSearchParamsRequestDto searchParams,
            Pageable pageable) {
        return animalService.search(searchParams, pageable);
    }
}
