package com.naver.ccy6451;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.ccy6451.domain.Board;
import com.naver.ccy6451.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "board/register", method = RequestMethod.GET)
	public String register(Model model) {
		return "board/register";
	}

	@RequestMapping(value = "board/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, RedirectAttributes attr, Model model) {
		boardService.register(request);
		attr.addFlashAttribute("msg", "게시글 작성에 성공");
		// 데이터베이스에 저장하는 작업을 수행했으므로
		// 리다이렉트로 이동
		return "redirect:list";
	}

	@RequestMapping(value = "board/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		List<Board> list = boardService.list();
		model.addAttribute("list", list);
		return "board/list";
	}

	// 게시물 상세보기를 처리
	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, Model model) {
		Board board = boardService.detail(request);
		model.addAttribute("vo", board);
		return "board/detail";
	}

	// 게시물 수정보기를 처리
	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Model model) {
		Board board = boardService.updateView(request);
		model.addAttribute("vo", board);
		return "board/update";
	}

	// 게시물 수정을 처리해 줄 메소드
	@RequestMapping(value = "board/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, RedirectAttributes attr, Model model) {
		boardService.update(request);
		//애드플래시인데 애드어트리부트로 했다.
		attr.addFlashAttribute("msg", "수정성공");
		return "redirect:list";
	}
}
