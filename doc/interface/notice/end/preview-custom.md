## 编辑课程提醒接口
+ 功能说明：编辑课程提醒

### 历史记录

#### 2019-08-29 
- 新增

**公共参数:**
+ 测试接口的地址为 http://squirrel-admin-api-test.ivykid.com/wx-push-message/preview-custom
+ 线上接口的地址为 https://squirrel-cms-api.ivykid.com/wx-push-message/preview-custom
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service									|
|:--------------|:--------------------------------------------------|
|功能	     	| 编辑课程提醒			    						|
|接口名称		|/wx-push-message/preview-custom					|
|请求方法		|POST					    						|

### 接口公共参数
|参数名		   		|类型					|是否必填	|说明			    					|
|:------------------|:----------------------|:----------|:--------------------------------------|
|openId			   	|String					|	是	  	|		      	  						|
|content			|String					|	是		|										|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code":0,
    "data":"",
    "success":true,
    "denied":false,
    "message":""
}
```

**返回值描述**  

```
content:错误描述
```