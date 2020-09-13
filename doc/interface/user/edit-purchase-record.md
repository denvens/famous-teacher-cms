## 修改会员接口
+ 功能说明：修改会员

### 历史记录

#### 2019-10-14 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/edit-purchase-record
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/edit-purchase-record
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service				|
|:--------------|:------------------------------|
|功能	     	| 修改会员						|
|接口名称		|/user/edit-purchase-record		|
|请求方法		|POST					    	|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|userId				|Integer|是		  	|用户id,从info接口获取					|
|expireDate 		|String |是		  	|过期时间，可为整型或者yyyy-MM-dd			|
|beginAt			|String |是		  	|开课时间,2019-10-14						|
|sendLessonDays		|Integer|是		  	|可收到课程天数							|
|vipEndTime 		|String |是		  	|会员结束时间，从回显信息中获取				|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code": 0,
    "data": "",
    "success": true,
    "denied": false,
    "message": ""
}
```

**返回值描述**  

```
content:错误描述
```