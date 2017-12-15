/* Created by Murugan_Nagarajan on 9/13/2017 */
package tamil.learn.springframework.learnspringwebapp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authorId;
    private String authorName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String authorName) {
        this.authorName = authorName;
    }

    public Author(Integer authorId, String authorName, Set<Book> books) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.books = books;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return authorId != null ? authorId.equals(author.authorId) : author.authorId == null;
    }

    @Override
    public int hashCode() {
        return authorId != null ? authorId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", books=" + books +
                '}';
    }
}
