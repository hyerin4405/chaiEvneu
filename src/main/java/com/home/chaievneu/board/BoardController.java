package com.home.chaievneu.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {


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
     * 게시판 리스트 화면
     * @return 게시판 리스트.html
     */
    @GetMapping("/board.bo")
    public String boardList(){
        return "board/news";
    }


}
