## 编辑课程提醒接口
+ 功能说明：编辑课程提醒

### 历史记录

#### 2019-12-13
- 新增

**公共参数:**
+ 测试接口的地址为 http://squirrel-admin-api-test.ivykid.com/wx-push-message/edit-ends-template
+ 线上接口的地址为 https://squirrel-cms-api.ivykid.com/wx-push-message/edit-ends-template
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service									|
|:--------------|:--------------------------------------------------|
|功能	     	| 编辑课程提醒			    						|
|接口名称		|/wx-push-message/edit-ends-template				|
|请求方法		|POST					    						|

### 接口公共参数
|参数名		   		|类型					|是否必填	|说明			    					|
|:------------------|:----------------------|:----------|:--------------------------------------|
|id			   		|Integer				|	否	  	|提醒 id	      	  						|
|lessonHead			|String					|	否		|			  							|  
|lessonHeadColor	|String					|	否		|	 									|
|lessonName			|String					|	否		|										|
|lessonNameColor	|String					|	否		| 										|
|lessonEndTime		|String					|	否		| 										|
|lessonEndTimeColor	|String					|	否		|										|
|lessonContent		|String					|	否		|										|
|lessonContentColor	|String					|	否		|	 									|
|url				|String					|	否		| 	 									|

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