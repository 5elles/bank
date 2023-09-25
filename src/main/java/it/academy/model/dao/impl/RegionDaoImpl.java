package it.academy.model.dao.impl;

import it.academy.model.DataSource;
import it.academy.model.dao.RegionDao;
import it.academy.model.entity.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionDaoImpl implements RegionDao {
    @Override
    public Region getById(Integer entityId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from region where id=?")) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Region.builder()
                    .country(new CountryDaoImpl().getById(resultSet.getInt("country_id")))
                    .regionName(resultSet.getString("region_name"))
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Region> getALl() {
        List<Region> result = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from region")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Region region = Region.builder()
                        .id(resultSet.getInt("id"))
                        .country(new CountryDaoImpl().getById(resultSet.getInt("country_id")))
                        .regionName(resultSet.getString("region_name"))
                        .build();
                result.add(region);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getCount() {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("select count(*) as count from region")) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean save(Region entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into region (country_id, region_name) values (?, ?)")) {
            preparedStatement.setInt(1, entity.getCountry().getId());
            preparedStatement.setString(2, entity.getRegionName());
            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Region entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update region set country_id=?, region_name=? where id=?")) {
            preparedStatement.setInt(1, entity.getCountry().getId());
            preparedStatement.setString(2, entity.getRegionName());
            preparedStatement.setInt(3, entity.getId());
            int executed = preparedStatement.executeUpdate();
            if (executed > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Region entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from region where id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) {
        try (Connection connection = DataSource.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from region where id = ?")) {
            preparedStatement.setInt(1, entityId);
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
