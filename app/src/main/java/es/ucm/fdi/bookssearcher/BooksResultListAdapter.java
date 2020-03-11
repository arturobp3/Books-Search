package es.ucm.fdi.bookssearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class  BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.BookViewHolder> {

    private LayoutInflater inflater;
    private List<BookInfo> mBooksData;

    public BooksResultListAdapter(Context context, ArrayList<BookInfo> wordList) {
        this.inflater = LayoutInflater.from(context);
        this.mBooksData = wordList;
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView bookItemView;
        private RecyclerView.Adapter parentAdapter;

        public BookViewHolder(@NonNull View itemView, BooksResultListAdapter adapter) {
            super(itemView);
            // Get the layout

            //Comprobar: creo que no es titleText
            bookItemView = itemView.findViewById(R.id.titleText);
            // Associate with this adapter
            this.parentAdapter = adapter;
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view from layout

        //Comprobar: creo que habría que crear otro layout para esto
        View itemView = inflater.inflate(R.layout.activity_main, parent, false);
        return new BookViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Retrieve the data for that position
        BookInfo current = mBooksData.get(position);
        // Add the data to the view

        //Comprobar: hay que añadir la info al bookItemView, no sé como
        holder.bookItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return this.mBooksData.size();
    }

    public void setBooksData(List<BookInfo> data) {
        this.mBooksData = data;
    }
}
