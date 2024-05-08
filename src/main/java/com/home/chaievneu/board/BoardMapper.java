package com.home.chaievneu.board;

import com.home.chaievneu.common.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    List<Board> boardList(SearchDto params);

    /**
     * 전체 게시글 수 카운팅 (페이징에 이용)
     * @return 게시글 수
     */
    int count(SearchDto params);


    /**
     * 게시글 상세 조회
     * @param boardNo 조회할 게시글 번호
     * @return 게시글 정보
     */
    Board detailBoard(int boardNo);

    /**
     * 게시글 저장
     * @param board 저장될 게시글 정보
     * @return 저장 성공 여부 (1. 성공 / 0.실패)
     */
    int boardSave(Board board);

    /**
     * 게시글 수정
     * @param board 수정될 게시글 정보
     * @return 수정 성공 여부 (1.성공 / 0.실패)
     */
    int updateBoard(Board board);

    /**
     * 게시글 삭제
     * @param boardNo 삭제할 게시글 번호
     * @return 삭제 성공 여부 (1.성공 / 0.실패)
     */
    int deleteBoard(int boardNo);




}
