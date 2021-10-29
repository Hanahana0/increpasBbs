package com.increpas.bbs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class ViewController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpServletRequest req;
	
	@RequestMapping("/view.inc")
	public ModelAndView goView(String b_idx, String cPage) {
		ModelAndView mv = new ModelAndView();
		
		BbsVO vo = b_dao.getBbs(b_idx);
		
		//여기서 cPage도 사실 가야하는데 request에 저장되기때문에
		//저장할 필요가 없다.
		//view.jsp로 forward되므로 여기까지 전달된
		//파라미터들이 모두 같이 가게된다.
		mv.addObject("vo",vo);
		mv.addObject("ip", req.getRemoteAddr());
		mv.setViewName("view");
		return mv;
	}
}
