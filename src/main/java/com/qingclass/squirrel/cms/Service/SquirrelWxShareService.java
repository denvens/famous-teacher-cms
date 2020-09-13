package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.wechat.WxShare;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWxShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelWxShareService {
	
	@Autowired
    private SquirrelWxShareMapper squirrelWxShareMapper;

	/**
     *
     * */
	public List<WxShare> selectAll(WxShare wxShare){
	    int pageNo = wxShare.getPageNo();
	    int pageSize = wxShare.getPageSize();

        pageNo = (pageNo-1)*pageSize;
        wxShare.setPageNo(pageNo);

        return squirrelWxShareMapper.selectAll(wxShare);
    }

    /**
     *
     * */
    public WxShare selectByPrimaryKey(int id){
	    return squirrelWxShareMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * */
    public WxShare insert(WxShare wxShare){
        squirrelWxShareMapper.insert(wxShare);
        return wxShare;
    }

    /**
     *
     * */
    public int updateByPrimaryKey(WxShare wxShare){
        return squirrelWxShareMapper.updateByPrimaryKey(wxShare);
    }


}
