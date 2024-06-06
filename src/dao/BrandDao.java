package dao;

import core.Db;
import entity.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection connection;

    public BrandDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM brand ORDER BY brand_id";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                brandList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public boolean save(Brand brand) {
        String query = "INSERT INTO brand (brand_name) VALUES (?);";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, brand.getName());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Brand brand) {
        String query = "UPDATE brand SET brand_name = ? WHERE brand_id = ?;";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, brand.getName());
            preparedStatement.setInt(2, brand.getId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Brand getById(int id) {
        Brand obj = null;
        String query = "SELECT * FROM brand WHERE brand_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                obj = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Brand match(ResultSet resultSet) throws SQLException {
        Brand obj = new Brand();
        obj.setId(resultSet.getInt("brand_id"));
        obj.setName(resultSet.getString("brand_name"));
        return obj;
    }
}
