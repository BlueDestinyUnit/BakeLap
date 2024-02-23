package com.bread.bakelab.domains.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class PaymentVO {
    String pay_number;
    String order_number;
    int price;
    @DateTimeFormat(pattern = "yyyyMMdd") // 패턴 변경
    LocalDateTime purchase_date; // LocalDate에서 LocalDateTime으로 변경
    String payment_state;
}
