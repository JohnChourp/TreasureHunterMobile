package org.codegrinders.treasure_hunter_mobile.retrofit;

import org.codegrinders.treasure_hunter_mobile.model.Markers;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MarkersCallTest {
MarkersCall markersCall = new MarkersCall();

    @Test
    public void whenSearchMarkerByTitleIsCalledWithExistingTitleReturnCorrespondingMarker() {
        markersCall.setMarkers(new ArrayList<>());
        markersCall.getMarkers().add(new Markers());
        markersCall.getMarkers().add(new Markers());

        markersCall.getMarkers().get(0).setMarkerTitle("first title");
        markersCall.getMarkers().get(0).setSnippet("first snippet");

        markersCall.getMarkers().get(1).setMarkerTitle("second title");
        markersCall.getMarkers().get(1).setSnippet("second snippet");

        assertEquals("second snippet",markersCall.searchMarkerByTitle("second title").getSnippet());
        assertEquals("first snippet",markersCall.searchMarkerByTitle("first title").getSnippet());
    }

    @Test
    public void whenSearchMarkerByTitleIsCalledWithNonExistingTitleReturnNull() {
        markersCall.setMarkers(new ArrayList<>());
        markersCall.getMarkers().add(new Markers());
        markersCall.getMarkers().add(new Markers());

        markersCall.getMarkers().get(0).setMarkerTitle("first title");
        markersCall.getMarkers().get(1).setMarkerTitle("second title");

        assertNull(markersCall.searchMarkerByTitle("some title"));
    }
}