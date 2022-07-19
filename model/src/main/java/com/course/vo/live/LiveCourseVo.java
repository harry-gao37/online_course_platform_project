package com.course.vo.live;

import com.course.model.live.LiveCourse;
import com.course.model.vod.Teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LiveCourseVo extends LiveCourse {

	@ApiModelProperty(value = "主播老师")
	private Teacher teacher;

	private Integer liveStatus;

	@ApiModelProperty(value = "直播开始时间")
	private String startTimeString;

	@ApiModelProperty(value = "直播结束时间")
	private String endTimeString;

}

