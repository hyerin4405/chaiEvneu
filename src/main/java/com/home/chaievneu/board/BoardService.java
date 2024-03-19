package com.home.chaievneu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Board detailBoard (int boardNo){
        return boardMapper.detailBoard(boardNo);
    }
    public int boardSave(Board board){
        return boardMapper.boardSave(board);
    }



}
