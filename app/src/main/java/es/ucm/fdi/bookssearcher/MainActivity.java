package es.ucm.fdi.bookssearcher;

//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int BOOK_LOADER_ID = 501;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks();
    private RecyclerView bookRecyclerView;
    private BooksResultListAdapter bookAdapter;

    /**
     * Chequea si hay conexión de Internet disponible. Es mejor hacerlo aquí ya que se puede poner
     * en la función Search y de esa manera no se crean Loaders.
     */
    private boolean internetConnectionAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connMgr != null) {
            activeNetwork = connMgr.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }

        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        bookAdapter = new BooksResultListAdapter(this, new ArrayList<BookInfo>());

        bookRecyclerView.setAdapter(bookAdapter);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void searchBooks(View view) {

        String authorText = ((EditText)findViewById(R.id.authorText)).getText().toString();
        String titleText = ((EditText)findViewById(R.id.titleText)).getText().toString();

        if(internetConnectionAvailable() && (authorText.length() != 0 || titleText.length() != 0)) {
            // Indica si es "all", "books", "magazines"
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

            String queryString = authorText + " " + titleText;
            String printType = rb.getText().toString();

            Bundle queryBundle = new Bundle();
            queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
            queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
            LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
        }
    }

    void updateBooksResultList(List<BookInfo> booksInfo) {
        bookAdapter.setBooksData(booksInfo);
        bookAdapter.notifyDataSetChanged();
    }


    public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>  {

        public static final String EXTRA_QUERY = "queryString";
        public static final String EXTRA_PRINT_TYPE = "printType";

        /**
         * Si al llamar a initLoader() en onCreate() de MainActivity el ID no existe, se ejecuta esta
         * función. Si existía, borra el loader que había y vuelve aquí.
         */
        @Override
        public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        /*return new BookLoader((Context)args.get("context"), args.getString(EXTRA_QUERY),
                args.getString(EXTRA_PRINT_TYPE));*/
            return new BookLoader(MainActivity.this, args.getString(EXTRA_QUERY),
                    args.getString(EXTRA_PRINT_TYPE));
        }


        /**
         * Si en initLoader() del onCreate de MainActivity existiera el ID y ya ha generado datos, se
         * llama a esta función ya que significa que habría otro loader creado con el mismo ID.
         *
         * Esta función se ejecuta también cuando un Loader previo se ha terminado. Este suele ser el
         * punto en el que mueve los datos a las View mediante el adapter. La ejecución del
         * loadInBackground() pasa a esta función.
         */
        @Override
        public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
            updateBooksResultList(data);
        }


        /**
         * Cuando un Loader previamente creado se está resetando. En este punto la app debería eliminar
         * cualquier referencia que tenga de los datos del Loader.
         *
         * Esta función se llama solamente cuando el Loader actual se está destruyendo, por lo que se puede
         * dejar en blanco la mayoría del tiempo. No intentaremos acceder a datos después de hayan sido
         * borrados
         */
        @Override
        public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {
            loader.reset();
            bookAdapter.notifyDataSetChanged();
        }
    }


}
