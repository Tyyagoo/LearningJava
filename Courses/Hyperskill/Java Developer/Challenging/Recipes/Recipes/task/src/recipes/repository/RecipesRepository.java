package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.model.Recipe;

import java.util.List;

public interface RecipesRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findDistinctByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
