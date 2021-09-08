package org.jeecg.modules.demo.zmexpress.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.zmexpress.entity.ZmSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2021-08-03
 * @Version: V1.0
 */
public interface ZmSupplierMapper extends BaseMapper<ZmSupplier> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id, @Param("status") String status);

}
