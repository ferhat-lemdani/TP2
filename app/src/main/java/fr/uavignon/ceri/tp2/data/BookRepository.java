package fr.uavignon.ceri.tp2.data;

import static fr.uavignon.ceri.tp2.data.BookRoomDatabase.databaseWriteExecutor;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookRepository {


    private MutableLiveData<Book> selectedBook = new MutableLiveData<>();
    private LiveData<List<Book>> allBooks;

    private BookDao bookDao;

    private static volatile BookRepository INSTANCE;

    public synchronized static BookRepository get(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new BookRepository(application);
        }

        return INSTANCE;
    }

    public BookRepository(Application application){
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        bookDao = db.bookDao();
        allBooks = bookDao.getAllBooks();
    }


    public long insertBook(Book newBook){
        Future<Long> flong = databaseWriteExecutor.submit(() -> {
            return bookDao.insertBook(newBook);
        });
        long res = -1;
        try {
            res = flong.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (res != -1)
            selectedBook.setValue(newBook);
        return res;
    }

    public void getBook(long id){
        Future<Book> gbook = databaseWriteExecutor.submit(() -> {
            return bookDao.getBook(id);
        });
        try {
            selectedBook.setValue(gbook.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(long id){
        databaseWriteExecutor.execute(() -> {
            bookDao.deleteBook(id);
        });
    }

    public LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }

    public MutableLiveData<Book> getSelectedBook(){ return selectedBook; }


    public int updateBook(Book book) {
        Future<Integer> fint = databaseWriteExecutor.submit(() -> {
            return bookDao.updateBook(book);
        });
        int res = -1;
        try {
            res = fint.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (res != -1)
            selectedBook.setValue(book);
        return res;
    }

}
