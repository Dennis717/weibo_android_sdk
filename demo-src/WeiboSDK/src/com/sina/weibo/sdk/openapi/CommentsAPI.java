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

import android.util.SparseArray;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * This class wraps the comments API
 * 
 * Please refer to <a href="http://t.cn/8F3geol">Comments API</a>
 * 
 * @author SINA
 * @since 2014-03-03
 */
public class CommentsAPI extends AbsOpenAPI {

    /** Author Type:
     * 0:all
     * 1:Following
     * 2:Strangers
     *  
     */
    public static final int AUTHOR_FILTER_ALL        = 0;
    public static final int AUTHOR_FILTER_ATTENTIONS = 1;
    public static final int AUTHOR_FILTER_STRANGER   = 2;

    /** Source Type:
     * 0:all
     * 1:from Weibo
     * 2:from Weibo group
     */
    public static final int SRC_FILTER_ALL    = 0;
    public static final int SRC_FILTER_WEIBO  = 1;
    public static final int SRC_FILTER_WEIQUN = 2;

    /**
     * APIs
     * Refer to legacy package {@link com.sina.weibo.sdk.openapi.legacy.CommentsAPI}
     */
    private static final int READ_API_TO_ME           = 0;
    private static final int READ_API_BY_ME           = 1;
    private static final int READ_API_SHOW            = 2;
    private static final int READ_API_TIMELINE        = 3;
    private static final int READ_API_MENTIONS        = 4;
    private static final int READ_API_SHOW_BATCH      = 5;
    private static final int WRITE_API_CREATE         = 6;
    private static final int WRITE_API_DESTROY        = 7;
    private static final int WRITE_API_SDESTROY_BATCH = 8;
    private static final int WRITE_API_REPLY          = 9;
    
    private static final String API_BASE_URL = API_SERVER + "/comments";
    
    private static final SparseArray<String> sAPIList = new SparseArray<String>();
    static {
        sAPIList.put(READ_API_TO_ME,           API_BASE_URL + "/to_me.json");
        sAPIList.put(READ_API_BY_ME,           API_BASE_URL + "/by_me.json");
        sAPIList.put(READ_API_SHOW,            API_BASE_URL + "/show.json");
        sAPIList.put(READ_API_TIMELINE,        API_BASE_URL + "/timeline.json");
        sAPIList.put(READ_API_MENTIONS,        API_BASE_URL + "/mentions.json");
        sAPIList.put(READ_API_SHOW_BATCH,      API_BASE_URL + "/show_batch.json");
        sAPIList.put(WRITE_API_CREATE,         API_BASE_URL + "/create.json");
        sAPIList.put(WRITE_API_DESTROY,        API_BASE_URL + "/destroy.json");
        sAPIList.put(WRITE_API_SDESTROY_BATCH, API_BASE_URL + "/sdestroy_batch.json");
        sAPIList.put(WRITE_API_REPLY,          API_BASE_URL + "/reply.json");
    }
    
    /**
     * Construct an instance of CommentsAPI with accessToken
     * 
     * @param accesssToken access token
     */
	public CommentsAPI(Oauth2AccessToken accessToken) {
        super(accessToken);
    }

    /**
     * Get comments for the weibo specifiy the id. 
     * 
     * @param id         weibo id
     * @param since_id   comment id, if specified, returns the comments newer than this one, default is 0
     * @param max_id     comment id, if specified, returns the comments older than this one. default is 0
     * @param count      max count of comments in one page. default is 50
     * @param page       page number, default is 1
     * @param authorType author type, default is 0
     *                   <li>{@link #AUTHOR_FILTER_ALL}
     *                   <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li>{@link #AUTHOR_FILTER_STRANGER}
     * @param listener   callback listener
     */
    public void show(long id, long since_id, long max_id, int count, int page, int authorType, RequestListener listener) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("id", id);
        params.put("filter_by_author", authorType);
        requestAsync(sAPIList.get(READ_API_SHOW), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Get comments posted by current user.
     * 
     * @param since_id   comment id, if specified, returns the comments newer than this one, default is 0
     * @param max_id     comment id, if specified, returns the comments older than this one. default is 0
     * @param count      max count of comments in one page. default is 50
     * @param page       page number, default is 1
     * @param sourceType source type, default is 0
     *                   <li>{@link #SRC_FILTER_ALL}
     *                   <li>{@link #SRC_FILTER_WEIBO}
     *                   <li>{@link #SRC_FILTER_WEIQUN} 
     * @param listener   callback listener
     */
    public void byME(long since_id, long max_id, int count, int page, int sourceType, RequestListener listener) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("filter_by_source", sourceType);
        requestAsync(sAPIList.get(READ_API_BY_ME), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Get comments to current user
     * 
     * @param since_id   comment id, if specified, returns the comments newer than this one, default is 0
     * @param max_id     comment id, if specified, returns the comments older than this one. default is 0
     * @param count      max count of comments in one page. default is 50
     * @param page       page number, default is 1
     * @param authorType author type, default is 0
     *                   <li>{@link #AUTHOR_FILTER_ALL}
     *                   <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li>{@link #AUTHOR_FILTER_STRANGER}
     * @param sourceType source type, default is 0
     *                   <li>{@link #SRC_FILTER_ALL}
     *                   <li>{@link #SRC_FILTER_WEIBO}
     *                   <li>{@link #SRC_FILTER_WEIQUN}
     * @param listener   callback listener
     */
    public void toME(long since_id, long max_id, int count, int page, int authorType, int sourceType,
            RequestListener listener) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("filter_by_author", authorType);
        params.put("filter_by_source", sourceType);
        requestAsync(sAPIList.get(READ_API_TO_ME), params, HTTPMETHOD_GET, listener);
    }

    /**
     * Get all comments, including both posted or received.
     * 
     * @param since_id   comment id, if specified, returns the comments newer than this one, default is 0
     * @param max_id     comment id, if specified, returns the comments older than this one. default is 0
     * @param count      max count of comments in one page. default is 50
     * @param page       page number, default is 1
     * @param trim_user  determine if return full user info. Return full user info if false, otherwise, only return user id. Default is false
     * @param listener   callback listener
     */
    public void timeline(long since_id, long max_id, int count, int page, boolean trim_user, RequestListener listener) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("trim_user", trim_user ? 1 : 0);
        requestAsync(sAPIList.get(READ_API_TIMELINE), params, HTTPMETHOD_GET, listener);
    }

    /**
     * Get comment, which mention (@) current user.
     * 
     * @param since_id   comment id, if specified, returns the comments newer than this one, default is 0
     * @param max_id     comment id, if specified, returns the comments older than this one. default is 0
     * @param count      max count of comments in one page. default is 50
     * @param page       page number, default is 1
     * @param authorType Author type, default is 0
     *                   <li> {@link #AUTHOR_FILTER_ALL}
     *                   <li> {@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li> {@link #AUTHOR_FILTER_STRANGER}
     *@param sourceType  Source type, default is 0
     *                   <li> {@link #SRC_FILTER_ALL}
     *                   <li> {@link #SRC_FILTER_WEIBO}
     *                   <li> {@link #SRC_FILTER_WEIQUN}
     * @param listener   callback listener
     */
    public void mentions(long since_id, long max_id, int count, int page, int authorType, int sourceType,
            RequestListener listener) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("filter_by_author", authorType);
        params.put("filter_by_source", sourceType);
        requestAsync(sAPIList.get(READ_API_MENTIONS), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Get comments information in batch.
     * 
     * @param cids      Array of comments id. max lenth is 50
     * @param listener  callback listener
     */
    public void showBatch(long[] cids, RequestListener listener) {
        WeiboParameters params = buildShowOrDestoryBatchParams(cids);
        requestAsync(sAPIList.get(READ_API_SHOW_BATCH), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Comments to one Weibo specified by id
     * 
     * @param comment     comment content
     * @param id          weibo id
     * @param comment_ori specify if comment to the original weibo.
     * @param listener    callback listener
     */
    public void create(String comment, long id, boolean comment_ori, RequestListener listener) {
        WeiboParameters params = buildCreateParams(comment, id, comment_ori);
        requestAsync(sAPIList.get(WRITE_API_CREATE), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * Remove a comment specified by id
     * 
     * @param cid      comment id to be removed
     * @param listener callback listener
     */
    public void destroy(long cid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("cid", cid);
        requestAsync(sAPIList.get(WRITE_API_DESTROY), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * Remove comments in batch.
     * 
     * @param ids      Array of comment ids. max length is 20
     * @param listener callback listener
     */
    public void destroyBatch(long[] ids, RequestListener listener) {
        WeiboParameters params = buildShowOrDestoryBatchParams(ids);
        requestAsync(sAPIList.get(WRITE_API_SDESTROY_BATCH), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * Reply to one comment
     * 
     * @param cid             comment id to reply
     * @param id              weibo id
     * @param comment         comment content
     * @param without_mention determine if add @username in reply automatically. true - add, false - not add
     * @param comment_ori     determine if comment to the original weibo. true - comments to orginal.
     * @param listener        callback listener
     */
    public void reply(long cid, long id, String comment, boolean without_mention, boolean comment_ori,
            RequestListener listener) {
        WeiboParameters params = buildReplyParams(cid, id, comment, without_mention, comment_ori);
        requestAsync(sAPIList.get(WRITE_API_REPLY), params, HTTPMETHOD_POST, listener);
    }
    
    /**
     * -----------------------------------------------------------------------
     * Notice: methods below are synchronized
     * -----------------------------------------------------------------------
     */
    
    /**
     * @see #show(long, long, long, int, int, int, RequestListener)
     */
    public String showSync(long id, long since_id, long max_id, int count, int page, int authorType) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("id", id);
        params.put("filter_by_author", authorType);
        return requestSync(sAPIList.get(READ_API_SHOW), params, HTTPMETHOD_GET);
    }

    /**
     * @see #byME(long, long, int, int, int, RequestListener)
     */
    public String byMESync(long since_id, long max_id, int count, int page, int sourceType) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("filter_by_source", sourceType);
        return requestSync(sAPIList.get(READ_API_BY_ME), params, HTTPMETHOD_GET);
    }

    /**
     * @see #toME(long, long, int, int, int, int, RequestListener)
     */
    public String toMESync(long since_id, long max_id, int count, int page, int authorType, int sourceType) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("filter_by_author", authorType);
        params.put("filter_by_source", sourceType);
        return requestSync(sAPIList.get(READ_API_TO_ME), params, HTTPMETHOD_GET);
    }

    /**
     * @see #timeline(long, long, int, int, boolean, RequestListener)
     */
    public String timelineSync(long since_id, long max_id, int count, int page, boolean trim_user) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("trim_user", trim_user ? 1 : 0);
        return requestSync(sAPIList.get(READ_API_TIMELINE), params, HTTPMETHOD_GET);
    }

    /**
     * @see #mentions(long, long, int, int, int, int, RequestListener)
     */
    public String mentionsSync(long since_id, long max_id, int count, int page, int authorType, int sourceType) {
        WeiboParameters params = buildTimeLineParamsBase(since_id, max_id, count, page);
        params.put("filter_by_author", authorType);
        params.put("filter_by_source", sourceType);
        return requestSync(sAPIList.get(READ_API_MENTIONS), params, HTTPMETHOD_GET);
    }

    /**
     * @see #showBatch(long[], RequestListener)
     */
    public String showBatchSync(long[] cids) {
        WeiboParameters params = buildShowOrDestoryBatchParams(cids);
        return requestSync(sAPIList.get(READ_API_SHOW_BATCH), params, HTTPMETHOD_GET);
    }

    /**
     * @see #create(String, long, boolean, RequestListener)
     */
    public String createSync(String comment, long id, boolean comment_ori) {
        WeiboParameters params = buildCreateParams(comment, id, comment_ori);
        return requestSync(sAPIList.get(WRITE_API_CREATE), params, HTTPMETHOD_POST);
    }

    /**
     * @see #destroyBatch(long[], RequestListener)
     */
    public String destroySync(long cid) {
        WeiboParameters params = new WeiboParameters();
        params.put("cid", cid);
        return requestSync(sAPIList.get(WRITE_API_DESTROY), params, HTTPMETHOD_POST);
    }

    /**
     * @see #destroyBatchSync(long[])
     */
    public String destroyBatchSync(long[] ids) {
        WeiboParameters params = buildShowOrDestoryBatchParams(ids);
        return requestSync(sAPIList.get(WRITE_API_SDESTROY_BATCH), params, HTTPMETHOD_POST);
    }

    /**
     * @see #reply(long, long, String, boolean, boolean, RequestListener)
     */
    public String replySync(long cid, long id, String comment, boolean without_mention, boolean comment_ori) {
        WeiboParameters params = buildReplyParams(cid, id, comment, without_mention, comment_ori);
        return requestSync(sAPIList.get(WRITE_API_REPLY), params, HTTPMETHOD_POST);
    }

    /** 
     * build TimeLines paramters
     */
    private WeiboParameters buildTimeLineParamsBase(long since_id, long max_id, int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("since_id", since_id);
        params.put("max_id", max_id);
        params.put("count", count);
        params.put("page", page);
        return params;
    }

    private WeiboParameters buildShowOrDestoryBatchParams(long[] cids) {
        WeiboParameters params = new WeiboParameters();
        StringBuilder strb = new StringBuilder();
        for (long cid : cids) {
            strb.append(cid).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        params.put("cids", strb.toString());
        return params;
    }

    private WeiboParameters buildCreateParams(String comment, long id, boolean comment_ori) {
        WeiboParameters params = new WeiboParameters();
        params.put("comment", comment);
        params.put("id", id);
        params.put("comment_ori", comment_ori ? 1: 0);
        return params;
    }

    private WeiboParameters buildReplyParams(long cid, long id, String comment, boolean without_mention,
            boolean comment_ori) {
        WeiboParameters params = new WeiboParameters();
        params.put("cid", cid);
        params.put("id", id);
        params.put("comment", comment);
        params.put("without_mention", without_mention ? 1: 0);
        params.put("comment_ori",     comment_ori ? 1: 0);
        return params;
    }
}
