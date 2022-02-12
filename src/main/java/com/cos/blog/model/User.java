package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

//ORM
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity         //User 클래스가 자동으로 MySQL에 테이블 생성
//@DynamicInsert      //insert 시에 null인 필드 제외
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에사 연결된 DB의 넘버링 전략을 따라간다.
    private int id;

    @Column(nullable = false,length = 100,unique = true)       //null이 될 수 없다
    private String username;

    @Column(nullable = false,length = 100)      //123456 => 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false,length = 50)
    private String email;

//    @ColumnDefault("user")
    //DB는 RoleType이라는게 없음
    @Enumerated(EnumType.STRING)
    private RoleType role;        //Enum을 쓰는게 좋다.

    private String oauth;       // kakao, google

    @CreationTimestamp      //시간이 자동 입력
    private Timestamp createDate;
}