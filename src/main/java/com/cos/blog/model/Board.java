package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,length = 100)
    private String title;

    @Lob        //대용량 데이터 디자인 시 사용
    private String content;     //섬머노트 라이브러리 : <html> 태그 섞여서 디자인됨

    private int count;      //조회수

    @ManyToOne(fetch = FetchType.EAGER)          //Many: Board , One: User
    @JoinColumn(name="userId")
    private User user;      //DB는 오브젝트 저장 불가능 -> FK, 자바는 오브젝트 저장 가능

    //댓글 펼치기 기능 사용 시에는 fetch를 LAZY로 해도 가능
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)      //연관관계의 주인이 아니다. (난 FK가 아니니 DB에 컬럼을 만들지 말아라)
    @JsonIgnoreProperties({"board"})        // 무한참조 방지
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}



