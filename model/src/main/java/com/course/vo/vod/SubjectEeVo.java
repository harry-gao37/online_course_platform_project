package com.course.vo.vod;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Dict
 * </p>
 *
 * @author qy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectEeVo {

	//设置excel中的表头信息
	@ExcelProperty(value = "id" ,index = 0)
	private Long id;

	@ExcelProperty(value = "课程分类名称" ,index = 1)
	private String title;

//	@ExcelProperty(value = "上级id" ,index = 2)
//	private Long parentId;
//
//	@ExcelProperty(value = "排序" ,index = 3)
//	private Integer sort;


}

