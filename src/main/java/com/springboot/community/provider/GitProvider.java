package com.springboot.community.provider;

import com.alibaba.fastjson.JSON;
import com.springboot.community.dto.UserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitProvider {

    public static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    public String getAccessToken(String url, String json) {
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDTO getUser(String accessToken) {
        accessToken = accessToken.substring(accessToken.indexOf("=") + 1, accessToken.indexOf("&"));
        String url = "https://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            UserDTO user = JSON.parseObject(result, UserDTO.class);
            if (user.getId() != null){
                return user;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
