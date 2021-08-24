package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipesRepository;

import java.util.Optional;

@Service
public class RecipesService {
    private final RecipesRepository repository;

    @Autowired
    public RecipesService(RecipesRepository repository){
        this.repository = repository;
    }

    public Optional<Recipe> getRecipeById(Integer id) {
        return repository.findById(id);
    }

    public boolean deleteRecipeById(Integer id) {
        return repository.findById(id).map(r -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return repository.save(recipe);
    }
}
