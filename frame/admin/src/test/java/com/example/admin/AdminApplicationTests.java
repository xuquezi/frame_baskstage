package com.example.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration//用来声明加载的类是一个WebApplicationContext
public class AdminApplicationTests {

	@Test
	public void contextLoads() {
	}

}
