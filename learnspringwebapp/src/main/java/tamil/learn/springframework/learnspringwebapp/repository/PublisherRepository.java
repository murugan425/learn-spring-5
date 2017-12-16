/* Created by Murugan_Nagarajan on 9/14/2017 */
package tamil.learn.springframework.learnspringwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import tamil.learn.springframework.learnspringwebapp.model.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
