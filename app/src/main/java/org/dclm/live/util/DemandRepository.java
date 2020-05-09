package org.dclm.live.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import org.dclm.live.R;
import org.dclm.live.ui.blog.Blog;
import org.dclm.live.ui.experience.Testimony;
import org.dclm.live.ui.listen.SubTitle;
import org.dclm.live.ui.ondemand.OnDemand;
import org.dclm.live.ui.ondemand.search.Search;
import org.dclm.live.util.NetworkCall;
import org.dclm.live.util.PodcastNetworkCall;
import org.dclm.live.util.SearchNetworkCall;
import org.dclm.live.util.VolleyRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemandRepository {
    private RequestQueue mQueue;
    private List<OnDemand> blogs;
    private Context context;
    private PodcastNetworkCall networkCall;
    private SearchNetworkCall searchNetworkCall;
    private NetworkCall networkCal;
    private SubtitleReceived subtitleReceived;
    private TestimonyRecieved testimonyRecieved;

    public DemandRepository(Context context, PodcastNetworkCall networkCall) {
        //mQueue = Volley.newRequestQueue(context.getApplicationContext());
        mQueue = VolleyRequest.getVolley(context);
        this.context = context;
        this.networkCall = networkCall;
    }

    public DemandRepository(Context context, SearchNetworkCall networkCall){
       // mQueue = Volley.newRequestQueue(context.getApplicationContext());
        this.context = context;
        mQueue = VolleyRequest.getVolley(context);
        this.searchNetworkCall = networkCall;
    }

    public DemandRepository(Context context, NetworkCall networkCall){
        this.context = context;
        this.networkCal = networkCall;
        mQueue = VolleyRequest.getVolley(context);
    }

    public DemandRepository(Context context, SubtitleReceived subtitleReceived){
        this.context = context;
        this.subtitleReceived = subtitleReceived;
        mQueue = VolleyRequest.getVolley(context);
    }

    public DemandRepository(Context context, TestimonyRecieved testimonyRecieved){
        this.context = context;
        this.testimonyRecieved = testimonyRecieved;
        mQueue = VolleyRequest.getVolley(context);
    }

    public void categoryJson() {
        List<OnDemand.Category> categories = new ArrayList<>();
        if (context != null) {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://api.dclmict.org/v1/sermon/category", null, response -> {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id = object.getInt("categoryId");
                        String category = object.getString("categoryName");
                        categories.add(new OnDemand.Category(id, category));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (networkCall != null){
                    networkCall.categoryRecieved(categories);
                }

            }, error -> {

            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                    headers.put("app", "dclm-radio");
                    return headers;

                }
            };
            mQueue.add(request);

        }
    }


    public void jsonParse(String url, int page, int languageID, List<OnDemand.Category> categories) {
        List<OnDemand> onDemands = new ArrayList<>();
        List<OnDemand.CheckState> checkStates = new ArrayList<>();
        if (context != null) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + String.valueOf(page), null, response -> {
                try {
                    JSONObject object = response.getJSONObject("meta");
                    int count = Integer.parseInt(object.getString("count"));
                    int count2 = count - ((page - 1) * 15) - 1;
                    for (int i = 0; i < 20; i++) {
                        JSONObject object2 = response.getJSONObject("result");
                        JSONObject object3 = object2.getJSONObject("data");
                        try {
                            JSONObject object4 = object3.getJSONObject(String.valueOf(count2));
                            String sermonTitle = object4.getString("sermonTitle");
                            String date = object4.getString("sermonDate");
                            String sermonLow = object4.getString("sermonLow");
                            String sermonHigh = object4.getString("sermonHigh");
                            String sermonAudio = object4.getString("sermonAudio");
                            int languageId = object4.getInt("languageId");
                            int category = object4.getInt("categoryId");
                            if (languageId == languageID){
                                if (languageId == 1){
                                    onDemands.add(new OnDemand(sermonTitle, date, sermonHigh, sermonLow, sermonAudio, categories.get(category - 1).getCategory()));
                                } else {
                                    onDemands.add(new OnDemand(sermonTitle, date, sermonLow, sermonHigh, sermonAudio, categories.get(category - 1).getCategory()));
                                }
                            }
                        }catch (JSONException E){
                            E.printStackTrace();
                        }
                        count2 --;
                        //Log.i(" count", date);
                    }

                    for (int i = 0; i < onDemands.size(); i++) {
                        checkStates.add(new OnDemand.CheckState(i, false));
                        // Log.i(" count", String.valueOf(false));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (networkCall != null){
                    networkCall.podcastRecieved(onDemands, checkStates);
                }
            }, error -> {
              if (networkCall != null){
                  networkCall.error(false);
              }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                    headers.put("app", "dclm-radio");
                    return headers;

                }
            };
            mQueue.add(request);
        }

    }

    public void getEvents( int page){
      List<Search> searches = new ArrayList<>();
        String url = "https://api.dclmict.org/v1/sermon/event";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = (response.length() - ((page -1) *20)); i > (response.length() -(page * 20)) ; i--) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String id = String.valueOf(jsonObject.getInt("eventId"));
                    String title = jsonObject.getString("eventCode");
                    String subTitle = jsonObject.getString("eventTheme");
                    searches.add(new Search(id, title, subTitle, "", "", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (searchNetworkCall != null){
                searchNetworkCall.onRecieved(searches);
            }

        }, error -> {

        }

        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                headers.put("app", "dclm-radio");
                return headers;

            }
        };
        mQueue.add(jsonArrayRequest);
    }

    public void jsonEvent(String eventID, String languageId, String url, int page){
       List<Search> searches = new ArrayList<>();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(	"event", eventID);
                jsonBody.put("language",languageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + String.valueOf(page), jsonBody,
               response -> {
                   for (int i = 5000; i >0 ; i--) {
                       try {
                          JSONObject object2 = response.getJSONObject("result");
                           JSONObject object3 = object2.getJSONObject("data");
                           try {
                               JSONObject object4 = object3.getJSONObject(String.valueOf(i));
                               String sermonTitle = object4.getString("sermonTitle");
                               String date = object4.getString("sermonDate");
                               String sermonLow = object4.getString("sermonLow");
                               String sermonHigh = object4.getString("sermonHigh");
                               String sermonAudio = object4.getString("sermonAudio");
                               if(languageId.equals("1")){
                                   searches.add(new Search(String.valueOf(i), sermonTitle, date, sermonAudio, sermonHigh, sermonLow));
                               } else {
                                   searches.add(new Search(String.valueOf(i), sermonTitle, date, sermonAudio, sermonLow, sermonHigh));
                               }
                           }catch (JSONException e){
                               e.printStackTrace();
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       if (searchNetworkCall != null){
                           searchNetworkCall.onRecieved(searches);
                       }

                   }
               }, error -> {

       }){
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String, String> headers = new HashMap<String, String>();
               headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
               headers.put("app", "dclm-radio");
               return headers;

           }
       };
        mQueue.add(jsonObjectRequest);
    }

    public void jsonTopic(String topic, String languageId, String url, int page){
        List<Search> searches = new ArrayList<>();

        //Toast.makeText(context, "mtopic is: " + mtopic, Toast.LENGTH_SHORT).show();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(	"title", topic);
            jsonBody.put("language",languageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + String.valueOf(page), jsonBody,
                response -> {
                    for (int i = 5000; i >0 ; i--) {
                        try {
                            JSONObject object2 = response.getJSONObject("result");
                            JSONObject object3 = object2.getJSONObject("data");
                            try {
                                JSONObject object4 = object3.getJSONObject(String.valueOf(i));
                                String sermonTitle = object4.getString("sermonTitle");
                                String date = object4.getString("sermonDate");
                                String sermonLow = object4.getString("sermonLow");
                                String sermonHigh = object4.getString("sermonHigh");
                                String sermonAudio = object4.getString("sermonAudio");
                                if(languageId.equals("1")){
                                    searches.add(new Search(String.valueOf(i), sermonTitle, date, sermonAudio, sermonHigh, sermonLow));
                                } else {
                                    searches.add(new Search(String.valueOf(i), sermonTitle, date, sermonAudio, sermonLow, sermonHigh));
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (searchNetworkCall != null){
                            searchNetworkCall.onRecieved(searches);

                        }

                    }
                }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                headers.put("app", "dclm-radio");
                return headers;

            }
        };
        mQueue.add(jsonObjectRequest);
    }

    public void getBlogs(int page){
        List<Blog> blog = new ArrayList<>();
        if (context != null) {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://dclm.org/wp-json/wp/v2/posts?page=" + page, null,
                    response -> {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                JSONObject jsonObject = object.getJSONObject("title");
                                String title = jsonObject.getString("rendered");
                                String time = object.getString("date_gmt");
                                JSONObject jsonObject1 = object.getJSONObject("content");
                                String content = jsonObject1.getString("rendered");
                                blog.add(new Blog(title, time, content));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (networkCal != null){
                            networkCal.blogRecieved(blog);
                        }

                    }, error -> {
                error.printStackTrace();
                if (networkCall != null && mQueue != null) {
                    //networkCall.dataError(error.networkResponse.statusCode);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                    headers.put("app", "dclm-radio");
                    return headers;

                }
            };
            mQueue.add(request);
        }
    }

    public void jsonParse(String url) {

        if (context != null) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONObject nowPlaying = response.getJSONObject("now_playing");
                            JSONObject song = nowPlaying.getJSONObject("song");
                            String minister = song.getString("artist");
                            String topic = song.getString("title");
                            JSONObject listeners = response.getJSONObject("listeners");
                            String number = listeners.getString("total");
                            String listining = " ";
                            if (context != null){
                                listining =context.getResources().getString(R.string.listning) + number;
                            } else {
                                listining = number;
                            }
                            if (subtitleReceived != null){
                                SubTitle subTitle = new SubTitle(topic, minister, listining);
                                subtitleReceived.subtitle(subTitle);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        SubTitle subTitle;
                        if(context != null){
                            subTitle = new SubTitle(context.getResources().getString(R.string.message), context.getResources().getString(R.string.ministering), " ");
                        } else {
                            subTitle = new SubTitle(" ", " ", " ");
                        }
                        if (subtitleReceived != null){
                            subtitleReceived.error(subTitle);
                        }
                       /* title.setText(R.string.message);
                        preacher.setText(R.string.ministering);
                        upNext.setText(" ");*/
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                    headers.put("app", "dclm-radio");
                    return headers;

                }
            };

            mQueue.add(request);
        }
    }

    public void parseTestimony(){
        List<Testimony> testimonies = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.dclmict.org/v1/webcast/testimonylist", null,
                response -> {
                    try {
                        JSONObject meta = response.getJSONObject("meta");
                        int count = meta.getInt("count");
                        for (int i = count; i >0 ; i--) {
                                JSONObject result = response.getJSONObject( "result");
                                JSONObject data = result.getJSONObject("data");
                            try {
                                JSONObject id = data.getJSONObject(String.valueOf(i));
                                String name = id.getString("name");
                                String subject = id.getString("subject");
                                String testimony = id.getString("testimony");
                                testimonies.add(new Testimony(name, subject, testimony));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (testimonyRecieved != null){
                        testimonyRecieved.onRecieved(testimonies);
                    }

                }, error -> {

        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Api-Token", "0f6add16057d571c0b001afe488b93296685de1b476450a21ecd15df97af9d64ab076a52b77a28db3eb1a0e67f71d88512dbb63c3f3009453472231e37ded2ed");
                headers.put("app", "dclm-radio");
                return headers;

            }
        };
        mQueue.add(jsonObjectRequest);
    }

}
