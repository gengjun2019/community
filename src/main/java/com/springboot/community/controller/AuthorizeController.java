package com.springboot.community.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.community.dto.AccessTokenDTO;
import com.springboot.community.dto.UserDTO;
import com.springboot.community.mapper.UserMapper;
import com.springboot.community.model.User;
import com.springboot.community.provider.GitProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitProvider gitProvider;

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.client_secret}")
    private String clientSecret;

    @Value("${github.redirect_uri}")
    private String redirectUri;

    @Value("${github.access_token_url}")
    private String accessTokenUrl;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);

        String json = JSON.toJSONString(accessTokenDTO);
        String accessToken = gitProvider.getAccessToken(accessTokenUrl, json);
        UserDTO userDTO = gitProvider.getUser(accessToken);
        if (userDTO != null){
            User user = new User();
            user.setAccountId(String.valueOf(userDTO.getId()));
            user.setName(userDTO.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            int result = userMapper.insert(user);
            if (result > 0){
                HttpSession session = request.getSession();
                session.setAttribute("user", userDTO);
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

}
