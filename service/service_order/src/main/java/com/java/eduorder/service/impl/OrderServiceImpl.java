package com.java.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.eduorder.entity.Order;
import com.java.eduorder.mapper.OrderMapper;
import com.java.eduorder.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 订单表
 * @since 2021-12-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
