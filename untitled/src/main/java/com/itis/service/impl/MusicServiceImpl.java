package com.itis.service.impl;

import com.itis.dto.Info;
import com.itis.model.Playlist;
import com.itis.model.Track;
import com.itis.repository.MusicRepository;
import com.itis.service.MusicService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;

    public MusicServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public List<Track> getTracks(Integer userId) {
        log.info("MusicServiceImpl getTracks userId - {}", userId);
        return musicRepository.getTracksByUserId(userId);
    }

    @Override
    public List<Playlist> getPlaylist(Integer userId) {
        log.info("MusicServiceImpl getPlayList userId - {}", userId);
        return musicRepository.getPlayListByUserId(userId);
    }

    @Override
    public void addTrackWithUser(Integer userId, Integer trackId) {
        log.info("MusicServiceImpl addTrackWithUser userId - {}", userId);
        musicRepository.addTrackWithUser(userId, trackId);
    }

    @Override
    public Integer addTrack(Track track) {
        log.info("MusicServiceImpl addTrack track - {}", track);
        return musicRepository.addTrack(track);
    }

    @Override
    public Integer addPlayList(Playlist playlist) {
        log.info("MusicServiceImpl addPlayList playlist - {}", playlist.getName());
        return musicRepository.addPlayList(playlist);
    }

    @Override
    public void addPlayListTrack(Integer playListId, Integer trackId) {
        log.info("MusicServiceImpl addPlayListTrack playListId - {}", playListId);
        musicRepository.addPlayListTrack(playListId, trackId);
    }

    @Override
    public List<Track> geOrderTracks() {
        log.info("MusicServiceImpl getOrderTracks");
        return musicRepository.getOrderTracks();
    }

    @Override
    public List<Track> getAllTracks() {
        log.info("MusicServiceImpl getAllTracks");
        return musicRepository.getAllTracks();
    }

    @Override
    public void updateTrack(Track track) {
        log.info("MusicServiceImpl updateTrack track - {}", track);
        musicRepository.updateTrack(track);
    }

    @Override
    public List<Info> getInfo() {
        log.info("MusicServiceImpl getInfo");
        return musicRepository.getInfo();
    }

    @Override
    public List<Playlist> getAllPlayList() {
        log.info("MusicServiceImpl getAllPlayList");
        return musicRepository.getAllPlayList();
    }

    @Override
    public void deleteTrackById(Integer trackId) {
        log.info("MusicServiceImpl deleteTrackById trackId - {}", trackId);
        musicRepository.deleteTrackById(trackId);
    }

    @Override
    public void deletePlayListById(Integer playListId) {
        log.info("MusicServiceImpl deletePlayListById playListId - {}", playListId);
        musicRepository.deletePlaylistById(playListId);
    }

    @Override
    public void deletePlayListTracks(Integer playListId) {
        log.info("MusicServiceImpl deletePlayListTracks playListId - {}", playListId);
        musicRepository.deletePlayListTracks(playListId);
    }

    @Override
    public List<Playlist> getPlayListByName(String name, Integer userId) {
        log.info("MusicServiceImpl getPlayListByName name - {} userId - {}", name, userId);
        return musicRepository.getPlayListByName(name, userId);
    }
}
