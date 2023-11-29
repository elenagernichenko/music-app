package com.itis.util;

import com.itis.model.Track;
import com.itis.service.MusicService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PopularitySet {

    private final MusicService musicService;

    public PopularitySet(MusicService musicService) {
        this.musicService = musicService;
    }

    public void setPopularity() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            List<Track> allTracks = musicService.getAllTracks();
            if (!allTracks.isEmpty()) {
                log.info("update popularity track");
                for (Track track : allTracks) {
                    track.setPopularity(new Random().nextInt(10) + 1);
                    musicService.updateTrack(track);
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}
