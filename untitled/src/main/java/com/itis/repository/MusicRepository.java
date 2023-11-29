package com.itis.repository;

import com.itis.dto.Info;
import com.itis.model.Playlist;
import com.itis.model.Track;

import java.util.List;

public interface MusicRepository {
    List<Playlist> getPlayListByUserId(Integer userId);

    Integer addPlayList(Playlist playlist);

    List<Track> getTracksByUserId(Integer userId);

    Integer addTrack(Track track);

    void addTrackWithUser(Integer userId, Integer trackId);

    void addPlayListTrack(Integer playListId, Integer trackId);

    List<Track> getOrderTracks();

    List<Track> getAllTracks();

    void updateTrack(Track track);

    List<Info> getInfo();

    List<Playlist> getAllPlayList();

    void deletePlaylistById(Integer playList);

    void deleteTrackById(Integer trackId);

    void deletePlayListTracks(Integer playListId);
    List<Playlist> getPlayListByName(String name,Integer userId);
}
