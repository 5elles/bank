package it.academy.model.dao.impl;

import it.academy.model.DataSource;
import it.academy.model.dao.SettlementDao;
import it.academy.model.entity.Settlement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettlementDaoImpl implements SettlementDao {
    @Override
    public Settlement getById(Integer entityId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from settlement where id = ?")) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Settlement.builder()
                    .id(resultSet.getInt("id"))
                    .settlementType(new SettlementTypeDaoImpl().getById(resultSet.getInt("settlement_type_id")))
                    .settlementName(resultSet.getString("settlement_name"))
                    .region(new RegionDaoImpl().getById(resultSet.getInt("region_id")))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Settlement> getALl() {
        List<Settlement> settlements = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from settlement")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Settlement settlement = Settlement.builder()
                        .id(resultSet.getInt("id"))
                        .settlementType(new SettlementTypeDaoImpl().getById(resultSet.getInt("settlement_type_id")))
                        .settlementName(resultSet.getString("settlement_name"))
                        .region(new RegionDaoImpl().getById(resultSet.getInt("region_id")))
                        .build();
                settlements.add(settlement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settlements;
    }

    @Override
    public int getCount() {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count from settlement")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean save(Settlement entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into settlement (region_id, settlement_type_id, settlement_name) values (?, ?, ?)")) {
            preparedStatement.setInt(1, entity.getRegion().getId());
            preparedStatement.setInt(2, entity.getSettlementType().getId());
            preparedStatement.setString(3, entity.getSettlementName());
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Settlement entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update settlement set region_id = ?, settlement_type_id = ?, settlement_name = ? where id=?")) {
            preparedStatement.setInt(1, entity.getRegion().getId());
            preparedStatement.setInt(2, entity.getSettlementType().getId());
            preparedStatement.setString(3, entity.getSettlementName());
            preparedStatement.setInt(4, entity.getId());
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Settlement entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from settlement where id = ?")
        ) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from settlement where id=?")) {
            preparedStatement.setInt(1, entityId);
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
