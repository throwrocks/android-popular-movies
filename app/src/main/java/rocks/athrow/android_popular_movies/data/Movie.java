package rocks.athrow.android_popular_movies.data;

/**
 * TODO: Determine if I need this, I didn't use a Movie object in the original project because I used CursorLoader
 * Movie
 * A class to manage Movie Objects
 * Created by josel on 8/23/2016.
 */
public class Movie {
    String id;
    String original_language;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    String popularity;
    String title;
    String video;
    String vote_average;
    String vote_count;

    /**
     * Movie Constructor
     * Creates a Movie object
     * @param id the movie id
     * @param original_language the movie's original language
     * @param original_title the movie's original title
     * @param overview the movie's overview
     * @param release_date the movie's release date
     * @param poster_path the movie's poster path url
     * @param popularity the movie's popularity rating
     * @param title the movie's title
     * @param video the movie's video
     * @param vote_average the movie's average votes
     * @param vote_count the movie's vote count
     */
    public void Movie(String id,
                      String original_language,
                      String original_title,
                      String overview,
                      String release_date,
                      String poster_path,
                      String popularity,
                      String title,
                      String video,
                      String vote_average,
                      String vote_count) {
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;

    }

    public String getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public String getVideo() {
        return video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }
}
