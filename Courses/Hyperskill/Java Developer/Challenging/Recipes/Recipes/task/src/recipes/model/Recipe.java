package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    @OrderColumn
    private List<String> ingredients;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    @OrderColumn
    private List<String> directions;

    @NotBlank
    private String category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;
}
