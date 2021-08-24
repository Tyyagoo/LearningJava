package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;

@RestController
@RequestMapping(path = "${v1API}/recipe")
public class RecipeController {
    Recipe recipe = new Recipe();

    @GetMapping
    public ResponseEntity<Recipe> getRecipe() {
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
        this.recipe = recipe;
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
