package org.jeecg.modules.demo.zmexpress.vo;

import java.util.List;
import org.jeecg.modules.demo.zmexpress.entity.ZmSupplier;
import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Data
@ApiModel(value="zm_supplierPage对象", description="供应商")
public class ZmSupplierPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**供应商类型*/
	@Excel(name = "供应商类型", width = 15)
	@ApiModelProperty(value = "供应商类型")
    private java.lang.String type;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "service_status")
    @Dict(dicCode = "service_status")
	@ApiModelProperty(value = "状态")
    private java.lang.String status;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private java.lang.String note;

	@ExcelCollection(name="客户表")
	@ApiModelProperty(value = "客户表")
	private List<ZmPartner> zmPartnerList;

}
