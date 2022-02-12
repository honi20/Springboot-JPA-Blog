package com.cos.blog.test;

        import lombok.*;

//@Getter
//@Setter
//@Data       //getter, setter 동시에
//@AllArgsConstructor : 생성자
//@RequiredArgsConstructor

@Data
//@AllArgsConstructor
@NoArgsConstructor      //빈 생성자
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder
    public Member(int id,String username,String password,String email){
        this.id=id;
        this.username=username;
        this.password=password;
        this.email=email;
    }
}



