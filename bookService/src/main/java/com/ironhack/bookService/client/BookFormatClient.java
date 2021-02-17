package com.ironhack.bookService.client;

import com.ironhack.bookService.controller.dto.BookDto;
import com.ironhack.bookService.controller.dto.BookFormatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient("bookFormat-service")
public interface BookFormatClient {

    @GetMapping("/format/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public String[] getBookFormat(@PathVariable String isbn);

    @PostMapping("/format")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBookFormat(@RequestBody @Valid BookFormatDto bookFormatDto);

}
