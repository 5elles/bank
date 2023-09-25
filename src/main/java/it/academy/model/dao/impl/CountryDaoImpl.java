package it.academy.model.dao.impl;

import it.academy.model.DataSource;
import it.academy.model.dao.CountryDao;
import it.academy.model.entity.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl implements CountryDao {

    @Override
    public Country getById(Integer entityId) {
        Country result = null;
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from country where id=?")) {
            preparedStatement.setString(1, entityId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            result = Country.builder()
                    .id(resultSet.getInt("id"))
                    .countryName(resultSet.getString("country_name"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Country> getALl() {
        List<Country> result = new ArrayList<>();

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from country")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Country country = Country.builder()
                        .id(resultSet.getInt("id"))
                        .countryName(resultSet.getString("country_name"))
                        .build();
                result.add(country);
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
             PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count from country")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean save(Country entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into country (country_name) values (?)")) {
            preparedStatement.setString(1, entity.getCountryName());
            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Country entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update country set country_name=? where id = ?")) {
            preparedStatement.setString(1, entity.getCountryName());
            preparedStatement.setInt(2, entity.getId());

            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Country entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from country where id=?")) {
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
        PreparedStatement preparedStatement = connection.prepareStatement("delete from country where id = ?")){
           preparedStatement.setInt(1, entityId);
            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
