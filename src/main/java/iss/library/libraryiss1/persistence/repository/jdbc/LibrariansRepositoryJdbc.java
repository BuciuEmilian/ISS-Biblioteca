package iss.library.libraryiss1.persistence.repository.jdbc;

import iss.library.libraryiss1.model.Librarian;
import iss.library.libraryiss1.persistence.ILibrariansRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class LibrariansRepositoryJdbc implements ILibrariansRepository {
    private final JdbcUtils dbUtils;

    public LibrariansRepositoryJdbc(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<Librarian> findAll() {
        return null;
    }

    @Override
    public Librarian findBy(String username, String password) {
        Librarian librarian = null;

        String sql = "SELECT * FROM librarians WHERE username = ? and password = ?";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int salary = resultSet.getInt("salary");

                librarian = new Librarian(
                        name,
                        username,
                        password,
                        salary
                );
                librarian.setId(id);
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return librarian;
    }
}
