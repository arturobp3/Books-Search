package es.ucm.fdi.bookssearcher;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * AsyncTaskLoader<D>, donde D es el tipo de dato que queremos cargar.
 */
public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private static final String DEBUG_TAG = BookLoader.class.getSimpleName();

    private String mQueryString, mPrintType;
    private BookService bookService;

    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        mQueryString = queryString;
        mPrintType = printType;
        this.bookService = new BookService();
    }

    /**
     * LoaderManager llama a esta función automáticamente al crear el Loader.
     */
    protected void onStartLoading() {
        forceLoad();
    }

    private List<BookInfo> loadData(String queryString, String printType) {
        List<BookInfo> data;
        data = bookService.getBookInfoJson(queryString, printType);
        if(data != null){
            return data;

        } else{
            return null;
        }
    }


    /**
     * Función que se ejecuta en otro thread distinto al UI Thread (o Main Thread). Los resultados
     * se pasan directamente al UI Thread por medio de la función onLoadFinished() del callback del
     * LoaderManager.
     */
    @Override
    @Nullable
    public List<BookInfo> loadInBackground() {
        List<BookInfo> data;
        data = loadData(mQueryString, mPrintType);

        return data;
    }

}
