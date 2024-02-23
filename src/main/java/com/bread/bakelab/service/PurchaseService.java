package com.bread.bakelab.service;

import com.bread.bakelab.domains.dto.OrderDTO;
import com.bread.bakelab.domains.vo.BasketVO;
import com.bread.bakelab.domains.vo.OrderVO;
import com.bread.bakelab.domains.vo.PaymentVO;
import com.bread.bakelab.domains.vo.PurchaseVO;
import com.bread.bakelab.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchaseMapper purchaseMapper;
    public boolean insert_basket(BasketVO basketVO) {
        purchaseMapper.insert_basket(basketVO);
        return true;
    }
    public List<BasketVO> find_basket_by_user(String userID){
        return purchaseMapper.find_basket_by_user(userID);
    }

    public BasketVO find_basket_by_no(int no){
        return purchaseMapper.find_basket_by_no(no);
    }

    public boolean delete_basket(int no){
        purchaseMapper.delete_basket(no);
        return true;
    }

    public boolean insert_orderNumber(OrderVO orderVO){
        purchaseMapper.insert_orderNumber(orderVO);
        return true;
    }

    public OrderVO find_LastOrderNumber(){
        return purchaseMapper.find_LastOrderNumber();
    }

    public boolean insert_payment(PaymentVO paymentVO){
        purchaseMapper.insert_payment(paymentVO);
        return true;
    };

    public boolean insert_purchase(PurchaseVO purchaseVO){
        purchaseMapper.insert_purchase(purchaseVO);
        return true;
    }

    public List<OrderDTO> find_my_list(String userID){
        return purchaseMapper.find_my_list(userID);
    }

    public int totalPayment(String userID){
        return purchaseMapper.totalPayment(userID);
    }

}
