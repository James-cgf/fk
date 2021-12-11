package com.java.web.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 实现excel读写操作
 * @date 2021/11/2 14:48
 */
//测试excel类
public class TestEasyExcel {

    public static void main(String[] args) {
        // //    实现excel写入操作
        // //    设置写入文件夹地址和excel文件名称
        // String fileName = "d:\\Users\\26384\\Desktop\\write.xlsx";
        // //    调用excel写入方法
        // EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());

        // 实现excel读操作
            String filename="d:\\Users\\26384\\Desktop\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    public static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSname("小曹" + i);
            data.setSno(i);
            list.add(data);
        }
        System.out.println(list.size());
        return list;
    }

}
