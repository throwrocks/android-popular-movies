package rocks.athrow.android_popular_movies.data;

/**
 * APIResponse
 * Created by josel on 9/7/2016.
 */
public final class APIResponse {

    private String responseType;
    private String responseText;
    private int responseCode;

    APIResponse(String responseType) {
        this.responseType = responseType;

    }

    /**
     * setResponseCode
     *
     * @param responseCode the API's response code number
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * setResponseText
     *
     * @param responseText the API's response text
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    /**
     * getResponseType
     * @return the API's response type (movies, reviews, or trailers)
     */
    public String getResponseType() {
        return responseType;
    }
    /**
     * getResponseCode
     *
     * @return the API's response code number
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * getResponseText
     *
     * @return the API's response text
     */
    public String getResponseText() {
        return responseText;
    }

}