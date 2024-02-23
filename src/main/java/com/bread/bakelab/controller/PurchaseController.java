package com.bread.bakelab.controller;

import com.bread.bakelab.domains.dto.OrderDTO;
import com.bread.bakelab.domains.dto.ProductDTO;
import com.bread.bakelab.domains.security.SecurityUser;
import com.bread.bakelab.domains.vo.*;
import com.bread.bakelab.service.ProductService;
import com.bread.bakelab.service.PurchaseService;
import com.bread.bakelab.service.SellerService;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
public class PurchaseController {
    @Autowired
    ProductService productService;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    SellerService sellerService;


    //장바구니
    @GetMapping("/mypage/basket")
    public String get_basket(@AuthenticationPrincipal SecurityUser user,Model model){
        if(user != null){
            List<BasketVO> basketVOS = purchaseService.find_basket_by_user(user.getName());
        }
        return "product/basket";
    }
    //장바구니 데이터
    @GetMapping("/mypage/basketList")
    @ResponseBody
    public String basketVOList(@AuthenticationPrincipal SecurityUser user){
        List<BasketVO> basketVOS = purchaseService.find_basket_by_user(user.getName());
        JSONArray jsonArray = new JSONArray();
        basketVOS.forEach(basketVO -> {
            JSONObject jsonObject = new JSONObject(basketVO);
            ProductDTO productDTO = productService.get_product(basketVO.getProduct_name());
            String image = productDTO.getImagesVO().get(0).getImage();
            log.info(image);
            int count =basketVO.getCount();
            int price = productDTO.getProductVO().getPrice() * count;
            jsonObject.put("price", price);
            jsonObject.put("image", image);
            jsonObject.put("userID", user.getUserVO());
            jsonArray.put(jsonObject);
        });
        return jsonArray.toString();
    }
    // 장바구니 -> 구매로 보내는 쿠키
    @PostMapping("/basketReady")
    public String post_basket(BasketVO basketVO, HttpServletResponse response,@AuthenticationPrincipal SecurityUser user){
        if (user == null){
            try{
                log.info(basketVO);
                ProductDTO productDTO = productService.get_product(basketVO.getProduct_name());
                String image = productDTO.getImagesVO().get(0).getImage();
                log.info(image);
                int count =basketVO.getCount();
                int price = productDTO.getProductVO().getPrice() * count;
                JSONObject jsonObject = new JSONObject(basketVO);
                jsonObject.put("price", price);
                jsonObject.put("image", image);
                String jsonString = jsonObject.toString();
                log.info(jsonString);
                // URL 인코딩을 사용하여 JSON 문자열을 안전하게 처리
                String encodedJsonString = URLEncoder.encode(jsonString, "UTF-8");
                String cookieName = "basketVO_" + System.currentTimeMillis();
                Cookie basketCookie = new Cookie(cookieName, encodedJsonString);
                response.addCookie(basketCookie);
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return "redirect:mypage/basket";
        }
        basketVO.setUserID(user.getName());
        purchaseService.insert_basket(basketVO);
        return "redirect:mypage/basket";
    }
    // 장바구니 목록 삭제
    @PostMapping("/basketCancle")
    public String post_cancle(@RequestBody Map<String, Object> dataToSend) {
        // dataToSend 객체를 사용하여 클라이언트에서 전송한 데이터를 처리
        List<String> checkedValues = (List<String>) dataToSend.get("key1");
        checkedValues.forEach(checkdValue -> {
            int no = Integer.parseInt(checkdValue);
            purchaseService.delete_basket(no);
        });
        return "redirect:mypage/basket";
    }
    // 장바구니에서 다수를 선택했을 경우 사용하는 데이터
    @PostMapping("/purchaseReady2")
    @ResponseBody
    public ResponseEntity<String> post_purchaseReady(@RequestBody Map<String, Object> dataToSend,HttpServletResponse response) {
        try {
            JSONArray jsonArray = new JSONArray();
            List<String> checkedValues = (List<String>) dataToSend.get("key1");
            log.info(checkedValues);
            checkedValues.forEach(checkedValue -> {
                int no = Integer.parseInt(checkedValue);
                BasketVO basketVO = purchaseService.find_basket_by_no(no);
                log.info(basketVO);
                JSONObject jsonObject = new JSONObject(basketVO);
                ProductDTO productDTO = productService.get_product(basketVO.getProduct_name());
                String image = productDTO.getImagesVO().get(0).getImage();
                log.info(image);
                int count = basketVO.getCount();
                int price = productDTO.getProductVO().getPrice() * count;
                jsonObject.put("price", price);
                jsonObject.put("image", image);
                jsonArray.put(jsonObject);
            });
            String purchaseReadyJson = jsonArray.toString();
            String encodedJsonString = URLEncoder.encode(purchaseReadyJson, "UTF-8");
            Cookie purchaseCookie = new Cookie("purchaseReadyJson", encodedJsonString);
            response.addCookie(purchaseCookie);
            log.info(purchaseReadyJson);
            return ResponseEntity.ok(purchaseReadyJson); // 성공적으로 처리되었음을 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    // 상세페이지에서 구매를 바로 눌렀을떄 사용하는 데이터
    @PostMapping("/purchaseReady")
    public String post_ready(BasketVO basketVO, HttpServletResponse response){
        try{
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject(basketVO);
            log.info(basketVO);
            ProductDTO productDTO = productService.get_product(basketVO.getProduct_name());
            String image = productDTO.getImagesVO().get(0).getImage();
            log.info(image);
            int count = basketVO.getCount();
            int price = productDTO.getProductVO().getPrice() * count;
            jsonObject.put("price", price);
            jsonObject.put("image", image);
            jsonArray.put(jsonObject);
            String purchaseReadyJson = jsonArray.toString();
            String encodedJsonString = URLEncoder.encode(purchaseReadyJson, "UTF-8");
            Cookie purchaseCookie = new Cookie("purchaseReadyJson", encodedJsonString);
            response.addCookie(purchaseCookie);
        }
        catch (UnsupportedEncodingException e){
               e.printStackTrace();
        }
        return "redirect:mypage/buy";
    }

    // 구매페이지
    @GetMapping("/mypage/buy")
    public String buy(@AuthenticationPrincipal SecurityUser user){
        return "product/buy";
    }

    // 주문번호 생성
    @GetMapping("/orderNumber")
    @ResponseBody
    public ResponseEntity<String> getPurchaseReadyJson() {
        try {
            // OrderVO 객체 생성 및 필드 설정
            OrderVO orderVO = purchaseService.find_LastOrderNumber();
            log.info(orderVO);
            // OrderVO가 null인 경우 초기화
            if (orderVO == null) {
                orderVO = new OrderVO();
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedDate = currentDateTime.format(formatter);
                String partOfDate = formattedDate.substring(0, 8);
                String orderNumber = "O" + partOfDate + "1";
                String pay_number = "P" + partOfDate + "1";
                orderVO.setOrder_number(orderNumber);
                // JSON으로 변환
                JSONObject jsonObject = new JSONObject(orderVO);
                jsonObject.put("pay_number", pay_number);
                String jsonString = jsonObject.toString();
                return ResponseEntity.ok(jsonString);
            }
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = currentDateTime.format(formatter);
            String partOfDate = formattedDate.substring(0, 8);

            String orderNumber = orderVO.getOrder_number();
            if(!(orderNumber.substring(1,9).equals(partOfDate))){
                orderNumber = "O"+partOfDate+"0";
            }
            String countNumberStr = orderNumber.substring(9);
            log.info(countNumberStr);
            int countNumber = Integer.parseInt(countNumberStr);
            countNumber = countNumber+1;
            orderNumber = orderNumber.substring(0,9)+Integer.toString(countNumber);
            String pay_number = orderNumber.replace('O','P');
            orderVO.setOrder_number(orderNumber);

            // JSON으로 변환
            JSONObject jsonObject = new JSONObject(orderVO);
            jsonObject.put("pay_number", pay_number);
            String jsonString = jsonObject.toString();

            return ResponseEntity.ok(jsonString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // 구매 완료 데이터
    @PostMapping("/payResult")
    @ResponseBody
    public ResponseEntity<String> postPayResult(@RequestBody Map<String, Object> requestMap,@AuthenticationPrincipal SecurityUser user) {
        try {
            String order_number = (String) requestMap.get("order_number");
            String pay_number = (String) requestMap.get("pay_number");
            int tPrice = (Integer) requestMap.get("amount");
            log.info(user);
            OrderVO orderVO = new OrderVO();
            UserVO userVo = user.getUserVO();
            orderVO.setOrder_number(order_number);
            orderVO.setAddress(userVo.getAddress());
            orderVO.setRecipient(userVo.getName());
            orderVO.setUserID(userVo.getId());
            log.info(orderVO);
            purchaseService.insert_orderNumber(orderVO);

            PaymentVO paymentVO = new PaymentVO();
            paymentVO.setPay_number(pay_number);
            paymentVO.setOrder_number(order_number);
            paymentVO.setPrice(tPrice);
            paymentVO.setPurchase_date(LocalDateTime.now());
            log.info(paymentVO);

            purchaseService.insert_payment(paymentVO);

            Object object = requestMap.get("purchase_ready_data");

            if (object instanceof ArrayList<?>) {
                ArrayList<HashMap<String, Object>> purchaseReadyDataList = (ArrayList<HashMap<String, Object>>) object;
                for (HashMap<String, Object> purchaseData : purchaseReadyDataList) {
                    PurchaseVO purchaseVO = new PurchaseVO();
                    int no = Integer.parseInt(purchaseData.get("no").toString());
                    String product_name = purchaseData.get("product_name").toString().replaceAll("\\+", " ");
                    int count = Integer.parseInt(purchaseData.get("count").toString());
                    purchaseVO.setOrder_number(order_number);
                    purchaseVO.setProduct_name(product_name);
                    purchaseVO.setCount(count);
                    log.info(purchaseVO);
                    purchaseService.insert_purchase(purchaseVO);
                    productService.update_product_stock(count,product_name);
                    sellerService.update_sell_stock(count,product_name);
                    purchaseService.delete_basket(no);

                }

            }
        } catch (Exception e) {
            log.error("Error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
        return ResponseEntity.ok("Success"); // 성공적으로 처리되었음을 응답
    }

    // 구매목록
    @GetMapping("/user/mypage/orderList")
    public String orderList(@AuthenticationPrincipal SecurityUser user, Model model){
        int userTotalPrice = purchaseService.totalPayment(user.getUserVO().getId());
        model.addAttribute("userTotalPrice", userTotalPrice);
        return "user/mypage/orderList";
    }


    @GetMapping("/orderList")
    @ResponseBody
    public ResponseEntity<String> getOrderList(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam(defaultValue = "1") int page
    ) {
        int pageSize = 3; // 페이지 크기를 3로 설정
        JSONArray jsonArray = new JSONArray();
        String userID = user.getUserVO().getId();

        // 전체 데이터 가져오기
        List<OrderDTO> allOrderDTOS = purchaseService.find_my_list(userID);

        // 페이지 번호와 페이지 크기를 기반으로 현재 페이지 데이터 추출
        List<OrderDTO> pageOrderDTOS = getPageData(allOrderDTOS, page, pageSize);

        // 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) allOrderDTOS.size() / pageSize);

        // 현재 페이지 데이터를 JSON 배열에 추가
        pageOrderDTOS.forEach(orderDTO -> {
            String product_name = orderDTO.getPurchaseVO().getProduct_name();
            ProductDTO productDTO = productService.get_product(product_name);
            int price = productDTO.getProductVO().getPrice();
            String image = productDTO.getImagesVO().get(0).getImage();
            JSONObject jsonObject = new JSONObject(orderDTO);
            jsonObject.put("price", price);
            jsonObject.put("image", image);
            jsonArray.put(jsonObject);
        });

        // JSON 응답에 현재 페이지 데이터와 전체 페이지 수 추가
        JSONObject responseJson = new JSONObject();
        responseJson.put("currentPage", page);
        responseJson.put("totalPage", totalPage);
        responseJson.put("orderList", jsonArray);

        return ResponseEntity.ok(responseJson.toString());
    }

    // 페이지 데이터 추출 메서드
    private List<OrderDTO> getPageData(List<OrderDTO> allData, int page, int pageSize) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allData.size());
        return allData.subList(startIndex, endIndex);
    }
}
