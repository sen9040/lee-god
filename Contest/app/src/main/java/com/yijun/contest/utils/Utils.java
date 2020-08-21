package com.yijun.contest.utils;

public class Utils {
    public static final String AUTH_KEY = "765867555473656e353874786d6572";

    public static final String WEATHER_URL ="https://weather-ydn-yql.media.yahoo.com/forecastrss/";
    public static final String SERVER_BASE_URL = "http://SnsServer-env.eba-dmyzzcij.ap-northeast-2.elasticbeanstalk.com";

    public static final String DATABASE_NAME = "record_db";     // 상수만드는 키워드 = public static final, 전부 대문자 = 상수
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "move_record";

    // 컬럼이름 : 컬럼명은 String (문자열이기때문에)
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_URL = "url";
}
