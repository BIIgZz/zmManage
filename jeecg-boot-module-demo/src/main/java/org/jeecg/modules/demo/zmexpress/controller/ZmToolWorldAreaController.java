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
import org.jeecg.modules.demo.zmexpress.entity.ZmToolWorldArea;
import org.jeecg.modules.demo.zmexpress.service.IZmToolWorldAreaService;

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
 * @Description: zm_tool_world_area
 * @Author: jeecg-boot
 * @Date:   2021-08-09
 * @Version: V1.0
 */
@Api(tags="zm_tool_world_area")
@RestController
@RequestMapping("/zmexpress/zmToolWorldArea")
@Slf4j
public class ZmToolWorldAreaController extends JeecgController<ZmToolWorldArea, IZmToolWorldAreaService> {
	@Autowired
	private IZmToolWorldAreaService zmToolWorldAreaService;
	
	/**
	 * ??????????????????
	 *
	 * @param zmToolWorldArea
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "zm_tool_world_area-??????????????????")
	@ApiOperation(value="zm_tool_world_area-??????????????????", notes="zm_tool_world_area-??????????????????")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmToolWorldArea zmToolWorldArea,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmToolWorldArea> queryWrapper = QueryGenerator.initQueryWrapper(zmToolWorldArea, req.getParameterMap());
		Page<ZmToolWorldArea> page = new Page<ZmToolWorldArea>(pageNo, pageSize);
		IPage<ZmToolWorldArea> pageList = zmToolWorldAreaService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   ??????
	 *
	 * @param zmToolWorldArea
	 * @return
	 */
	@AutoLog(value = "zm_tool_world_area-??????")
	@ApiOperation(value="zm_tool_world_area-??????", notes="zm_tool_world_area-??????")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ZmToolWorldArea zmToolWorldArea) {
		zmToolWorldAreaService.save(zmToolWorldArea);
		return Result.OK("???????????????");
	}
	
	/**
	 *  ??????
	 *
	 * @param zmToolWorldArea
	 * @return
	 */
	@AutoLog(value = "zm_tool_world_area-??????")
	@ApiOperation(value="zm_tool_world_area-??????", notes="zm_tool_world_area-??????")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ZmToolWorldArea zmToolWorldArea) {
		zmToolWorldAreaService.updateById(zmToolWorldArea);
		return Result.OK("????????????!");
	}
	
	/**
	 *   ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "zm_tool_world_area-??????id??????")
	@ApiOperation(value="zm_tool_world_area-??????id??????", notes="zm_tool_world_area-??????id??????")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		zmToolWorldAreaService.removeById(id);
		return Result.OK("????????????!");
	}
	
	/**
	 *  ????????????
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "zm_tool_world_area-????????????")
	@ApiOperation(value="zm_tool_world_area-????????????", notes="zm_tool_world_area-????????????")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.zmToolWorldAreaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("??????????????????!");
	}
	
	/**
	 * ??????id??????
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "zm_tool_world_area-??????id??????")
	@ApiOperation(value="zm_tool_world_area-??????id??????", notes="zm_tool_world_area-??????id??????")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ZmToolWorldArea zmToolWorldArea = zmToolWorldAreaService.getById(id);
		if(zmToolWorldArea==null) {
			return Result.error("?????????????????????");
		}
		return Result.OK(zmToolWorldArea);
	}

    /**
    * ??????excel
    *
    * @param request
    * @param zmToolWorldArea
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmToolWorldArea zmToolWorldArea) {
        return super.exportXls(request, zmToolWorldArea, ZmToolWorldArea.class, "zm_tool_world_area");
    }

    /**
      * ??????excel????????????
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmToolWorldArea.class);
    }

}
