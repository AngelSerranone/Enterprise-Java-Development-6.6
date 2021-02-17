package com.ironhack.bookService.controller.interfaces;

import com.ironhack.bookService.controller.dto.BookDto;
import com.ironhack.bookService.model.Book;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface IBookController {

    public BookDto getBook(String isbn);

    public Book registerNewBook(BookDto bookDto);
}
