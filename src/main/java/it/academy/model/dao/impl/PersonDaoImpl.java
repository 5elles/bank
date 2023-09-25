package it.academy.model.dao.impl;

import it.academy.model.DataSource;
import it.academy.model.dao.PersonDao;
import it.academy.model.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

    @Override
    public Person getById(Integer entityId) {
        Person result = null;

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id = ?")) {
            preparedStatement.setString(1, entityId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Person person = Person.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .middleName(resultSet.getString("middle_name"))
                    .lastName(resultSet.getString("last_name"))
                    .dateOfBirth(resultSet.getDate("dob").toLocalDate())
                    .citizenIdNumber(resultSet.getString("citizenID"))
                    .passportNumber(resultSet.getString("passport_number"))
                    .isStaff(resultSet.getBoolean("isStaff"))
                    .isClient(resultSet.getBoolean("isClient"))
                    .build();
            result = person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Person> getALl() {
        List<Person> result = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("select  * from person")) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Person person = Person.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .middleName(rs.getString("middle_name"))
                        .lastName(rs.getString("last_name"))
                        .dateOfBirth(rs.getDate("dob").toLocalDate())
                        .citizenIdNumber(rs.getString("citizenID"))
                        .passportNumber(rs.getString("passport_number"))
                        .isStaff(rs.getBoolean("isStaff"))
                        .isClient(rs.getBoolean("isClient"))
                        .build();
                result.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getCount() {
        int result = 0;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count from person")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean save(Person person) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("" +
                     "INSERT INTO person(first_name, middle_name, last_name, dob, citizenID, passport_number, isStaff, isClient) " +
                     "VALUE (?,?,?,?,?,?,?,?)")) {
            String isStaff = "0";
            String isClient = "0";

            if (person.isStaff()) isStaff = "1";
            if (person.isClient()) isClient = "1";

            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getMiddleName());
            statement.setString(3, person.getLastName());
            statement.setDate(4, Date.valueOf(person.getDateOfBirth()));
            statement.setString(5, person.getCitizenIdNumber());
            statement.setString(6, person.getPassportNumber());
            statement.setString(7, isStaff);
            statement.setString(8, isClient);
            int executed = statement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Person entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update person set " +
                     "first_name=?, middle_name=?, last_name=?, dob=?, citizenID=?, passport_number=?, isStaff=?, isClient=? where id=?")) {

            String isStaff = "0";
            String isClient = "0";
            if (entity.isStaff()) isStaff = "1";
            if (entity.isClient()) isClient = "1";

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getMiddleName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setDate(4, Date.valueOf(entity.getDateOfBirth()));
            preparedStatement.setString(5, entity.getCitizenIdNumber());
            preparedStatement.setString(6, entity.getPassportNumber());
            preparedStatement.setString(7, isStaff);
            preparedStatement.setString(8, isClient);
            preparedStatement.setInt(9, entity.getId());

            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Person entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id=?")) {
            preparedStatement.setInt(1, entity.getId());
            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id = ?")) {
            preparedStatement.setString(1, entityId.toString());
            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
