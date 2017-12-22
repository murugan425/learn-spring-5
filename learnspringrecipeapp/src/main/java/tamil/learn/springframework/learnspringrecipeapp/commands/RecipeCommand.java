/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tamil.learn.springframework.learnspringrecipeapp.enums.Difficulty;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Byte[] image;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<IngredientCommand>();
    private Set<CategoryCommand> categories = new HashSet<CategoryCommand>();
}
