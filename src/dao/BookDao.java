package dao;

import core.Db;
import entity.Book;
import entity.Car;
import entity.Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookDao {
    private Connection connection;
    private final CarDao carDao;

    public BookDao() {
        this.connection = Db.getInstance();
        this.carDao = new CarDao();
    }

    public Book getById(int id) {
        Book obj = null;
        String query = "SELECT * FROM book WHERE book_id = ?";
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

    public ArrayList<Book> findAll() {
        return this.selectByQuery("SELECT * FROM book ORDER BY book_id");
    }

    public ArrayList<Book> selectByQuery(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                books.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean save(Book book) {
        String query = "INSERT INTO book " +
                "(" +
                "book_car_id, " +
                "book_name, " +
                "book_idno, " +
                "book_mpno, " +
                "book_mail, " +
                "book_strt_date, " +
                "book_fnsh_date, " +
                "book_prc, " +
                "book_case, " +
                "book_note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, book.getCar_id());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getIdno());
            preparedStatement.setString(4, book.getMpno());
            preparedStatement.setString(5, book.getMail());
            preparedStatement.setDate(6, Date.valueOf(book.getStrt_date()));
            preparedStatement.setDate(7, Date.valueOf(book.getFnsh_date()));
            preparedStatement.setInt(8, book.getPrice());
            preparedStatement.setString(9, book.getbCase());
            preparedStatement.setString(10, book.getNote());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int book_id) {
        String query = "DELETE FROM book WHERE book_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, book_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Book match(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setbCase(resultSet.getString("book_case"));
        book.setCar_id((resultSet.getInt("book_car_id")));
        book.setName(resultSet.getString("book_name"));
        book.setStrt_date(LocalDate.parse(resultSet.getString("book_strt_date")));
        book.setFnsh_date(LocalDate.parse(resultSet.getString("book_fnsh_date")));
        book.setCar(this.carDao.getById(resultSet.getInt("book_car_id")));
        book.setIdno(resultSet.getString("book_idno"));
        book.setMpno(resultSet.getString("book_mpno"));
        book.setMail(resultSet.getString("book_mail"));
        book.setNote(resultSet.getString("book_note"));
        book.setPrice(resultSet.getInt("book_prc"));
        return book;
    }
}
