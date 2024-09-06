package com.Internship.Backend.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.Internship.Backend.models.BookModel;

public interface BookMapper {

	@Insert("insert into tbl_book(title, year) values(#{title}, #{year})")
	void addBook(BookModel bookdata);

	@Select("select * from tbl_book")
	List<BookModel> getBooks();
}
