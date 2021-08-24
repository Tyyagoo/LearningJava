package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.service.RecipesService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "${v1API}/recipe")
public class RecipeController {

    @Autowired
    RecipesService recipesService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        return recipesService.getRecipeById(id)
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Map<String, Integer>> createRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe newbyRecipe = recipesService.saveRecipe(recipe);
        return new ResponseEntity<>(Map.of("id", newbyRecipe.getId()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer id) {
        return recipesService.deleteRecipeById(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
