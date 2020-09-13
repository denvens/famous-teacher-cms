## 题新建接口
+ 功能说明：题新建列表

### 历史记录

#### 2019-09-26 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/question/info
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/question/info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 题新建						|
|接口名称		|/question/info				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|id					|Integer|是		  	|1，课程ID								|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "denied": false,
    "success": true,
    "data": {
		id:11,
		unitId:11,
		questionType:’xx’,
		questionData:’json’,
		order:11,
		questionKey:’uuid’
	}
}
```

**返回值描述**  

```
content:错误描述
```