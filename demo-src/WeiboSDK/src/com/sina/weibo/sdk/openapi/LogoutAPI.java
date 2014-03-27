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

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * Class LogoutAPI
 * 
 * Refer to<a href="http://t.cn/zYeuB0k">Revoke Authentication</a>
 * 
 * @author SINA
 * @since 2013-11-05
 */
public class LogoutAPI extends AbsOpenAPI {
    /** Logout API URL */
    private static final String REVOKE_OAUTH_URL = "https://api.weibo.com/oauth2/revokeoauth2";
    
    /**
     * Constructs an instance of LogoutAPI
     * 
     * @param oauth2AccessToken access token
     */
    public LogoutAPI(Oauth2AccessToken oauth2AccessToken) {
        super(oauth2AccessToken);
    }

    /**
     * Asynchronized Logout
     * 
     * @param listener callback
     */
    public void logout(RequestListener listener) {
        requestAsync(REVOKE_OAUTH_URL, new WeiboParameters(), HTTPMETHOD_POST, listener);
    }
}
