package iss.library.libraryiss1.model;

import java.sql.Date;

public class Borrow implements Identifiable<Pair<Integer, Integer>>{
    private BorrowStatus borrowStatus;
    private Date borrowDate;
    private Date returnDate;

    public Borrow() {
        this.borrowStatus = BorrowStatus.ACTIVE;
        this.borrowDate = new Date(System.currentTimeMillis());
    }

    public Borrow(BorrowStatus status, Date borrowDate, Date returnDate) {
        this.borrowStatus = status;
    }

    public Integer getBookId() {
        return id.getFirst();
    }

    public void setBookId(Integer bookId) {
        this.id.setFirst(bookId);
    }

    public Integer getSubscriberId() {
        return id.getSecond();
    }

    public void setSubscriberId(Integer subscriberId) {
        this.id.setSecond(subscriberId);
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

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    Pair<Integer, Integer> id = new Pair<>(-1, -1);
    @Override
    public Pair<Integer, Integer> getId() {
        return id;
    }

    @Override
    public void setId(Pair<Integer, Integer> id) {
        this.setBookId(id.getFirst());
        this.setSubscriberId(id.getSecond());
    }
}
