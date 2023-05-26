package iss.library.libraryiss1.persistence.repository.jdbc;

import iss.library.libraryiss1.model.Book;
import iss.library.libraryiss1.persistence.IBooksRepository;
import iss.library.libraryiss1.persistence.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BooksRepositoryJdbc implements IBooksRepository {
    private final JdbcUtils dbUtils;

    public BooksRepositoryJdbc(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    private Book extractAttributes(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String genre = rs.getString("genre");
        int quantity = rs.getInt("quantity");

        Book book = new Book(
                title,
                author,
                genre,
                quantity
        );
        book.setId(id);

        return book;
    }

    private void addAttributes(PreparedStatement ps, Book book) throws SQLException {
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getGenre());
        ps.setInt(4, book.getQuantity());
    }

    @Override
    public void save(Book book) {
        Book oldBook = this.findBy(book.getTitle(), book.getAuthor());
        // if the book is already in the database
        if (oldBook != null) {
            // we just need to increase the quantity, not insert it again
            oldBook.increaseQuantity(book.getQuantity());
            this.update(oldBook);
            return;
        }

        // otherwise insert it as a new book
        String sql = "INSERT INTO books (title, author, genre, quantity) VALUES (?, ?, ?, ?)";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            this.addAttributes(preparedStatement, book);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
//                ResultSet resultSet = preparedStatement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    int id = resultSet.getInt(1);
//                    book.setId(id);
//                }
            }
            else
                throw new RepositoryException("Save error: " + book);
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }

//        return book;
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, genre = ?, quantity = ? WHERE id = ?";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            this.addAttributes(preparedStatement, book);
            preparedStatement.setInt(5, book.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 0)
                throw new RepositoryException("id not found");
        }
        catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void delete(Book book) {
        String sql = "DELETE FROM books WHERE id = ?";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, book.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 0)
                throw new RepositoryException("id not found");
        }
        catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public Book findBy(String title, String author) {
        Book book = null;

        String sql = "SELECT * FROM books WHERE title = ? and author = ?";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = this.extractAttributes(resultSet);
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        Connection connection = dbUtils.getConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(this.extractAttributes(resultSet));
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return books;
    }
}
