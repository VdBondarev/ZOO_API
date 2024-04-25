package animals.dto.animal;

import jakarta.validation.constraints.Size;
import java.util.List;

/**
 *
 * @param name - searching by this param will be like that: "%name%
 * @param type - searching by this param will be like that: "%type%
 * @param sex - searching by this param will be like that: "sex%
 *
 * @param weight - if you pass one element,
 *               then searching params will be from 0 to your element inclusively
 *               But, if you pass 2 elements,
 *               then searching params will be from the first element to the second inclusively
 *
 * @param cost - the same as with weight
 *
 * @param categoriesIds - searching by this param will be like that: "in (your categories)"
 */
public record AnimalSearchParamsRequestDto(
        String name,
        String type,
        String sex,
        @Size(min = 1, max = 2)
        List<Integer> weight,
        @Size(min = 1, max = 2)
        List<Integer> cost,
        List<Long> categoriesIds
) {
}
