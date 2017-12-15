/* Created by Murugan_Nagarajan on 9/13/2017 */
package tamil.learn.springframework.learnspringwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import tamil.learn.springframework.learnspringwebapp.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
