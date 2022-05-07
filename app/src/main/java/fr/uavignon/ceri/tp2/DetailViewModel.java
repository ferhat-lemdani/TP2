package fr.uavignon.ceri.tp2;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;
import fr.uavignon.ceri.tp2.data.BookRepository;

public class DetailViewModel extends AndroidViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();

    private BookRepository repository;
    private MutableLiveData<Book> selectedBook;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = BookRepository.get(application);
        selectedBook = new MutableLiveData<>();
    }



    public void setSelectedBook(long id) {
        repository.getBook(id);
        selectedBook = repository.getSelectedBook();
    }

    public LiveData<Book> getSelectedBook() { return selectedBook; }

    public long insertOrUpdateBook(Book newBook) {
        long res = 0;
        if (selectedBook.getValue() == null) {
            res = repository.insertBook(newBook);
            // return -1 if there is a conflict
            setSelectedBook(res);
        } else {
            // ID does not change for updates
            newBook.setId(selectedBook.getValue().getId());
            int nb = repository.updateBook(newBook);
            // return the nb of rows updated by the query
            if (nb ==0)
                res = -1;
        }
        Log.d(TAG,"insert book="+selectedBook.getValue());
        return res;
    }

}
