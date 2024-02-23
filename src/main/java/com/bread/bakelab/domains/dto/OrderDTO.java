package com.bread.bakelab.domains.dto;

import com.bread.bakelab.domains.vo.OrderVO;
import com.bread.bakelab.domains.vo.PaymentVO;
import com.bread.bakelab.domains.vo.PurchaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class OrderDTO {
    OrderVO orderVO;
    PurchaseVO purchaseVO;
    PaymentVO paymentVO;
}
