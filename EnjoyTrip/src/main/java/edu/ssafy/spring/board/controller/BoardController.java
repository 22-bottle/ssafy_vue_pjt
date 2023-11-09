package edu.ssafy.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ssafy.spring.board.dto.BoardDto;
import edu.ssafy.spring.board.model.service.BoardService;
import edu.ssafy.spring.comment.dto.CommentDto;
import edu.ssafy.spring.comment.model.service.CommentService;
import edu.ssafy.spring.user.dto.UserDto;
import edu.ssafy.spring.util.PageNavigation;

@Controller
@RequestMapping("/board")
public class BoardController {

	private BoardService boardService;
	private CommentService commentService;

	@Autowired
	public BoardController(BoardService boardService, CommentService commentService) {
		super();
		this.boardService = boardService;
		this.commentService = commentService;
	}

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> map) {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> result = new HashMap();
		try {
			List<BoardDto> list = boardService.listArticle(map);
//			PageNavigation pageNavigation = boardService.makePageNavigation(map);
			result.put("articles", list);
//			result.put("navigation", pageNavigation);
			result.put("key", map.get("key"));
			result.put("word", map.get("word"));
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "조회 실패");
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		} 
		return response;
	}

	@GetMapping("/write")
	public ModelAndView write(@RequestParam Map<String, String> map, ModelAndView mav) {
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("/board/write");
		return mav;
	}

	@PostMapping("/write")
	public ResponseEntity<Map<String, Object>> write(@RequestBody BoardDto boardDto, HttpSession session) {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> map = new HashMap();
		try {
			boardService.writeArticle(boardDto);
			map.put("result", "삽입 성공");
			response = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "삽입 실패");
			response = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			return response;
		}
		return response;
	}

	@GetMapping("/view/{articleNo}")
	public ResponseEntity<Map<String, Object>> view(@PathVariable int articleNo) {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> map = new HashMap();
		try {
			BoardDto boardDto = boardService.getArticle(articleNo);
			List<CommentDto> list = commentService.listComment(articleNo);
			boardService.updateHit(articleNo);
			map.put("article", boardDto);
			map.put("comments", list);
			response = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "조회 실패");
			response = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			return response;
		}
		return response;
	}

	@GetMapping("/modify")
	public String modify(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model,
			HttpSession session) throws Exception {
		try {
			UserDto userDto = (UserDto) session.getAttribute("userInfo");
			if (userDto.getUserId() != null) {
				BoardDto boardDto = boardService.getArticle(articleNo);
				model.addAttribute("article", boardDto);
				model.addAttribute("pgno", map.get("pgno"));
				model.addAttribute("key", map.get("key"));
				model.addAttribute("word", map.get("word"));
				return "board/modify";
			} else {
				return "/user/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "글을 가져오다가 문제가 발생했어요!😥");
			return "/error/error";
		}
	}

	@PutMapping("/modify")
	public ResponseEntity<Map<String, Object>> modify(@RequestBody BoardDto boardDto) throws Exception {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> map = new HashMap();
		try {
			boardService.modifyArticle(boardDto);
			map.put("result", "수정 성공");
			response = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "수정 실패");
			response = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			return response;
		}
		return response;
	}

	@DeleteMapping("/delete/{articleNo}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int articleNo) throws Exception {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> map = new HashMap();
		try {
			boardService.deleteArticle(articleNo);
			map.put("result", "삭제 성공");
			response = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "삭제 실패");
			response = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			return response;
		}
		return response;
	}

	@PostMapping("/commentAdd")
	public ResponseEntity<Map<String, Object>> commentAdd(@RequestBody CommentDto commentDto) {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> map = new HashMap();
		try {
			commentService.addComment(commentDto);
			boardService.updateCommentCnt(commentDto);
			map.put("result", "삽입 성공");
			response = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "삽입 실패");
			response = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			return response;
		}
		return response;
	}

	@DeleteMapping("/commentDelete/{articleNo}/{commentNo}")
	public ResponseEntity<Map<String, Object>> commentDelete(@PathVariable int articleNo, @PathVariable int commentNo) {
		ResponseEntity<Map<String, Object>> response = null;
		Map<String, Object> map = new HashMap();
		try {
			commentService.deleteComment(commentNo);
			boardService.deleteCommentCnt(articleNo);
			map.put("result", "삭제 성공");
			response = new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "삭제 실패");
			response = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			return response;
		}
		return response;
	}

}
