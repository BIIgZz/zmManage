package org.jeecg.modules.system.rule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.zmexpress.entity.ZmWaybill;
import org.jeecg.modules.demo.zmexpress.mapper.ZmWaybillMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmWaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShopOrderRule implements IFillRuleHandler {
    @Autowired
    private IZmWaybillService zmWaybillService;
    @Autowired
    private ZmWaybillMapper zmWaybillMapper;

    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        String prefix = "SCFX";
        //订单前缀默认为CN 如果规则参数不为空，则取自定义前缀
        if (params != null) {
            Object obj = params.get("prefix");
            if (obj != null) {
                prefix = obj.toString();
            }
        }
        ZmWaybill zmWaybill = new ZmWaybill();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        int random = RandomUtils.nextInt(90) + 10;
        String value = prefix + format.format(new Date()) + random;



        List<ZmWaybill> count = zmWaybillMapper.selectList(null);
        System.out.println(count.size());


        // 根据formData的值的不同，生成不同的订单号
//        String name = formData.getString("name");
//        if (!StringUtils.isEmpty(name)) {
//            value += name;
//        }
        return value;
    }
}
