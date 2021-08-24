package recipes.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String name;
    private String description;
    private String ingredients;
    private String directions;
}
