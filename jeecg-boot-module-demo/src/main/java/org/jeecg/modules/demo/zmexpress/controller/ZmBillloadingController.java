package org.jeecg.modules.demo.zmexpress.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmBillloading;
import org.jeecg.modules.demo.zmexpress.service.IZmBillloadingService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 提单表
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Api(tags="提单表")
@RestController
@RequestMapping("/zmexpress/zmBillloading")
@Slf4j
public class ZmBillloadingController extends JeecgController<ZmBillloading, IZmBillloadingService> {
	@Autowired
	private IZmBillloadingService zmBillloadingService;
	
	/**
	 * 分页列表查询
	 *
	 * @param zmBillloading
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "提单表-分页列表查询")
	@ApiOperation(value="提单表-分页列表查询", notes="提单表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmBillloading zmBillloading,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmBillloading> queryWrapper = QueryGenerator.initQueryWrapper(zmBillloading, req.getParameterMap());
		Page<ZmBillloading> page = new Page<ZmBillloading>(pageNo, pageSize);
		IPage<ZmBillloading> pageList = zmBillloadingService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param zmBillloading
	 * @return
	 */
	@AutoLog(value = "提单表-添加")
	@ApiOperation(value="提单表-添加", notes="提单表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmBillloading zmBillloading) {
		zmBillloadingService.save(zmBillloading);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param zmBillloading
	 * @return
	 */
	@AutoLog(value = "提单表-编辑")
	@ApiOperation(value="提单表-编辑", notes="提单表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmBillloading zmBillloading) {
		zmBillloadingService.updateById(zmBillloading);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提单表-通过id删除")
	@ApiOperation(value="提单表-通过id删除", notes="提单表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmBillloadingService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "提单表-批量删除")
	@ApiOperation(value="提单表-批量删除", notes="提单表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmBillloadingService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提单表-通过id查询")
	@ApiOperation(value="提单表-通过id查询", notes="提单表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmBillloading zmBillloading = zmBillloadingService.getById(id);
		if(zmBillloading==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(zmBillloading);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param zmBillloading
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmBillloading zmBillloading) {
        return super.exportXls(request, zmBillloading, ZmBillloading.class, "提单表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmBillloading.class);
    }

}
