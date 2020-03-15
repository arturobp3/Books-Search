package es.ucm.fdi.bookssearcher;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.List;

public class BookService {

    private static final String DEBUG_TAG = BookService.class.getSimpleName();
    private final String BOOK_VOLUME_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

    // Reads an InputStream and converts it to a String.
    public String convertInputToString(InputStream stream) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader (stream,"UTF-8"));
        String line = "";
        String result = "";
        while ((line = reader.readLine()) != null){
            result += line;
        }
        stream.close();
        return result;
    }


    private String createURL(String queryString, String printType, String baseURL) {
        // Parameter for the search string
        final String QUERY_PARAM = "q";
        // Parameter to limit search results.
        final String MAX_RESULTS = "maxResults";
        // Parameter to filter by print type
        final String PRINT_TYPE = "printType";
        final String ORDER_BY = "orderBy";

        // Build up the query URI, limiting results to 5 printed books.
        Uri builtURI = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "5")
                .appendQueryParameter(PRINT_TYPE, printType)
                .appendQueryParameter(ORDER_BY, "relevance")
                .build();

        return builtURI.toString();
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream responseBody = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int response = conn.getResponseCode();
            if(response == 200){
                Log.d(DEBUG_TAG, "The code response is: " + response);
                responseBody = conn.getInputStream();
                String contentAsString = convertInputToString(responseBody);
                Log.d(DEBUG_TAG, "The string is: " + contentAsString);
                return contentAsString;
            } else{
                return null;
            }
            // Close the InputStream and connection
        } finally {
            conn.disconnect();
            if (responseBody != null) {
                responseBody.close();
            }
        }
    }


    public List<BookInfo> getBookInfoJson(String queryString, String printType) {
        String url = this.createURL(queryString, printType, this.BOOK_VOLUME_BASE_URL);
        List<BookInfo> books = null;
        try {
            String info = this.downloadUrl(url);
            books = BookInfo.fromJsonResponse(info);

        } catch (IOException | JSONException e) {
            System.out.println("Ha habido un error en BookService: " + e.getMessage());
        }
        Log.d(DEBUG_TAG, "The JSON is: " + books);
        return books;
    }

}


