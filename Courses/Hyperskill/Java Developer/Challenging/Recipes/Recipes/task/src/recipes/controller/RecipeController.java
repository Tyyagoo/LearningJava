package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.service.RecipesService;
import recipes.service.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "${v1API}/recipe")
public class RecipeController {

    @Autowired
    RecipesService recipesService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        return recipesService.getRecipeById(id)
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Map<String, Integer>> createRecipe(@Valid @RequestBody Recipe recipe) {
        return userService.getUserByEmail(getUserEmailByContext()).map(u -> {
            recipe.setOwner(u);
            Recipe newbyRecipe = recipesService.saveRecipe(recipe);
            List<Recipe> recipes = new ArrayList<>();
            recipes.addAll(u.getRecipes());
            recipes.add(newbyRecipe);
            u.setRecipes(recipes);
            return new ResponseEntity<>(Map.of("id", newbyRecipe.getId()), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer id) {
        return userService.getUserByEmail(getUserEmailByContext())
                .map(user -> recipesService.getRecipeById(id)
                        .map(recipe -> {
                            if (recipe.getOwner().equals(user)) {
                                recipesService.deleteRecipeById(id);
                                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                            }
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Integer id, @Valid @RequestBody Recipe recipe) {
        return userService.getUserByEmail(getUserEmailByContext())
                .map(user -> recipesService.getRecipeById(id)
                        .map(r -> {
                            if (r.getOwner().equals(user)) {
                                recipe.setOwner(r.getOwner());
                                recipesService.updateRecipe(id, recipe);
                                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                            }
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String name) {
        if ((category != null && name != null) || (category == null && name == null))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(category != null ?
                recipesService.getAllByCategory(category) :
                recipesService.getAllByName(name), HttpStatus.OK);
    }

    private String getUserEmailByContext() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
        return userDetails.getUsername();
    }
}
