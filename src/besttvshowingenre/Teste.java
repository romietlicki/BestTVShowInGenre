package besttvshowingenre;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Teste {
	

    /*
     * Complete the 'bestInGenre' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING genre as parameter.
     *
     * Base URL: https://jsonmock.hackerrank.com/api/tvseries?page=
     */
     
     
    class TVSeries {
    String name;
    String genre;
    double imdb_rating;

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getImdbRating() {
        return imdb_rating;
    }
 }
 
 public static String getResponseFromURL(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();
        }
    }

    public static String bestInGenre(String genre) {
    List<TVSeries> allTVSeries = new ArrayList<>();
        int currentPage = 1;
        int totalPages = 1;

        try {
            Gson gson = new Gson();

            while (currentPage <= totalPages) {
                String url = "https://jsonmock.hackerrank.com/api/tvseries?page=" + currentPage;
                String jsonResponse = getResponseFromURL(url);

                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                totalPages = jsonObject.get("total_pages").getAsInt();

                String dataJson = jsonObject.get("data").toString();
                List<TVSeries> tvSeriesList = gson.fromJson(dataJson, new TypeToken<List<TVSeries>>() {}.getType());

                for (TVSeries tvSeries : tvSeriesList) {
                    if (tvSeries.getGenre().contains(genre)) {
                        allTVSeries.add(tvSeries);
                    }
                }

                currentPage++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allTVSeries.stream()
                .filter(series -> !Double.isNaN(series.getImdbRating()))
                .max(Comparator.comparingDouble(TVSeries::getImdbRating)
                        .thenComparing((serie1, serie2) -> serie1.getName().compareTo(serie2.getName()))) 
                .map(TVSeries::getName)
                .orElse("No series found in the given genre.");
    }


}
