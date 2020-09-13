## 课程列表接口
+ 功能说明：课程列表

### 历史记录

#### 2019-09-26 
- 新增

**公共参数:**
+ 测试接口的地址为 https://test-msyb-cms-api.qingclasswelearn.com/lesson/list
+ 线上接口的地址为 https://msyb-cms-api.qingclasswelearn.com/lesson/list
+ 公共参数 说明： 公共参数需要传递到get或post里面，get请求参数都传到参数中，post请求参数都传到form中。

### 接口信息
|接口调用方式 	|	Restful Service			|
|:--------------|:--------------------------|
|功能	     	| 课程列表					|
|接口名称		|/lesson/list				|
|请求方法		|POST					    |

### 接口公共参数
|参数名		   		|类型	|是否必填	|说明			    					|
|:------------------|:------|:----------|:--------------------------------------|
|levelId			|Integer|是		  	|1000005，子频道ID,四六级ID				|
|pageNo				|Integer|是		  	|1，当前页								|
|pageSize			|Integer|是		  	|10，页面容量								|
  

### 接口返回值
+ 返回值数据类型：json
+ 返回值说明：

**返回值**  

```
{
    "pageTotal":25,
    "data":{
        "orderMax":24,
        "lessonList":[
            {
                "audition":0,							//	试听：0为常规课; 1为试听课
                "id":1000137,
                "image":"images/uploads/2019-03-11/fd59a8d0-e3e7-4d93-97e8-9b7e33be62b2.png",
                "shareImage":"images/uploads/2019-03-11/fd59a8d0-e3e7-4d93-97e8-9b7e33be62b2.png",
                "isOpen":1,								//	0为关闭，1为打开
                "lessonkey":"561989a2-bfc4-486f-8ba5-9791deaf4975",
                "levelid":1000005,						//	父级id,子频道id,四六级ID 
                "name":"day 24",						//	课程名称
                "order":24,								//	排序列
                "pageNo":0,
                "pageSize":0,
                "part":0,
                "picId":0,
                "relation":1,							// 是否关联绘本
                "star":0,								// 子级unit数量
                "title":"day 24",						// 标题
                "updateDate":1568217600000				// 最后更新时间
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