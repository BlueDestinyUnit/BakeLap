package com.bread.bakelab.service;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;



@Service
@Log4j2
@PropertySource("classpath:properties/naver-sms.properties")
public class SMSService {
    @Value("${SERVICE_ID}")
    private String SERVICE_ID;
    @Value("${REQUEST_URL}")
    private String REQUEST_URL;
    @Value("${API_URL}")
    private String API_URL;
    @Value("${ACCESS_KEY}")
    private String ACCESS_KEY;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${MESSAGE}")
    private String MESSAGE;
    @Value("${FROM_NUMBER}")
    private String FROM_NUMBER;

    private String TIME_STAMP;
    private String FQN;


    // SMS를 보내고, 결과 상태에 따라 인증번호를 반환
    public String get_verify_key(String TO_NUMBER) {
        // 인증번호 생성
        String VERIFY_KEY = create_key();
        // SMS 요청
//        HttpStatus status = send_sms(TO_NUMBER, VERIFY_KEY);
//        // 요청 결과 확인
//        if(status.is5xxServerError() || status.is4xxClientError()){
//            return null; // 요청에 실패
//        }
        return VERIFY_KEY; // 요청 성공 => key 반환
    }


    // 요청 보낼 때 사용하는 NAVER CLOUD SERVICE의 시그니쳐 생성 코드
    public String makeSignature(String method, String url) throws Exception {
        String space = " ";                    // one space
        String newLine = "\n";                    // new line
        String timestamp = TIME_STAMP;            // current timestamp (epoch)
        String accessKey = ACCESS_KEY;            // access key id (from portal or Sub Account)
        String secretKey = SECRET_KEY;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    //TO_NUMBER=>받는 사람
    public HttpStatus send_sms(String TO_NUMBER, String VERIFY_KEY) {
        //인증번호가 부착된 메세지 생성
        MESSAGE += " [" + VERIFY_KEY + "]";
        // 0. 현재 타임 스탬프 값 생성

        TIME_STAMP = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());

        log.info("REST_TEMPLATE 생성");
        //1. RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();
        //1-1. //1-1. 응답 메세지를 받기 위한 HttpComponentsClientHttpRequestFactory 객체 생성 및 RestTemplate에 삽입
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(20000);//커넷션 타임 아웃 20초
        factory.setReadTimeout(20000);//REad 타임아웃 20초
        HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(50).build();//최대 커넷션수 50
        factory.setHttpClient(httpClient);
        //RestTemplate에 삽입
        restTemplate.setRequestFactory(factory);

        //2. HTTP 헤더 생성
        //2-1. 헤더에 실어줄 Signature 생성
        REQUEST_URL = REQUEST_URL.replace("{serviceId}", SERVICE_ID);
        FQN = API_URL + REQUEST_URL;
        String SIGNATURE;
        try {
            log.info("SIGNATURE 생성");
            SIGNATURE = makeSignature("POST", REQUEST_URL);
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.info("HEADER 생성");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-ncp-apigw-timestamp", TIME_STAMP);
        httpHeaders.set("x-ncp-iam-access-key", ACCESS_KEY);
        httpHeaders.set("x-ncp-apigw-signature-v2", SIGNATURE);

        //3. JSON 형태의 body 데이터 생성
        JSONObject body = new JSONObject();
        body.put("type", "SMS");
        body.put("contentType", "COMM");
        body.put("countryCode", "82");
        body.put("from", FROM_NUMBER); //보내는 사람 번호
        body.put("content", MESSAGE);//보내는 메세지 내용

        JSONObject messageBodies = new JSONObject();
        messageBodies.put("content", MESSAGE);
        messageBodies.put("to", TO_NUMBER);

        body.put("messages", List.of(messageBodies));

        log.info("생성된 BODY=>" + body);

        //4. body데이터와 HTTP결합
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), httpHeaders);
        //5. URL과 메소드 설정 및 6. HTTP 요청
        ResponseEntity<String> response = restTemplate.postForEntity(FQN, entity, String.class);
        //POST 요청 결과 받아오기
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        return response.getStatusCode();
    }

    // 4자리의 랜덤 숫자 생성 (인증번호)
    private String create_key() {
        SecureRandom secureRandom = new SecureRandom();
        String randomNumber = String.valueOf(secureRandom.nextInt(10000));
        randomNumber = "0".repeat(4 - randomNumber.length()) + randomNumber;
        return randomNumber;
    }

}

