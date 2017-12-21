/* Created by murugan_nagarajan on 9/25/2017 */
package tamil.learn.springframework.learnspringrecipeapp.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tamil.learn.springframework.learnspringrecipeapp.domain.Recipe;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
}
