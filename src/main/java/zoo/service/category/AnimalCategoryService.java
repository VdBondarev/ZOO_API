package zoo.service.category;

public interface AnimalCategoryService {

    Long getCategory(int cost);

    boolean isApplicable(int cost);
}
