package es.ucm.fdi.bookssearcher;

//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private final int BOOK_LOADER_ID = 501;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }
    }

    public void searchBooks(View view) {

        String authorText = ((EditText)findViewById(R.id.authorText)).getText().toString();
        String titleText = ((EditText)findViewById(R.id.titleText)).getText().toString();

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
