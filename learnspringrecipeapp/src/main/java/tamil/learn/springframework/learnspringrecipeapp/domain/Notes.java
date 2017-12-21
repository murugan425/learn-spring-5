/* Created by Murugan_Nagarajan on 9/24/2017 */
package tamil.learn.springframework.learnspringrecipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
@ToString(exclude = {"recipe"})
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String description;

}
