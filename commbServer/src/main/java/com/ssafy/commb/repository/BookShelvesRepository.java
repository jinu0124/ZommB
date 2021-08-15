package com.ssafy.commb.repository;

import com.ssafy.commb.dto.book.BookDto;
import com.ssafy.commb.model.Book;
import com.ssafy.commb.model.BookShelves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookShelvesRepository extends JpaRepository<BookShelves, Integer> {

    Optional<BookShelves> findByBookIdAndUserId(int bookId, int userId);

    public List<BookShelves> findByUserIdAndIsRead(Integer userId, Integer isRead);
}
