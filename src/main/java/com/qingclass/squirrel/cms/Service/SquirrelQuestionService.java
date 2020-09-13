package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelQuestion;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelQuestionService {
	
	@Autowired
    private SquirrelQuestionMapper squirrelQuestionMapper;

	public RequestInfo selectBy(SquirrelQuestion squirrelQuestion){
		RequestInfo info = new RequestInfo();
		List<SquirrelQuestion> squirrelQuestions = squirrelQuestionMapper.selectByPage(squirrelQuestion);

		if(squirrelQuestions.size()>0){
			Collections.sort(squirrelQuestions);
			info.setOrderMax(squirrelQuestions.get(squirrelQuestions.size()-1).getOrder());
		}

		info.setDataList(squirrelQuestions);
	    return info;
    }
	
	public List<SquirrelQuestion> selectQuestion(SquirrelQuestion squirrelQuestion){
		List<SquirrelQuestion> squirrelQuestions = squirrelQuestionMapper.selectByPage(squirrelQuestion);
	    return squirrelQuestions;
    }

	public RequestInfo selectAll(){
		RequestInfo info = new RequestInfo();

		info.setDataList(squirrelQuestionMapper.selectAll());
		return info;
	}

	public int getCount(SquirrelQuestion squirrelQuestion){
		return squirrelQuestionMapper.listCount(squirrelQuestion);
	}
	
	public RequestInfo insert(SquirrelQuestion squirrelQuestion){
		RequestInfo info = new RequestInfo();
		squirrelQuestionMapper.insert(squirrelQuestion);
		info.setDataObject(squirrelQuestion.getId());
		return info;
	}

	public RequestInfo updateByPrimaryKeySelective(SquirrelQuestion squirrelQuestion){
		RequestInfo info = new RequestInfo();
		squirrelQuestionMapper.updateByPrimaryKeySelective(squirrelQuestion);
		info.setDataObject(squirrelQuestion.getUnitId());
		return info;
	}

	public RequestInfo selectByPrimaryKey(Integer id){
		RequestInfo info = new RequestInfo();
		SquirrelQuestion squirrelQuestion = squirrelQuestionMapper.selectByPrimaryKey(id);

		info.setDataObject(squirrelQuestion);
		return info;
	}

	public RequestInfo deleteByPrimaryKey(Integer id){
		RequestInfo info = new RequestInfo();

		squirrelQuestionMapper.deleteByPrimaryKey(id);

		return info;
	}


}
