package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelWord;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelWordService {
	
	@Autowired
    SquirrelWordMapper squirrelWordMapper;

	public RequestInfo selectByPrimaryKey(Integer id){
        RequestInfo info;
	    info = new RequestInfo();

        SquirrelWord squirrelWord = squirrelWordMapper.selectByPrimaryKey(id);
        info.setDataObject(squirrelWord);
        return info;
    }

    public RequestInfo updateByPrimaryKeySelective(SquirrelWord squirrelWord){
        RequestInfo info;
        info = new RequestInfo();

        int i = squirrelWordMapper.updateByPrimaryKeySelective(squirrelWord);

        return info;
    }

    public RequestInfo selectBy(SquirrelWord squirrelWord){
        RequestInfo info;
        info = new RequestInfo();

        List<SquirrelWord> squirrelWords = squirrelWordMapper.selectBy(squirrelWord);

        info.setDataList(squirrelWords);
        return info;
    }

    public RequestInfo selectAll(SquirrelWord squirrelWord){
        RequestInfo info;
        info = new RequestInfo();


        squirrelWord.setPageNo((squirrelWord.getPageNo()-1)*squirrelWord.getPageSize());

        List<SquirrelWord> squirrelWords = squirrelWordMapper.selectAll(squirrelWord);

        info.setDataList(squirrelWords);
        return info;
    }

    public RequestInfo insert(SquirrelWord squirrelWord){
        RequestInfo info;
	    info = new RequestInfo();

        squirrelWordMapper.insert(squirrelWord);

        return info;
    }

}
