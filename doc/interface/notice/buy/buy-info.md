## 购买通知详情接口
+ 功能说明：购买通知详情，本接口包含二部分：模板消息,客户消息

### 历史记录

#### 2019-12-10 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/wx-purchase/info
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/wx-purchase/info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 购买通知详情				|
|接口名称		|/wx-purchase/info			|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|是否必填	|说明			    			|
|:------------------|:----------|:------------------------------|
|无			   		|		  	|			      	  			|

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "code":0,
    "data":{
        "template":{
            "id":1000001,
            "type":"purchase-notice-template",
            "content":null,
            "isOpen":0,
            "url":"http://baidu.com",
            "levelId":null,
            "contentMap":{
                "contentHead":"",
                "contentDate":"",
                "contentInfoColor":"#6d1919",
                "contentBodyColor":"#aab17b",
                "contentInfo":"商品:{dateTime}",
                "contentBody":"成功购买课程{levelName}祝{nickName}学习愉快！！"
            }
        },
        "custom":{
            "id":1000000,
            "type":"purchase-notice-custom",
            "content":"成功购买课程{levelName}购买课程将于{dateTime}开课祝{nickName}学习愉快",
            "isOpen":1,
            "levelId":null
        }
    },
    "success":true,
    "denied":false,
    "message":""
}
```

**返回值描述**  

```
content:错误描述
```