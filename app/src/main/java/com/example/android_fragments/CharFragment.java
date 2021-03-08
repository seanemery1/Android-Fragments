package com.example.android_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class CharFragment extends Fragment{

    private static final String api_url = "https://rickandmortyapi.com/api/character";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private TextView name;
    private TextView status;
    private TextView species;
    private ImageView image;
    private TextView gender;
    private TextView origin;
    private TextView location;
    private TextView appearances;

    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character, container, false);

        this.name = v.findViewById(R.id.textView_charName);
        this.status = v.findViewById(R.id.textView_charStatus);
        this.species = v.findViewById(R.id.textView_charSpecies);
        this.image = v.findViewById(R.id.imageView_charImg);
        this.gender = v.findViewById(R.id.textView_charGender);
        this.origin = v.findViewById(R.id.textView_charOrigin);
        this.location = v.findViewById(R.id.textView_charLocation);
        this.appearances = v.findViewById(R.id.textView_charAppearances);
        
        this.viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Log.d("api input", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                String json1 = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(json1);
                    Random rand = new Random();
                    int charCount = jsonObject.getJSONObject("info").getInt("count");
                    int charIndex = rand.nextInt(charCount) + 1;
                    Log.d("api input", api_url + "/" + charIndex);
                    client.get(api_url + "/" + charIndex, new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Log.d("api response", new String(responseBody));
                                    String json2 = new String(responseBody);
                                    try {
                                        JSONObject chr = new JSONObject(json2);
                                        String appearances = "";
                                        for (int i = 0; i < chr.getJSONArray("episode").length(); i++) {
                                            if (i != chr.getJSONArray("episode").length()-1) {
                                                appearances = appearances + chr.getJSONArray("episode").getString(i).replace("https://rickandmortyapi.com/api/episode/", "") + " ";
                                            } else {
                                                appearances = appearances + chr.getJSONArray("episode").getString(i).replace("https://rickandmortyapi.com/api/episode/", "");
                                            }

                                        }
                                        Character character = new Character(
                                                chr.getString("name"),
                                                chr.getString("status"),
                                                chr.getString("species"),
                                                chr.getString("image"),
                                                chr.getString("gender"),
                                                chr.getJSONObject("origin").getString("name"),
                                                chr.getJSONObject("location").getString("name"),
                                                appearances
                                        );
                                        viewModel.setCharInfo(character);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Log.e("api error", new String(responseBody));
                                }
                            });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //intent.putExtra("json", json);
                //startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });

        viewModel.getCharInfo().observe(getViewLifecycleOwner(), new Observer<Character>() {
            @Override
            public void onChanged(Character chr) {
                if (chr != null) {
                    setCharInfo(chr);
                }
            }
        });
        
        return v;
    }

    public void setCharInfo(Character character) {
        this.name.setText(character.getName());
        String tempStatus = "Status: " + character.getStatus();
        this.status.setText(tempStatus);
        String tempSpecies = "Species: " + character.getSpecies();
        this.species.setText(tempSpecies);
        Picasso.get().load(character.getImageURL()).into(this.image);
        String tempGender = "Gender: " + character.getGender();
        this.gender.setText(tempGender);
        String tempOrigin = "Origin: " + character.getOrigin();
        this.origin.setText(tempOrigin);
        String tempLocation = "Location: " + character.getLocation();
        this.location.setText(tempLocation);
        String tempAppearances = "Appeared in Episodes: " + character.getAppearances();
        this.appearances.setText(tempAppearances);
        Log.d("appearances", character.getAppearances());
    }
}