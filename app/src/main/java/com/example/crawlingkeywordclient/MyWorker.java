package com.example.crawlingkeywordclient;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Create Retrofit instance
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        // Create API service
        MyApiService service = retrofit.create(MyApiService.class);

        // Make HTTP request
        try {
            Log.i("HTTP Request", "Making HTTP request...");
            Call<NewTitlesResponse> call = service.getNewTitles(BuildConfig.SECRET_KEY);
            Response<NewTitlesResponse> response = call.execute();

            if (response.isSuccessful()) {
                // Show notification with the result
                NewTitlesResponse newTitlesResponse = response.body();
                showNotification("HTTP Request Result", newTitlesResponse.toString());
                Log.i("HTTP Request Result", newTitlesResponse.toString());
            } else {
                // Show notification with the error message
                showNotification("HTTP Request Error", "Your HTTP request failed: " + response.errorBody().string());
                Log.i("HTTP Request Error", response.errorBody().string());
            }
        } catch (Exception e) {
            // Show notification with the exception message
            showNotification("HTTP Request Exception", "An exception occurred: " + e.getMessage());
            Log.e("HTTP Request Exception", e.getMessage());
        }

        return Result.success();
    }

    private void showNotification(String title, String message) {
        String channelId = "channel_id";
        String channelName = "channel_name";

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat.from(getApplicationContext()).notify(0, builder.build());
    }
}