package com.Internship.Backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Internship.Backend.models.BookModel;
import com.Internship.Backend.payload.response.MessageResponse;
import com.Internship.Backend.services.BookService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/book/")
public class BookController {
	
	@Autowired
	BookService service;
	
	@PostMapping("addbook")
	public ResponseEntity<MessageResponse> addBook(@RequestBody BookModel bookdata){
		try {
			  service.addBook(bookdata);
			MessageResponse message = new MessageResponse();
			message.setMessage("book added");
			return new ResponseEntity<MessageResponse>(message, HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			MessageResponse message = new MessageResponse();
			message.setMessage("server error");
			return new ResponseEntity<MessageResponse>(message, HttpStatus.BAD_REQUEST);
		}

	}
	
	
	@GetMapping("getbooks")
	public ResponseEntity<List<BookModel>> getBooks(){
		List<BookModel> books = service.getBooks();
		return new ResponseEntity<List<BookModel>>(books, HttpStatus.OK);
	}

}
