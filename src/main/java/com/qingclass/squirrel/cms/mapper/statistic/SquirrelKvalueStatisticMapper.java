package com.qingclass.squirrel.cms.mapper.statistic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.qingclass.squirrel.cms.entity.statistic.SquirrelKvalueStatistic;

@Repository
public interface SquirrelKvalueStatisticMapper {

	@Insert("insert into squirrel_kvalue_statistics (`date`, onRead, \n" + 
			"      `share`, goShare, care, \n" + 
			"      init, buy, buySuccess)\n" + 
			"    values (#{date,jdbcType=DATE}, #{onRead,jdbcType=INTEGER}, \n" + 
			"      #{share,jdbcType=INTEGER}, #{goShare,jdbcType=INTEGER}, #{care,jdbcType=INTEGER}, \n" + 
			"      #{init,jdbcType=INTEGER}, #{buy,jdbcType=INTEGER}, #{buySuccess,jdbcType=INTEGER})")
    int insert(SquirrelKvalueStatistic record);

	@Select("select ifnull(count(1),0) from squirrel_kvalue_statistics a where `date`= DATE_SUB(STR_TO_DATE(curdate(), '%Y-%m-%d'), INTERVAL 1 DAY)")
	int selectStatistic();


	@Select("<script>" +
    	    "SELECT `date`,format(ifnull(buySuccess/onRead,0)* #{lessonDay},2) as kValue,concat(format(ifnull(buySuccess/onRead,0)*100,2),'%') as conversion, `onRead`,concat(format(ifnull(share/onRead,0)*100,2),'%') as shareRate, `share`,concat(format(ifnull(goShare/`share`,0)*100,2),'%') as goShareRate, `goShare`, `care`, \n" + 
    	    "concat(format(ifnull(`care`/goShare,0)*100,2),'%') as careRate,`init`,concat(format(ifnull(`init`/`care`,0)*100,2),'%') as initRate, `buy`, concat(format(ifnull(`buy`/init,0)*100,2),'%') as buyRate,`buySuccess`  from squirrel_kvalue_statistics where  1=1"+
    	    "<if test='levelId != null'>" +
	            " and levelId=#{levelId}" +
	        "</if>" +
    	    "<if test='startTime != null and endTime!=null'>" +
	            " and `date` BETWEEN  CAST(#{startTime} AS date)  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        "<if test='startTime != null and endTime==null'>" +
		        " and `date` BETWEEN  CAST(#{startTime} AS date)  and CURRENT_DATE() " +
		    "</if>" +
		    "<if test='startTime == null and endTime!=null'>" +
		    " and `date` BETWEEN  CURRENT_DATE()  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        " order by id desc "+
        "</script>")
	List<Map<String, Object>> selectAllByConditionMap(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("levelId")Integer levelId,
			@Param("lessonDay")Integer lessonDay);

	@Select("<script>" +
    	    "SELECT count(1)  from squirrel_kvalue_statistics where  1=1"+
    	    "<if test='levelId != null'>" +
	            " and levelId=#{levelId}" +
	        "</if>" +
    	    "<if test='startTime != null and endTime!=null'>" +
	            " and `date` BETWEEN  CAST(#{startTime} AS date)  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        "<if test='startTime != null and endTime==null'>" +
		        " and `date` BETWEEN  CAST(#{startTime} AS date)  and CURRENT_DATE() " +
		    "</if>" +
		    "<if test='startTime == null and endTime!=null'>" +
		    " and `date` BETWEEN  CURRENT_DATE()  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        " order by id desc "+
        "</script>")
	int selectCountByCondition(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("levelId")Integer levelId,
			@Param("lessonDay")Integer lessonDay);
	@Select("<script>" +
    	    "SELECT ifnull(auditionInit,0) as init,ifnull(auditionBuySuccess,0) as buySuccess,`date`,concat(format(ifnull(auditionBuySuccess/auditionInit,0)*100,2),'%') as buySuccessRatio,\n" + 
    	    "clickAudition,concat(format(ifnull(clickAudition/auditionInit,0)*100,2),'%') as nowLearnRatio,\n" + 
    	    "learnAudition,clickBuyAtAudition,learnFinish,\n" + 
    	    "concat(format(ifnull(learnFinish/learnAudition,0)*100,2),'%') as learnFinishRatio ,\n" + 
    	    "learnAndBuy,concat(format(ifnull(auditionBuySuccess/learnAudition,0)*100,2),'%') as iabAndLfRatio,\n" + 
    	    "concat(format(ifnull(learnAndBuy/learnFinish,0)*100,2),'%') as iabAndBsRatio\n" + 
    	    "  from squirrel_kvalue_statistics where  1=1 and auditionInit is not null " + 
    	    "<if test='levelId != null'>" +
	            " and levelId=#{levelId}" +
	        "</if>" +
    	    "<if test='startTime != null and endTime!=null'>" +
	            " and `date` BETWEEN  CAST(#{startTime} AS date)  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        "<if test='startTime != null and endTime==null'>" +
		        " and `date` BETWEEN  CAST(#{startTime} AS date)  and CURRENT_DATE() " +
		    "</if>" +
		    "<if test='startTime == null and endTime!=null'>" +
		    " and `date` BETWEEN  CURRENT_DATE()  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        " order by id desc "+
        "</script>")
	List<Map<String, Object>> selectAuditionByConditionMap(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("levelId")Integer levelId);

	@Select("<script>" +
    	    "SELECT count(1)  from squirrel_kvalue_statistics where  1=1"+
    	    "<if test='levelId != null'>" +
	            " and levelId=#{levelId}" +
	        "</if>" +
    	    "<if test='startTime != null and endTime!=null'>" +
	            " and `date` BETWEEN  CAST(#{startTime} AS date)  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        "<if test='startTime != null and endTime==null'>" +
		        " and `date` BETWEEN  CAST(#{startTime} AS date)  and CURRENT_DATE() " +
		    "</if>" +
		    "<if test='startTime == null and endTime!=null'>" +
		    " and `date` BETWEEN  CURRENT_DATE()  and CAST(#{endTime} AS date) " +
	        "</if>" +
	        " order by id desc "+
        "</script>")
	int selectAuditionCountByCondition(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("levelId")Integer levelId);
	 
}