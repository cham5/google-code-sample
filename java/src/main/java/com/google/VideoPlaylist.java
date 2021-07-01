package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist implements Comparable<VideoPlaylist> {
    private List<Video> videoPlaylist;
    private String playlistName;

    public VideoPlaylist(String playlistName) {
        this.videoPlaylist = new ArrayList<>();
        this.playlistName = playlistName;
    }

    public String getName() {
        return playlistName;
    }

    public List<Video> getPlaylist() {
        return videoPlaylist;
    }

    public void addVideo(Video video) {
        videoPlaylist.add(video);
    }

    public void removeVideo(Video video) {
        videoPlaylist.remove(video);
    }

    public void clear() {
        videoPlaylist.clear();
    }

    public int compareTo(VideoPlaylist other) {
        return playlistName.compareTo(other.playlistName);
    }

    public String toString() {
        return playlistName;
    }
}
