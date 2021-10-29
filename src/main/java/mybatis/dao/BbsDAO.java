package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.BbsVO;
import mybatis.vo.CommVO;

@Component
public class BbsDAO {
	
	@Autowired
	private SqlSessionTemplate ss;
	
	//원하는 페이지의 게시물 목록기능
	
	public BbsVO[] getList(int begin, int end, String bname, String subject) {
		BbsVO[] ar = null;
		
		//맵퍼(bbs.list)를 호출하기 위해 Map구조 생성!!
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("bname", bname);
		map.put("subject", subject);
		
		List<BbsVO> list = ss.selectList("bbs.List", map);
		if(list != null && list.size() > 0) {
			//한개라도 넘어올경우!!
			ar = new BbsVO[list.size()];
			list.toArray(ar);
		}
		return ar;
	}
	
	public int getTotalCount(String bname) {
		
		return ss.selectOne("bbs.totalCount",bname);
	}
	//기본키를 인자로 하여 원글 검색하는기능
	public BbsVO getBbs(String b_idx) {
		
		return ss.selectOne("bbs.getBbs",b_idx);
	}
	//글을 저장해주는 친구
	public int addBbs(BbsVO vo) {
		
		return ss.insert("bbs.add",vo);
	}
	//댓글을 저장해주는 친구
	public int addAns(CommVO vo) {
		return ss.insert("bbs.addAns",vo);
	}
	//게시물업데이트해주는친구
	public int editBbs(BbsVO vo) {
		return ss.update("bbs.edit", vo);
	}
	//게시물 안보이게 해주는친구!(삭제가아니라 안보이는거야)
	public int del(String b_idx) {
		return ss.update("bbs.del", b_idx);
	}
	
}
