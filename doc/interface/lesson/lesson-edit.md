## 子频道编辑接口
+ 功能说明：子频道编辑

### 历史记录

#### 2019-10-09 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/lesson/edit
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/lesson/edit
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 子频道编辑					|
|接口名称		|/lesson/edit				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|id					|Integer|是		  	|level id								|
|name				|String	|是		  	|level名称								|
|isOpen				|Integer|是		  	|0为关闭，1为打开							|
|image				|String	|是		  	|level图片								| 
|introduction		|String	|是		  	|level简介								|
|buySite			|String	|是		  	|购买页地址								|

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