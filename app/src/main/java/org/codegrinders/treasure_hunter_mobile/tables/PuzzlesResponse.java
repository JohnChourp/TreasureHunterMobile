package org.codegrinders.treasure_hunter_mobile.tables;

import java.util.List;

import retrofit2.Call;

public class PuzzlesResponse {
    private List<Puzzle> call;

    public List<Puzzle> getCall() {
        return call;
    }

    public void setCall(List<Puzzle> call) {
        this.call = call;
    }
}
