package es.ucm.fdi.bookssearcher;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

/**
 * LoaderCallbacks<D>, donde D es el tipo de datos que maneja el Loader.
 */
public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    public static final String EXTRA_QUERY = "queryString";
    public static final String EXTRA_PRINT_TYPE = "printType";
    //TODO: CREO QUE HAY QUE CREAR UN ADAPTER AQUI PARA PASARSELO A LA VISTA


    /**
     * Si al llamar a initLoader() en onCreate() de MainActivity el ID no existe, se ejecuta esta
     * función. Si existía, borra el loader que había y vuelve aquí.
     */
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader((Context)args.get("context"), args.getString(EXTRA_QUERY),
                args.getString(EXTRA_PRINT_TYPE));
        /*return new BookLoader(getActivity(), args.getString(EXTRA_QUERY),
                args.getString(EXTRA_PRINT_TYPE));*/
    }


    /**
     * Si en initLoader() del onCreate de MainActivity existiera el ID y ya ha generado datos, se
     * llama a esta función ya que significa que habría otro loader creado con el mismo ID.
     *
     * Esta función se ejecuta también cuando un Loader previo se ha terminado. Este suele ser el
     * punto en el que mueve los datos a las View.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        //mAdapter.setData(data);
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


    }
}
