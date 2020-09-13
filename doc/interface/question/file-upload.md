## 文件上传接口
+ 功能说明：文件上传

### 历史记录

#### 2019-09-26 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/word/file-upload
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/word/file-upload
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 文件上传					|
|接口名称		|/word/file-upload			|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    													|
|:------------------|:------|:----------|:----------------------------------------------------------------------|
|file				|file	|是		  	|文件																	|
|fileType			|String	|是		  	|image:图片; voice:语音(用户跟读录音); audio:音频(老师讲解录音); video:视频	|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "pageTotal":25,
    "data":{
    	'xxxx-xxxx-xxxx',
    }
    "success":true,
    "denied":false
}
```

**返回值描述**  

```
content:错误描述
```