package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentVideo;
  private boolean isPaused;
  private Map<String, VideoPlaylist> videoPlaylists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.videoPlaylists = new HashMap<>();
    currentVideo = null;
    isPaused = false;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos = videoLibrary.getVideos();
    Collections.sort(videos);
    for(Video video : videos) {
      System.out.println("\t" + video);
    }
  }

  public void playVideo(String videoId) {
    List<Video> videos = videoLibrary.getVideos();
    for (Video video : videos) {
      if (video.getVideoId().equals(videoId)) {
        if(currentVideo != null) {
          System.out.println("Stopping video: " + currentVideo.getTitle());
        }
        System.out.println("Playing video: " + video.getTitle());
        currentVideo = video;
        isPaused = false;
        return;
      }
    }
    System.out.println("Cannot play video: Video does not exist");
  }

  public void stopVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot stop video: No video is currently playing");
    } else {
      System.out.println("Stopping video: " + currentVideo.getTitle());
      currentVideo = null;
    }
  }

  public void playRandomVideo() {
    Random rand = new Random();
    int randomInt = rand.nextInt(videoLibrary.getVideos().size());

    if(currentVideo != null) {
      System.out.println("Stopping video: " + currentVideo.getTitle());
    }

    currentVideo = videoLibrary.getVideos().get(randomInt);
    System.out.println("Playing video: " + currentVideo.getTitle());
    isPaused = false;
  }

  public void pauseVideo() {
    if(currentVideo != null) {
      if (isPaused) {
        System.out.println("Video already paused: " + currentVideo.getTitle());
      } else {
        System.out.println("Pausing video: " + currentVideo.getTitle());
        isPaused = true;
      }
    } else {
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if(currentVideo == null) {
      System.out.println("Cannot continue video: No video is currently playing");
    } else {
      if(!isPaused) {
        System.out.println("Cannot continue video: Video is not paused");
      } else {
        System.out.println("Continuing video: " + currentVideo.getTitle());
      }
    }
  }

  public void showPlaying() {
    if(currentVideo == null) {
      System.out.println("No video is currently playing");
    } else {
      String playStatus = "Currently playing: " + currentVideo;
      if(isPaused) playStatus = playStatus + " - PAUSED";
      System.out.println(playStatus);
    }
  }

  public void createPlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = videoPlaylists.get(playlistName.toLowerCase());
    if (videoPlaylist != null) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      videoPlaylist = new VideoPlaylist(playlistName);
      videoPlaylists.put(playlistName.toLowerCase(), videoPlaylist);
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    VideoPlaylist videoPlaylist = videoPlaylists.get(playlistName.toLowerCase());
    Video video = null;

    if (videoPlaylist == null) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }

    for (Video v : videoLibrary.getVideos()) {
      if (videoId.equals(v.getVideoId())) {
        video = v;
        break;
      }
    }

    if (video == null) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      return;
    }

    for (Video v : videoPlaylist.getPlaylist()) {
      if (video.equals(v)) {
        System.out.println("Cannot add video to " + playlistName + ": Video already added");
        return;
      }
    }

    videoPlaylist.addVideo(video);
    System.out.println("Added video to " + playlistName + ": " + video.getTitle());
  }

  public void showAllPlaylists() {
    if (videoPlaylists.isEmpty()) {
      System.out.println("No playlists exist yet");
    } else {
      System.out.println("Showing all playlists:");
      for (VideoPlaylist videoPlaylist : videoPlaylists.values()) {
        System.out.println("\t" + videoPlaylist);
      }
    }
  }

  public void showPlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = videoPlaylists.get(playlistName.toLowerCase());
    if (videoPlaylist == null) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    } else {
      System.out.println("Showing playlist: " + playlistName);
      if (videoPlaylist.getPlaylist().isEmpty()) {
        System.out.println("\tNo videos here yet");
      } else {
        for (Video video : videoPlaylist.getPlaylist()) {
          System.out.println("\t" + video);
        }
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    VideoPlaylist videoPlaylist = videoPlaylists.get(playlistName.toLowerCase());
    Video video = null;
    boolean inLibrary = false;
    boolean inPlaylist = false;

    if (videoPlaylist == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      return;
    }

    for (Video v : videoLibrary.getVideos()) {
      if (videoId.equals(v.getVideoId())) {
        inLibrary = true;
        video = v;
        break;
      }
    }

    if (!inLibrary) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;
    }

    for (Video v : videoPlaylist.getPlaylist()) {
      if (videoId.equals(v.getVideoId())) {
        inPlaylist = true;
        video = v;
        break;
      }
    }

    if (!inPlaylist) {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      return;
    }

    videoPlaylist.removeVideo(video);
    System.out.println("Removed video from " + playlistName + ": " + video.getTitle());
  }

  public void clearPlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = videoPlaylists.get(playlistName.toLowerCase());

    if (videoPlaylist == null) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    } else {
      videoPlaylist.clear();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = videoPlaylists.get(playlistName.toLowerCase());

    if (videoPlaylist == null) {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    } else {
      videoPlaylists.remove(playlistName.toLowerCase());
      System.out.println("Deleted playlist: " + playlistName);
    }
  }

  public void searchVideos(String searchTerm) {
    List<Video> videoResults = new ArrayList<>();

    for(Video video : videoLibrary.getVideos()) {
      if(video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
        videoResults.add(video);
      }
    }

    
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}