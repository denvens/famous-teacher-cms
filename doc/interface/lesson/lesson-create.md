## 课程新建接口
+ 功能说明：课程新建

### 历史记录

#### 2019-09-26 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/lesson/create
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/lesson/create
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 课程新建					|
|接口名称		|/lesson/create				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|name				|String	|是		  	|课程名称								|
|levelId			|Integer|是		  	|子频道Id，四六级							|
|isOpen				|Integer|是		  	|0为关闭，1为打开							|
|order				|Integer|是		  	|序号，排序								|
|title				|String	|是		  	|课程标题								|
|image				|String	|是		  	|课程图片								|
|shareImage			|String	|是		  	|分享页图片								|  

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