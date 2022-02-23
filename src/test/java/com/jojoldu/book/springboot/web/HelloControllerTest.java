package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;    // JUnit4
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)    // 스프링 실행자; 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class)    // Web에 집중할 수 있는 어노테이션; @Controller, ControllerAdvice 사용 가능
public class HelloControllerTest {
    @Autowired  // Bean 주입
    private MockMvc mvc;    // 웹 API 테스트시 사용; HTTP GET, POST 등에 대한 API 테스트

    @Test
    public void helloIsReturned() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))  // /hello주소로 HTTP GET 요청
                .andExpect(status().isOk()) // mvc.perform의 결과 검증, OK(200) 검증
                .andExpect(content().string(hello));    // "hello"의 값이 맞는지 검증
    }

    @Test
    public void helloDtoIsReturned() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
        // param : API 테스트시 사용될 요청 파라미터 설정; 값은 String만 허용
        // jsonPath : JSON 응답값을 필드별로 검증; $.필드명
    }
}
