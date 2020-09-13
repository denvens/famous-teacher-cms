package com.qingclass.squirrel.cms.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.qingclass.squirrel.cms.constant.Global;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@Component
public class OssUtil {

    @Autowired
    Global global;
    private static OssUtil ossUtil;
    @PostConstruct
    public void init(){
        ossUtil = this;
        ossUtil.global = this.global;
    }
    
    public String upLoad(String filePath, MultipartFile file){
        try {
			// 初始化OSSClient
			OSSClient ossClient = new OSSClient(ossUtil.global.getOssEndPoint(), ossUtil.global.getOssAccessKeyId(), ossUtil.global.getOssAccessKeySecret());
			if (ossClient != null) {
		            try {
		                if (!ossClient.doesBucketExist(ossUtil.global.getOssBucket())) {
		                    ossClient.createBucket(ossUtil.global.getOssBucket());
		                }

		                byte[] content = file.getBytes();

		                ossClient.putObject(ossUtil.global.getOssBucket(), filePath, new ByteArrayInputStream(content));

		            }catch(Exception e){
		                e.printStackTrace();
		            }finally {
		                ossClient.shutdown();
		            }
		     }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return filePath;
    }

    /**
     * 上传文件公共方法
     * @param uploadDir 上传路径
     * @param file	上传文件
     * @param suffix	后缀
     * @return 文件名称
     * */
    public String executeUpLoad(String uploadDir, MultipartFile file, String suffix){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date)+"/";
        //文件名
        String fileName = uploadDir+ossUtil.global.getOssLoads()+format+UUID.randomUUID() + suffix;
        String filePath = fileName;
        upLoad(filePath, file);
        return fileName;

    }

    /**
     * 上传文件公共方法
     * @param uploadDir 上传路径
     * @param file	上传文件
     * @param suffix	后缀
     * @return 文件名称
     * */
    public String executeUpLoad(String uploadDir, File file, String suffix){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date)+"/";
        //文件名
        String fileName = uploadDir+ossUtil.global.getOssLoads()+format+UUID.randomUUID() + suffix;
        String filePath = fileName;

        File serverFile = new File(filePath);


        try {
            FileUtils.copyInputStreamToFile(new FileInputStream(file), serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 初始化OSSClient
        OSSClient client = new OSSClient(ossUtil.global.getOssEndPoint(), ossUtil.global.getOssAccessKeyId(), ossUtil.global.getOssAccessKeySecret());

        InputStream content = null;
        try {
            content = new FileInputStream(serverFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 上传Object.
        PutObjectResult result = client.putObject(ossUtil.global.getOssBucket(), filePath, content);

        return fileName;

    }

    public void executeDelete(String fileName){

        // 初始化OSSClient
        OSSClient client = new OSSClient(ossUtil.global.getOssEndPoint(), ossUtil.global.getOssAccessKeyId(), ossUtil.global.getOssAccessKeySecret());

        //删除文件
        client.deleteObject(ossUtil.global.getOssBucket(), fileName);

    }

    public void temp(String fileName, MultipartFile file){
        File serverFile = new File(fileName);

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 初始化OSSClient
        OSSClient client = new OSSClient(ossUtil.global.getOssEndPoint(), ossUtil.global.getOssAccessKeyId(), ossUtil.global.getOssAccessKeySecret());

        InputStream content = null;
        try {
            content = new FileInputStream(serverFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 上传Object.
        PutObjectResult result = client.putObject(ossUtil.global.getOssBucket(), fileName, content);

    }


}