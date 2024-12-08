package com.bookshop.oz.controller;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookshop.oz.config.PdfGenerator;
import com.bookshop.oz.dto.BookProductDTOItem;
import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.dto.LocationReport;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.Stock;
import com.bookshop.oz.service.BookProductService;
import com.bookshop.oz.service.LocationPointService;
import com.bookshop.oz.service.OrderService;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.service.StockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ADMIN")
@RequiredArgsConstructor
public class AdminController {
	private final BookProductService bookProductService;
	private final LocationPointService locationPointService;
	private final StockService stockService;
	private final PersonService personService;
	private final OrderService orderService;

	private final PdfGenerator pdfGenerator;

	/**
	 * Тут можно удалить книгу, создать книгу, назначив ей локации, изменить
	 * существующую книгу.
	 */
	@GetMapping("/books")
	public String books(Model model, @RequestParam(value = "like", required = false) String like) {
		List<BookProductDTOItem> books = bookProductService.getItems(null, like);
		model.addAttribute("stock", books);
		return "/ADMIN/books";
	}

	@GetMapping("/books/add")
	public String books(Model model) {
		BookProduct book = new BookProduct();
		model.addAttribute("book", book);
		return "/ADMIN/book_add";
	}

	@GetMapping("/books/edit-{isbn}")
	public String editBook(@PathVariable("isbn") String isbn, Model model) {
		BookProduct book = bookProductService.getBookProduct(isbn);
		model.addAttribute("book", book);
		return "/ADMIN/editbook";
	}

	@PostMapping("/books/edit-{isbn}")
	public String editBookSubmit(@ModelAttribute("book") @Valid BookProduct book, @PathVariable("isbn") String isbn,
			Model model) {
		bookProductService.editBook(book);
		return "redirect:/ADMIN/books";
	}

	@PostMapping("/books/add")
	public String addBook(@ModelAttribute("book") @Valid BookProduct book, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/ADMIN/book_add";
		}
		bookProductService.createBook(book);
		return "redirect:/ADMIN/books";
	}

	/**
	 * Тут можем назначать юзерам роли: - покупатель - продавец (не может быть
	 * покупателем) - бухгалтер (может быть покупателем)
	 */
	@GetMapping("/users")
	public String users(Model model) {
		List<Person> getPeople = personService.getAllUsersExceptAdmin();
		model.addAttribute("people", getPeople);
		return "ADMIN/users";
	}

	@PostMapping("/users/toggleRole")
	public String toggleRole(@RequestParam Integer personId) {
		personService.toggleUserRole(personId);
		return "redirect:/ADMIN/users";
	}

	@PostMapping("/users/toggleStatus")
	public String toggleStatus(@RequestParam Integer personId) {
		personService.toggleUserStatus(personId);
		return "redirect:/ADMIN/users";
	}

	/**
	 * Передаем сюда локации. Перейдя по клику, попадаем на список товаров и их
	 * количество. Можем добавить книгу или изменить колво у уже имеющихся книг.
	 */
	@GetMapping("/stock")
	public String stock(Model model) {
		List<LocationPointDTO> locs = locationPointService.getLocations();
		model.addAttribute("locations", locs);
		return "/ADMIN/loc";
	}

	@GetMapping("/stock/{locationId}")
	public String stockItem(@PathVariable("locationId") String locId, Model model) {
		List<Stock> stock = stockService.getStockByLocation(locId);
		model.addAttribute("stock", stock);
		return "/ADMIN/stock";
	}

	@PostMapping("/{locationId}/add")
	public String addBookToStock(@PathVariable("locationId") String locationId, @RequestParam("isbn") String isbn,
			Model model) {
		try {
			BookProduct book = bookProductService.getBookProduct(isbn);
			stockService.saveStock((short) 0, locationId, book);
			model.addAttribute("successMessage", "Book added successfully!");
		} catch (Exception ex) {
			model.addAttribute("errorMessage", ex.getMessage());
		}

		return "redirect:/ADMIN/stock/" + locationId;
	}

	@PostMapping("/{locationId}/updateQuantity")
	public String updateStockQuantity(@PathVariable("locationId") String locId, @RequestParam("isbn") String isbn,
			@RequestParam("quantity") Short quantity, Model model) {
		try {
			stockService.updateStockQuantity(locId, isbn, quantity);
			model.addAttribute("successMessage", "Quantity updated successfully!");
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/ADMIN/stock/" + locId;
	}

	@ResponseBody
	@GetMapping(produces = MediaType.APPLICATION_PDF_VALUE, path = "/stats")
	public ResponseEntity<byte[]> generateStats() {
		List<LocationReport> reports = orderService.getLocationReports();
		byte[] pdfContent = pdfGenerator.generatePdf(reports);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename("location_stats.pdf").build());

		return ResponseEntity.ok().headers(headers).body(pdfContent);
	}

}
