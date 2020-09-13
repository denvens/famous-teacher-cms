package com.qingclass.squirrel.cms.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Global {
    @Value("${oss.bucket}")
    private String ossBucket; //oss bucket
    @Value("${oss.access.key.id}")
    private String ossAccessKeyId; //oss accessKeyId
    @Value("${oss.access.key.secret}")
    private String ossAccessKeySecret;  //oss accessKeySecret
    @Value("${oss.end.point}")
    private String ossEndPoint;  //oss endPoint 
    @Value("${oss.domain}")
    private String domain;
    private String ossLessonPath = "lessons/"; //oss lessonPath

    private String ossVoicePath = "voices/";   //oss voicePath
    
    private String ossAudioPath = "audioes/";   //oss audioPath

    private String ossImagePath = "images/";  //oss imagePath
    
    private String ossVideoPath = "videoes/";  //oss videoPath
    
    private String ossQrPath = "qr/";

    private Integer pageSize = 10; //page size

    private String ossLoads = "uploads/";

    
    public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getOssQrPath() {
        return ossQrPath;
    }

    public void setOssQrPath(String ossQrPath) {
        this.ossQrPath = ossQrPath;
    }

    public String getOssBucket() {
        return ossBucket;
    }

    public void setOssBucket(String ossBucket) {
        this.ossBucket = ossBucket;
    }

    public String getOssAccessKeyId() {
        return ossAccessKeyId;
    }

    public void setOssAccessKeyId(String ossAccessKeyId) {
        this.ossAccessKeyId = ossAccessKeyId;
    }

    public String getOssAccessKeySecret() {
        return ossAccessKeySecret;
    }

    public void setOssAccessKeySecret(String ossAccessKeySecret) {
        this.ossAccessKeySecret = ossAccessKeySecret;
    }

    public String getOssEndPoint() {
        return ossEndPoint;
    }

    public void setOssEndPoint(String ossEndPoint) {
        this.ossEndPoint = ossEndPoint;
    }

    public String getOssLessonPath() {
        return ossLessonPath;
    }

    public void setOssLessonPath(String ossLessonPath) {
        this.ossLessonPath = ossLessonPath;
    }

    public String getOssVoicePath() {
        return ossVoicePath;
    }

    public void setOssVoicePath(String ossVoicePath) {
        this.ossVoicePath = ossVoicePath;
    }

    public String getOssImagePath() {
        return ossImagePath;
    }

    public void setOssImagePath(String ossImagePath) {
        this.ossImagePath = ossImagePath;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOssLoads() {
        return ossLoads;
    }

    public void setOssLoads(String ossLoads) {
        this.ossLoads = ossLoads;
    }

	public String getOssAudioPath() {
		return ossAudioPath;
	}

	public void setOssAudioPath(String ossAudioPath) {
		this.ossAudioPath = ossAudioPath;
	}

	public String getOssVideoPath() {
		return ossVideoPath;
	}

	public void setOssVideoPath(String ossVideoPath) {
		this.ossVideoPath = ossVideoPath;
	}
    
    
}
