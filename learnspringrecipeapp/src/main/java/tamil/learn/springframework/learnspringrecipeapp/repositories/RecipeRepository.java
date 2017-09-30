/* Created by murugan_nagarajan on 9/25/2017 */
package tamil.learn.springframework.learnspringrecipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
