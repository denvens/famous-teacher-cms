## 分享打卡页信息接口
+ 功能说明：分享打卡页信息

### 历史记录

#### 2019-10-09 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/wx-share/info
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/wx-share/info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 分享打卡页信息				|
|接口名称		|/wx-share/info				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    													|
|:------------------|:------|:----------|:----------------------------------------------------------------------|
|id					|Integer|是		  	|shareId,分享页shareId,如果shareId为空,说明还没设置分享页配置,前端不调接口	|
|levelId			|Integer|是		  	|levelId,分享页id														|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code": 0,
    "data": {
        "id": 1000000,
        "url": "\b/www",
        "spaceTitle": "这里是朋友圈title",
        "freTitle": "这里是好友title",
        "content": "这里是内容",
        "img": "/",
        "pageNo": null,
        "pageTotal": null,
        "pageSize": null
        "channelId":
    },
    "success": true,
    "denied": false,
    "message": ""
}
```

**返回值描述**  

```
content:错误描述
```