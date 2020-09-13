## 子频道新建接口
+ 功能说明：子频道新建

### 历史记录

#### 2019-11-26
- 增加lessonDay课程天数

#### 2019-11-25
- 增加returnFeeDay返学费天数

#### 2019-10-10 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/level/edit
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/level/edit
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 课程新建					|
|接口名称		|/level/edit				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|name				|String	|是		  	|课程名称								|
|subjectId			|Integer|是		  	|1000000, 频道ID							|
|isOpen				|Integer|是		  	|0为关闭，1为打开							|
|image				|String	|是		  	|课程图片								|
|introduction		|String	|是		  	|level简介								|
|buySite			|String	|是		  	|购买页地址								|
|returnFeeDay		|Integer|是		  	|返学费天数								|
|lessonDay			|Integer|是		  	|课程天数								|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "denied": false,
    "success": true,
    "data": {}
}
```

**返回值描述**  

```
content:错误描述
```