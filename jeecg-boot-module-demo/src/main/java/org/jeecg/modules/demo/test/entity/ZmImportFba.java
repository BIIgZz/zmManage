package org.jeecg.modules.demo.test.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 导入FBA表(已导出)
 * @Author: jeecg-boot
 * @Date:   2021-08-25
 * @Version: V1.0
 */
@ApiModel(value="zm_import_fba对象", description="导入FBA表(已导出)")
@Data
@TableName("zm_import_fba")
public class ZmImportFba implements Serializable {
    private static final long serialVersionUID = 1L;

	/**fba仓库id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "fba仓库id")
    private java.lang.String id;
	/**FBA ID*/
	@Excel(name = "FBA ID", width = 15)
    @ApiModelProperty(value = "FBA ID")
    private java.lang.String fbaid;
	/**客户订单号*/
	@Excel(name = "客户订单号", width = 15)
    @ApiModelProperty(value = "客户订单号")
    private java.lang.String orderid;
	/**服务*/
	@Excel(name = "服务", width = 15)
    @ApiModelProperty(value = "服务")
    private java.lang.String serviceId;
	/**地址库编码*/
	@Excel(name = "地址库编码", width = 15)
    @ApiModelProperty(value = "地址库编码")
    private java.lang.String code;
	/**收件人姓名*/
	@Excel(name = "收件人姓名", width = 15)
    @ApiModelProperty(value = "收件人姓名")
    private java.lang.String name;
	/**收件人公司*/
	@Excel(name = "收件人公司", width = 15)
    @ApiModelProperty(value = "收件人公司")
    private java.lang.String company;
	/**收件人地址*/
	@Excel(name = "收件人地址", width = 15)
    @ApiModelProperty(value = "收件人地址")
    private java.lang.String address;
	/**收件人城市*/
	@Excel(name = "收件人城市", width = 15)
    @ApiModelProperty(value = "收件人城市")
    private java.lang.String city;
	/**收件人省份*/
	@Excel(name = "收件人省份", width = 15)
    @ApiModelProperty(value = "收件人省份")
    private java.lang.String province;
	/**收件人邮编*/
	@Excel(name = "收件人邮编", width = 15)
    @ApiModelProperty(value = "收件人邮编")
    private java.lang.String postcode;
	/**收件人国家代码*/
	@Excel(name = "收件人国家代码", width = 15)
    @ApiModelProperty(value = "收件人国家代码")
    private java.lang.String countryCode;
	/**收件人电话*/
	@Excel(name = "收件人电话", width = 15)
    @ApiModelProperty(value = "收件人电话")
    private java.lang.String tel;
	/**收件人邮箱*/
	@Excel(name = "收件人邮箱", width = 15)
    @ApiModelProperty(value = "收件人邮箱")
    private java.lang.String email;
	/**总箱数*/
	@Excel(name = "总箱数", width = 15)
    @ApiModelProperty(value = "总箱数")
    private java.lang.Integer caseNumber;
	/**是否带电*/
	@Excel(name = "是否带电", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
    @ApiModelProperty(value = "是否带电")
    private java.lang.Integer electrical;
	/**是否带磁性*/
	@Excel(name = "是否带磁性", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
    @ApiModelProperty(value = "是否带磁性")
    private java.lang.Integer magnetic;
	/**是否是液体*/
	@Excel(name = "是否是液体", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
    @ApiModelProperty(value = "是否是液体")
    private java.lang.Integer liquid;
	/**是否粉末*/
	@Excel(name = "是否粉末", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
    @ApiModelProperty(value = "是否粉末")
    private java.lang.Integer powder;
	/**是否是危险品*/
	@Excel(name = "是否是危险品", width = 15, dicCode = "judgment")
    @Dict(dicCode = "judgment")
    @ApiModelProperty(value = "是否是危险品")
    private java.lang.Integer dangerous;
	/**报关方式*/
	@Excel(name = "报关方式", width = 15, dicCode = "customs_eclaration")
    @Dict(dicCode = "customs_eclaration")
    @ApiModelProperty(value = "报关方式")
    private java.lang.Integer customsEclaration;
	/**清关方式*/
	@Excel(name = "清关方式", width = 15, dicCode = "customs_clearance")
    @Dict(dicCode = "customs_clearance")
    @ApiModelProperty(value = "清关方式")
    private java.lang.Integer customsClearance;
	/**交税方式*/
	@Excel(name = "交税方式", width = 15, dicCode = "tax_payment")
    @Dict(dicCode = "tax_payment")
    @ApiModelProperty(value = "交税方式")
    private java.lang.Integer taxPayment;
	/**交货条款*/
	@Excel(name = "交货条款", width = 15)
    @ApiModelProperty(value = "交货条款")
    private java.lang.String deliveryTerm;
	/**VAT号*/
	@Excel(name = "VAT号", width = 15)
    @ApiModelProperty(value = "VAT号")
    private java.lang.String vat;
	/**参考号1*/
	@Excel(name = "参考号1", width = 15)
    @ApiModelProperty(value = "参考号1")
    private java.lang.String referenceNumber1;
	/**参考号2*/
	@Excel(name = "参考号2", width = 15)
    @ApiModelProperty(value = "参考号2")
    private java.lang.String referenceNumber2;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String note;
	/**发件人公司*/
	@Excel(name = "发件人公司", width = 15)
    @ApiModelProperty(value = "发件人公司")
    private java.lang.String companySender;
	/**发件人姓名*/
	@Excel(name = "发件人姓名", width = 15)
    @ApiModelProperty(value = "发件人姓名")
    private java.lang.String nameSender;
	/**发件人地址*/
	@Excel(name = "发件人地址", width = 15)
    @ApiModelProperty(value = "发件人地址")
    private java.lang.String addressSender;
	/**发件人城市*/
	@Excel(name = "发件人城市", width = 15)
    @ApiModelProperty(value = "发件人城市")
    private java.lang.String citySender;
	/**发件人省份*/
	@Excel(name = "发件人省份", width = 15)
    @ApiModelProperty(value = "发件人省份")
    private java.lang.String provinceSender;
	/**发件人邮编*/
	@Excel(name = "发件人邮编", width = 15)
    @ApiModelProperty(value = "发件人邮编")
    private java.lang.String postcodeSender;
	/**发件人国家代码*/
	@Excel(name = "发件人国家代码", width = 15)
    @ApiModelProperty(value = "发件人国家代码")
    private java.lang.String countryCodeSender;
	/**发件人电话*/
	@Excel(name = "发件人电话", width = 15)
    @ApiModelProperty(value = "发件人电话")
    private java.lang.String telSender;
	/**发件人邮箱*/
	@Excel(name = "发件人邮箱", width = 15)
    @ApiModelProperty(value = "发件人邮箱")
    private java.lang.String emailSender;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
}
