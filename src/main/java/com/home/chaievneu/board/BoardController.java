package com.home.chaievneu.board;

import com.google.gson.JsonObject;
import com.home.chaievneu.common.MessageDto;
import com.home.chaievneu.common.PagingResponse;
import com.home.chaievneu.common.SearchDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ResourceLoader resourceLoader;

    private final BoardService boardService;
    private final WebApplicationContext context;


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
    public String boardList(@ModelAttribute("params") final SearchDto params, Model model){

        PagingResponse<Board> board = boardService.boardList(params);

//        for(int i = 0; i < board.size(); i++){
//            var con = board.get(i).getContent();
//            board.get(i).setContent(removeHtmlTag(con));
//        }

        model.addAttribute("board", board);
        return "board/news";
    }


    /**
     *
     * @param model
     * @return 관리자 리스트 목록
     */
    @GetMapping("admin.page")
    public String adminList(@ModelAttribute("params") final SearchDto params, Model model){

        PagingResponse<Board> board = boardService.boardList(params);

        for(int i = 0; i < board.getList().size(); i++){
            var con = board.getList().get(i).getContent();
            board.getList().get(i).setContent(removeHtmlTag(con));
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
    @PostMapping("boardSave.bo")
    public String boardSave(final Board board, Model model){
        int result = boardService.boardSave(board);
        MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/admin.page", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }


    /**
     * summernote 이미지 폴더에 저장
     * @param multipartFile
     * @param httpServletRequest
     * @return
     */
//    @RequestMapping(value = "/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
//    @ResponseBody
//    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
//
//        // Json 객체를 생성
//        JsonObject jsonObject = new JsonObject();
//
//        // 이미지 파일지 저장될 경로를 설정
//        String fileRoot = realPath;
//
//        // 업로드 된 파일의 원본 파일명과 확장자를 추출
//        String originalFileName = multipartFile.getOriginalFilename();
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//
//        // 새로운 파일명 생성 (고유한 식별자 + 확장자)
//        String savedFileName = UUID.randomUUID() + extension;
//
//        // 저장될 파일의 경로와 파일명을 나타내는 File 객체 생성
//        File targetFile = new File(fileRoot + savedFileName);
//
//        try {
//            //업로드 된 파일의 InputStream 열기
//            java.io.InputStream fileStream = multipartFile.getInputStream();
//
//            // 업로드된 파일을 지정된 경로에 저장
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);
//
//            // JSON 객체에 이미지 URL과 응답 코드 추가
//            jsonObject.addProperty("url", "/upload/image/fileupload/29/" + savedFileName);
//            jsonObject.addProperty("responseCode", "success");
//
//        } catch (IOException e) {
//
//            // 파일 저장 중 오류가 발생한 경우 해당 파일 삭제 및 응답 에러 코드 추가
//            FileUtils.deleteQuietly(targetFile);
//            jsonObject.addProperty("responsecode", "error");
//            e.printStackTrace();
//        }
//
//        // Json 객체를 문자열로 변환하여 반환
//        String a = jsonObject.toString();
//        return a;
//
//    }

    @RequestMapping(value = "/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
    @ResponseBody
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest httpServletRequest) {

        JsonObject jsonObject = new JsonObject();

        try {
            // 업로드 된 파일을 읽어들입니다.
            InputStream inputStream = multipartFile.getInputStream();

            // 클래스패스 상의 리소스 디렉토리에 대한 Resource 객체를 생성합니다.
            Resource resource = resourceLoader.getResource("classpath:/static/images/upload/");

            // Resource의 File 객체를 얻습니다.
            File uploadDirectory = resource.getFile();

            // 새로운 파일명을 생성합니다.
            String originalFileName = multipartFile.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID().toString() + extension;

            // 저장될 파일의 경로와 파일명을 나타내는 File 객체 생성
            File targetFile = new File(uploadDirectory, savedFileName);

            // 파일을 저장합니다.
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장이 성공했을 경우 응답 데이터를 설정합니다.
            String contextPath = httpServletRequest.getContextPath();
            String imageUrl = contextPath + "/images/upload/" + savedFileName;
            jsonObject.addProperty("url", imageUrl);
            jsonObject.addProperty("responseCode", "success");

            // 파일 저장이 성공했을 경우, 저장된 파일의 절대 경로를 확인합니다.
            if (targetFile.exists()) {
                System.out.println("파일이 정상적으로 저장되었습니다: " + targetFile.getAbsolutePath());
            } else {
                System.out.println("파일 저장에 실패했습니다.");
            }
        } catch (IOException e) {
            // 파일 저장 중 오류가 발생한 경우 응답 데이터를 설정합니다.
            jsonObject.addProperty("responsecode", "error");
            e.printStackTrace();
        }

        // JSON 객체를 문자열로 변환하여 반환
        return jsonObject.toString();
    }


    /**
     * 게시글 삭제
     * @param boardNo 삭제할 게시글 번호
     * @return 관리자 메인 페이지
     */
    @GetMapping("boardDelete.bo")
    public String boardDelete(final int boardNo, Model model){
        System.out.println(boardNo);
        int result = boardService.boardDelete(boardNo);
        MessageDto message = new MessageDto("게시글이 삭제 되었습니다.", "/admin.page", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    @GetMapping("boardChangeForm.bo")
    public String boardChangeForm(@RequestParam final int boardNo, Model model){
        Board board = boardService.detailBoard(boardNo);
        model.addAttribute(board);
        return "admin/modify";
    }


    /**
     * 게시글 수정하기
     * @param board 수정될 게시글 정보
     * @return 관리자 메인페이지
     */
    @GetMapping("boardChange.mo")
    public String boardChange(final Board board, Model model){
        System.out.println(board.getBoardNo());
        int result = boardService.boardChange(board);
        MessageDto message = new MessageDto("게시글이 수정 되었습니다.", "/admin.page", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }


    // 메세지 전달, 페이지 리다이렉트 용
    private String showMessageAndRedirect(final MessageDto params, Model model){
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }




}
