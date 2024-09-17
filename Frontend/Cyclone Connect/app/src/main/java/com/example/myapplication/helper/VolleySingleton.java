package com.example.myapplication.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
/**
 * @author Abhay Prasanna Rao
 * <p>
 * Singleton class that encapsulates the Volley library's RequestQueue and ImageLoader
 * for network operations and image caching, ensuring efficient resource management.
 * This class provides a centralized access point for network requests and image loading
 * within the application.
 *
 */

public class VolleySingleton {
    @SuppressLint("StaticFieldLeak")
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;
    /**
     * Private constructor to initialize the singleton instance.
     *
     * @param context The application context.
     */
    private VolleySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }
    /**
     * Provides the global singleton instance and initializes it if necessary.
     *
     * @param context The application context.
     * @return The singleton instance of VolleySingleton.
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }
    /**
     * Returns the application's RequestQueue, creating it if it is not already initialized.
     *
     * @return The RequestQueue for managing network requests.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    /**
     * Adds a request to the RequestQueue for execution.
     *
     * @param req The request to add to the queue.
     * @param <T> The type of the request's response.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    /**
     * Returns the ImageLoader for loading and caching images.
     *
     * @return The ImageLoader instance.
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    /**
     * Initiates a GET request to the specified URL.
     *
     * @param url The URL for the GET request.
     * @param responseListener Listener for successful responses.
     * @param errorListener Listener for error responses.
     */
    // Method for sending GET requests
    public void sendGetRequest(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        addToRequestQueue(jsonObjectRequest);
    }
    /**
     * Initiates a POST request with the specified URL and request body.
     *
     * @param url The URL for the POST request.
     * @param requestBody The body of the POST request.
     * @param responseListener Listener for successful responses.
     * @param errorListener Listener for error responses.
     */
    // Method for sending POST requests
    public void sendPostRequest(String url, JSONObject requestBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, responseListener, errorListener);
        addToRequestQueue(jsonObjectRequest);
    }
    /**
     * Initiates a PUT request with the specified URL and request body.
     *
     * @param url The URL for the PUT request.
     * @param requestBody The body of the PUT request.
     * @param responseListener Listener for successful responses.
     * @param errorListener Listener for error responses.
     */
    // Adding methods for PUT and DELETE requests
    // Method for sending PUT requests
    public void sendPutRequest(String url, JSONObject requestBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody, responseListener, errorListener);
        addToRequestQueue(jsonObjectRequest);
    }
    /**
     * Initiates a DELETE request to the specified URL.
     *
     * @param url The URL for the DELETE request.
     * @param responseListener Listener for successful responses.
     * @param errorListener Listener for error responses.
     */
    // Method for sending DELETE requests
    public void sendDeleteRequest(String url, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, responseListener, errorListener);
        addToRequestQueue(stringRequest);
    }
}
