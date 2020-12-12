package org.codegrinders.treasure_hunter_mobile.tables;

public class Marker {

    private String id;
    private double latitude;
    private double longitude;
    private String markerTile;
    private String snippet;
    private String puzzleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMarkerTile() {
        return markerTile;
    }

    public void setMarkerTile(String markerTile) {
        this.markerTile = markerTile;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(String puzzleId) {
        this.puzzleId = puzzleId;
    }
}
