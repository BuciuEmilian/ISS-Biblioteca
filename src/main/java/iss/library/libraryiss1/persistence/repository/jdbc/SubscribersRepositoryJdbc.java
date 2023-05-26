package iss.library.libraryiss1.persistence.repository.jdbc;

import iss.library.libraryiss1.model.Subscriber;
import iss.library.libraryiss1.persistence.ISubscribersRepository;
import iss.library.libraryiss1.persistence.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SubscribersRepositoryJdbc implements ISubscribersRepository {
    private final JdbcUtils dbUtils;

    public SubscribersRepositoryJdbc(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    private Subscriber extractAttributes(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String password = rs.getString("password");
        long CNP = rs.getLong("CNP");
        String phoneNumber = rs.getString("phone_number");
        int addressId = rs.getInt("address_id");

        Subscriber subscriber = new Subscriber(
                name,
                username,
                password,
                CNP,
                phoneNumber,
                addressId
        );
        subscriber.setId(id);

        return subscriber;
    }

    @Override
    public Subscriber findBy(String username, String password) {
        Subscriber subscriber = null;

        String sql = "SELECT * FROM subscribers WHERE username = ? and password = ?";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                subscriber = this.extractAttributes(resultSet);
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return subscriber;
    }

    @Override
    public List<Subscriber> findAll() {
        String sql = "SELECT * FROM subscribers";
        Connection connection = dbUtils.getConnection();
        List<Subscriber> subscribers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subscribers.add(this.extractAttributes(resultSet));
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return subscribers;
    }

    @Override
    public void save(Subscriber subscriber) {
        String sql = "INSERT INTO subscribers(name, username, password, CNP, phone_number, address_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, subscriber.getName());
            preparedStatement.setString(2, subscriber.getUsername());
            preparedStatement.setString(3, subscriber.getPassword());
            preparedStatement.setLong(4, subscriber.getCNP());
            preparedStatement.setString(5, subscriber.getPhoneNumber());
            preparedStatement.setInt(6, subscriber.getAddressId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
//                ResultSet resultSet = preparedStatement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    int id = resultSet.getInt(1);
//                    subscriber.setId(id);
//                }
            }
            else
                throw new RepositoryException("Save error: " + subscriber);
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
//        return subscriber;
    }
}
