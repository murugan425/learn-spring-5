/* Created by Murugan_Nagarajan on 9/14/2017 */
package tamil.learn.springframework.learnspringwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tamil.learn.springframework.learnspringwebapp.repository.BookRepository;

@Controller
public class BookController {
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Get all the books
     * @param model - spring obj in which we can add the data/pojo
     * @return String - viewName used to display the model
     */
    @RequestMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }
}
