## 分享打卡页列表接口
+ 功能说明：分享打卡页列表

### 历史记录

#### 2019-10-09 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/wx-share/list
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/lesson/wx-share/list
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 分享打卡页列表				|
|接口名称		|/wx-share/list				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|无					|		|		  	|										|

  

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "pageTotal":25,
    "data":{
    },
    "success":true,
    "denied":false
}
```

**返回值描述**  

```
content:错误描述
```