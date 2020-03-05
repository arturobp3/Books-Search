package es.ucm.fdi.bookssearcher;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;
    private BookService bookService;

    public BookLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Nullable
    public String loadInBackground(String queryString, String printType) {

        json = bookService.getBookInfoJson(queryString, printType);


        return "Hola";
    }


    protected void onStartLoading() {
        forceLoad();
    }



    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }
}
