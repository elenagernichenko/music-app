package com.itis.service;

import com.itis.dto.Info;
import com.itis.model.Playlist;
import com.itis.model.Track;

import java.util.List;

public interface MusicService {

    List<Track> getTracks(Integer userId);

    List<Playlist> getPlaylist(Integer userId);

    void addTrackWithUser(Integer userId, Integer trackId);

    Integer addTrack(Track track);

    Integer addPlayList(Playlist playlist);

    void addPlayListTrack(Integer playListId, Integer trackId);

    List<Track> geOrderTracks();

    List<Track> getAllTracks();

    void updateTrack(Track track);

    List<Info> getInfo();

    List<Playlist> getAllPlayList();

    void deleteTrackById(Integer trackId);

    void deletePlayListById(Integer playListId);

    void deletePlayListTracks(Integer playListId);

    List<Playlist> getPlayListByName(String name, Integer userId);
}
