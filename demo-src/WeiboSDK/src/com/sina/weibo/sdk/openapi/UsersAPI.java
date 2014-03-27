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
 * Class UsesAPI to access user information
 * 
 * Refer to<a href="http://t.cn/8F1n1eF">User API</a>
 * 
 * @author SINA
 * @since 2014-03-03
 */
public class UsersAPI extends AbsOpenAPI {

    private static final int READ_USER           = 0;
    private static final int READ_USER_BY_DOMAIN = 1;
    private static final int READ_USER_COUNT     = 2;

    private static final String API_BASE_URL = API_SERVER + "/users";

    private static final SparseArray<String> sAPIList = new SparseArray<String>();
    static {
        sAPIList.put(READ_USER,           API_BASE_URL + "/show.json");
        sAPIList.put(READ_USER_BY_DOMAIN, API_BASE_URL + "/domain_show.json");
        sAPIList.put(READ_USER_COUNT,     API_BASE_URL + "/counts.json");
    }

    public UsersAPI(Oauth2AccessToken accessToken) {
        super(accessToken);
    }

    /**
     * Get information for the user specified by uid
     * 
     * @param uid      user id
     * @param listener callback
     */
    public void show(long uid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Get information for the user specified by screen_namn (nick name)
     * 
     * @param screen_name screen name
     * @param listener    callback
     */
    public void show(String screen_name, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("screen_name", screen_name);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Get user information and the latest Weibo through personal domain name
     * 
     * @param domain   domain（It's not the full URL, only the "xxx" in http://weibo.com/xxx）
     * @param listener callback
     */
    public void domainShow(String domain, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("domain", domain);
        requestAsync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * Get the nubmer of followers, followings, and Weibo for users in batch.
     * 
     * @param uids     uid, multiple uid saparted by comma (,), max length is 100
     * @param listener callback
     */
    public void counts(long[] uids, RequestListener listener) {
        WeiboParameters params = buildCountsParams(uids);
        requestAsync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET, listener);
    }
    
    /**
     * -----------------------------------------------------------------------
     * Notice: APIs below are synchronized methods
     * -----------------------------------------------------------------------
     */
    
    /**
     * @see #show(long, RequestListener)
     */
    public String showSync(long uid) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
     * @see #show(String, RequestListener)
     */
    public String showSync(String screen_name) {
        WeiboParameters params = new WeiboParameters();
        params.put("screen_name", screen_name);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
     * @see #domainShow(String, RequestListener)
     */
    public String domainShowSync(String domain) {
        WeiboParameters params = new WeiboParameters();
        params.put("domain", domain);
        return requestSync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET);
    }

    /**
     * @see #counts(long[], RequestListener)
     */
    public String countsSync(long[] uids) {
        WeiboParameters params = buildCountsParams(uids);
        return requestSync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET);
    }

    private WeiboParameters buildCountsParams(long[] uids) {
        WeiboParameters params = new WeiboParameters();
        StringBuilder strb = new StringBuilder();
        for (long cid : uids) {
            strb.append(cid).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        params.put("uids", strb.toString());
        return params;
    }
}
