package com.increpas.bbs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import mybatis.vo.CommVO;
import spring.util.FileRenameUtil;
import spring.vo.ImgVO;

@Controller
public class WriteController {
	
	//에디터에서 이미지가 들어갈 때 해당 이미지를 받아서
	//저장할 위치
	private String editor_img = "/resources/editor_img";
	private String bbs_upload = "/resources/bbs_upload";
	
	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private ServletContext api;
	
	@Autowired
	private HttpServletRequest req;
	
	@RequestMapping("/write.inc")
	public String goWrite() {
		return "write";
	}
	
	@RequestMapping(value="/saveImg.inc", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveImg(ImgVO vo) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		
		MultipartFile f = vo.getS_file();
		
		String fname = null;
		if(f.getSize() > 0) {
			String realPath = api.getRealPath(editor_img);
			
			fname = f.getOriginalFilename();
			
			//이미지가 이미 저장된 이미지 이름과 동일하다면
			//파일명 뒤에 숫자를 하나 붙여서 중복예외처리를 해줘야한다!
			fname = FileRenameUtil.checkSameFileName(fname, realPath);
			try {
				f.transferTo(new File(realPath, fname));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		String cPath = req.getContextPath();
		
		map.put("url", cPath+editor_img);
		map.put("fname", fname);
		return map;
	}
	@RequestMapping(value="write.inc", method=RequestMethod.POST)
	public ModelAndView write(BbsVO vo) {
		ModelAndView mv = new ModelAndView();
		//파일을 첨부했냐 안했냐 알아내기
		MultipartFile f = vo.getFile();
		
		String fname = null;
		if(f.getSize() > 0 && f != null) {
			//뭔가 저장을 할려면
			// 경로를 얻어내고 유효성검사를 하고 파일을 생성한다 !
			
			String realPath = api.getRealPath(bbs_upload);
			
			fname = f.getOriginalFilename();
			fname = FileRenameUtil.checkSameFileName(fname, realPath);
			
			try {
				//첨부파일 업로드 인자로 파일객체를 생성해야한다.
				//왜와이 트렌스펄투 객체는 파일객체를 인자로받는다
				f.transferTo(new File(realPath, fname));
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setFile_name(fname);
			vo.setOri_name(fname);
		}
		vo.setBname("BBS");
		vo.setIp(req.getRemoteAddr());
		
		b_dao.addBbs(vo);
		mv.setViewName("redirect:/list.inc");
		
	  return mv;
	}
	
	@RequestMapping("/ans_write.inc")
	public ModelAndView ans(CommVO vo, String cPage) {
		ModelAndView mv = new ModelAndView();
			b_dao.addAns(vo);
			mv.setViewName("redirect:/view.inc?b_idx="+vo.getB_idx()+"&cPage="+cPage);
		return mv;
	}
}
