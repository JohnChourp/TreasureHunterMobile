package org.codegrinders.treasure_hunter_mobile.retrofit;

public interface RetroCallBack {
    void onCallUsersFinished();
    void onCallPuzzlesFinished();
    void onCallFailed(String errorMessage);
}
