/* Created by Murugan_Nagarajan on 12/22/2017 */
package tamil.learn.springframework.learnspringrecipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private Recipe recipe;
    private String description;
}
