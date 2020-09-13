package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelLevel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelLevelService {
	
	@Autowired
    private SquirrelLevelMapper squirrelLevelMapper;


    public RequestInfo list(SquirrelLevel squirrelLevel){
        RequestInfo info;
        info = new RequestInfo(false, true, null);

        info.setDataList(squirrelLevelMapper.selectBy(squirrelLevel));

        return info;
    }

    public RequestInfo insert(SquirrelLevel squirrelLevel){
        RequestInfo info;
        info = new RequestInfo(false, true, null);

        int returnInt = squirrelLevelMapper.insert(squirrelLevel);

        System.out.println("returnInt:"+returnInt);

        return info;
    }

    public RequestInfo update(SquirrelLevel squirrelLevel){
        RequestInfo info;
        info = new RequestInfo();

        squirrelLevelMapper.updateByPrimaryKeySelective(squirrelLevel);

        return info;

    }

    public RequestInfo selectByPrimaryKey(Integer id){
        RequestInfo info;
        info = new RequestInfo();

        info.setDataObject(squirrelLevelMapper.selectByPrimaryKey(id));

        return info;
    }

}
