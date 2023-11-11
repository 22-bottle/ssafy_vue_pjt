package edu.ssafy.spring.user.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ssafy.spring.user.dto.UserDto;
import edu.ssafy.spring.user.model.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserService service;
	
	@Autowired
	public UserController(UserService service) {
		super();
		this.service = service;
	}
	
    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto, HttpSession session) {
    	try {
	        String salt = service.getSalt(userDto.getUserId());
	        if (salt == null) {
	        	throw new Exception();
	        }
	        userDto.setSalt(salt);
	        service.updateUser(userDto);
	        UserDto userInfo = service.getUser(userDto.getUserId());
	        session.setAttribute("userInfo", userInfo);
	        return ResponseEntity.ok("include/updatesuccess");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.ok("error/error");
    	}
    }
	
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String userId) {
        int cnt = service.deleteUser(userId);
        return cnt > 0 ? ResponseEntity.ok("include/deletesuccess") : ResponseEntity.ok("include/deletefail");
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody UserDto userDto) throws IOException {
    	try {
    		service.registUser(userDto);
    		return ResponseEntity.ok("include/joinsuccess");
    	} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error/error");
		}
        
    }
    
    @GetMapping("/join")
	private String join() {
    	return "user/join";
	}
    
    @GetMapping("/login")
	private String login() {
    	return "user/login";
	}
    
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/index";
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "user/mypage";
	}
    
    @PostMapping("/login")
	private ResponseEntity<?> login(@RequestBody UserDto userDto, 
						 @RequestParam(required = false) String remember,
						 HttpServletRequest request,
						 HttpServletResponse response,
						 HttpSession session) throws IOException {
		boolean login = service.loginUser(userDto);
		
		if(login) {
			String userId = userDto.getUserId();
			UserDto userInfo = service.getUser(userId);
			session.setAttribute("userInfo", userInfo);
			if(remember != null) {
				Cookie c = new Cookie("id", userId);
				c.setPath("/");
				c.setMaxAge(60*60*24);
				response.addCookie(c);
			} else {
				Cookie c = new Cookie("id", userId);
				c.setMaxAge(0);
				response.addCookie(c);
			}
			return  ResponseEntity.ok("include/loginsuccess");
		} else {
			return  ResponseEntity.ok("include/loginfail");
		}
		
	}
	
    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody UserDto userDto) {
        boolean find = service.findUser(userDto);
        return find ? ResponseEntity.ok("redirect:/include/findsuccess") : ResponseEntity.ok("redirect:/include/findfail");
    }

}
