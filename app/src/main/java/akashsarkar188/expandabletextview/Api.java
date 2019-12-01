package akashsarkar188.expandabletextview;


import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String baseUrl = "http://api.plos.org/";

    @GET("search?q=title:DNA")
    Call<Model> getData();
}
