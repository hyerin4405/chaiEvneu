package com.home.chaievneu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<Board> boardList(){
        return boardMapper.boardList();
    }

    public int boardSave(Board board){
        return boardMapper.boardSave(board);
    }



}
