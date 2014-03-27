/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sina.weibo.sdk.openapi;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * Weibo APIs
 * Refer to: <a href="http://t.cn/8F3e7SE">Weibo APIs</a>
 * 
 * @author SINA
 * @since 2014-03-03
 */
public class StatusesAPI extends AbsOpenAPI {
    
    /** Weibo type，
     * 0:all
     * 1:original
     * 2:image
     * 3:video
     * 4:music
     * */
    public static final int FEATURE_ALL      = 0;
    public static final int FEATURE_ORIGINAL = 1;
    public static final int FEATURE_PICTURE  = 2;
    public static final int FEATURE_VIDEO    = 3;
    public static final int FEATURE_MUSICE   = 4;
    
    /** Author Type
     * 0:all
     * 1:Following
     * 2:Stranger 
     */
    public static final int AUTHOR_FILTER_ALL        = 0;
    public static final int AUTHOR_FILTER_ATTENTIONS = 1;
    public static final int AUTHOR_FILTER_STRANGER   = 2;
    
    /** Source Type
     * 0: all
     * 1: comments from weibo
     * 2:comments from weibo group 
     * */
    public static final int SRC_FILTER_ALL      = 0;
    public static final int SRC_FILTER_WEIBO    = 1;
    public static final int SRC_FILTER_WEIQUN   = 2;
    
    public static final int TYPE_FILTER_ALL     = 0;
    public static final int TYPE_FILTER_ORIGAL  = 1;    

    /** API URL */
    private static final String API_BASE_URL = API_SERVER + "/statuses";

    /**
     * APIs
     * 
     * Notice: This class does not include all Webio Status APIs, developers may extend
     * this class to implement other APIs
     * 
     * Refer to {@link com.sina.weibo.sdk.openapi.legacy.StatusesAPI}
     */
    private static final int READ_API_FRIENDS_TIMELINE = 0;
    private static final int READ_API_MENTIONS         = 1;    
    private static final int WRITE_API_UPDATE          = 2;
    private static final int WRITE_API_REPOST          = 3;
    private static final int WRITE_API_UPLOAD          = 4;
    private static final int WRITE_API_UPLOAD_URL_TEXT = 5;

    private static final SparseArray<String> sAPIList = new SparseArray<String>();
    static {
        sAPIList.put(READ_API_FRIENDS_TIMELINE, API_BASE_URL + "/friends_timeline.json");
        sAPIList.put(READ_API_MENTIONS,         API_BASE_URL + "/mentions.json");
        sAPIList.put(WRITE_API_REPOST,          API_BASE_URL + "/repost.json");
        sAPIList.put(WRITE_API_UPDATE,          API_BASE_URL + "/update.json");
        sAPIList.put(WRITE_API_UPLOAD,          API_BASE_URL + "/upload.json");
        sAPIList.put(WRITE_API_UPLOAD_URL_TEXT, API_BASE_URL + "/upload_url_text.json");
    }

    /**
     * Constructs an instance of StatusesAPI with the specified accessToken
     * 
     * @param accesssToken access token
     */
    public StatusesAPI(Oauth2AccessToken accessToken) {
        super(accessToken);
    }
    
    /**
     * Get Weibo (status) of current user and of his/her followings
     * 
     * @param since_id    Weibo id, if specified, only return Weibo later than this one. Default is 0. 
     * @param max_id      Weibo id, if specified, only return Weibo older than this one. Default is 0. 
     * @param count       the max number of Weibo returned in one page. Default is 50.
     * @param page        Page number, Default is 1
     * @param base_app    Determine if only returns the Weibo from this app。false- all，true - Weibo posted from this app,
     *                    Default is false.
     * @param featureType Filter type，
     *                      0: all (Default)
     *                      1: Original
     *                      2: Images
     *                      3: Video
     *                      4: Music
     *                      
     *                    <li>{@link #FEATURE_ALL}
     *                    <li>{@link #FEATURE_ORIGINAL}
     *                    <li>{@link #FEATURE_PICTURE}
     *                    <li>{@link #FEATURE_VIDEO}
     *                    <li>{@link #FEATURE_MUSICE}
     * @param trim_user   Determine if returns detail user information，false: returns detail user information,
     *                    true: only returns user_id. Default is false。
     * @param listener    callback listener
     */
    public void friendsTimeline(long since_id, long max_id, int count, int page, boolean base_app,
            int featureType, boolean trim_user, RequestListener listener) {
        WeiboParameters params = 
                buildTimeLineParamsBase(since_id, max_id, count, page, base_app, trim_user, featureType);
        requestAsync(sAPIList.get(READ_API_FRIENDS_TIMELINE), params, HTTPMETHOD_GET, listener);
    }    
    
    /**
     * Get Weibo metioned current user. @CurrentUser
     * 
     * @param since_id      Weibo id, if specified, only return Weibo later than this one. Default is 0.
     * @param max_id        Weibo id, if specified, only return Weibo older than this one. Default is 0.
     * @param count         The max number of Weibo returned in one page. Default is 50.
     * @param page          Page number, Default is 1
     * @param authorType    Author type，
     *                      0: all
     *                      1: My followings
     *                      2: Strangers
     *                      Default is 0, Refer to:
     *                      <li>{@link #AUTHOR_FILTER_ALL}
     *                      <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                      <li>{@link #AUTHOR_FILTER_STRANGER}
     * @param sourceType    Source type，
     *                      0: all (Default)
     *                      1: Comments from Weibo
     *                      2: Comments from Weibo group
     *                      
     *                      <li>{@link #SRC_FILTER_ALL}
     *                      <li>{@link #SRC_FILTER_WEIBO}
     *                      <li>{@link #SRC_FILTER_WEIQUN}
     * @param filterType    filter type，
     *                      0: all (Default)
     *                      1: Original
     *                      <li>{@link #TYPE_FILTER_ALL}
     *                      <li>{@link #TYPE_FILTER_ORIGAL}
     * @param trim_user     Determine if returns detail user information，false: returns detail user information,
     *                      true: only returns user_id. Default is false。
     * @param listener      callback listener
     */
    public void mentions(long since_id, long max_id, int count, int page, int authorType, int sourceType,
            int filterType, boolean trim_user, RequestListener listener) {
        WeiboParameters params = buildMentionsParams(since_id, max_id, count, page, authorType, sourceType, filterType, trim_user);
        requestAsync(sAPIList.get(READ_API_MENTIONS), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Post a new Weibo (two successive Weibo cannot be same)
     * 
     * @param content  Weibo content, max length is 140 Chinese Characters
     * @param lat      latitude，Valid Range [-90.0, 90.0]，+ means north latitude, Default is 0.0
     * @param lon      longitude，Valid Range[-180.0, +180.0]，+ means east longitude, Default is 0.0
     * @param listener callback
     */
    public void update(String content, String lat, String lon, RequestListener listener) {
        WeiboParameters params = buildUpdateParams(content, lat, lon);
        requestAsync(sAPIList.get(WRITE_API_UPDATE), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * Post a Weibo with an image
     * 
     * @param content  Weibo content, max length is 140 Chinese Characters
     * @param bitmap   Bitmap, only support JPEG、GIF、PNG formats，max size is 5MB
     * @param lat      latitude，Valid Range [-90.0, 90.0]，+ means north latitude, Default is 0.0
     * @param lon      longitude，Valid Range[-180.0, +180.0]，+ means east longitude, Default is 0.0
     * @param listener callback
     */
    public void upload(String content, Bitmap bitmap, String lat, String lon, RequestListener listener) {
        WeiboParameters params = buildUpdateParams(content, lat, lon);
        params.put("pic", bitmap);
        requestAsync(sAPIList.get(WRITE_API_UPLOAD), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * Post a Weibo with an image specified by a http URL, or pic_id
     * 
     * @param status   Weibo content, max length is 140 Chinese Characters
     * @param imageUrl URL to the image, should start with "http"
     * @param pic_id   id of the uploaded image (multiple ids saprated by comma, max length is 9).
     *                 Either imageUrl or pic_id should be specified, if both present, pic_id is used. 
     *                 
     *                 <b>Notice: This param cannot be used by individual developers</b>
     * @param lat      latitude，Valid Range [-90.0, 90.0]，+ means north latitude, Default is 0.0
     * @param lon      longitude，Valid Range[-180.0, +180.0]，+ means east longitude, Default is 0.0
     * @param listener callback
     */
    public void uploadUrlText(String status, String imageUrl, String pic_id, String lat, String lon,
            RequestListener listener) {
        WeiboParameters params = buildUpdateParams(status, lat, lon);
        params.put("url", imageUrl);
        params.put("pic_id", pic_id);
        requestAsync(sAPIList.get(WRITE_API_UPLOAD_URL_TEXT), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * @see #friendsTimeline(long, long, int, int, boolean, int, boolean, RequestListener)
     */
    public String friendsTimelineSync(long since_id, long max_id, int count, int page, boolean base_app, int featureType,
            boolean trim_user) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page, base_app,
                trim_user, featureType);
        return requestSync(sAPIList.get(READ_API_FRIENDS_TIMELINE), params, HTTPMETHOD_GET);
    }

    /**
     * -----------------------------------------------------------------------
     * APIs below are synchronized methods
     * -----------------------------------------------------------------------
     */
    
    /**
     * @see #mentions(long, long, int, int, int, int, int, boolean, RequestListener)
     */
    public String mentionsSync(long since_id, long max_id, int count, int page,
            int authorType, int sourceType, int filterType, boolean trim_user) {
        WeiboParameters params = buildMentionsParams(since_id, max_id, count, page, authorType, sourceType, filterType, trim_user);
        return requestSync(sAPIList.get(READ_API_MENTIONS), params, HTTPMETHOD_GET);
    }

    /**
     * @see #update(String, String, String, RequestListener)
     */
    public String updateSync(String content, String lat, String lon) {
        WeiboParameters params = buildUpdateParams(content, lat, lon);
        return requestSync(sAPIList.get(WRITE_API_UPDATE), params, HTTPMETHOD_POST);
    }

    /**
     * @see #upload(String, Bitmap, String, String, RequestListener)
     */
    public String uploadSync(String content, Bitmap bitmap, String lat, String lon) {
        WeiboParameters params = buildUpdateParams(content, lat, lon);
        params.put("pic", bitmap);
        return requestSync(sAPIList.get(WRITE_API_UPLOAD), params, HTTPMETHOD_POST);
    }

    /**
     * @see #uploadUrlText(String, String, String, String, String, RequestListener)
     */
    public String uploadUrlTextSync(String status, String imageUrl, String pic_id, String lat, String lon) {
        WeiboParameters params = buildUpdateParams(status, lat, lon);
        params.put("url", imageUrl);
        params.put("pic_id", pic_id);
        return requestSync(sAPIList.get(WRITE_API_UPLOAD_URL_TEXT), params, HTTPMETHOD_POST);
    }

    // build params for TimeLines
    private WeiboParameters buildTimeLineParamsBase(long since_id, long max_id, int count, int page,
            boolean base_app, boolean trim_user, int featureType) {
        WeiboParameters params = new WeiboParameters();
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        params.put("base_app", base_app ? 1 : 0);
        params.put("trim_user", trim_user ? 1 : 0);
        params.put("feature", featureType);
        return params;
    }

    // build params for Weibo request
    private WeiboParameters buildUpdateParams(String content, String lat, String lon) {
        WeiboParameters params = new WeiboParameters();
        params.put("status", content);
        if (!TextUtils.isEmpty(lon)) {
            params.put("long", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            params.put("lat", lat);
        }
        return params;
    }
    
    private WeiboParameters buildMentionsParams(long since_id, long max_id, int count, int page,
            int authorType, int sourceType, int filterType, boolean trim_user) {
        WeiboParameters params = new WeiboParameters();
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        params.put("filter_by_author", authorType);
        params.put("filter_by_source", sourceType);
        params.put("filter_by_type", filterType);
        params.put("trim_user", trim_user ? 1 : 0);
        
        return params;
    } 
}
