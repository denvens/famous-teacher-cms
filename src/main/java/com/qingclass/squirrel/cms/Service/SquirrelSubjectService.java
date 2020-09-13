package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelSubjectService {
	
	@Autowired
    private SquirrelSubjectMapper squirrelSubjectMapper;

	public RequestInfo selectAll(){
		RequestInfo info;
		info = new RequestInfo(false, true, null);

		info.setDataList(squirrelSubjectMapper.selectAll());

	    return info;
    }

}
