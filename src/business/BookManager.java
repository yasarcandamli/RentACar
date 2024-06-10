package business;

import core.Helper;
import dao.BookDao;
import entity.Book;
import entity.Model;

import java.util.ArrayList;

public class BookManager {
    private final BookDao bookDao;

    public BookManager() {
        this.bookDao = new BookDao();
    }

    public boolean save(Book book) {
        return this.bookDao.save(book);
    }

    public Book getById(int id) {
        return this.bookDao.getById(id);
    }

    public ArrayList<Book> findAll() {
        return this.bookDao.findAll();
    }

    public boolean delete(int book_id) {
        if (this.getById(book_id) == null) {
            Helper.showMessage(book_id + "ID kayıtlı rezervasyon bulunamadı!");
            return false;
        }
        return this.bookDao.delete(book_id);
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Book> bookList) {
        ArrayList<Object[]> bookObjList = new ArrayList<>();
        for (Book obj : bookList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getCar().getPlate();
            rowObject[i++] = obj.getCar().getModel().getBrand().getName();
            rowObject[i++] = obj.getCar().getModel().getName();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getMpno();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getIdno();
            rowObject[i++] = obj.getStrt_date();
            rowObject[i++] = obj.getFnsh_date();
            rowObject[i++] = obj.getPrice();
            bookObjList.add(rowObject);
        }
        return bookObjList;
    }

    public ArrayList<Book> searchForTable(int carId) {
        String query = "SELECT * FROM book";
        ArrayList<String> whereList = new ArrayList<>();

        if (carId != 0) {
            whereList.add("book_car_id = " + carId);
        }

        String whereStr = String.join(" AND ", whereList);
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        return this.bookDao.selectByQuery(query);
    }
}
