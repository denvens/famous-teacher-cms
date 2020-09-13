package com.qingclass.squirrel.cms.mapper.cms;

import com.qingclass.squirrel.cms.entity.cms.SquirrelSubject;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquirrelSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SquirrelSubject record);

    int insertSelective(SquirrelSubject record);

    SquirrelSubject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SquirrelSubject record);

    int updateByPrimaryKey(SquirrelSubject record);

    @Select("select id,name from squirrel_subjects")
    List<SquirrelSubject> selectAll();

    List<SquirrelSubject> selectBy(SquirrelSubject record);  //多条件查询
}