package com.github.zjjfly.readinglist.controller;

import com.github.zjjfly.readinglist.config.AmazonProperties;
import com.github.zjjfly.readinglist.model.Book;
import com.github.zjjfly.readinglist.model.Reader;
import com.github.zjjfly.readinglist.repository.ReadingListRepository;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zjjfly on 2017/7/2.
 */
@Controller
@RequestMapping("/readinglist")
public class ReadingListController {

    private SimpleMeterRegistry simpleMeterRegistry;

    private ReadingListRepository readingListRepository;

    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository, AmazonProperties amazonProperties) {
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }

    @GetMapping(value = "/{reader}")
    @PreAuthorize("hasRole('ROLE_READER')")
    public String readerBooks(@PathVariable String reader, Model model, @AuthenticationPrincipal Reader users) {
        List<Book> readingList = readingListRepository.findBooksByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", users);
            model.addAttribute("amazonID", amazonProperties.getAssociatedId());
        }
        return "readingList";
    }

    @PostMapping(value = "/{reader}")
    @PreAuthorize("hasRole('ROLE_READER')")
    public String addToReadingList(@PathVariable String reader, @Valid Book book, Errors errors) {
        if (!errors.hasErrors()) {
            book.setReader(reader);
            readingListRepository.save(book);
            simpleMeterRegistry.counter("book.saved");
            simpleMeterRegistry.gauge("book.last.saved", System.currentTimeMillis());
        }
        return "redirect:/readinglist/{reader}";
    }

}
