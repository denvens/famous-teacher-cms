## 分享打卡页修改接口
+ 功能说明：分享打卡页修改

### 历史记录

#### 2019-10-09 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/wx-share/edit
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/wx-share/edit
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 分享打卡页修改				|
|接口名称		|/wx-share/edit				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|id					|Integer|是		  	|分享打卡配置id							|
|levelId			|Integer|是		  	|子频道Id，四六级							|
|url				|String |是		  	|自定义url								|
|spaceTitle			|Integer|是		  	|微信分享朋友圈标题 						|
|freTitle			|String	|是		  	|微信分享好友标题 							|
|content			|String	|是		  	|微信分享好友描述 							|
|img				|String	|是		  	|自定义图标  								|  
|channelId			|Integer|是		  	| 										|
|type				|String	|是		  	|分享打卡页类型							|
|shareContent		|String	|是		  	|个别分享样式的文本内容					|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code": 0,
    "data": "123456",
    "success": true,
    "denied": false,
    "message": ""
}
```

**返回值描述**  

```
content:错误描述
```