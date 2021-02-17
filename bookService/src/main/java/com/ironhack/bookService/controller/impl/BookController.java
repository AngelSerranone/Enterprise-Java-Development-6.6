package com.ironhack.bookService.controller.impl;

import com.ironhack.bookService.client.BookFormatClient;
import com.ironhack.bookService.controller.dto.BookDto;
import com.ironhack.bookService.controller.dto.BookFormatDto;
import com.ironhack.bookService.controller.interfaces.IBookController;
import com.ironhack.bookService.model.Book;
import com.ironhack.bookService.repository.BookRepository;
import com.ironhack.bookService.service.impl.BookService;
import com.ironhack.bookService.service.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController implements IBookController {

    @Autowired
    private IBookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook (@PathVariable("isbn") String isbn) {

        return bookService.getBook(isbn);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book registerNewBook(@RequestBody @Valid BookDto bookDto){

        return bookService.registerNewBook(bookDto);
    }



}
