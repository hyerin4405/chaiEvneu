package com.home.chaievneu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 메인 페이지
    @GetMapping("main.home")
    public String mainPage(){
        return "main";
    }

    // 메뉴 페이지
    @GetMapping("menu.bo")
    public String menuPage(){
        return "menu";
    }

    // 브랜드 페이지
    @GetMapping("/brand.bo")
    public String brandPage(){
        return "brand";
    }

    // 매장 안내 페이지
    @GetMapping("/map.bo")
    public String mapPage(){
        return "map";
    }


    @GetMapping("/write.bo")
    public String boardWriteForm(){
        return "board/write";
    }
    /**
     * 게시판 리스트 화면
     * @return 게시판 리스트.html
     */
    @GetMapping("/board.bo")
    public String boardList(Model model){
        List<Board> board = boardService.boardList();
        model.addAttribute("board", board);
        return "board/news";
    }

    /**
     * 게시글 상세 보기
     * @param boardNo
     * @return
     */
    public String detailBoard(int boardNo){
        Board board = boardService.detailBoard(boardNo);
        return "pass";
    }

    @GetMapping("boardSave.bo")
    public String boardSave(final Board board){
        int result = boardService.boardSave(board);
        return "redirect:/board.bo";

    }

}
