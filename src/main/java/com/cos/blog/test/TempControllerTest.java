package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("tempHome()");
        //파일리턴 기본경로: src/main/resource/static
        //리턴명: /home.html
        //풀경로: src/main/resource/static/home.html
        return "/home.html";
    }

    @GetMapping("/temp/img")        //정적 파일
    public String tempImg(){
        return "/halla.png";
    }

    @GetMapping("/temp/jsp")        //동적 파일
    public String tempJsp(){
        //prefix: /WEB-INF/views/
        //suffix: .jsp
        //풀네임: /WEB-INF/views/test.jsp
        return "test";
    }
}
