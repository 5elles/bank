package it.academy.model.dao.impl;

import it.academy.model.DataSource;
import it.academy.model.dao.SettlementTypeDao;
import it.academy.model.entity.SettlementType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettlementTypeDaoImpl implements SettlementTypeDao {
    @Override
    public SettlementType getById(Integer entityId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from settlement_type where id=?")) {
            preparedStatement.setInt(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return SettlementType.builder()
                    .id(resultSet.getInt("id"))
                    .settlementTypeName(resultSet.getString("settlement_type"))
                    .shortName(resultSet.getString("short_name"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SettlementType> getALl() {
        List<SettlementType> settlementTypes = new ArrayList<>();
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from settlement_type")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SettlementType settlementType = SettlementType.builder()
                        .id(resultSet.getInt("id"))
                        .settlementTypeName(resultSet.getString("settlement_type"))
                        .shortName(resultSet.getString("short_name"))
                        .build();

                settlementTypes.add(settlementType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settlementTypes;
    }

    @Override
    public int getCount() {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select count(*) as count from settlement_type")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean save(SettlementType entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into settlement_type (settlement_type, short_name) values (?, ?)")) {
            preparedStatement.setString(1, entity.getSettlementTypeName());
            preparedStatement.setString(2, entity.getShortName());

            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(SettlementType entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update settlement_type set settlement_type = ?, short_name = ? where id=?")) {
            preparedStatement.setString(1, entity.getSettlementTypeName());
            preparedStatement.setString(2, entity.getShortName());
            preparedStatement.setInt(3, entity.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(SettlementType entity) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from settlement_type where id = ?")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("delete from settlement_type where id = ?")) {
            preparedStatement.setInt(1, entityId);
            if (preparedStatement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}