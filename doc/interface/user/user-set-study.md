## 学习记录设置学习完成接口
+ 功能说明：学习记录设置学习完成

### 历史记录

#### 2019-11-08 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/user/set-study
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/user/set-study
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service				|
|:--------------|:------------------------------|
|功能	     	| 学习记录设置学习完成				|
|接口名称		|/user/set-study				|
|请求方法		|POST					    	|

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|userId				|Integer|是		  	|userId									|
|date				|String |是		  	|课程推送日期 yyyy-MM-dd					|
|flag				|Integer|是		  	|1.设置打卡; 2.设置学习完成.   传：2		|

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