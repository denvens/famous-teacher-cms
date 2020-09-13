package com.qingclass.squirrel.cms.mapper.quartz;

import com.qingclass.squirrel.cms.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobMapper {

	@Select({
			"SELECT",
			"qrtz_job_details.JOB_NAME,",
			"qrtz_job_details.JOB_GROUP,",
			"qrtz_job_details.JOB_CLASS_NAME,",
			"qrtz_triggers.TRIGGER_NAME,",
			"qrtz_triggers.TRIGGER_GROUP,",
			"qrtz_cron_triggers.CRON_EXPRESSION,",
			"qrtz_cron_triggers.TIME_ZONE_ID",
			"FROM",
			"qrtz_job_details",
			"JOIN qrtz_triggers",
			"JOIN qrtz_cron_triggers ON qrtz_job_details.JOB_NAME = qrtz_triggers.JOB_NAME",
			"AND qrtz_triggers.TRIGGER_NAME = qrtz_cron_triggers.TRIGGER_NAME",
			"AND qrtz_triggers.TRIGGER_GROUP = qrtz_cron_triggers.TRIGGER_GROUP",
	})
	List<JobAndTrigger> getJobAndTriggerDetails();
}
