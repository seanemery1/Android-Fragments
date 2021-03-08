package com.example.android_fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class EpFragment extends Fragment {
    private static final String api_url = "https://rickandmortyapi.com/api/episode";
    private static final String CHANNEL_ID = "my_channel_01";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private TextView name;
    private TextView airDate;
    private TextView charInfo;
    private RecyclerView recyclerView;
    private Button moreInfo;

    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_episode, container, false);

        this.name = v.findViewById(R.id.textView_epName);
        this.airDate= v.findViewById(R.id.textView_epAirDate);
        this.charInfo = v.findViewById(R.id.textView_epCharInfo);
        this.recyclerView = v.findViewById(R.id.recyclerView_epCharImgs);
        this.moreInfo = v.findViewById(R.id.button_epMoreInfo);

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
                    int epCount = jsonObject.getJSONObject("info").getInt("count");
                    int epIndex = rand.nextInt(epCount) + 1;
                    Log.d("api input", api_url + "/" + epIndex);
                    client.get(api_url + "/" + epIndex, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Log.d("api response", new String(responseBody));
                            String json2 = new String(responseBody);
                            try {
                                JSONObject ep = new JSONObject(json2);
                                List<String> charImgs = new ArrayList<>();
                                for (int i = 0; i < ep.getJSONArray("characters").length(); i++) {
                                    charImgs.add(ep.getJSONArray("characters").getString(i).replace("character/", "character/avatar/") + ".jpeg");
                                }
                                Episode episode = new Episode(
                                        ep.getString("episode"),
                                        ep.getString("name"),
                                        ep.getString("air_date"),
                                        charImgs
                                );
                                viewModel.setEpInfo(episode);

                                viewModel.getEpInfo().observe(getViewLifecycleOwner(), ep1 -> {
                                    if (ep1 != null) {
                                        setEpInfo(ep1);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });

        moreInfo.setOnClickListener(view -> createNotificationChannel(view));
        return v;
    }

//    private void launchActivity(View view) {
//        if (viewModel.getEpInfo()!=null) {
//            String url = viewModel.getEpInfo().getValue().getUrl();
//            Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(url));
//            Log.d("url", url);
//
//            if (intent.resolveActivity(getActivity().getPackageManager())!=null) {
//                startActivity(intent);
//            } else {
//                Log.e("ImplicitIntent", "Cannot handle this intent.");
//            }
//        }
//    }

    public void setEpInfo(Episode episode) {
        EpCharImgAdapter adapter = new EpCharImgAdapter(requireContext(), episode.getCharImgs());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecoration);
        this.name.setText(episode.getTitle());
        String tempDate = "Air Date: " + episode.getAirDate();
        this.airDate.setText(tempDate);
        //this.moreInfo.setText(episode.getUrl());
    }

    private void createNotificationChannel(View view) {
        int NOTIFICATION_ID = 234;
        NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.setShowBadge(false);
            notificationManager.createNotificationChannel(channel);
        }



        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("To read more information about Episode " +
                                viewModel.getEpInfo().getValue().getEpisode() + ", please visit: " +
                                viewModel.getEpInfo().getValue().getUrl()))
                .setContentTitle(viewModel.getEpInfo().getValue().getTitle());

        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(viewModel.getEpInfo().getValue().getUrl()));
        Log.d("url", viewModel.getEpInfo().getValue().getUrl());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(requireContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}