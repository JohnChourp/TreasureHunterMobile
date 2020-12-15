package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.tables.Markers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkersGetRequest {

    private List<Markers> markers;
    private RetroCallBack callBack;
    private Call<List<Markers>> callMarkers;

    public void markersGetRequest() {
        callMarkers = RetroInstance.initializeAPIService().getMarkers();
        callMarkers.enqueue(new Callback<List<Markers>>() {
            @Override
            public void onResponse(@NotNull Call<List<Markers>> call, @NotNull Response<List<Markers>> response) {
                if (!response.isSuccessful()) {
                    callBack.onCallFailed("code: " + response.code());
                    return;
                }

                markers = response.body();
                callBack.onCallFinished("Markers");
            }

            @Override
            public void onFailure(@NotNull Call<List<Markers>> call, @NotNull Throwable t) {
                callBack.onCallFailed(t.getMessage());
            }
        });
    }

    public List<Markers> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Markers> markers) {
        this.markers = markers;
    }

    public Call<List<Markers>> getCallMarkers() {
        return callMarkers;
    }

    public void setCallMarkers(Call<List<Markers>> callMarkers) {
        this.callMarkers = callMarkers;
    }

    public RetroCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(RetroCallBack callBack) {
        this.callBack = callBack;
    }

}
