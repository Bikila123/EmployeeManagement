package com.Internship.Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internship.Backend.mappers.BookMapper;
import com.Internship.Backend.models.BookModel;

@Service
public class BookService {
    @Autowired
    BookMapper mapper;
	public void addBook(BookModel bookdata) {
       mapper.addBook(bookdata);	
	}
	public List<BookModel> getBooks() {
		return mapper.getBooks();
	}
  
	
}
