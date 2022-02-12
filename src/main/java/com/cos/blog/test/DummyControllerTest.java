package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;


@RestController         // html파일이 아니라 data 리턴해주는 controller
public class DummyControllerTest {

    @Autowired      // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }
        return "삭제되었습니다. id: "+id;
    }

    // save 함수는 id를 전달하지 않으면 insert를 해주고
    // id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //                               없으면 insert를 함.
    // email, password
    @Transactional      // 함수 종료 시 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        System.out.println("id: "+id);
        System.out.println("password: "+requestUser.getPassword());
        System.out.println("email: "+requestUser.getEmail());

        // 영속화
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

//        userRepository.save(user);

        // 더티체킹
        return user;
    }

    // http://localhost:8000/blog/dummy/users
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    // http://localhost:8000/blog/dummy/user?page=1
    // 한 페이지당 2건의 데이터 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }


    // {id} 주소로 파라메터를 전달 받을 수 있음
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){

        // user/4를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
        // 그럼 return null이 리턴되자나.. 그럼 프로그램에 문제가 생김
        // Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!

        // 람다식 : new Supp...  ==>   ()->{ });
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
            }
        });

        // 요청: 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json
        // 스프링부트 = MessageConverter라는 애가 응답시 자동 작동
        // 만약 자바 오브젝트 리턴 시 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줌.
        return user;
    }

    // http://localhost:8000/blog/dummy/join(요청)
    // postman에서 실행해봄 - Post
    // http의 body에 username, password, email 데이터 가지고(요청)
    @PostMapping("/dummy/join")
//    public String join(String username,String password,String email){
//        System.out.println("username: "+username);
//        System.out.println("password: "+password);
//        System.out.println("email: "+email);
//        return "회원가입이 완료되었습니다.";
//    }

    public String join(User user){
        System.out.println("id: "+user.getId());
        System.out.println("username: "+user.getUsername());
        System.out.println("password: "+user.getPassword());
        System.out.println("email: "+user.getEmail());
        System.out.println("role: "+user.getRole());
        System.out.println("createDate: "+user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
