## 预览模板消息接口
+ 功能说明：预览模板消息

### 历史记录

#### 2019-10-10 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/wx-purchase/preview-template
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/wx-purchase/preview-template
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service									|
|:--------------|:--------------------------------------------------|
|功能	     	| 预览模板消息			    						|
|接口名称		|/wx-purchase/preview-template						|
|请求方法		|POST					    						|

### 接口公共参数
|参数名		   		|类型					|是否必填	|说明			    					|
|:------------------|:----------------------|:----------|:--------------------------------------|
|openId			   	|String					|	是	  	|		      	  						|
|contentInfo		|Integer				|	否		|										|
|contentInfoColor	|String					|	否		|										|
|contentBody		|String					|	否		|			  							|  
|contentBodyColor	|String					|	否		|										|
|url				|String					|	否		| 										|

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