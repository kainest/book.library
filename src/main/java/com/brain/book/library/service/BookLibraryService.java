package com.brain.book.library.service;

import com.brain.book.library.dao.AuthorRepository;
import com.brain.book.library.dao.BookRepository;
import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GanreEnum;
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.List;
import java.util.Set;


public class BookLibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookLibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void addNewBook(Book book) {
        Author author = book.getAuthor();
        if (author == null) {
            throw new RuntimeException("Невозможно сохранить книгу без автора");

        }
        if (author.getId() == null) {
            author = authorRepository.save(author);
            book.setAuthor(author);
        }
        bookRepository.save(book);
    }

    public Author findAuthorByFullName(String name, String secondName, String lastName) {
        Author author = authorRepository
                .getAuthorByNameAndSecondNameAndLastName(name, secondName, lastName)
                .orElse(null);
        if (author == null) {
        throw new RuntimeException("Невозможно найти автора по данным ФИО");
    }
        return author;
}

        public List<Book> findBooksByGanres(Set<GanreEnum> ganres) {
            System.out.println("Выводим список книг по жанрам");
            return bookRepository.findBookByGanreIn(ganres);
        }

        public List<Book> findBookByAuthor(String name, String secondName, String lastName) {
            System.out.println("");
            Author author = findAuthorByFullName(name, secondName, lastName);
            return bookRepository.findBookByAuthor(author);
        }
}
