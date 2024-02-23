package com.bread.bakelab.controller;

import com.bread.bakelab.domains.security.SecurityUser;
import com.bread.bakelab.domains.vo.UserVO;
import com.bread.bakelab.mapper.UserMapper;
import com.bread.bakelab.service.SMSService;
import com.bread.bakelab.service.UserMailService;
import com.bread.bakelab.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired private UserMapper userMapper;
    @Autowired private UserService userService;
    @Autowired private SMSService smsService;
    @Autowired private UserMailService userMailService;



    // 유저 역할을 가져오게함
    @GetMapping("/api")
    @ResponseBody
    public Map<String, String> getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> result = new HashMap<>();
        if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            result.put("role", securityUser.getUserVO().getRole());
        } else {
            result.put("role", "UNKNOWN"); // 사용자가 인증되지 않았을 때의 처리
        }
        return result;
    }

    @GetMapping("/login")
    public void get_login(){}

    @PostMapping("/login")
    public void post_login(){}

    @GetMapping("/logout")
    public void get_logout(){}

    @PostMapping("/logout")
    public void post_logout(){}

    /******************* 회원가입 ***********************/
    @PostMapping("/reg")
    public void post_reg(){

    }
    //소셜 회원가입
    @GetMapping("/socialjoin")
    public void get_social_join( Authentication authentication,Model model){
        SecurityUser user=(SecurityUser)authentication.getPrincipal();
        model.addAttribute("userEmail",user.getUserVO().getEmail());
        UserVO vo=userMapper.get_user_email(user.getUserVO().getEmail());

    }
    @PostMapping("/socialjoin")
    public String post_social_join(HttpSession session,
                                   Authentication authentication,
                                   @Validated UserVO userVO,
                                   @RequestParam(defaultValue = "false") boolean phone_verified,
                                   BindingResult result
    ){
        if(result.hasErrors()){
            log.error("USER_VO의 형식이 맞지 않음");
            log.info(userVO);
            return "redirect:user/socialjoin";
        }
        Object phoneobject = session.getAttribute("phone_verified");
        if(!phone_verified || phoneobject == null || !(boolean)phoneobject){
            log.error("휴대폰 인증이 안된 사용자!");
            return "redirect:user/socialjoin";
        }
        if(userVO == null){
            log.info(userVO);
            return "redirect:user/socialjoin";
        }
        userService.social_join_user(userVO,authentication);
        return "redirect:user/logout";
    }
    //일반 회원가입
    @GetMapping("/join")
    public void get_join(){

    }
    @PostMapping("/join")
    public String post_join( HttpSession session,
                             @Validated UserVO userVO,
                             @RequestParam(defaultValue = "false") boolean phone_verified,
                             @RequestParam(defaultValue = "false")  boolean email_verified,
                             BindingResult result){
        if(result.hasErrors()){
            log.error("USER_VO의 형식이 맞지 않음");
            log.info(userVO);
            return "redirect:user/join";
        }

        Object phoneobject = session.getAttribute("phone_verified");
        Object emailobject = session.getAttribute("email_verified");
        Object idObject = session.getAttribute("id_checked");

        if(!phone_verified || phoneobject == null || !(boolean)phoneobject){
            log.error("휴대폰 인증이 안된 사용자!");
            return "redirect:user/join";
        }
        if(!email_verified||emailobject==null ||!(boolean)emailobject){
            log.error("이메일 인증이 안된 사용자");
            return "redirect:user/join";
        }
        if(userVO == null){
            log.info(userVO);
            return "redirect:user/join";
        }
        userService.join_user(userVO);
        return "user/reg";
    }


    // 회원 가입 전 아이디 중복 체크
    @ResponseBody
    @GetMapping("/join/id")
    public boolean get_join_id(
            @RequestParam String userId
    ){
        int checked = userMapper.check_user_id(userId);

        // DB에 해당 아이디 존재 한다
        if (checked != 0) {
            log.info("기존 유저가 사용 중인 ID!");
            return false;
        }
        // 존재하지 않는다.
        log.info("가입 가능한 ID!");
        return true;
    }

    /* ****************** 이메일 인증 ********************** */
    // 이메일 인증 요청시 이메일보냄
    @ResponseBody
    @GetMapping("/join/email")
    public boolean get_join_email(HttpSession session,@RequestParam String userEmail){
        if(session.getAttribute("email_key") != null){
            log.warn("이미 email_key 존재하므로, 기존 코드를 삭제합니다");
            session.removeAttribute("email_key");
            return false;
        }
        session.setAttribute("email_verified", false);
        int number = userMailService.sendMail(userEmail);
        session.setAttribute("email_key",number);
        log.info("email_key 생성완료");
        return true;
    }
    //이메일 인증번호 작성하고 인증 시도
    @ResponseBody
    @GetMapping("/join/email/verify")
    public boolean get_email_code(HttpSession session,
                                  @RequestParam int userEmailKey){
        Object object = session.getAttribute("email_key");
        if(object == null){
            log.error("생성되어있는 email_key 존재하지 않음!");
            return false; //인증 실패!
        }
        int email_key = (int) object;
        log.info(email_key);
        if(email_key!=userEmailKey){
            log.error("email_key가 일치하지않음");
            return false;
        }
        log.info("email_code가 일치함! 인증 성공!");
        session.setAttribute("email_verified", true);
        session.removeAttribute("email_key");
        return true;
    }

    /******************* 휴대폰 인증 ***********************/

    // 인증번호 요청 시 인증번호를 생성
    @ResponseBody
    @GetMapping("/sms/key")
    public boolean get_verifyKey(
            HttpSession session,
            @RequestParam String phoneNumber
    ){
        session.setAttribute("phone_verified", false);
        // 이미 한번 인증 요청을 해서, 인증번호를 받은 적이 있다면
        if(session.getAttribute("VERIFY_KEY") != null){
            log.warn("이미 VERIFY_KEY가 존재하므로, 기존 코드를 삭제합니다");
            session.removeAttribute("VERIFY_KEY");
        }
        // 새로 VERIFY_KEY를 발급받음
        String VERIFY_KEY = smsService.get_verify_key(phoneNumber);
        // 발급에 실패했다면
        if(VERIFY_KEY == null){
            log.error("VERIFY_KEY가 생성되지 않았음 => SMS 요청 실패!");
            return false;
        }
        // 발급에 성공했다면
        log.info("VERIFY_KEY가 생성되었음 => " + VERIFY_KEY);
        session.setAttribute("VERIFY_KEY", VERIFY_KEY);
        return true;
    }

    // 사용자가 인증번호를 작성하고 인증 시도
    @ResponseBody
    @GetMapping("/sms/verify")
    public boolean get_verify(
            HttpSession session,
            @RequestParam("key") String userKey
    ){
        Object object = session.getAttribute("VERIFY_KEY");
        session.setAttribute("phone_verified", false);
        // 인증번호를 발급받은적이 없는데 session에서 가져오는것은 에러
        if(object == null){
            log.error("생성되어있는 VERIFY_KEY가 존재하지 않음!");
            return false; //인증 실패!
        }
        String VERIFY_KEY = (String) object;
        log.info("생성되어있는 VERIFY_KEY => " + VERIFY_KEY);
        // 사용자가 입력한 값이 틀렸다면
        if(!VERIFY_KEY.equals(userKey)){
            return false;
        }
        // 사용자가 입력한 값과, 기존 코드가 동일하다면
        log.info("VERIFY_KEY가 일치함! 인증 성공!");
        session.setAttribute("phone_verified", true);
        session.removeAttribute("VERIFY_KEY");
        return true;
    }

    /******************* 아이디 / 비밀 번호 찾기 ***********************/

    //////////// 아이디 /////////////

    @GetMapping("/find/id")
    public String get_find_id(){
        return "user/find/find_id";
    }

    @GetMapping("/find/id/view")
    public String get_find_id_view(){
        return "user/find/find_id_view";
    }

    @PostMapping("/find/id/view")
    public String post_find_id_view(
            @RequestParam ("u_name") String name,
            @RequestParam String email_id,
            @RequestParam String email_dns,
            Model model
    ){
        String email = email_id + "@" + email_dns;
        UserVO vo = userMapper.get_user_find_id(name, email);
        model.addAttribute("name", name);
        if(vo == null){
            model.addAttribute("userid","찾을 수 없음");
            return "user/find/find_id_view";
        }
        model.addAttribute("userid", vo.getId());


        return "user/find/find_id_view";
    }

    @GetMapping("/find/pwd")
    public String get_find_pwd(){
        return "user/find/find_pwd";
    }

    @GetMapping("/find/pwdAccount")
    public String get_find_pwd_account(){
        return "user/find/find_pwd_account";
    }

    @PostMapping("/find/pwdAccount")
    public String post_find_pwd_account(
            @RequestParam String id,
            Model model
    ){
        UserVO userVO = userMapper.get_user(id);
        model.addAttribute("userName", userVO.getName());
        model.addAttribute("userId", userVO.getId());

        return "user/find/find_pwd_account";
    }

    @GetMapping("/find/pwdReform")
    public String get_reform_pwd(
            UserVO userVO,
            Model model
    ){
        model.addAttribute("userId", userVO.getId());
        log.info(userVO);

        return "user/find/reform_pwd";
    }

    @PostMapping("/find/pwdReform")
    public String post_reform_pwd(
            @AuthenticationPrincipal SecurityUser user,
            UserVO userVO,
            HttpServletRequest request
    ){
        log.info("user:"+user);
        log.info("userVo:"+userVO);

        userService.update_pw(user, userVO);

        // 세션 삭제
        HttpSession session = request.getSession(false);
        // null 이 아니다 => 기존 세션 존재
        // null 이다 => session.invalidate()로 세션 삭제
        if (session != null){
            session.invalidate();
        }

        return "redirect:user/find/pwdReform/view";
    }

    @GetMapping("/find/pwdReform/view")
    public String get_reform_pwd_view(){
        return "user/find/reform_pwd_view";}

    // 아이디 일치 확인
    @ResponseBody
    @GetMapping("/find/pwd/check")
    public boolean post_find_pwd(
            @RequestParam String userId
    ){
        int checked = userMapper.check_user_id(userId);

        log.info(userId);
        log.info(checked);

        // DB에 해당 아이디 존재 하지 않는다
        if (checked == 0) {
            log.info("일치 하는 ID 없음!");
            return false;
        }
        // 존재 한다
        log.info("일치 하는 ID 찾음!");
        return true;
    }
    /* ****************** 마이 페이지 관련 ********************** */

    @GetMapping("/mypage/info")
    public void get_mypage_info(){}

    @GetMapping("/mypage/infoUpdate")
    public void get_info_update(){}

    @PostMapping("/mypage/infoUpdate")
    public String post_info_update(
            @AuthenticationPrincipal SecurityUser user,
            UserVO userVO
    ){
        userService.update_user(user, userVO);
        return "redirect:user/mypage/info";
    }
    /******************* 회원 탈퇴 ********************** */
    @GetMapping("/delete/account/{id}")
    public String delete_account(@PathVariable String id,HttpSession session){
        userMapper.delete_account(id);
        session.invalidate();
        return "user/mypage/delete_account";
    }
}