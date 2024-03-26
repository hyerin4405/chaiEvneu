package com.home.chaievneu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    /**
     * @return 게시글 작성 페이지
     */
    @GetMapping("/write.bo")
    public String boardWriteForm(){
        return "admin/write";
    }


    /**
     * 게시판 리스트 화면
     * @return 게시판 리스트.html
     */
    @GetMapping("/board.bo")
    public String boardList(Model model){
        List<Board> board = boardService.boardList();

        for(int i = 0; i < board.size(); i++){
            var con = board.get(i).getContent();
            board.get(i).setContent(removeHtmlTag(con));
        }

        model.addAttribute("board", board);
        return "board/news";
    }

    @GetMapping("admin.page")
    public String adminList(Model model){
        List<Board> board = boardService.boardList();

        for(int i = 0; i < board.size(); i++){
            var con = board.get(i).getContent();
            board.get(i).setContent(removeHtmlTag(con));
        }

        model.addAttribute("board", board);
        return "admin/list";
    }

    /**
     * HTML 태그 제거
     * @param con html 제거가 필요한 문장
     * @return html 태그가 제거된 문장
     */
    public String removeHtmlTag(String con){

        String result = "";
        String strRegExp = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
        if(con != null){
            result = con.replaceAll(strRegExp, "");
        }
        return result;

    }

    /**
     * 게시글 상세 보기
     * @param boardNo
     * @return
     */
    @GetMapping("boardDetail.bo")
    public String detailBoard(@RequestParam final int boardNo, Model model){
        Board board = boardService.detailBoard(boardNo);
        model.addAttribute(board);
        return "board/newsDetail";
    }

    /**
     * 게시물 저장
     * @param board 저장할 게시글 정보
     * @return 리스트 페이지
     */
    @GetMapping("boardSave.bo")
    public String boardSave(final Board board){
        int result = boardService.boardSave(board);
        return "redirect:/board.bo";

    }



}
