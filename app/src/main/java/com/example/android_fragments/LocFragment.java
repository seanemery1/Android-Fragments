package com.example.android_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LocFragment extends Fragment {

    private static final String api_url = "https://rickandmortyapi.com/api/location";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private List<Location> locations;
    private RecyclerView recyclerView;


    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location, container, false);
        this.recyclerView = v.findViewById(R.id.recyclerView_locations);
        this.viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        this.locations = new ArrayList<>();

        Log.d("api input", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                String json = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray locationsArray = jsonObject.getJSONArray("results");
                    Log.d("api loop", locationsArray.toString());
                    for (int i = 0; i < locationsArray.length(); i++) {
                        JSONObject locTemp = locationsArray.getJSONObject(i);
                        Location location = new Location(
                                locTemp.getString("name"),
                                locTemp.getString("type"),
                                locTemp.getString("dimension")
                        );
                        locations.add(location);
                    }
                    viewModel.setLocInfo(locations);

                    viewModel.getLocInfo().observe(getViewLifecycleOwner(), loc -> {
                        if (loc != null) {
                            setLocInfo(loc);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });

        return v;
    }

    public void setLocInfo(List<Location> locations) {

        Log.d("adapter", "yeah it works");
        Log.d("location", locations.get(1).getName());

        LocationAdapter adapter = new LocationAdapter(locations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }
}