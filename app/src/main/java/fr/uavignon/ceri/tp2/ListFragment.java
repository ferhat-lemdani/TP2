package fr.uavignon.ceri.tp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class ListFragment extends Fragment {

    public ListViewModel getViewModel() {
        return viewModel;
    }

    private ListViewModel viewModel;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //association du fragment "ListFragment" avec son modele de vue(ListViewModel).
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);



        listenerSetup();
        observerSetup();
        recyclerSetup();

    }

    private void observerSetup(){
        //Fixer RecyclerAdapter comme observateur de bookList
        final Observer<List<Book>> bookListObserver = new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBookList(books);
            }
        };
        viewModel.getBookList().observe(getViewLifecycleOwner(), bookListObserver);
    }

    private void recyclerSetup(){
        recyclerView = getView().findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setListViewModel(viewModel);
    }

    private void listenerSetup(){
        FloatingActionButton fab = getView().findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                ListFragmentDirections.ActionFirstFragmentToSecondFragment action = ListFragmentDirections.actionFirstFragmentToSecondFragment();
                action.setBookNum(Book.ADD_ID);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}