package com.home.chaievneu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public List<Board> boardList(){
        return boardMapper.boardList();
    }

    /**
     * 게시글 상세 조회
     * @param boardNo 조회 할 게시글 번호
     * @return 게시글 정보
     */
    public Board detailBoard (int boardNo){
        return boardMapper.detailBoard(boardNo);
    }

    /**
     * 게시글 저장
     * @param board 저장할 게시글 정보
     * @return 결과
     */
    @Transactional
    public int boardSave(Board board){
        return boardMapper.boardSave(board);
    }

    /**
     * 게시글 수정
     * @param board 수정할 게시글 정보
     * @return 결과
     */
    @Transactional
    public int updateBoard(Board board){
        return boardMapper.updateBoard(board);
    }

    /**
     * 게시글 삭제
     * @param boardNo 삭제할 게시글 번호
     * @return 삭제 성공 여부
     */
    @Transactional
    public int deleteBoard(int boardNo){
        return boardMapper.deleteBoard(boardNo);
    }





}
