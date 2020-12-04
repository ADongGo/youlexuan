package com.offcn.shop;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

public class FastDfsTest {

    public static void main(String[] args) throws Exception {

        //加载配置文件
        ClientGlobal.init("D:\\AAA_date\\workspace\\ideaworkspace\\youlexuan\\youlexuan-shop-web\\src\\main\\resources\\config\\fdfs_client.conf");
        //创建trackerClient获取trackerServer对象
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        //声明了storageServer
        StorageServer storageServer=null;
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);

        //参数 1;本地文件 参数 2:后缀 参数 3:文件元信息
        String[] strings = storageClient.upload_file("","jpg",null);
        for(String s:strings){
            System.out.println(s);
        }
    }
}
