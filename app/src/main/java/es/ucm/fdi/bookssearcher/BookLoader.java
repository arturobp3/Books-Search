package es.ucm.fdi.bookssearcher;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONObject;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private static final String DEBUG_TAG = BookLoader.class.getSimpleName();

    private String mQueryString;
    private BookService bookService;
    private Context context;

    public BookLoader(Context context, String queryString) {
        super(context);
        this.context = context;
        mQueryString = queryString;
        bookService = new BookService();
    }

    private boolean internetConnectionAvailable(){

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        /*NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();*/

        /*Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);*/
        //return (isMobileConn || isWifiConn);

        // AL DESCOMENTAR ESAS INSTRUCCIONES DE ARRIBA, PONE QUE NO SE PUEDE CON LA API 26 DE ANDROID


        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        return isConnected;
    }


    @Nullable
    public String loadInBackground(String queryString, String printType) {

        if (internetConnectionAvailable()) {
            JSONObject json = bookService.getBookInfoJson(queryString, printType);
            if(json != null){

                // ASI SE PUEDE ACCEDER A LOS DATOS

                /*JSONArray menuItemArray = data.getJSONArray("menuitem");
                JSONObject thirdItem = menuItemArray.getJSONObject(2);
                String onClick = thirdItem.getString("onclick");*/
            } else{


            }
        } else{

        }


        return "Hola";
    }


    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {


    }
}
