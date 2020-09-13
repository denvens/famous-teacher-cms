## 题列表接口
+ 功能说明：课程列表

### 历史记录

#### 2019-09-26 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/question/list
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/question/list
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 题列表					|
|接口名称		|/question/list				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|lessonId			|Integer|是		  	|1000000，课程ID							|
|pageNo				|Integer|是		  	|1，当前页								|
|pageSize			|Integer|是		  	|10，页面容量								|
  

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "data":{
        "orderMax":0,
        "questionList":[
            {
                "id":1000579,
                "lessonId":1000000,
                "order":0,
                "questionData":"{"word":"wear","selectWord":{"baseImage":"images/uploads/2019-02-19/de32302a-ff38-4ead-94cf-afa87ced335e.png,images/uploads/2019-02-19/6ffaea81-3ad7-45ce-a029-f9f7447c52f2.png,images/uploads/2019-02-19/7d47ef3b-884a-4c42-a085-7c1fd8c7dc77.png","confusionImage":"images/uploads/2019-02-19/eec4de4e-12c0-4975-a358-3ff31300dd8e.png","id":1000020,"isKey":1,"keyImage":"images/uploads/2019-02-19/9df52d8d-238b-4db3-9fc8-85d7809ec0bb.png","translation":"穿着","voice":"voices/uploads/2019-02-19/b717965b-cffb-4456-b787-f2a143c602c2.mp3","word":"wear"},"voice":"voices/uploads/2019-02-19/b717965b-cffb-4456-b787-f2a143c602c2.mp3","selection":{"translate":"穿着","id":1000020,"isRight":true}}",
                "questionKey":"8707a85a-54f2-4bfd-ac63-dc4170405620",
                "questionType":"question-selection",
                "unitId":1000101
            }
        ]
    },
    "success":true,
    "denied":false
}
```

**返回值描述**  

```
content:错误描述
```