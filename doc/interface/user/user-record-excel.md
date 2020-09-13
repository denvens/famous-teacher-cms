## 学习记录导出接口
+ 功能说明：学习记录导出

### 历史记录

#### 2019-10-14 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/user-record-excel
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/user-record-excel
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service				|
|:--------------|:------------------------------|
|功能	     	| 学习记录导出					|
|接口名称		|/user/user-record-excel		|
|请求方法		|POST					    	|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|nickName			|String	|是		  	|昵称或openId							|
|startTime			|String |是		  	|开始时间,yyyy-MM-dd						|
|endTime			|String	|是		  	|结束时间,yyyy-MM-dd						|

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