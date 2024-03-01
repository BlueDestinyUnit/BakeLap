package com.bread.bakelab.mapper;

import com.bread.bakelab.domains.dto.OrderDTO;
import com.bread.bakelab.domains.vo.BasketVO;
import com.bread.bakelab.domains.vo.OrderVO;
import com.bread.bakelab.domains.vo.PaymentVO;
import com.bread.bakelab.domains.vo.PurchaseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    // 장바구니 리스트
    List<BasketVO> find_basket_by_user(String userID);
    // 선택한 장바구니
    BasketVO find_basket_by_no(int no);
    // 장바구니 추가
    void insert_basket(BasketVO basketVO);
    // 장바구니 삭제
    void delete_basket(int no);
    // 주문 번호 생성
    void insert_orderNumber(OrderVO orderVO);
    // 마지막 주문번호 삭제
    OrderVO find_LastOrderNumber();
    // 거래내역
    void insert_payment(PaymentVO paymentVO);
    // 거래목록
    void insert_purchase(PurchaseVO purchaseVO);

    // 결제 목록
    List<OrderDTO> find_my_list(String userID);
    // 결제한 총금액
    Integer totalPayment(String userID);


}
