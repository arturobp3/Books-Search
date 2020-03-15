package es.ucm.fdi.bookssearcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {

    private String title, authors; //Los magazines parece que no tienen author
    private URL infoLink;

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public URL getInfoLink() {
        return infoLink;
    }

    public BookInfo(String title, String authors, URL infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    public static List<BookInfo> fromJsonResponse(String info) throws JSONException, MalformedURLException {

        URL infoLink = null;
        String title = "", authors = "";
        List<BookInfo> booksList = null;
        JSONObject data = new JSONObject(info);

        if((int)data.get("totalItems") > 0){
            booksList = new ArrayList<>();
            JSONArray itemsArray = (JSONArray) data.get("items");

            for(int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                if(book.has("volumeInfo")){
                    JSONObject volumeInfo = (JSONObject) book.get("volumeInfo");
                    if(volumeInfo.has("authors")) {
                        JSONArray authorsArray = (JSONArray) volumeInfo.get("authors");
                        authors = "";

                        if(authorsArray.length() > 0) {
                            authors += authorsArray.get(0);

                            for (int j = 1; j < authorsArray.length(); j++) {
                                authors += ", " + authorsArray.get(j);
                            }
                        }
                    }
                    if(volumeInfo.has("title")) {
                        title = volumeInfo.getString("title");
                    }
                    if(volumeInfo.has("infoLink")) {
                        infoLink = new URL(volumeInfo.getString("infoLink"));
                    }
                }
                BookInfo bookInfo = new BookInfo(title, authors, infoLink);
                booksList.add(bookInfo);
            }
        }

        return booksList;
    }

}
