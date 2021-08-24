package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.model.Recipe;

public interface RecipesRepository extends CrudRepository<Recipe, Integer> { }
