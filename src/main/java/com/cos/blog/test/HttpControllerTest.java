package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자가 요청 -> 응답(html 파일)
//@Controller

//사용자가 요청 -> 응답(데이터)
@RestController
public class HttpControllerTest {

    // === < Lombok 예제 > ===
    //localhost:8000/blog/http/lombok
    private static final String TAG = "HttpControllerTest: ";
    @GetMapping("/http/lombok")
    public String lombokTest(){
//        member m = new member(1,"ssar","1234","email");
        Member m = Member.builder().username("ssar").password("1234").email("email").build();
        System.out.println(TAG+"getter: "+m.getId());
        m.setId(5000);
        System.out.println(TAG+"setter: "+m.getId());
        return "lombok test 완료";
    }

    // === < GET 예제 > ===
    //인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다.
    //http://localhost:8080/http/get
    //http://localhost:8080/http/get?id=1&username=heon&password=1234&email=heon@gmail.com
    @GetMapping("/http/get")
//    public String getTest(@RequestParam int id){
//        return "get 요청: "+id;
//    }
    public String getTest(Member m){

        return "get 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    // === < POST 예제 > ===
    //http://localhost:8080/http/post
    @PostMapping("/http/post")
    // == # body: raw (text/plain) ==
//    public String postTest(@RequestBody String text){
//        return "post 요청: "+text;
//    }

    // == # body: raw (application/json) ==
//    public String postTest(@RequestBody member m){
//        return "post 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
//    }

    // == # body: x-www-... ==
    public String postTest(Member m){
        return "post 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    // === < PUT 예제 > ===
    //http://localhost:8080/http/put
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "put 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }

    // === < DELETE 예제 > ===
    //http://localhost:8080/http/delete
    @DeleteMapping("/http/delete")
    public String deleteTest(@RequestBody Member m){
        return "delete 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
    }
}