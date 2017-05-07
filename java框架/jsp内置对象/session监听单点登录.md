## 监听session的销毁，有两种方式：
1. 使用HttpSessionListener监听session的销毁。 

2. 使用HttpSessionBindingListener监听session的销毁。

* 一、使用HttpSessionListener，主要是继承HttpSessionListener接口
```java
public class OnlineUserListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {}//创建session时调用

    public void sessionDestroyed(HttpSessionEvent event) {//销毁session时调用

        HttpSession session = event.getSession();

        ServletContext application = session.getServletContext();

        // 取得登录的用户名

        String username = (String)session.getAttribute("username");

        // 从在线列表中删除用户名

        List onlineUserList = (List)application.getAttribute("onlineUserList");

        onlineUserList.remove(username);

        System.out.println(username + "超时退出");

    }

}
```
### 为了让监听器发挥作用，将它添加到web.xml中：
```xml

    <listener>

        <listener-class>advance.OnlineUserListener</listener-class>

    </listener>
```
### 以下两种情况下就会发生sessionDestoryed（会话销毁）事件： 
<p>
1.执行session.invalidate()方法时。 

2.如果用户长时间没有访问服务器，超过了会话最大超时时间，服务器就会自动销毁超时的session。
</p>
### 设置session超时时间

（1）在主页面或者公共页面中加入：
```java

HttpSession session = request.getSession(true);  

session.setMaxInactiveInterval(900);

```
	单位是秒，即在没有活动15分钟后，session将失效。这里要注意这个session设置的时间是根据服务器来计算的，而不是客户端。 

（2）在项目的web.xml中设置
```xml
<session-config>

    <session-timeout>15</session-timeout>

</session-config>
``` 
单位是分钟。 

（3）直接在应用服务器中设置，如果是tomcat，可以在tomcat目录下conf/web.xml中找到<session-config>元素，tomcat默认设置是30分钟，只要修改这个值就可以了。 

优先级：（1）>(2)>(3)

* 二、继承HttpSessionBindListener接口
```java
public class BindingListener implements HttpSessionBindingListener

{

    public void valueBound(HttpSessionBindingEvent arg0) {

        System.out.println("valueBound");

    }

    public void valueUnbound(HttpSessionBindingEvent arg0) {

        System.out.println("valueUnbound");

    }

}
```

不需要配置web.xml，但要将listener加入session的属性中：
```java
BindingListener  binding = new BindingListener();  

session.setAttribute("anyname", binding );//这个时候要触发valueBound方法了
```
valueUnbound()的触发条件是以下三种情况： 

1. 执行session.invalidate()时。 

2. session超时自动销毁时。 

3. 执行session.setAttribute(“anyname”, “其他对象”)或session.removeAttribute(“anyname”)将listener从session中删除时。 

只要不将listener从session中删除，就可以监听到session的销毁。


## 单点登录防止同一账户多次登录

### 两种解决方案：

1. 通过数据库状态位判断该用户是否已经登录。

2. 利用session监听器监听每一个登录用户的登录情况。

### 第一种方案：

为用户增加标志位（是否登录状态），在登录时判断该用户状态。然后登录成功修改状态为登录状态。

退出时修改为未登录状态（此处1、正常注销处理修改。2、通过前端js方法beforeunload 关闭页面触发action异步修改状态。3、使用上述session超时时间更新用户状态）

### 第二种方案的具体实现：

* A.用户登录后，先去数据库查询该登录名是否存在、是否锁定，在登录名存在且非锁定的情况下，从application内置作用域对象中取出所有的登录信息，

查看该登录名是否已经登录，如果登录了，就友好提示下；反之表示可以登录，将该登录信息保存在application中
```java
//所有的登录信息

Map<String, String> loginUserMap = (Map<String, String>) super.getApplicationAttr(Constant.LOGIN_USER_MAP);

for (String username : loginUserMap.keySet()) {

//判断是否已经保存该登录用户的信息，是否为同一个用户进行重复登录

if(!username.equals(user.getFuUserName()) || loginUserMap.containsValue(sessionId)){

continue;

}

isExist = true;

break;

}

if(isExist){

//该用户已登录

}else {

//该用户没有登录

loginUserMap.put(result.getFuUserName(), sessionId);

}
```
* B.登录考虑完之后，来考虑考虑退出。
<p>
用户正常退出时，我们需要将该用户的登录信息从session中移除。我们可以写一个Session监听器，监听sessioon销毁的时候，我们将登录的用户注销掉，也就是从application中移除。表示该用户已经下线了。另外，还有一个问题，如果说登录的用户突然关闭了浏览器而没有点击退出按钮。那么可以利用beforeunload 事件，在浏览器刷新或者关闭的时候触发。
</p>
```java
//在session销毁的时候 把loginUserMap中保存的键值对清除   

        User user = (User)event.getSession().getAttribute("loginUser");  

        if(user!=null){  

                Map<String, String> loginUserMap = (Map<String, String>)event.getSession().getServletContext().getAttribute("loginUserMap");  

                loginUserMap.remove(user.getFuUserName());  

event.getSession().getServletContext().setAttribute("loginUserMap",loginUserMap); 

        } 

```
