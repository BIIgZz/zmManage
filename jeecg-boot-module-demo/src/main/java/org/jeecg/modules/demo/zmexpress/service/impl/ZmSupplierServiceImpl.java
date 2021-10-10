package org.jeecg.modules.demo.zmexpress.service.impl;

import org.jeecg.modules.demo.zmexpress.entity.ZmSupplier;
import org.jeecg.modules.demo.zmexpress.entity.ZmPartner;
import org.jeecg.modules.demo.zmexpress.mapper.ZmPartnerMapper;
import org.jeecg.modules.demo.zmexpress.mapper.ZmSupplierMapper;
import org.jeecg.modules.demo.zmexpress.service.IZmSupplierService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2021-10-10
 * @Version: V1.0
 */
@Service
public class ZmSupplierServiceImpl extends ServiceImpl<ZmSupplierMapper, ZmSupplier> implements IZmSupplierService {

	@Autowired
	private ZmSupplierMapper zmSupplierMapper;
	@Autowired
	private ZmPartnerMapper zmPartnerMapper;
	
	@Override
	@Transactional
	public void delMain(String id) {
		zmPartnerMapper.deleteByMainId(id);
		zmSupplierMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			zmPartnerMapper.deleteByMainId(id.toString());
			zmSupplierMapper.deleteById(id);
		}
	}
	
}
