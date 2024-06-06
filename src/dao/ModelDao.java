package dao;

import core.Db;
import entity.Brand;
import entity.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDao {
    private Connection connection;
    private BrandDao brandDao = new BrandDao();

    public ModelDao() {
        this.connection = Db.getInstance();
    }

    public Model getById(int id) {
        Model obj = null;
        String query = "SELECT * FROM model WHERE model_id = ?";
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

    public ArrayList<Model> findAll() {
        return this.selectByQuery("SELECT * FROM model ORDER BY model_id");
    }

    public ArrayList<Model> getByListBrandId(int brandId) {
        return this.selectByQuery("SELECT * FROM model WHERE model_brand_id = " + brandId);
    }

    public ArrayList<Model> selectByQuery(String query) {
        ArrayList<Model> modelList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                modelList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    public boolean save(Model model) {
        String query = "INSERT INTO model " +
                "(" +
                "model_brand_id, " +
                "model_name, " +
                "model_type, " +
                "model_year, " +
                "model_fuel, " +
                "model_gear) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, model.getBrand_id());
            preparedStatement.setString(2, model.getName());
            preparedStatement.setString(3, model.getType().toString());
            preparedStatement.setString(4, model.getYear());
            preparedStatement.setString(5, model.getFuel().toString());
            preparedStatement.setString(6, model.getGear().toString());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Model model) {
        String query = "UPDATE model SET " +
                "model_brand_id = ?, " +
                "model_name = ?, " +
                "model_type = ?, " +
                "model_year = ?, " +
                "model_fuel = ?, " +
                "model_gear = ? " +
                "WHERE model_id = ?;";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, model.getBrand_id());
            preparedStatement.setString(2, model.getName());
            preparedStatement.setString(3, model.getType().toString());
            preparedStatement.setString(4, model.getYear());
            preparedStatement.setString(5, model.getFuel().toString());
            preparedStatement.setString(6, model.getGear().toString());
            preparedStatement.setInt(7, model.getId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM model WHERE model_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Model match(ResultSet resultSet) throws SQLException {
        Model model = new Model();
        model.setId(resultSet.getInt("model_id"));
        model.setBrand_id(resultSet.getInt("model_brand_id"));
        model.setBrand(this.brandDao.getById(resultSet.getInt("model_brand_id")));
        model.setName(resultSet.getString("model_name"));
        model.setType(Model.Type.valueOf(resultSet.getString("model_type")));
        model.setFuel(Model.Fuel.valueOf(resultSet.getString("model_fuel")));
        model.setGear(Model.Gear.valueOf(resultSet.getString("model_gear")));
        model.setYear(resultSet.getString("model_year"));
        return model;
    }
}
