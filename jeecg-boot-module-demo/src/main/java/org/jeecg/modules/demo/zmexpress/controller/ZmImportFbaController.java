package org.jeecg.modules.demo.zmexpress.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import org.jeecg.modules.demo.zmexpress.entity.ZmProduct;
import org.jeecg.modules.demo.zmexpress.utils.InsertUtils;
import org.jeecgframework.poi.excel.def.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportGood;
import org.jeecg.modules.demo.zmexpress.entity.ZmImportFba;
import org.jeecg.modules.demo.zmexpress.vo.ZmImportFbaPage;
import org.jeecg.modules.demo.zmexpress.service.IZmImportFbaService;
import org.jeecg.modules.demo.zmexpress.service.IZmImportGoodService;
import org.jeecgframework.poi.excel.view.JeecgTemplateExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: ??????FBA???
 * @Author: jeecg-boot
 * @Date: 2021-07-13
 * @Version: V1.0
 */
@Api(tags = "??????FBA???")
@RestController
@RequestMapping("/zmexpress/zmImportFba")
@Slf4j
public class ZmImportFbaController {
    @Autowired
    private IZmImportFbaService zmImportFbaService;
    @Autowired
    private IZmImportGoodService zmImportGoodService;
    @Autowired
    private InsertUtils insertUtils;


    /**
     * ??????????????????
     *
     * @param zmImportFba
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "??????FBA???-??????????????????")
    @ApiOperation(value = "??????FBA???-??????????????????", notes = "??????FBA???-??????????????????")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ZmImportFba zmImportFba,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<ZmImportFba> queryWrapper = QueryGenerator.initQueryWrapper(zmImportFba, req.getParameterMap());
        Page<ZmImportFba> page = new Page<ZmImportFba>(pageNo, pageSize);
        IPage<ZmImportFba> pageList = zmImportFbaService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * ??????
     *
     * @param zmImportFbaPage
     * @return
     */
    @AutoLog(value = "??????FBA???-??????")
    @ApiOperation(value = "??????FBA???-??????", notes = "??????FBA???-??????")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ZmImportFbaPage zmImportFbaPage) {
        ZmImportFba zmImportFba = new ZmImportFba();
        BeanUtils.copyProperties(zmImportFbaPage, zmImportFba);
        zmImportFbaService.saveMain(zmImportFba, zmImportFbaPage.getZmImportGoodList());
        return Result.OK("???????????????");
    }

    /**
     * ??????
     *
     * @param zmImportFbaPage
     * @return
     */
    @AutoLog(value = "??????FBA???-??????")
    @ApiOperation(value = "??????FBA???-??????", notes = "??????FBA???-??????")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ZmImportFbaPage zmImportFbaPage) {
        ZmImportFba zmImportFba = new ZmImportFba();
        BeanUtils.copyProperties(zmImportFbaPage, zmImportFba);
        ZmImportFba zmImportFbaEntity = zmImportFbaService.getById(zmImportFba.getId());
        if (zmImportFbaEntity == null) {
            return Result.error("?????????????????????");
        }
        zmImportFbaService.updateMain(zmImportFba, zmImportFbaPage.getZmImportGoodList());
        return Result.OK("????????????!");
    }

    /**
     * ??????id??????
     *
     * @param id
     * @return
     */
    @AutoLog(value = "??????FBA???-??????id??????")
    @ApiOperation(value = "??????FBA???-??????id??????", notes = "??????FBA???-??????id??????")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        zmImportFbaService.delMain(id);
        return Result.OK("????????????!");
    }

    /**
     * ????????????
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "??????FBA???-????????????")
    @ApiOperation(value = "??????FBA???-????????????", notes = "??????FBA???-????????????")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.zmImportFbaService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("?????????????????????");
    }

    /**
     * ??????id??????
     *
     * @param id
     * @return
     */
    @AutoLog(value = "??????FBA???-??????id??????")
    @ApiOperation(value = "??????FBA???-??????id??????", notes = "??????FBA???-??????id??????")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ZmImportFba zmImportFba = zmImportFbaService.getById(id);
        if (zmImportFba == null) {
            return Result.error("?????????????????????");
        }
        return Result.OK(zmImportFba);
    }

    /**
     * ??????id??????
     *
     * @param id
     * @return
     */
    @AutoLog(value = "????????????????????????ID??????")
    @ApiOperation(value = "??????????????????ID??????", notes = "????????????-?????????ID??????")
    @GetMapping(value = "/queryZmImportGoodByMainId")
    public Result<?> queryZmImportGoodListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(id);
        return Result.OK(zmImportGoodList);
    }

    /**
     * ??????excel
     *
     * @param request
     * @param zmImportFba
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ZmImportFba zmImportFba) {
        // Step.1 ??????????????????????????????
        QueryWrapper<ZmImportFba> queryWrapper = QueryGenerator.initQueryWrapper(zmImportFba, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 ??????????????????
        List<ZmImportFba> queryList = zmImportFbaService.list(queryWrapper);
        // ??????????????????
        String selections = request.getParameter("selections");
        List<ZmImportFba> zmImportFbaList = new ArrayList<ZmImportFba>();
        if (oConvertUtils.isEmpty(selections)) {
            zmImportFbaList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            zmImportFbaList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 ??????pageList
        List<ZmImportFbaPage> pageList = new ArrayList<ZmImportFbaPage>();
        for (ZmImportFba main : zmImportFbaList) {
            ZmImportFbaPage vo = new ZmImportFbaPage();
            BeanUtils.copyProperties(main, vo);
            List<ZmImportGood> zmImportGoodList = zmImportGoodService.selectByMainId(main.getId());
            vo.setZmImportGoodList(zmImportGoodList);
            pageList.add(vo);
        }
//      // Step.3.1 ????????????
        Map<String, Object> map = new HashMap<>();
        map.put("fbaid", pageList.get(0).getFbaid());
        map.put("orderid", pageList.get(0).getOrderid());
        map.put("serviceId", pageList.get(0).getServiceId());
        map.put("code", pageList.get(0).getCode());
        map.put("name", pageList.get(0).getName());
        map.put("company", pageList.get(0).getCompany());
        map.put("address", pageList.get(0).getAddress());
        map.put("city", pageList.get(0).getCity());
        map.put("province", pageList.get(0).getProvince());
        map.put("countryCode", pageList.get(0).getCountryCode());
        map.put("tel", pageList.get(0).getTel());
        map.put("email", pageList.get(0).getEmail());
        map.put("caseNumber", pageList.get(0).getCaseNumber());
        map.put("electrical", pageList.get(0).getElectrical());
        map.put("magnetic", pageList.get(0).getMagnetic());
        map.put("liquid", pageList.get(0).getLiquid());
        map.put("powder", pageList.get(0).getPowder());
        map.put("dangerous", pageList.get(0).getDangerous());
        map.put("customs_eclaration", pageList.get(0).getCustomsEclaration());
        map.put("customs_clearance", pageList.get(0).getCustomsClearance());
        map.put("tax_payment", pageList.get(0).getTaxPayment());
        map.put("deliveryTerm", pageList.get(0).getDeliveryTerm());
        map.put("referenceNumber1", pageList.get(0).getReferenceNumber1());
        map.put("referenceNumber2", pageList.get(0).getReferenceNumber2());
        map.put("note", pageList.get(0).getNote());
        map.put("companySender", pageList.get(0).getCompanySender());
        map.put("nameSender", pageList.get(0).getNameSender());
        map.put("addressSender", pageList.get(0).getAddressSender());
        map.put("provinceSender", pageList.get(0).getPostcodeSender());
        map.put("countryCodeSender", pageList.get(0).getCountryCodeSender());
        map.put("telSender", pageList.get(0).getTelSender());
        map.put("emailSender", pageList.get(0).getEmailSender());

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (ZmImportGood zmImportGood : pageList.get(0).getZmImportGoodList()) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("caseid", zmImportGood.getCaseid());
            m.put("weight", zmImportGood.getWeight());
            m.put("length", zmImportGood.getLength());
            m.put("height", zmImportGood.getHeight());
            m.put("enName", zmImportGood.getEnName());
            m.put("cnName", zmImportGood.getCnName());
            m.put("declaredPrice", zmImportGood.getDeclaredPrice());
            m.put("declaredNumber", zmImportGood.getDeclaredNumber());
            m.put("material", zmImportGood.getMaterial());
            m.put("hscode", zmImportGood.getHscode());
            m.put("application", zmImportGood.getApplication());
            m.put("brand", zmImportGood.getBrand());
            m.put("type", zmImportGood.getType());
            m.put("model", zmImportGood.getModel());
            m.put("link", zmImportGood.getLink());
            m.put("hscode", zmImportGood.getHscode());
            m.put("price", zmImportGood.getPrice());
            m.put("picture", zmImportGood.getPicture());
            listMap.add(m);
        }
        map.put("maplist", listMap);
        TemplateExportParams params = new TemplateExportParams();
        params.setTemplateUrl("D:\\idea\\zmManage\\jeecg-boot-module-demo\\src\\main\\resources\\exportTemplate\\template.xls");

        // Step.4 AutoPoi ??????Excel
        ModelAndView mv = new ModelAndView(new JeecgTemplateExcelView());
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      mv.addObject(TemplateExcelConstants.FILE_NAME, map.get("fbaid"));
//      mv.addObject(NormalExcelConstants.CLASS, ZmImportFbaPage.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("??????FBA?????????", "?????????:"+sysUser.getRealname(), "??????FBA???"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        // map??????
        params.setSheetName(map.get("fbaid").toString());
        mv.addObject(TemplateExcelConstants.PARAMS, params);
        mv.addObject(TemplateExcelConstants.MAP_DATA, map);
        return mv;
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
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        //key-value

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// ????????????????????????
            cn.afterturn.easypoi.excel.entity.ImportParams params = new cn.afterturn.easypoi.excel.entity.ImportParams();
            params.setKeyMark(":");
            params.setReadSingleCell(true);
            params.setTitleRows(17);

            try {
                ExcelImportResult<Map> result = ExcelImportUtil.importExcelMore(file.getInputStream(), Map.class, params);
                List<ZmImportGood> importGoodList = new ArrayList<>();
                Map<String, Object> map = result.getMap();
                Map map1 = result.getList().get(0);

                for (int i = 0; i < result.getList().size(); i++) {
                    ZmImportGood zmImportGood = new ZmImportGood();
                    zmImportGood.setFbaid((String) map.get("FBA ID*:"));
                    zmImportGood.setApplication((String) result.getList().get(i).get("????????????*"));
                    zmImportGood.setCaseid((String) result.getList().get(i).get("????????????*"));
                    zmImportGood.setWeight(Double.parseDouble(result.getList().get(i).get("????????????(KG)*").toString()));
                    zmImportGood.setLength(Double.parseDouble(result.getList().get(i).get("????????????(CM)*").toString()));
//					zmImportGood.setWeight((String) map.get("????????????(CM)*:"));
                    zmImportGood.setHeight(Double.parseDouble(result.getList().get(i).get("????????????(CM)*").toString()));
                    zmImportGood.setEnName((String) result.getList().get(i).get("??????????????????*"));
                    zmImportGood.setCnName((String) result.getList().get(i).get("??????????????????*"));
                    zmImportGood.setDeclaredPrice(result.getList().get(i).get("??????????????????*").toString());
                    zmImportGood.setDeclaredNumber((Integer) result.getList().get(i).get("??????????????????*"));
                    zmImportGood.setMaterial(result.getList().get(i).get("????????????*").toString());
                    try {
                        Object o = result.getList().get(i).get("??????????????????*");
                        double v = Double.parseDouble(o.toString());
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        numberFormat.setGroupingUsed(false);
                        String format = numberFormat.format(v);
                        zmImportGood.setHscode(format);
                    } catch (NumberFormatException e) {
                        zmImportGood.setHscode(result.getList().get(i).get("??????????????????*").toString());
                    }
                    zmImportGood.setBrand((String) result.getList().get(i).get("????????????*"));
                    zmImportGood.setType((String) result.getList().get(i).get("????????????*"));
                    zmImportGood.setModel((String) result.getList().get(i).get("????????????*"));
//					zmImportGood.set((String) map.get("PO Number*"));
                    zmImportGood.setLink((String) result.getList().get(i).get("??????????????????"));
                    zmImportGood.setPrice((Double) result.getList().get(i).get("??????????????????"));
                    zmImportGood.setPicture((String) result.getList().get(i).get("??????????????????"));
                    importGoodList.add(zmImportGood);
                }

                ZmImportFba zmImportFbaPage = new ZmImportFba();
//				ZmImportFbaPage zmImportFbaPage = new ZmImportFbaPage();
                zmImportFbaPage.setAddress((String) map.get("??????????????????*:"));
                zmImportFbaPage.setFbaid((String) map.get("FBA ID*:"));
                zmImportFbaPage.setOrderid((String) map.get("???????????????:"));
                zmImportFbaPage.setCode((String) map.get("???????????????*:"));
                zmImportFbaPage.setServiceId((String) map.get("??????*:"));
                zmImportFbaPage.setName((String) map.get("???????????????*:"));
                zmImportFbaPage.setCity((String) map.get("???????????????*:"));
                zmImportFbaPage.setProvince((String) map.get("???????????????/???*(????????????):"));
                zmImportFbaPage.setPostcode((String) map.get("???????????????*:"));
                zmImportFbaPage.setCountryCode((String) map.get("?????????????????????(????????????)*:"));
                zmImportFbaPage.setTel((String) map.get("???????????????:"));
                zmImportFbaPage.setEmail((String) map.get("???????????????:"));
//				zmImportFbaPage.setCaseNumber(Integer.parseInt((String) map.get("?????????*:")) );
                if (map.get("??????*:").equals("???")) {
                    zmImportFbaPage.setElectrical(1);
                } else {
                    zmImportFbaPage.setElectrical(0);
                }
                if (map.get("??????*:").equals("???")) {
                    zmImportFbaPage.setMagnetic(1);
                } else {
                    zmImportFbaPage.setMagnetic(0);
                }
                if (map.get("??????*:").equals("???")) {
                    zmImportFbaPage.setLiquid(1);
                } else {
                    zmImportFbaPage.setLiquid(0);
                }
                if (map.get("??????*:").equals("???")) {
                    zmImportFbaPage.setPowder(1);
                } else {
                    zmImportFbaPage.setPowder(0);
                }
                if (map.get("?????????*:").equals("???")) {
                    zmImportFbaPage.setDangerous(1);
                } else {
                    zmImportFbaPage.setDangerous(0);
                }
                if (map.get("????????????*:").equals("????????????")) {
                    zmImportFbaPage.setCustomsEclaration(0);
                } else if (map.get("????????????*:").equals("????????????")) {
                    zmImportFbaPage.setCustomsEclaration(1);
                } else {
                    zmImportFbaPage.setCustomsEclaration(2);
                }
                if (map.get("????????????:").equals("????????????")) {
                    zmImportFbaPage.setCustomsEclaration(0);
                } else if (map.get("????????????:").equals("????????????")) {
                    zmImportFbaPage.setCustomsClearance(1);
                } else {
                    zmImportFbaPage.setCustomsClearance(2);
                }
                if (map.get("????????????*:").equals("??????")) {
                    zmImportFbaPage.setTaxPayment(1);
                } else {
                    zmImportFbaPage.setTaxPayment(0);
                }
                zmImportFbaPage.setVat((String) map.get("VAT???:"));
                zmImportFbaPage.setReferenceNumber1((String) map.get("????????????:"));
                zmImportFbaPage.setReferenceNumber2((String) map.get("????????????:"));
                zmImportFbaPage.setNote((String) map.get("??????:"));
                zmImportFbaPage.setAddressSender((String) map.get("?????????????????????:"));
                zmImportFbaPage.setNameSender((String) map.get("???????????????:"));
                zmImportFbaPage.setCompanySender((String) map.get("???????????????:"));
                zmImportFbaPage.setCitySender((String) map.get("???????????????:"));
                zmImportFbaPage.setPostcodeSender((String) map.get("???????????????:"));
                zmImportFbaPage.setCitySender((String) map.get("?????????????????????(????????????):"));
                zmImportFbaPage.setProvinceSender((String) map.get("???????????????/???:"));
                zmImportFbaPage.setTelSender((String) map.get("???????????????:"));
                zmImportFbaPage.setEmailSender((String) map.get("???????????????:"));

                zmImportFbaService.saveMain(zmImportFbaPage, importGoodList);
//				BeanUtils.copyProperties(page, po);
                //????????????
                List<ZmProduct> zmProductList = new ArrayList<>();
                for (ZmImportGood zmImportGood : importGoodList) {
                    ZmProduct zmProduct = new ZmProduct();
                    zmProduct.setCnName(zmImportGood.getCnName());
                    zmProduct.setApplication((zmImportGood.getApplication()));
                    zmProduct.setBrand((zmImportGood.getBrand()));
                    zmProduct.setCreateBy(zmImportGood.getCreateBy());
                    zmProduct.setCreateTime(zmImportGood.getCreateTime());
                    zmProduct.setDeclaredPrice(Double.parseDouble(zmImportGood.getDeclaredPrice()));
                    zmProduct.setEnName(zmImportGood.getEnName());
                    zmProduct.setHscode(zmImportGood.getHscode());
                    zmProduct.setLink(zmImportGood.getLink());
                    zmProduct.setMaterial(zmImportGood.getMaterial());
                    zmProduct.setModel(zmImportGood.getModel());
                    zmProduct.setPicture(zmImportGood.getPicture());
                    zmProduct.setPrice(zmImportGood.getPrice());
                    zmProduct.setType(1);
                    zmProduct.setUpdateBy(zmImportGood.getUpdateBy());
                    zmProduct.setUpdateTime(zmImportGood.getUpdateTime());
                    zmProduct.setId(zmImportGood.getId());
                    zmProductList.add(zmProduct);
                }

                try {
                    insertUtils.insertProduct(zmProductList);
                    insertUtils.insertHscode(importGoodList);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                return Result.OK("?????????????????????????????????:" + result.getMap());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                if (e.getMessage().contains("fbaid"))
                    return Result.error("??????????????????:fbaid??????");
                return Result.error("??????????????????:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //????????????
//      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//          MultipartFile file = entity.getValue();// ????????????????????????
//          ImportParams params = new ImportParams();
//          params.setTitleRows(2);
//          params.setHeadRows(1);
//		  params.setNeedSave(true);
//
//          try {
//
//              List<ZmImportFbaPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ZmImportFbaPage.class, params);
//              for (ZmImportFbaPage page : list) {
//                  ZmImportFba po = new ZmImportFba();
//                  BeanUtils.copyProperties(page, po);
//                  zmImportFbaService.saveMain(po, page.getZmImportGoodList());
//                  List<ZmProduct> zmProductList = new ArrayList<>();
//				  for (ZmImportGood zmImportGood:page.getZmImportGoodList()) {
//					  ZmProduct zmProduct = new ZmProduct();
//					  zmProduct.setCnName(zmImportGood.getCnName());
//					  zmProduct.setApplication((zmImportGood.getApplication()));
//					  zmProduct.setBrand((zmImportGood.getBrand()));
//					  zmProduct.setCreateBy(zmImportGood.getCreateBy());
//					  zmProduct.setCreateTime(zmImportGood.getCreateTime());
//					  zmProduct.setDeclaredPrice(Double.parseDouble(zmImportGood.getDeclaredPrice()));
//					  zmProduct.setEnName(zmImportGood.getEnName());
//					  zmProduct.setHscode(zmImportGood.getHscode());
//					  zmProduct.setLink(zmImportGood.getLink());
//					  zmProduct.setMaterial(zmImportGood.getMaterial());
//					  zmProduct.setModel(zmImportGood.getModel());
//					  zmProduct.setPicture(zmImportGood.getPicture());
//					  zmProduct.setPrice(zmImportGood.getPrice());
//					  zmProduct.setType(1);
//					  zmProduct.setUpdateBy(zmImportGood.getUpdateBy());
//					  zmProduct.setUpdateTime(zmImportGood.getUpdateTime());
//					  zmProduct.setId(zmImportGood.getId());
//					  zmProductList.add(zmProduct);
//				  }
//				  insertUtils.insertProduct(zmProductList);
//				  insertUtils.insertHscode(page.getZmImportGoodList());
//              }
//              return Result.OK("?????????????????????????????????:" + list.size());
//          } catch (Exception e) {
//              log.error(e.getMessage(),e);
//              return Result.error("??????????????????:"+e.getMessage());
//          } finally {
//              try {
//                  file.getInputStream().close();
//              } catch (IOException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
        return Result.OK("?????????????????????");
    }
    /**
     * ???????????????
     * ????????????????????????????????????????????????????????????
     * ??????????????????
     * @param request
     * @param response
     * @return
     */
    @PutMapping(value = "/change")
    public Result<?> change(@RequestBody String ids) {
       //???????????????????????? ??? ?????????????????????
        ids = ids.replaceAll("[^-?0-9]+", " ");
        List<String> list = Arrays.asList(ids.trim().split(" "));
        System.out.println(Arrays.asList(ids.trim().split(" ")));
        for (String id : list) {
            ZmImportFba zmImportFba = zmImportFbaService.getById(id);
            List<ZmImportGood> zmImportGoods = zmImportGoodService.selectByMainId(id);

            if (zmImportFba == null) {
                return Result.error("?????????????????????");
            }
            zmImportFba.setStatus(5+"");
            zmImportFbaService.updateMain(zmImportFba,zmImportGoods);
        }
        //????????????

        //????????????
        System.out.println("?????????????????????");
        return Result.OK("?????????????????????");
    }

}
