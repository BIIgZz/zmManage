package org.jeecg.modules.demo.zmexpress.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import org.jeecg.modules.demo.zmexpress.entity.ZmSupplier;
import org.jeecg.modules.demo.zmexpress.service.IZmSupplierService;
import org.jeecg.modules.demo.zmexpress.service.IZmPartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Api(tags="供应商")
@RestController
@RequestMapping("/zmexpress/zmSupplier")
@Slf4j
public class ZmSupplierController extends JeecgController<ZmSupplier, IZmSupplierService> {

	@Autowired
	private IZmSupplierService zmSupplierService;

	@Autowired
	private IZmPartnerService zmPartnerService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param zmSupplier
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "供应商-分页列表查询")
	@ApiOperation(value="供应商-分页列表查询", notes="供应商-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ZmSupplier zmSupplier,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ZmSupplier> queryWrapper = QueryGenerator.initQueryWrapper(zmSupplier, req.getParameterMap());
		Page<ZmSupplier> page = new Page<ZmSupplier>(pageNo, pageSize);
		IPage<ZmSupplier> pageList = zmSupplierService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param zmSupplier
     * @return
     */
    @AutoLog(value = "供应商-添加")
    @ApiOperation(value="供应商-添加", notes="供应商-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ZmSupplier zmSupplier) {
        zmSupplierService.save(zmSupplier);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param zmSupplier
     * @return
     */
    @AutoLog(value = "供应商-编辑")
    @ApiOperation(value="供应商-编辑", notes="供应商-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ZmSupplier zmSupplier) {
        zmSupplierService.updateById(zmSupplier);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "供应商-通过id删除")
    @ApiOperation(value="供应商-通过id删除", notes="供应商-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        zmSupplierService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "供应商-批量删除")
    @ApiOperation(value="供应商-批量删除", notes="供应商-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.zmSupplierService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmSupplier zmSupplier) {
        return super.exportXls(request, zmSupplier, ZmSupplier.class, "供应商");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ZmSupplier.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-客户表-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "客户表-通过主表ID查询")
	@ApiOperation(value="客户表-通过主表ID查询", notes="客户表-通过主表ID查询")
	@GetMapping(value = "/listZmPartnerByMainId")
    public Result<?> listZmPartnerByMainId(ZmPartner zmPartner,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<ZmPartner> queryWrapper = QueryGenerator.initQueryWrapper(zmPartner, req.getParameterMap());
        Page<ZmPartner> page = new Page<ZmPartner>(pageNo, pageSize);
        IPage<ZmPartner> pageList = zmPartnerService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param zmPartner
	 * @return
	 */
	@AutoLog(value = "客户表-添加")
	@ApiOperation(value="客户表-添加", notes="客户表-添加")
	@PostMapping(value = "/addZmPartner")
	public Result<?> addZmPartner(@RequestBody ZmPartner zmPartner) {
		zmPartnerService.save(zmPartner);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param zmPartner
	 * @return
	 */
	@AutoLog(value = "客户表-编辑")
	@ApiOperation(value="客户表-编辑", notes="客户表-编辑")
	@PutMapping(value = "/editZmPartner")
	public Result<?> editZmPartner(@RequestBody ZmPartner zmPartner) {
		zmPartnerService.updateById(zmPartner);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户表-通过id删除")
	@ApiOperation(value="客户表-通过id删除", notes="客户表-通过id删除")
	@DeleteMapping(value = "/deleteZmPartner")
	public Result<?> deleteZmPartner(@RequestParam(name="id",required=true) String id) {
		zmPartnerService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客户表-批量删除")
	@ApiOperation(value="客户表-批量删除", notes="客户表-批量删除")
	@DeleteMapping(value = "/deleteBatchZmPartner")
	public Result<?> deleteBatchZmPartner(@RequestParam(name="ids",required=true) String ids) {
	    this.zmPartnerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportZmPartner")
    public ModelAndView exportZmPartner(HttpServletRequest request, ZmPartner zmPartner) {
		 // Step.1 组装查询条件
		 QueryWrapper<ZmPartner> queryWrapper = QueryGenerator.initQueryWrapper(zmPartner, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<ZmPartner> pageList = zmPartnerService.list(queryWrapper);
		 List<ZmPartner> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "客户表"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, ZmPartner.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("客户表报表", "导出人:" + sysUser.getRealname(), "客户表"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importZmPartner/{mainId}")
    public Result<?> importZmPartner(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<ZmPartner> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmPartner.class, params);
				 for (ZmPartner temp : list) {
                    temp.setType(mainId);
				 }
				 long start = System.currentTimeMillis();
				 zmPartnerService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-客户表-end----------------------------------------------*/




}
