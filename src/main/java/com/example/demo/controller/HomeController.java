package com.example.demo.controller;

import com.example.demo.models.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");

        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);

        return "home";
    }

    @GetMapping("/add")
    public String addNewBook(Model model){
        return "add";
    }
    @PostMapping(path="/add") // Map ONLY POST Requests
    public  String addBook (@RequestParam String title,
                            @RequestParam String genre, @RequestParam int year,
                            @RequestParam boolean availability, Model model) {
        Book n = new Book(title,genre,year,availability);
        bookRepository.save(n);
        return "redirect:/";
    }

    @GetMapping("/book/{id}")
    public String gameDetail(@PathVariable(value = "id") long id, Model model){
        if (!bookRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<Book> book = bookRepository.findAllById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);

        return "book_detail";
    }
}
