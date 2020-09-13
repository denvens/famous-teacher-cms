## 子频道信息查询接口
+ 功能说明：子频道信息查询

### 历史记录

#### 2019-11-26
- 增加lessonDay课程天数

#### 2019-11-25
- 增加returnFeeDay返学费天数


#### 2019-10-09 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/level/info
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/level/info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 子频道信息查询				|
|接口名称		|/level/info				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|id					|Integer|是		  	|课程id									|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "denied": false,
    "success": true,
    "data": {
                id:1,
                name:’level1',
                updateDate:2011-12-2,
                Image:xxxxx-xxxx-xxxx.png,
                isOpen:0,
                introduction:"level简介",
                buySite:"http://bigbuy.html",
                returnFeeDay:10,
                lessonDay:35
            }
}
```

**返回值描述**  

```
content:错误描述
```