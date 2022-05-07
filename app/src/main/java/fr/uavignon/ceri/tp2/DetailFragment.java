package fr.uavignon.ceri.tp2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import fr.uavignon.ceri.tp2.data.Book;

public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getSimpleName();

    private EditText textTitle, textAuthors, textYear, textGenres, textPublisher;

    private DetailViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //association du fragment "DetailFragment" avec son modele de vue(DetailViewModel).
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        // Get selected book
        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
        long bookID = args.getBookNum();
        viewModel.setSelectedBook(bookID);


        observerSetup();
        listenerSetup();


    }

    private void observerSetup(){
        //Fixer RecyclerAdapter comme observateur de bookList
        viewModel.getSelectedBook().observe(getViewLifecycleOwner(),
                book -> {
                    if (book != null) {
                        Log.d(TAG, "observing book view");
                        textTitle.setText(book.getTitle());
                        textAuthors.setText(book.getAuthors());
                        textYear.setText(book.getYear());
                        textGenres.setText(book.getGenres());
                        textPublisher.setText(book.getPublisher());
                    }
                });

    }

    private void listenerSetup(){

        textTitle = (EditText) getView().findViewById(R.id.nameBook);
        textAuthors = (EditText) getView().findViewById(R.id.editAuthors);
        textYear = (EditText) getView().findViewById(R.id.editYear);
        textGenres = (EditText) getView().findViewById(R.id.editGenres);
        textPublisher = (EditText) getView().findViewById(R.id.editPublisher);



        getView().findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fr.uavignon.ceri.tp2.DetailFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        getView().findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NavHostFragment.findNavController(fr.uavignon.ceri.tp2.DetailFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);*/
                String title = textTitle.getText().toString();
                String authors = textAuthors.getText().toString();
                String year = textYear.getText().toString();
                String genres = textGenres.getText().toString();
                String publisher = textPublisher.getText().toString();
                if(title.equals("") || authors.equals("") || year.equals("") || genres.equals("") || publisher.equals("") ) {
                    Snackbar.make(view, "Un ou plusieurs champs sont vides", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else
                if (viewModel.insertOrUpdateBook(new Book(title, authors, year, genres, publisher)) == -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Un livre avec le même nom et les mêmes auteurs existe déjà dans la base de données")
                            .setTitle("Erreur à l'ajout");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Snackbar.make(view, "Le livre a été ajoutée à la base de données",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}