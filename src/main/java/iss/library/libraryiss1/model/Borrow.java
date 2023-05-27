package iss.library.libraryiss1.model;

import java.sql.Date;

public class Borrow implements Identifiable<Integer>{
    private Book book;
    private Subscriber subscriber;
    private BorrowStatus borrowStatus;
    private Date borrowDate;
    private Date returnDate;

    public Borrow() {
        this.borrowStatus = BorrowStatus.ACTIVE;
    }

    public Borrow(Book book, Subscriber subscriber, BorrowStatus borrowStatus, Date borrowDate, Date returnDate) {
        this.book = book;
        this.subscriber = subscriber;
        this.borrowStatus = borrowStatus;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Borrow(Book book, Subscriber subscriber, Date borrowDate) {
        this(
                book,
                subscriber,
                BorrowStatus.ACTIVE,
                borrowDate,
                null
        );
    }

    public Borrow(Book book, Subscriber subscriber) {
        this(
                book,
                subscriber,
                BorrowStatus.ACTIVE,
                new Date(System.currentTimeMillis()),
                null
        );
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getBookTitle() {
        return this.book.getTitle();
    }

    public String getBookAuthor() {
        return this.book.getAuthor();
    }

    public String getSubscriberName() {
        return this.subscriber.getName();
    }

    public Integer getBookId() {
        return book.getId();
    }

    public void setBookId(Integer bookId) {
        this.book.setId(bookId);
    }

    public Integer getSubscriberId() {
        return subscriber.getId();
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriber.setId(subscriberId);
    }

    public BorrowStatus getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(BorrowStatus borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    private Integer id;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
