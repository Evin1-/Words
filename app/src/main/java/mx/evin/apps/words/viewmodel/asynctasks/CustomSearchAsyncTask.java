package mx.evin.apps.words.viewmodel.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import mx.evin.apps.words.model.entities.gsearch.CustomSearch;
import mx.evin.apps.words.model.entities.gsearch.Item;
import mx.evin.apps.words.view.fragments.SearchTermGoogleFragment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by evin on 1/16/16.
 */
public class CustomSearchAsyncTask extends AsyncTask<String, Item, Void>{
    //TODO Remove http listener
    //TODO What happens if no results, or above limit?

    private static final String TAG_ = "CustomSearchATTAG_";

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Item... values) {
        super.onProgressUpdate(values);
        if (values.length > 0){
            SearchTermGoogleFragment.mItems.add(values[0]);
            SearchTermGoogleFragment.mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected Void doInBackground(String... params) {

        if (params.length != 3){
            Log.i(TAG_, "CustomSearchAsyncTask usage: $ query key1 key2");
            return null;
        }

        final String query = params[0];
        final String google_api_key = params[1];
        final String google_custom_search_key = params[2];

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        GoogleSearch googleSearch = retrofit.create(GoogleSearch.class);
        Call<CustomSearch> searchCall = googleSearch.getCustomSearch(query, google_custom_search_key, google_api_key);

        try{
            CustomSearch customSearch = searchCall.execute().body();
            for (Item item : customSearch.getItems()){
                publishProgress(item);
            }
//            customSearch.getItems()
//            Log.e(TAG_, String.valueOf(customSearch));
        }catch (Exception e){
            Log.e(TAG_, e.toString());
        }

        return null;
    }

    public interface GoogleSearch {
        @GET("/customsearch/v1")
        Call<CustomSearch> getCustomSearch(@Query("q") String query, @Query("cx") String custom_search_key, @Query("key") String google_api_key);
    }
}
