package com.increpas.bbs;

import java.io.File;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.FileRenameUtil;

@Controller
public class EditController {

	private String editor_img = "/resources/editor_img";
	private String bbs_upload = "/resources/bbs_upload";
	
	@Autowired
	private BbsDAO b_dao;

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private ServletContext api;

	

	@RequestMapping(value="/edit.inc", method=RequestMethod.POST)
	public ModelAndView edit(BbsVO vo, String cPage) {
		ModelAndView mv = new ModelAndView();

		String ctx = req.getContentType();
		String fname = null;

		if (ctx.startsWith("application")) {

			BbsVO bvo = b_dao.getBbs(vo.getB_idx());
			mv.addObject("vo", bvo);
		} else if (ctx.startsWith("multipart")) {
			MultipartFile f = vo.getFile();
			if (f.getSize() > 0) {
				String realPath = api.getRealPath(bbs_upload);
				fname = f.getOriginalFilename();
				fname = FileRenameUtil.checkSameFileName(fname, realPath);

				try {
					f.transferTo(new File(realPath, fname));
				} catch (Exception e) {
					e.printStackTrace();
				}
				vo.setFile_name(fname);
				vo.setOri_name(fname);
			}

			vo.setIp(req.getRemoteAddr());
			b_dao.editBbs(vo);
			mv.setViewName("redirect:/view.inc?b_idx=" + vo.getB_idx() + "&cPage=" + cPage);
		}

		return mv;
	}

}
