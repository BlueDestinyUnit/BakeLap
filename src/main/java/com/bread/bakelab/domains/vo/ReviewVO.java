package com.bread.bakelab.domains.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
public class ReviewVO {
    int no;
    String product_name;
    String userID;
    String context;
    int score;
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`HH:mm:ss")
    private LocalDateTime submit_date;
}
//Responst (서버 -> 클라이언트) 로 전달할 때에는 @JsonFormat 을 사용,
//Request(클라이언트 -> 서버)로 전달할 때는 @DateTimeFormat 을 사용한다.
