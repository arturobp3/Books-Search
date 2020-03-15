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

    public BooksResultListAdapter(Context context, List<BookInfo> wordList) {
        this.inflater = LayoutInflater.from(context);
        this.mBooksData = wordList;
    }

    /**
     * Un RecyclerView.ViewHolder describe el elemento de una vista y metadata sobre su localización
     * dentro del RecyclerView. Cada ViewHolder mantiene un conjunto de datos. El adapter añade
     * datos a cada ViewHolder para que el LayoutManager lo muestre en la pantalla.
     */
    class BookViewHolder extends RecyclerView.ViewHolder {
        public String title;
        public String author;
        public String url;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // Get the layout

            //TODO: Esto está mal, creo que habría que poner la view, no el string
            this.title = itemView.findViewById(R.id.titleItem).toString();
            this.author = itemView.findViewById(R.id.authorsItem).toString();
            this.url = itemView.findViewById(R.id.urlItem).toString();

            // Associate with this adapter



        }

    }

    /**
     * "Infla" el elemento de una vista y devuelve un nuevo ViewHolder que lo contiene. Este metodo
     * es llamado cuando el RecyclerView necesita un nuevo ViewHolder para representar un item.
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view from layout
        View itemView = inflater.inflate(R.layout.result_list_item, parent, false);
        return new BookViewHolder(itemView);
    }


    /**
     * Establecer el contenido del item de una vista en una posición dada en el RecyclerView.
     * Esta es llamada por el RecyclerView, por ejemplo, cuando un elemento de la vista se desplaza en
     * la pantalla.
     */
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        // Retrieve the data for that position
        BookInfo current = mBooksData.get(position);
        // Add the data to the view
        holder.title = current.getTitle();
        holder.author = current.getAuthors();
        holder.url = current.getInfoLink().toString();
    }


    /**
     *  Devuelve el número total de elementos en el conjunto de datos que tiene el Adapter
     */
    @Override
    public int getItemCount() {
        return this.mBooksData.size();
    }



    public void setBooksData(List<BookInfo> data) {
        this.mBooksData = data;
    }
}
