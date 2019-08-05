package com.brain.book.library;

import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GanreEnum;
import com.brain.book.library.service.BookLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Application {

	@Autowired
	private BookLibraryService bookLibraryService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		addTestRoman();
		addTestFantasy();
		printAuthorBooks();
		printFilterBooks();
	}

	private void addTestRoman(){
		Book book = new Book();
		book.setBooking("Подвал на улицу Ленина");
		book.setName("Армено и Жилетта");
		book.setPagesCount(300);
		book.setReleaseDate(Year.of(2017));

		book.setGanre(GanreEnum.ROMAN);

		Author author = new Author();
		author.setName("Инокентий");
		author.setSecondName("Педросович");
		author.setLastName("Пупырышкин");
		author.setBirthDate(LocalDate.of(1990, 4, 12));
		book.setAuthor(author);
		bookLibraryService.addNewBook(book);
	}

	private void addTestFantasy(){
		Book book = new Book();
		book.setBooking("Крыша на улице Чекистров");
		book.setName("Пластелин Колец");
		book.setPagesCount(800);
		book.setReleaseDate(Year.of(2010));

		book.setGanre(GanreEnum.FANTASY);

		Author author = bookLibraryService
				.findAuthorByFullName("Инокентий", "Педросович", "Пупырышкин");
		book.setAuthor(author);
		bookLibraryService.addNewBook(book);
	}

	private void printAuthorBooks(){
		List<Book> books = bookLibraryService
				.findBookByAuthor()
	}

	private void printFilterBooks(){
		Set<GanreEnum> ganres = new HashSet<>();
		ganres.add(GanreEnum.FANTASY);
		ganres.add(GanreEnum.ROMAN);

		List<Book> books = bookLibraryService
				.findBooksByGanres(ganres);
		books.forEach(System.out::println);

	}



}
