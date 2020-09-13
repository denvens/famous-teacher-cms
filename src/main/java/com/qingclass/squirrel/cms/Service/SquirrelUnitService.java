package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelUnit;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelQuestionMapper;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelUnitService {
	
	@Autowired
    private SquirrelUnitMapper squirrelUnitMapper;
	@Autowired
    private SquirrelQuestionMapper squirrelQuestionMapper;


	/**
     *
     * */
    public RequestInfo selectBy(SquirrelUnit squirrelUnit){
        RequestInfo info;
        info = new RequestInfo();

        List<SquirrelUnit> squirrelUnits = squirrelUnitMapper.selectBy(squirrelUnit);

        if(squirrelUnits.size()>0){
            Collections.sort(squirrelUnits);
            info.setOrderMax(squirrelUnits.get(squirrelUnits.size()-1).getOrder());
        }
        info.setDataList(squirrelUnits);

        return info;
    }

    /**
     *
     * */
    public RequestInfo selectAll(){
        RequestInfo info;
        info = new RequestInfo();

        List<SquirrelUnit> squirrelUnits = squirrelUnitMapper.selectAll();

        info.setDataList(squirrelUnits);
        return info;
    }

    /**
     *
     * */
    public RequestInfo selectByPrimaryKey(Integer id){
        RequestInfo info;
        info = new RequestInfo();

        SquirrelUnit squirrelUnit = squirrelUnitMapper.selectByPrimaryKey(id);

        info.setDataObject(squirrelUnit);

        return info;
    }

    /**
     *
     * */
    public int selectCountByLessonId(int lessonId){
        return squirrelUnitMapper.selectCountByLessonId(lessonId);
    }

    /**
     *
     * */
    public RequestInfo insert(SquirrelUnit squirrelUnit){
        RequestInfo info;
        info = new RequestInfo();

        squirrelUnitMapper.insert(squirrelUnit);
        info.setDataObject(squirrelUnit.getId());
        return info;
    }

    /**
     *
     * */
    public RequestInfo update(SquirrelUnit squirrelUnit){
        RequestInfo info;
        info = new RequestInfo();

        int i = squirrelUnitMapper.updateByPrimaryKeySelective(squirrelUnit);

        return info;
    }

    public RequestInfo delete(Integer id){
        RequestInfo info;
        info = new RequestInfo();

        squirrelUnitMapper.deleteByPrimaryKey(id);
        squirrelQuestionMapper.deleteByUnitId(id);
        return info;
    }
}
