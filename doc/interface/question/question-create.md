## 题新建接口
+ 功能说明：题新建列表

### 历史记录

#### 2019-09-26 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/question/create
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/question/create
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 题新建						|
|接口名称		|/question/create			|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|lessonId			|Integer|是		  	|1000000，课程ID							|
|questionType		|String |是		  	|fill:填空;	choice:选择;	audio:音频;	explain:讲解;	graphicVideo:图文视频|							|
|questionData		|json	|是		  	|json字符串								|
|order				|Integer|否		  	|order,排序								|

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