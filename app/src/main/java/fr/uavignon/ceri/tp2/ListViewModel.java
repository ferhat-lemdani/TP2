package fr.uavignon.ceri.tp2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;
import fr.uavignon.ceri.tp2.data.BookRepository;

public class ListViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<Book>> bookList;
    private MutableLiveData<Book> selectedBook = new MutableLiveData<>();

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        bookList = repository.getAllBooks();
        selectedBook = repository.getSelectedBook();
    }

    MutableLiveData<Book> getSelectedBook() { return selectedBook; }
    LiveData<List<Book>> getBookList(){ return bookList;}
    public void insertBook(Book book) { repository.insertBook(book);}
    public void getBook(long id) { repository.getBook(id);}
    public void deleteBook(long id) {repository.deleteBook(id);}

}
