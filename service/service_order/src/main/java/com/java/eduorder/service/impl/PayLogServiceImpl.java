package com.java.eduorder.service.impl;

import com.java.eduorder.entity.PayLog;
import com.java.eduorder.mapper.PayLogMapper;
import com.java.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author 订单表
 * @since 2021-12-12
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
