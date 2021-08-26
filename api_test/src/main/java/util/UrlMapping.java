package util;

public class UrlMapping {

    public static final String VERSION = "/v1";
    public static final String CREATE_POST = VERSION + "/post";
    public static final String GET_ALL_POSTS = VERSION + "/posts";
    public static final String POST_BY_ID = VERSION + "/post/%s";
    public static final String CREATE_COMMENT = VERSION + "/comment/%s";
    public static final String GET_ALL_COMMENTS = VERSION + "/comments/%s";
    public static final String COMMENT_BY_ID = VERSION + "/comment/%s/%s";

}
