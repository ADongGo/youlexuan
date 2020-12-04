package com.offcn.shop.controller;

import com.offcn.util.FastDFSClient;
import com.offcn.vo.resp.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @RequestMapping("/fileUpload")
    public CommonResult fileUpload(MultipartFile file){

        try{
            FastDFSClient client=new FastDFSClient("classpath:config/fdfs_client.conf");
            byte[] bytes = file.getBytes();
            //获取后缀名
            String extName=getExtName(file);
            String url="http://192.168.188.146/" + client.uploadFile(bytes, extName);
            return new CommonResult(true,url,null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"",null);
        }
    }

    private String getExtName(MultipartFile file){

        //获取原图片名
        String originalFileName=file.getOriginalFilename();
        return originalFileName.substring(originalFileName.indexOf(".")+1,originalFileName.length());
    }
}
