package org.jeecg.modules.demo.zmexpress.service;

import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import org.jeecg.modules.demo.zmexpress.entity.ZmSupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
public interface IZmSupplierService extends IService<ZmSupplier> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
