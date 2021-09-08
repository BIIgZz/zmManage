package org.jeecg.modules.demo.zmexpress.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 货柜详情
 * @Author: jeecg-boot
 * @Date:   2021-07-13
 * @Version: V1.0
 */
@ApiModel(value="zm_import_good对象", description="货柜详情")
@Data
@TableName("zm_import_good")
public class ZmImportGood implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**fbaid*/
    @ApiModelProperty(value = "fbaid")
    private String fbaid;
	/**货箱编号*/
	@Excel(name = "货箱编号", width = 15)
    @ApiModelProperty(value = "货箱编号")
    private String caseid;
	/**货箱重量*/
	@Excel(name = "货箱重量", width = 15)
    @ApiModelProperty(value = "货箱重量")
    private Double weight;
	/**货箱长度*/
	@Excel(name = "货箱长度", width = 15)
    @ApiModelProperty(value = "货箱长度")
    private Double length;
    /**货箱宽度*/
    @Excel(name = "货箱宽度", width = 15)
    @ApiModelProperty(value = "货箱宽度")
    private Double width;
	/**货箱高度*/
	@Excel(name = "货箱高度", width = 15)
    @ApiModelProperty(value = "货箱高度")
    private Double height;
	/**中文名称*/
	@Excel(name = "中文名称", width = 15)
    @ApiModelProperty(value = "中文名称")
    private String cnName;
	/**英文名称*/
	@Excel(name = "英文名称", width = 15)
    @ApiModelProperty(value = "英文名称")
    private String enName;
	/**申报单价*/
	@Excel(name = "申报单价", width = 15)
    @ApiModelProperty(value = "申报单价")
    private String declaredPrice;
	/**申报数量*/
	@Excel(name = "申报数量", width = 15,isStatistics = true)
    @ApiModelProperty(value = "申报数量")
    private Integer declaredNumber;
	/**产品海关编码*/
	@Excel(name = "产品海关编码", width = 15)
    @ApiModelProperty(value = "产品海关编码")
    private String    hscode;
	/**产品材料*/
	@Excel(name = "产品材料", width = 15)
    @ApiModelProperty(value = "产品材料")
    private String material;
	/**产品用途*/
	@Excel(name = "产品用途", width = 15)
    @ApiModelProperty(value = "产品用途")
    private String application;
	/**品牌*/
	@Excel(name = "品牌", width = 15)
    @ApiModelProperty(value = "品牌")
    private String brand;
	/**品牌类型*/
	@Excel(name = "品牌类型", width = 15, dicCode = "type")
    @ApiModelProperty(value = "品牌类型")
    private String type;
	/**产品型号*/
	@Excel(name = "产品型号", width = 15)
    @ApiModelProperty(value = "产品型号")
    private String model;
	/**销售链接*/
	@Excel(name = "销售链接", width = 15)
    @ApiModelProperty(value = "销售链接")
    private String link;
	/**产品售价*/
	@Excel(name = "产品售价", width = 15)
    @ApiModelProperty(value = "产品售价")
    private Double price;
	/**图片链接*/
	@Excel(name = "图片链接", width = 15)
    @ApiModelProperty(value = "图片链接")
    private String picture;
}
