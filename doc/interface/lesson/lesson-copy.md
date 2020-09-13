## 课程复制确认接口
+ 功能说明：课程复制确认

### 历史记录

#### 2019-11-19 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/lesson/copy
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/lesson/copy
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 课程复制确认				|
|接口名称		|/lesson/copy				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|lessonIds			|String	|是 			|源课程id集合，逗号分隔列：1,2,3 			|
|levelId			|Integer|是		  	|目标level Id							|

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