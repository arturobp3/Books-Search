package es.ucm.fdi.bookssearcher;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookService {

    private static final String DEBUG_TAG = BookService.class.getSimpleName();;
    private final String BOOK_VOLUME_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

    // Reads an InputStream and converts it to a String.
    public String convertInputToString(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


    private String createURL(String queryString, String printType, String baseURL){
        // Parameter for the search string
        final String QUERY_PARAM = "q";
        // Parameter to limit search results.
        final String MAX_RESULTS = "maxResults";
        // Parameter to filter by print type
        final String PRINT_TYPE = "printType";

        // Build up the query URI, limiting results to 5 printed books.
        Uri builtURI = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "5")
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();

        return builtURI.toString();
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream inputStream = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            // Start the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            inputStream = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputToString(inputStream, len);
            return contentAsString;

            // Close the InputStream and connection
        } finally {
            conn.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


    public JSONObject getBookInfoJson(String queryString, String printType) {

        String url = this.createURL(queryString, printType, this.BOOK_VOLUME_BASE_URL);
        JSONObject data = null;
        try {
            String info = this.downloadUrl(url);
            data = new JSONObject(info);

            System.out.println(data.toString());


        } catch (IOException | JSONException e) {
            System.out.println("Ha habido un error en BookService: " + e.getStackTrace());
        }

        return data;
    }


}
