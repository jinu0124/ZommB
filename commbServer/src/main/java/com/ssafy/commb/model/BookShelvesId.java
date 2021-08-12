package com.ssafy.commb.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class BookShelvesId implements Serializable {

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "user_id")
    private int userId;

    public BookShelvesId(){}
    public BookShelvesId(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookShelvesId)) return false;
        BookShelvesId that = (BookShelvesId) o;
        return bookId == that.bookId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userId);
    }
}
