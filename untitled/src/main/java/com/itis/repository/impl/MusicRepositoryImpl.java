package com.itis.repository.impl;

import com.itis.dto.Info;
import com.itis.model.Playlist;
import com.itis.model.Track;
import com.itis.repository.MusicRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MusicRepositoryImpl implements MusicRepository {
    private static final String GET_PLAYLIST_BY_NAME =
            "SELECT * FROM playlists WHERE name = ? and user_id = ?";
    private static final String DELETE_TRACKS_IN_PLAYLIST_BY_PLAYLIST_ID =
            "DELETE FROM PlaylistTracks WHERE playlist_id = ?";
    private static final String DELETE_PLAYLIST_BY_ID =
            "DELETE FROM Playlists WHERE id = ?";
    private static final String DELETE_TRACK_BY_ID =
            "DELETE FROM Tracks WHERE id = ?";
    private static final String GET_ALL_PLAYLIST =
            "SELECT * FROM playlists";

    private static final String GET_INFO =
            "select tr.id as id_track, username, ps.name as playlist_name, tr.year, tr.name AS track_name, u.username from users u " +
                    "inner join usertracks ut on ut.user_id = u.id " +
                    "inner join playlisttracks pl on pl.track_id = ut.track_id " +
                    "inner join playlists ps on ps.id = pl.playlist_id " +
                    "right join tracks tr on tr.id = pl.track_id";
    private static final String UPDATE_TRACKS =
            "UPDATE tracks SET popularity = ? where id = ?";

    private static final String GEA_ALL_TRACKS =
            "SELECT * FROM tracks";
    private static final String GET_TRACK_ORDER =
            "SELECT * FROM tracks ORDER BY popularity DESC LIMIT 10";
    private static final String ADD_TRACK =
            "INSERT INTO tracks (name, artist, album, year, filename) VALUES (?, ?, ?, ?, ?)";

    private static final String ADD_PLAYLIST_TRACK =
            "INSERT INTO playlisttracks (playlist_id, track_id) VALUES (?, ?)";

    private static final String ADD_TRACK_WITH_USER =
            "INSERT INTO usertracks (user_id, track_id) VALUES (?, ?)";
    private static final String GET_PLAYLIST =
            "select * from playlists where user_id = ?";

    private static final String INSERT_PLAYLIST =
            "INSERT INTO playlists (name, user_id) VALUES (?, ?)";

    private static final String GET_TRACKS =
            "SELECT tracks.* FROM tracks " +
                    "INNER JOIN usertracks ON tracks.id = usertracks.track_id " +
                    "WHERE usertracks.user_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public MusicRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public void deleteTrackById(Integer trackId) {
        jdbcTemplate.update(DELETE_TRACK_BY_ID, trackId);
    }

    @Override
    public void deletePlayListTracks(Integer playListId) {
        jdbcTemplate.update(DELETE_TRACKS_IN_PLAYLIST_BY_PLAYLIST_ID, playListId);
    }

    @Override
    public List<Playlist> getPlayListByName(String name, Integer userId) {
        try {
            return jdbcTemplate.query(GET_PLAYLIST_BY_NAME, playlistRowMapper, name, userId);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void deletePlaylistById(Integer playlistId) {
        jdbcTemplate.update(DELETE_PLAYLIST_BY_ID, playlistId);
    }

    @Override
    public List<Playlist> getPlayListByUserId(Integer userId) {
        try {
            return jdbcTemplate.query(GET_PLAYLIST, playlistRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer addPlayList(Playlist playlist) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_PLAYLIST, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, playlist.getName());
                ps.setInt(2, playlist.getUserId());
                return ps;
            }
        }, keyHolder);
        return (Integer) keyHolder.getKeys().get("id");
    }

    @Override
    public List<Track> getTracksByUserId(Integer userId) {
        try {
            return jdbcTemplate.query(GET_TRACKS, trackRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer addTrack(Track track) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(ADD_TRACK, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, track.getName());
                        ps.setString(2, track.getArtist());
                        ps.setString(3, track.getAlbum());
                        ps.setString(4, track.getYear());
                        ps.setString(5, track.getFilename());
                        return ps;
                    }
                },
                keyHolder);

        // Возвращаем сгенерированный ключ
        return (Integer) keyHolder.getKeys().get("id");
    }


    @Override
    public void addTrackWithUser(Integer userId, Integer trackId) {
        jdbcTemplate.update(ADD_TRACK_WITH_USER, userId, trackId);
    }

    @Override
    public void addPlayListTrack(Integer playListId, Integer trackId) {
        jdbcTemplate.update(ADD_PLAYLIST_TRACK, playListId, trackId);
    }

    @Override
    public List<Track> getOrderTracks() {
        try {
            return jdbcTemplate.query(GET_TRACK_ORDER, trackRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Track> getAllTracks() {
        try {
            return jdbcTemplate.query(GEA_ALL_TRACKS, trackRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void updateTrack(Track track) {
        jdbcTemplate.update(UPDATE_TRACKS, track.getPopularity(), track.getId());
    }

    @Override
    public List<Info> getInfo() {
        try {
            return jdbcTemplate.query(GET_INFO, infoRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Playlist> getAllPlayList() {
        try {
            return jdbcTemplate.query(GET_ALL_PLAYLIST, playlistRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    private static final RowMapper<Info> infoRowMapper = (row, rowNumber) -> {
        Info info = new Info();
        info.setTrackId(row.getInt("id_track"));
        info.setCreateDate(row.getString("year"));
        info.setTrackName(row.getString("track_name"));
        info.setPlayListName(row.getString("playlist_name"));
        info.setUserName(row.getString("username"));
        return info;
    };

    private static final RowMapper<Track> trackRowMapper = (row, rowNumber) -> {
        Track track = new Track();
        track.setId(row.getInt("id"));
        track.setName(row.getString("name"));
        track.setArtist(row.getString("artist"));
        track.setAlbum(row.getString("album"));
        track.setYear(row.getString("year"));
        track.setFilename(row.getString("filename"));
        track.setPopularity(row.getInt("popularity"));
        return track;
    };
    private static final RowMapper<Playlist> playlistRowMapper = (row, rowNumber) -> {
        Playlist playlist = new Playlist();
        playlist.setId(row.getInt("id"));
        playlist.setName(row.getString("name"));
        playlist.setUserId(row.getInt("user_id"));
        return playlist;
    };
}
