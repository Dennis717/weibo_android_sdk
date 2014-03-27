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

import org.json.JSONObject;

import android.text.TextUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.LogUtil;

/**
 * Inviatation API
 * Refer to: 
 * <li><a href="http://open.weibo.com/wiki/2/messages/invite">Invite API</a>
 * <li><a href=http://t.cn/8F75vDo>Invite friends</a>
 * 
 * @author SINA
 * @since 2013-11-04
 */
public class InviteAPI extends AbsOpenAPI {
    private final static String TAG = InviteAPI.class.getName();
    
    /** API root URL */
    private final String INVITE_URL = "https://m.api.weibo.com/2/messages/invite.json";
    
    /**（Mandatory）private message. Max length is 300 Chinese Characters.*/
    public final static String KEY_TEXT = "text";
    
    /**（Optional）redirct URL */
    public final static String KEY_URL  = "url";
    /**（Optional）Logo URL */
    public final static String KEY_INVITE_LOGO = "invite_logo";

    /**
     * Constructs an instance of InviteAPI
     * 
     * @param oauth2AccessToken access token
     */
    public InviteAPI(Oauth2AccessToken oauth2AccessToken) {
        super(oauth2AccessToken);
    }
    
    /**
     * send invitation.
     * 
     * Sample of jsonData:
     *  {
     *      "text": "This app is great!",
     *      "url": "http://app.sina.com.cn/appdetail.php?appID=770915",
     *      "invite_logo": "http://hubimage.com2us.com/hubweb/contents/123_499.jpg" 
     *  }
     * 
     * @param uid      user id to be invited
     * @param jsonData invitation data {@link JSONObject} 
     * @param listener callback listener
     * 
     */
    public void sendInvite(String uid, JSONObject jsonData, RequestListener listener) {
        if (!TextUtils.isEmpty(uid) 
                && jsonData != null 
                && !TextUtils.isEmpty(jsonData.toString())) {
            
        	WeiboParameters params = new WeiboParameters();
            params.put("uid", uid);
            params.put("data", jsonData.toString());
            requestAsync(INVITE_URL, params, HTTPMETHOD_POST, listener);
        } else {
            LogUtil.d(TAG, "Invite args error!");
        }
    }
}
