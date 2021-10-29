package com.increpas.bbs;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.Paging;

@Controller
public class ListController {

	@Autowired
	BbsDAO b_dao;

	// **페이징기법 변수들**
	// 리스트 원하는대로 보고싶으면 여기서 조정하면됨!!
	int block_list = 8;// 한페이지당 보여질 게시물의 수
	int block_page = 3;// 한블럭당 보여질 페이지 수
	int nowPage;
	int rowTotal; // 전체게시물의 수
	String pageCode; // 여기다가 페이징코드를 담아서 보낼거야 !!
	// **********************
	
	List<BbsVO> b_list;

	@RequestMapping("list.inc")
	public ModelAndView List(String cPage, String bname, String search) {
		ModelAndView mv = new ModelAndView();

		if (cPage == null)
			nowPage = 1;
		else
			nowPage = Integer.parseInt(cPage);

		if (bname == null)
			bname = "BBS"; // 일반게시물

		int begin = 0;
		int end = 0;
		
		//전체 게시물 수 구하기!!!
		rowTotal = b_dao.getTotalCount(bname);

		//전체게시물 수를 구하면 페이징처리를 위한 객체를 생성할 수 있다!
		//그게바로 Paging이라는 객체다.
		Paging page = new Paging(nowPage, rowTotal, block_list, block_page);

		//pageCode = page.getSb().toString(); // 만들어놓은 페이징을위한 코드 가져오기

		begin = page.getBegin();
		end = page.getEnd();
		BbsVO[] ar = null;
		
		if(search == null) {
			System.out.println("안들어왔다ㅣ.");
			ar = b_dao.getList(begin, end, bname, null);
			mv.setViewName("list");// views/list.jsp를 만들어야한다 
			pageCode = page.getSb().toString();
		}else {
			System.out.println("값들어왔다.");
			ar = b_dao.getList(begin, end, bname, search);
			b_list = Arrays.asList(ar);
			
			if(b_list != null && !b_list.isEmpty()) {
				// 들어왔을 때
				Paging page1 = new Paging(nowPage, rowTotal, block_list, block_page);
				begin = page1.getBegin();
				end = page1.getEnd();
				pageCode = page1.getSb().toString();
				b_list.toArray(ar);
			}else {
				//값은 입력했지만 출력결과물이 없을경우
				
			}
			mv.setViewName("list");// views/list.jsp를 만들어야한다 !
		}

		// JSP에서 표현해야 하므로 mv에 저장해주자 !
		// 여기서 mv에 저장을하면 request에 저장이되는거야 !!
		// 번호표현할려고 저장한 친구들!
		mv.addObject("nowPage", nowPage);
		mv.addObject("rowTotal", rowTotal);
		mv.addObject("blockList", block_list);
		
		//페이징 코드!
		mv.addObject("pageCode", pageCode);
		mv.addObject("ar", ar);
		

		return mv;
	}
	@RequestMapping("search_list")
	public BbsVO[] searchBbs(BbsVO vo) {
		BbsVO[] ar = null;
		
		return ar;
	}

}
