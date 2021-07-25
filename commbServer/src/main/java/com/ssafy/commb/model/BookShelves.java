package com.ssafy.commb.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Book_Shelves")
@Getter
@Setter
public class BookShelves {

    @EmbeddedId
    BookShelvesId id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    private int isRead;

    private int rate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
