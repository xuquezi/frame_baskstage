package com.example.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.FrameUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JsonTest extends AdminApplicationTests {
    @Autowired
    private ObjectMapper mapper;
    @Test
    public void test1() throws JsonProcessingException {
        FrameUser user = new FrameUser();
        user.setUserId(1);
        user.setUserName("陈平安");
        user.setUserPassword("123");
        String s = mapper.writeValueAsString(user);
        System.out.println(s);
    }
}
