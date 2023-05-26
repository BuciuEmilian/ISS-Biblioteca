package iss.library.libraryiss1.persistence.repository.jdbc;

import iss.library.libraryiss1.model.Borrow;
import iss.library.libraryiss1.model.BorrowStatus;
import iss.library.libraryiss1.model.Pair;
import iss.library.libraryiss1.persistence.IBorrowsRepository;
import iss.library.libraryiss1.persistence.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BorrowsRepositoryJdbc implements IBorrowsRepository {
    private final JdbcUtils dbUtils;

    public BorrowsRepositoryJdbc(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    private Borrow extractAttributes(ResultSet rs) throws SQLException {
        int bookId = rs.getInt("book_id");
        int subscriberId = rs.getInt("subscriber_id");
        String status = rs.getString("status");
        Date borrowDate = rs.getDate("borrow_date");
        Date returnDate = rs.getDate("return_date");

        Borrow borrow = new Borrow(
                BorrowStatus.valueOf(status),
                borrowDate,
                returnDate
        );
        borrow.setId(new Pair<>(bookId, subscriberId));

        return borrow;
    }

    @Override
    public List<Borrow> findAll() {
        String sql = "SELECT * FROM borrows";
        Connection connection = dbUtils.getConnection();
        List<Borrow> borrows = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrows.add(this.extractAttributes(resultSet));
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return borrows;
    }

    @Override
    public List<Borrow> findBorrowsBySubscriberId(int subscriberId) {
        String sql = "SELECT * FROM borrows WHERE subscriber_id = ?";
        Connection connection = dbUtils.getConnection();
        List<Borrow> borrows = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, subscriberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrows.add(this.extractAttributes(resultSet));
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return borrows;
    }

    @Override
    public void save(Borrow borrow) {
        String sql = "INSERT INTO borrows(book_id, subscriber_id, status, borrowDate) VALUES (?, ?, ?, ?)";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, borrow.getBookId());
            preparedStatement.setInt(2, borrow.getSubscriberId());
            preparedStatement.setString(3, borrow.getBorrowStatus().toString());
            preparedStatement.setDate(4, borrow.getBorrowDate());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
//                ResultSet resultSet = preparedStatement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    int id = resultSet.getInt(1);
//                    subscriber.setId(id);
//                }
            }
            else
                throw new RepositoryException("Save error: " + borrow);
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
    }

    @Override
    public void update(Borrow borrow) {

    }
}
