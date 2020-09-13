## 结课提醒详情信息接口
+ 功能说明：结课提醒详情信息，本接口包含三部分：课程提醒,客户消息,模板消息

### 历史记录

#### 2019-12-13	
- 新增

**公共参数:**
+ 测试接口的地址为 http://squirrel-admin-api-test.ivykid.com/wx-push-message/end-info
+ 线上接口的地址为 https://squirrel-cms-api.ivykid.com/wx-push-message/end-info
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 结课提醒详情信息			|
|接口名称		|/wx-push-message/end-info	|
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
    	"levelId":100005;
    	"levelName":"魔幻森林";
        "template":{
            "id":1000001,
            "type":"push-message-begin-study-template",
            "content":null,
            "isOpen":1,													//生效,失效
            "url":" https://squirrel.ivykid.com/game.html",				//跳转链接
            "contentMap":{
                "lessonHead":"你的课程{levelName}已经更新，快点开始学习吧～", 	//模板头部
                "lessonHeadColor":"#f81a30",
                
                "lessonName":"{lessonName}",								//课程名称
                "lessonNameColor":"#f81a30",
                
                "lessonEndTime":"2016年7月20日",								//结课时间
                "lessonEndTimeColor":"#f81a30",
                
                "lessonContent":"请提前做好热身准备，开始今天的课程吧～",		//提醒内容
                "lessonContentColor":"#f81a30"
            }
        },
        "custom":{
            "id":1000001,
            "type":"push-message-begin-study-custom",
            "content":"\"Hi~亲爱的学员：您好! 您参与的课程已更新，快来学习和打卡最新课程吧！\"",	//客服消息内容
            "isOpen":0														//生效,失效
        },
        "remind":{
            "id":1000001,
            "remindRate":"1,2,3",
            "firstRemind":"20:30",
            "type":"class-ends"
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