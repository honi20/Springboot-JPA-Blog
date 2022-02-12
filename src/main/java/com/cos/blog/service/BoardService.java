package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. 즉, IoC를 해줌.
//@RequiredArgsConstructor
@Service
public class BoardService {

    // Autowired 방법 1
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // 방법 2
//    public BoardService(BoardRepository bRepo, ReplyRepository rRepo){
//        this.boardRepository = bRepo;
//        this.replyRepository = rRepo;
//    }

    // 방법 3
//    private final BoardRepository boardRepository;
//    private final ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(Board board,User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void 글삭제하기(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
                });     //영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());

        // 해당 함수로 종료시(서비스 종료) 트랜잭션 종료됨. 이때 더티체킹 - 자동 업데이트가 됨. db flush
   }

   @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto){

        // 방법1
        User user = userRepository.findById(replySaveRequestDto.getUserId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 찾기 실패: 유저 id를 찾을 수 없습니다.");
                });

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 찾기 실패: 게시글 id를 찾을 수 없습니다.");
                });

        Reply reply = Reply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .build();

        replyRepository.save(reply);

        // 방법 2
       // replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());

   }

   @Transactional
    public void 댓글삭제(int replyId){
        replyRepository.deleteById(replyId);
   }
}
