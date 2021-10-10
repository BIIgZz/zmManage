package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.CesOrderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 订单客户
 * @Author: jeecg-boot
 * @Date:   2021-09-10
 * @Version: V1.0
 */
public interface ICesOrderCustomerService extends IService<CesOrderCustomer> {

	public List<CesOrderCustomer> selectByMainId(String mainId);
}
