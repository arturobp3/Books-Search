package es.ucm.fdi.bookssearcher;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<String> {

    public static final String EXTRA_QUERY = "q";
    public static final String EXTRA_PRINT_TYPE = "printType";

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader((Context)args.get("context"), args.getString("queryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) { }
}
