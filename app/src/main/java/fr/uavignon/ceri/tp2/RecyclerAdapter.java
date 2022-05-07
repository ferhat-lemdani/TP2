package fr.uavignon.ceri.tp2;



import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class RecyclerAdapter extends RecyclerView.Adapter<fr.uavignon.ceri.tp2.RecyclerAdapter.ViewHolder> {

    private static final String TAG = fr.uavignon.ceri.tp2.RecyclerAdapter.class.getSimpleName();

    private List<Book> bookList;
    private MutableLiveData<Book> selectedBook;
    private ListViewModel listViewModel;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(bookList.get(i).getTitle());
        viewHolder.itemDetail.setText(bookList.get(i).getAuthors());
    }

    public void setBookList(List<Book> books){
        bookList = books;
        notifyDataSetChanged();
    }

    public void setListViewModel(ListViewModel viewModel) {
        listViewModel = viewModel;
    }

    private void deleteItem(long id) {
        if (listViewModel != null)
            listViewModel.deleteBook(id);
    }

    @Override
    public int getItemCount() {
        return (bookList != null)? bookList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemDetail;
        ActionMode actionMode;

        long bookId;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    //int position = getAdapterPosition();

                    long id = RecyclerAdapter.this.bookList.get((int)getAdapterPosition()).getId();

                    ListFragmentDirections.ActionFirstFragmentToSecondFragment action = ListFragmentDirections.actionFirstFragmentToSecondFragment();
                    action.setBookNum(id);
                    Navigation.findNavController(v).navigate(action);


                }
            });

            ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

                // Called when the action mode is created; startActionMode() was called
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    // Inflate a menu resource providing context menu items
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.context_menu, menu);
                    return true;
                }

                // Called each time the action mode is shown. Always called after onCreateActionMode, but
                // may be called multiple times if the mode is invalidated.
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false; // Return false if nothing is done
                }

                // Called when the user selects a contextual menu item
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.delete:
                            listViewModel.deleteBook(bookId);
                            mode.finish(); // Action picked, so close the CAB
                            return true;
                        default:
                            return false;
                    }
                }

                // Called when the user exits the action mode
                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    actionMode = null;
                }
            };

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {

                    //int position = getAdapterPosition();

                    long id = RecyclerAdapter.this.bookList.get((int)getAdapterPosition()).getId();
                    bookId = id;

                    /*Snackbar.make(v, "Long Click detected on chapter " + (position+1),
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    if (actionMode != null) {
                        return false;
                    }

                    // Start the CAB using the ActionMode.Callback defined above
                    actionMode =  FragmentManager.findFragment(itemView).getActivity().startActionMode(actionModeCallback);
                    itemView.setSelected(true);
                    return true;
                }
            });
        }
    }

}