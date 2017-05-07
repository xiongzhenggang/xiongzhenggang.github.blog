## 使用cookie自动登录
### 步骤如下：
1. 保存用户信息阶段：
<p>
当 用户登陆网站时，在登陆页面填写完用户名和密码后，如果用户在提交时还选择了“两星期内自动登陆”复选框，那么在后台程序中验证用户名和密码全都正确后，
</P>
<p>
  还要为用户保存这些信息，以便用户下一次可以直接进入网站；如果用户没有勾选“两星期内自动登陆”复选框，则不必为用户保存信息，那么用户在下一次登陆网 站时仍需要填写用户名和密码。
  在保存用户信息阶段，主要的工作是对用户的信息进行加密并保存到客户端。加密用户的信息是较为繁琐的，大致上可分为以下几个步聚：</p>

<P>
① 得到用户名、经MD5加密后的用户密码、cookie有效时间(本文设置的是两星期，可根据自己需要修改)

② 自定义的一个webKey，这个Key是我们为自己的网站定义的一个字符串常量，这个可根据自己需要随意设置

③ 将上两步得到的四个值得新连接成一个新的字符串，再进行MD5加密，这样就得到了一个MD5明文字符串

④ 将用户名、cookie有效时间、MD5明文字符串使用“：”间隔连接起来，再对这个连接后的新字符串进行Base64编码

⑤ 设置一个cookieName,将cookieName和上一步产生的Base64编码写入到客户端。
</P>
2. 读取用户信息：
<p>
其实弄明白了保存原理，读取及校验原理就很容易做了。读取和检验可以分为下面几个步骤：

① 根据设置的cookieName，得到cookieValue，如果值为空，就不帮用户进行自动登陆；否则执行读取方法

② 将cookieValue进行Base64解码，将取得的字符串以split(“:”)进行拆分，得到一个String数组cookieValues（此操作与保存阶段的第4步正好相反），这一步将得到三个值：

       cookieValues[0] ---- 用户名
       cookieValues[1] ---- cookie有效时间
       cookieValues[2] ---- MD5明文字符串
③ 判断cookieValues的长度是否为3，如果不为3则进行错误处理。

④ 如果长度等于3，取出第二个,即cookieValues[1]，此时将会得到有效时间（long型），将有效时间与服务器系统当前时间比较，如果小于当前时间，则说明cookie过期，进行错误处理。

⑤ 如果cookie没有过期，就取cookieValues[0]，这样就可以得到用户名了，然后去数据库按用户名查找用户。

⑥ 如果上一步返回为空，进行错误处理。如果不为空，那么将会得到一个已经封装好用户信息的User实例对象user

⑦ 取出实例对象user的用户名、密码、cookie有效时间（即cookieValues[1]）、webKey，然后将四个值连接起来，然后进行MD5加密，这样做也会得到一个MD5明文字符串（此操作与保存阶段的第3步类似）

⑧ 将上一步得到MD5明文与cookieValues[2]进行equals比较，如果是false，进行错误处理；如果是true，则将user对象添加到session中，帮助用户完成自动登陆

</p>
### 实现的主要代码如下

* 一、js弹出框是否保存cookie用于自动登录
```html
 <script type="text/javascript">

      $(function() {

    		$("#submit").click(function(e){

    			debugger;

    			var cn_xzg= get_cookie("cn.xzg");

    			if(cn_xzg== null||typeof(cn_xzg) == "undefined"||cn_xzg==""){

    				firm();

    			}

    			});

    	});

   function firm() {

	   var url ='${ctx}/saveCookie.do';

	   var username= $("input[id='username']").val();

	   var password= $("input[id='password']").val();

          //利用对话框返回的值 （true 或者 false）  

          if (confirm("确定保存密码？")) {  

        	 //使用ajax异步处理

        	 $.ajax({

    		type:"POST", 

    		url:url,  /* 这里就是action名+要执行的action中的函数 */

    		contentType: "application/json; charset=utf-8",

    		data:JSON.stringify({username:username,password:password}),  //url后面要传送的参数    

    		async: true,

    		dataType : "json",

    		success:function(data){

    			//

    			}

    		});

          }

          else {  

          }  

      } 

   function get_cookie(Name) {

	   var search = Name + "="//查询检索的值

	   var returnvalue = "";//返回值

	   if (document.cookie.length > 0) {

	     sd = document.cookie.indexOf(search);

	     if (sd!= -1) {

	        sd += search.length;

	        end = document.cookie.indexOf(";", sd);

	        if (end == -1)

	         end = document.cookie.length;

	         //unescape() 函数可对通过 escape() 编码的字符串进行解码。

	        returnvalue=unescape(document.cookie.substring(sd, end))

	      }

	   } 

	   return returnvalue;

	}

      </script>

```
* 二、处理cookie的工具类,包括读取,保存,清除三个主要方法。

```java
public class CookieUtil {

       //保存cookie时的cookieName

       private final static String cookieDomainName = "cn.xzg";

      

       //加密cookie时的网站自定码

       public  final static String webKey = "myinfo";

      

//设置cookie有效期是一小时，根据需要自定义

       private final static long cookieMaxAge = 60 * 5  ;//测试5分钟        

       public static void saveCookie(String username,String password, HttpServletResponse response) {

              //cookie的有效期

              long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);

              //MD5加密用户详细信息

              //首先将用户密码进行MD5加密得到加密后的用户密码

              String passwordMd5=getMD5(password);

              //形成MD5明文

              String cookieValueWithMd5 =getMD5(username +passwordMd5+webKey);

              //将要被保存的完整的Cookie值

              String cookieValue = username + ":" + validTime + ":" + cookieValueWithMd5;

              //再一次对Cookie的值进行BASE64编码 Base64.encodeBase64(src.getBytes());

              String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));

              //开始保存Cookie

              Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);

              cookie.setMaxAge(60 * 60 * 24 * 365 * 2);

              //cookie有效路径是网站根目录

              cookie.setPath("/");

              //向客户端写入

              response.addCookie(cookie);

       }



	/**

	 * @param string

	 * @return

	 */

	public static String getMD5(String string) {

		// TODO Auto-generated method stub

		 //确定计算方法

	    try { 

        MessageDigest md5=MessageDigest.getInstance("MD5");

        // 输入的字符串转换成字节数组  

        byte[] inputByteArray = string.getBytes();  

        // inputByteArray是输入字符串转换得到的字节数组  

        md5.update(inputByteArray);  

        // 转换并返回结果，也是字节数组，包含16个元素  

        byte[] resultByteArray = md5.digest();  

        // 字符数组转换成字符串返回  

        return byteArrayToHex(resultByteArray); 

        } catch (NoSuchAlgorithmException e) {  

            return null;  

         }  

	}

	 public static String byteArrayToHex(byte[] byteArray) {  

	        // 首先初始化一个字符数组，用来存放每个16进制字符  

	        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };  

	        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））  

	        char[] resultCharArray =new char[byteArray.length * 2];  

	        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去  

	        int index = 0; 

	        for (byte b : byteArray) {  

	           resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];  

	           resultCharArray[index++] = hexDigits[b& 0xf];  

	        }

	        // 字符数组组合成字符串返回  

	        return new String(resultCharArray);  

	    }

	

	 /**

	      * 删除cookie

	      * 

	      * @param response

	      * @param name

	     */

	  public static void removeCookie(HttpServletResponse response, String name) {

	         Cookie uid = new Cookie(name, null);

	         uid.setPath("/");

	         uid.setMaxAge(0);

	        response.addCookie(uid);

    }

	  /**

	    * 获取cookie值

	   * @param request

	  * @return

	*/

	    public static String getUid(HttpServletRequest request,String cookieName) {

	        Cookie cookies[] = request.getCookies();

	       for (Cookie cookie : cookies) {

	              if (cookie.getName().equals(cookieName)) {

	                return cookie.getValue();

	               }

	           }

	         return null;

	         }

}
```

* 三、保存cookie加密的controler
```java
//保存cookie自动登录

@RequestMapping(value="/saveCookie.do",method={RequestMethod.POST,RequestMethod.GET})

@ResponseBody

public void saveCookie(@RequestBody  Map<String, String> map,HttpServletRequest request, HttpServletResponse response){

	if(map.containsKey("username")&&map.containsKey("password")){

	String	username=map.get("username");

	String	password = map.get("password");

	//保存

	CookieUtil.saveCookie(username, password,response);

	}else{

		logger.error("未获取到用户姓名和密码！");

	}

}
```
* 四、初始登录检测是否存在cookie，以及是否未超时且用户密码正确。
```java
@RequestMapping(value="/loginAuto.do",method={RequestMethod.GET,RequestMethod.GET})

	public String login(HttpServletRequest request,RedirectAttributes redirectAttributes){

		String forword="";

		String userId="";//等同于前台的username

		String password="";

		boolean isLogin = false;

		User user;

		//使用cookie自动登录

		String cookieValue=CookieUtil.getUid(request, "cn.xzg");

		if(cookieValue!=null){

			//base64解密

			String cookiedeBase64=new String(Base64.decode(cookieValue.getBytes()));

			//通过：来拆分存到string数组中

			String[] cookieArry = cookiedeBase64.split(":");

			//拆分后的值

			/* cookieArry[0] ---- 用户名 cookieArry[1] ---- cookie有效时间 cookieArry[2] ---- MD5明文字符串*/

			if(cookieArry.length!=3){

				isLogin=false;

				}else{

					long effictTime = Long.valueOf(cookieArry[1]);

					userId= cookieArry[0];

					String	cookieMd5Cli=cookieArry[2];

						if(effictTime<System.currentTimeMillis()){

							isLogin=false;

							logger.debug("======cookie有效时间过期！===========");

						}else{

							user = activitiWorkflowLogin.getUserInfo(userId);

							if(user!=null){

								password=user.getPassword();

								String cookieMd5Ser = CookieUtil.getMD5(userId+password+CookieUtil.webKey);

								//

								if(cookieMd5Cli.equals(cookieMd5Ser)){

									//验证成功自动登录

									isLogin=true;

									logger.debug("==============cookie可以自动登录！=========");

								}else{

									//用户信息不匹配阻止登录

									isLogin=false;

									logger.debug("用户信息和数据库中不符！");

								}

							}

					}

				}

		}

//要根据判断结果来确定是否登录

		if(isLogin){

			user=activitiWorkflowLogin.getUserInfo(userId);

			List<com.xzg.domain.Group> listGroup  = activitiWorkflowLogin.getUserOfGroup(userId);

			request.getSession().setAttribute("loginuser", user);

			request.getSession().setAttribute("listGroup", listGroup);

			//listener加入session的属性中：开始监听

			SessionListener sessionListener = new SessionListener(request.getServletContext());

			//获取sessionId保存到全局变量application中

			 ServletContext application = request.getServletContext();

			 //获取保运的用户id

			 String appId = (String) application.getAttribute("userid");

			 //先判断session会话是否已存在全局变量中

			if(userId.equals(appId)){

				redirectAttributes.addFlashAttribute("message", "您已登录，请不要重复登录!");

				forword="/login.do";//login.jsp

			}else{

					application.setAttribute("userid", userId);

				 	request.getSession().setAttribute("sessionListener", sessionListener);

					redirectAttributes.addFlashAttribute("message", "登录成功!");

					forword="/main.do";//main.jsp

			}

		}else{

			redirectAttributes.addFlashAttribute("message", "用户名或密码错误!");

			//登录失败，在login_tmp表中更新字段num-1直到为0时锁定用户（5分钟内）当锁定用户时禁止登录

			forword="/login.do";//login.jsp

		}

	return "redirect:"+forword;

		}

```
* 五、注意在数据库中用户密码是经过md5加密后存入数据库中。


