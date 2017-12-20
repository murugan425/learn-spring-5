/* Created by Murugan_Nagarajan on 9/25/2017 */
package tamil.learn.springframework.learnspringrecipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
@ToString(exclude = {"recipes"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryname;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
