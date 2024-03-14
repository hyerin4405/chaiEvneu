package com.home.chaievneu.board;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Board {

    private int boardNo;    //게시글 번호 pk
    private String title;   //제목
    private String Content; //내용
    private Date createDate;//작성일
    private int status;     //글상태 (1.default / 2.delete)


}
