package iss.library.libraryiss1.persistence.repository.jdbc;

import iss.library.libraryiss1.model.Address;
import iss.library.libraryiss1.persistence.IAddressesRepository;
import iss.library.libraryiss1.persistence.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AdressesRepositoryJdbc implements IAddressesRepository {
    private final JdbcUtils dbUtils;

    public AdressesRepositoryJdbc(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Address find(Address address) {
        String sql = "SELECT * FROM addresses WHERE street_name = ? and street_number = ? and county = ?";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address.getStreetName());
            preparedStatement.setInt(2, address.getStreetNumber());
            preparedStatement.setString(3, address.getCounty());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                address.setId(id);
                return address;
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Address save(Address address) {
        String sql = "INSERT INTO addresses (street_name, street_number, county) VALUES (?, ?, ?)";
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, address.getStreetName());
            preparedStatement.setInt(2, address.getStreetNumber());
            preparedStatement.setString(3, address.getCounty());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    address.setId(id);
                }
            }
            else
                throw new RepositoryException("Save error: " + address);
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }

        return address;
    }

    @Override
    public List<Address> findAll() {
        String sql = "SELECT * FROM addresses";
        Connection connection = dbUtils.getConnection();
        List<Address> races = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String streetName = resultSet.getString("street_name");
                int streetNumber = resultSet.getInt("street_number");
                String county = resultSet.getString("county");

                Address address = new Address(streetName, streetNumber, county);
                address.setId(id);

                races.add(address);
            }
        }
        catch (SQLException e) {
            System.err.println("Error DB" + e);
        }

        return races;
    }
}
