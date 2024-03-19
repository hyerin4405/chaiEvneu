
$(function(){

    // 모바일 메뉴
    $('#hamburger').on('click',function(){
        $('#mMenu').css('display','block');
        $('.blackBg').css('display','block');
    });
    $('#mMenu img').on('click',() =>{
        $('#mMenu').css('display','none');
        $('.blackBg').css('display','none');
    });
    
    $('.blackBg').on('click',() =>{
        $('#mMenu').css('display','none');
        $('.blackBg').css('display','none');
    });

    // 패밀리사이트
    $('#famillySite').on('click', () => {
        $('#famillySite ul').toggle("slow");
    });
});
