һ��SpringMVC�ĸ��ֲ����󶨷�ʽ�����controller��ȡǰ�����ݣ�
 
1. ������������(��intΪ������������)��
Controller���룺
@RequestMapping("saysth.do")
public void test(int count) {
}
�����룺
<form action="saysth.do" method="post">
<input name="count" value="10" type="text"/>
......
</form>
����input��nameֵ��Controller�Ĳ�������������һ�£�����������ݰ󶨣������һ�¿���ʹ��@RequestParamע�⡣��Ҫע����ǣ����Controller���������ж�����ǻ����������ͣ����Ǵ�ҳ���ύ����������Ϊnull���ߡ�"�Ļ������������ת�����쳣��Ҳ���Ǳ��뱣֤�����ݹ��������ݲ���Ϊnull��"�����ԣ��ڿ��������У��Կ���Ϊ�յ����ݣ���ý������������Ͷ���ɰ�װ���ͣ�����μ���������ӡ�
 
2. ��װ����(��IntegerΪ������������)��
Controller���룺
@RequestMapping("saysth.do")
public void test(Integer count) {
}
�����룺
<form action="saysth.do" method="post">
<input name="count" value="10" type="text"/>
......
</form>
�ͻ����������ͻ���һ������֮ͬ�����ڣ������ݹ��������ݿ���Ϊnull��"�����������Ϊ�����������numΪ��"���߱�����num���input����ô��Controller���������е�numֵ��Ϊnull��
 
3. �Զ���������ͣ�
Model���룺
 
public class User {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
 
Controller���룺
@RequestMapping("saysth.do")
public void test(User user) {
}
�����룺
<form action="saysth.do" method="post">
<input name="firstName" value="��" type="text"/>
<input name="lastName" value="��" type="text"/>
......
</form>
�ǳ��򵥣�ֻ�轫�������������input��nameֵһһƥ�伴�ɡ�
 
4. �Զ��帴�϶������ͣ�
Model���룺
 
public class ContactInfo {
    private String tel;
    private String address;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

public class User {
    private String firstName;
    private String lastName;
    private ContactInfo contactInfo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

}
 
Controller���룺
 
@RequestMapping("saysth.do")
public void test(User user) {
    System.out.println(user.getFirstName());
    System.out.println(user.getLastName());
    System.out.println(user.getContactInfo().getTel());
    System.out.println(user.getContactInfo().getAddress());
}
 
�����룺
 
<form action="saysth.do" method="post">
<input name="firstName" value="��" /><br>
<input name="lastName" value="��" /><br>
<input name="contactInfo.tel" value="13809908909" /><br>
<input name="contactInfo.address" value="��������" /><br>
<input type="submit" value="Save" />
</form>
 
User��������ContactInfo���ԣ�Controller�еĴ���͵�3��˵��һ�£����ǣ��ڱ������У���Ҫʹ�á�������(�������͵�����).��������������input��name��
 
5. List�󶨣�
List��Ҫ���ڶ����ϣ�������ֱ��д��Controller�����Ĳ����С�
Model���룺
 
public class User {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

public class UserListForm {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
 
Controller���룺
@RequestMapping("saysth.do")
public void test(UserListForm userForm) {
    for (User user : userForm.getUsers()) {
        System.out.println(user.getFirstName() + " - " + user.getLastName());
    }
}
�����룺
 
<form action="saysth.do" method="post">
<table>
<thead>
<tr>
<th>First Name</th>
<th>Last Name</th>
</tr>
</thead>
<tfoot>
<tr>
<td colspan="2"><input type="submit" value="Save" /></td>
</tr>
</tfoot>
<tbody>
<tr>
<td><input name="users[0].firstName" value="aaa" /></td>
<td><input name="users[0].lastName" value="bbb" /></td>
</tr>
<tr>
<td><input name="users[1].firstName" value="ccc" /></td>
<td><input name="users[1].lastName" value="ddd" /></td>
</tr>
<tr>
<td><input name="users[2].firstName" value="eee" /></td>
<td><input name="users[2].lastName" value="fff" /></td>
</tr>
</tbody>
</table>
</form>
 
��ʵ����͵�4��User�����е�contantInfo���ݵİ��е����ƣ����������UserListForm������������Ա������List����������ͨ�Զ���������ԣ��ڱ�����Ҫָ��List���±ꡣֵ��һ����ǣ�Spring�ᴴ��һ��������±�ֵΪsize��List�������ԣ���������ж�̬����С�ɾ���е����������Ҫ�ر�ע�⣬Ʃ��һ������û���ʹ�ù����о������ɾ���С������еĲ���֮���±�ֵ�ͻ���ʵ�ʴ�С��һ�£���ʱ��List�еĶ���ֻ���ڱ��ж�Ӧ���±����Щ�Ż���ֵ�������Ϊnull���������ӣ�
�����룺
 
<form action="saysth.do" method="post">
<table>
<thead>
<tr>
<th>First Name</th>
<th>Last Name</th>
</tr>
</thead>
<tfoot>
<tr>
<td colspan="2"><input type="submit" value="Save" /></td>
</tr>
</tfoot>
<tbody>
<tr>
<td><input name="users[0].firstName" value="aaa" /></td>
<td><input name="users[0].lastName" value="bbb" /></td>
</tr>
<tr>
<td><input name="users[1].firstName" value="ccc" /></td>
<td><input name="users[1].lastName" value="ddd" /></td>
</tr>
<tr>
<td><input name="users[20].firstName" value="eee" /></td>
<td><input name="users[20].lastName" value="fff" /></td>
</tr>
</tbody>
</table>
</form>
 
���ʱ��Controller�е�userForm.getUsers()��ȡ��List��sizeΪ21��������21��User���󶼲���Ϊnull�����ǣ���2����19��User�����е�firstName��lastName��Ϊnull����ӡ�����
 
aaa - bbb
ccc - ddd
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
null - null
eee - fff
 
 
6. Set�󶨣�
Set��List���ƣ�Ҳ��Ҫ���ڶ����ϣ�������ֱ��д��Controller�����Ĳ����С����ǣ���Set����ʱ����������Set������add��Ӧ��������ģ�Ͷ���
Model���룺
 
public class User {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

public class UserSetForm {
    private Set<User> users = new HashSet<User>();

    public UserSetForm() {
        users.add(new User());
        users.add(new User());
        users.add(new User());
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
 
Controller���룺
@RequestMapping("saysth.do")
public void test(UserSetForm userForm) {
    for (User user : userForm.getUsers()) {
        System.out.println(user.getFirstName() + " - " + user.getLastName());
    }
}
�����룺
 
<form action="saysth.do" method="post">
<table>
<thead>
<tr>
<th>First Name</th>
<th>Last Name</th>
</tr>
</thead>
<tfoot>
<tr>
<td colspan="2"><input type="submit" value="Save" /></td>
</tr>
</tfoot>
<tbody>
<tr>
<td><input name="users[0].firstName" value="aaa" /></td>
<td><input name="users[0].lastName" value="bbb" /></td>
</tr>
<tr>
<td><input name="users[1].firstName" value="ccc" /></td>
<td><input name="users[1].lastName" value="ddd" /></td>
</tr>
<tr>
<td><input name="users[2].firstName" value="eee" /></td>
<td><input name="users[2].lastName" value="fff" /></td>
</tr>
</tbody>
</table>
</form>
 
������List�����ơ�
��Ҫ�ر����ѵ��ǣ��������±�ֵ����Set��size������׳�org.springframework.beans.InvalidPropertyException�쳣�����ԣ���ʹ��ʱ��Щ���㡣
 
7. Map�󶨣�
Map��Ϊ����Ҳ��Ҫ���ڶ����ϣ�������ֱ��д��Controller�����Ĳ����С�
Model���룺
 
public class User {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

public class UserMapForm {
    private Map<String, User> users;

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

}
 
Controller���룺
 
@RequestMapping("saysth.do")
public void test(UserMapForm userForm) {
    for (Map.Entry<String, User> entry : userForm.getUsers().entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue().getFirstName() + " - " +
        entry.getValue().getLastName());
    }
}
 
�����룺
 
<form action="saysth.do" method="post">
<table>
<thead>
<tr>
<th>First Name</th>
<th>Last Name</th>
</tr>
</thead>
<tfoot>
<tr>
<td colspan="2"><input type="submit" value="Save" /></td>
</tr>
</tfoot>
<tbody>
<tr>
<td><input name="users['x'].firstName" value="aaa" /></td>
<td><input name="users['x'].lastName" value="bbb" /></td>
</tr>
<tr>
<td><input name="users['y'].firstName" value="ccc" /></td>
<td><input name="users['y'].lastName" value="ddd" /></td>
</tr>
<tr>
<td><input name="users['z'].firstName" value="eee" /></td>
<td><input name="users['z'].lastName" value="fff" /></td>
</tr>
</tbody>
</table>
</form>
 
��ӡ�����
x: aaa - bbb
y: ccc - ddd
z: eee - fff

����ǰ��jsp��ȡ���controller����
1��  @RequestMapping("/") 
    public ModelAndView getIndex(){      
        ModelAndView mav = new ModelAndView("index");   
      User  user = userDao.selectUserById(1);  
        mav.addObject("user", user);   
        return mav;    
}
����ModeAndView������addObject����������ֱ�Ӱ󶨷���index����spring-mvc.xml�����ö�ģ����ͼ���ǰ��׺����ͼ��Jsp��value="${user.userId}"ʹ��el���ʽֱ�������
2����ǰ̨���ݲ���
 
    //pass the parameters to front-end
    @RequestMapping("/show")
    public String showPerson(Map<String,Object> map){
        Person p =new Person();
        map.put("p", p);
        p.setAge(20);
        p.setName("jayjay");
        return "show";
    }
 
ǰ̨����Request����ȡ��"p"

����ʹ��ajax��ֵ���磺
��jspҳ����ajax����
  $.ajax({
type:"POST",       
url:"test.do",  /* �������action��+Ҫִ�е�action�еĺ��� */
contentType: "application/json; charset=utf-8",
data:JSON.stringify({name:$("#userName").val()}),  //url����Ҫ���͵Ĳ���    
async: true,
dataType : "json",
success:function(data){ 
alert(data.name);}
});
��̨controller�����£�
  @RequestMapping("/test.do")
    @ResponseBody  //��springMVC���ṩ��JSON��Ӧ��֧��
    public Map<String,Object> test2(@RequestBody Map<String, String> map){
    	String name;
    	if(map.containsKey("name")){
    		 name=map.get("name");
    	}else{
    		name=null;
    	}
    	Map<String,Object> mapout=new HashMap<String, Object>();
    	mapout.put("id", 16);
    	mapout.put("name",name.trim());
    	return mapout;
}

��Ȼ��Ϊspringmvc���Զ��󶨲��������Լ��༶������������������ֱ�ӻ�ȡ  
public Map<String,Object>  test2(@RequestBody String name){
  ��������ʡ��

ʹ��ajax�ύ��controller���ص�Response������ͼ��ʵ����ת���£�
��Ϊajax�ύ������ܹ���controller��ʵ����ת�����Է���������ҳ�棬ˢ�£���ֻ��ͨ������;����ʵ�֡�  
$(function(){
         $("#reg01").click(function(){
            $.get("showUser.do",function(data){
            	  window.location.href = "showUser.do";  
            });
     });
     });
�����ύ����ת���ύ���ص�ҳ�档
Spring mvc cotnroller���ַ������ͣ�cotnrolle������֧�����µķ��ط�ʽ��ModelAndView, Model, ModelMap, Map,View, String, void��
1��	ModelAndView  ���ص���һ������ģ�ͺ���ͼ��ModelAndView����
1.	 //����ModelAndView���캯������ָ������ҳ������ƣ�Ҳ����ͨ��setViewName��������������Ҫ��ת��ҳ�棻      
2.	    @RequestMapping(value="/index2",method=RequestMethod.GET)  
3.	    public ModelAndView index2(){  
4.	        ModelAndView modelAndView = new ModelAndView();  
5.	        modelAndView.addObject("name", "xxx");  
6.	        modelAndView.setViewName("/user/index");  
7.	        return modelAndView;  
8.	    }  
9.	    //���ص���һ������ģ�ͺ���ͼ��ModelAndView����  
2��	Model   Modelһ��ģ�Ͷ�����Ҫ����spring��װ�õ�model��modelMap,�Լ�java.util.Map�� ��û����ͼ���ص�ʱ����ͼ���ƽ���requestToViewNameTranslator������
3��	Map  
1.	@RequestMapping(value="/index3",method=RequestMethod.GET)  
2.	    public Map<String, String> index3(){  
3.	        Map<String, String> map = new HashMap<String, String>();  
4.	        map.put("1", "1");  
5.	        //map.put�൱��request.setAttribute����  
6.	        return map;  
7.	    }  
8.	    //��Ӧ��viewӦ��Ҳ�Ǹ������view����ͬ��void���ء�
4��	����String 
1.	//ͨ��model���з�װ���ݲ���  
2.	    @RequestMapping(value="/index4",method = RequestMethod.GET)  
3.	    public String index(Model model) {  
4.	        String retVal = "user/index";  
5.	        User user = new User();  
6.	        user.setName("XXX");  
7.	        model.addAttribute("user", user);  
8.	        return retVal;  
9.	    }

5��	 String ���أ�ʹ��json����
10.	//ͨ�����@ResponseBody�������ݻ��߶�����ΪHTTP��Ӧ���ķ��أ��ʺ�����ʱУ�飩��  
11.	    @RequestMapping(value = "/valid", method = RequestMethod.GET)  
12.	    @ResponseBody  
13.	    public String valid(@RequestParam(value = "userId", required = false) Integer userId,  
14.	            @RequestParam(value = "name") String name) {  
15.	        return String.valueOf(true);  
16.	    }  
17.	    //�����ַ�����ʾһ����ͼ���ƣ����ʱ�������Ҫ����Ⱦ��ͼ�Ĺ�������Ҫģ�͵Ļ����Ϳ��Ը����������һ��ģ�Ͳ�����Ȼ���ڷ�������ģ�����ֵ�Ϳ����ˣ�  

6��	void  
1.	@RequestMapping(method=RequestMethod.GET)  
2.	    public void index5(){  
3.	        ModelAndView modelAndView = new ModelAndView();  
4.	        modelAndView.addObject("xxx", "xxx");  
5.	    }  
6.	    //���صĽ��ҳ�滹�ǣ�/type  
7.	    //���ʱ������һ���ǽ����ؽ��д����HttpServletResponse ���ˣ����ûд�Ļ���  
8.	    //spring�ͻ�����RequestToViewNameTranslator ������һ����Ӧ����ͼ���ơ�������ʱ����Ҫģ�͵Ļ����������ͷ����ַ������������ͬ�ġ�  
9.	  
10.	}  




����web_inf�µ���Դ��һ�ַ�ʽ
����web_inf�µ���Դ�ǰ�ȫ�Ľ�ֱֹ�ӷ��ʣ�����һ��ͨ����̨��spring mvc �е�����ӳ�䵽controller��ת���������Ǽ��з�ʽ��
����һ
����WEB-INF�е�jsp�����޷�ͨ����ַ�����ʵģ����԰�ȫ��
���˵��Ҫ��������ļ����е�jsp�ļ���Ҫ����Ŀ��web.xml�ļ���ȥ����servlet��ʽ�������þ�ok��
      ����:
[html] view plain copy
   
1.	<servlet>  
2.	<servlet-name>runtain</servlet-name>  
3.	<jsp-file>/WEB-INF/INF.jsp</jsp-file>  
4.	</servlet>  
5.	<servlet-mapping>  
6.	<servlet-name>runtain</servlet-name>  
7.	<url-pattern>/XXX</url-pattern>  

���ʵ�ַ:http://localhost:8080/runtain/xxx
���ɷ���jspҳ������
������
<jsp:forward page = "/WEB-INF/jsp/test/test.jsp" />
������
request.getRequestDispatcher("/WEB-INF/a.jsp").forward(request, response);
��ô����servlet����web-inf�µ���ҳ��jsp�ļ��أ�
��Ϊweb-inf��,Ӧ�÷���������ָΪ����Ŀ¼����ֱ������������ǲ��ܷ��ʵ��ġ�
��Щ��������servlet���з��ʣ���web-inf����a.jsp���������request.getRequestDispatcher("/WEB-INF/a.jsp").forward(request,response);������ǲ����<jsp:forward page = "/WEB-INF/leaveoff/leavestart.jsp" />ͬ��
�����web-inf����a.htm,����request.getRequestDispatcher("/WEB-INF/a.htm").forward(request,response);���ܷ��ʡ�
һ��ʼ�벻ͨ�����ù֡��������룬jsp��ʵҲ��servlet,���Զ�����ģ�����workĿ¼�»���/web-inf/a$jsp.class���ͣ�������ͷ����,��Ӧ�÷������ܹ�����.htm,��a$htm.class.��������뷨����ʼ����
��tomcat�µ�conf/web.xml���ҵ�jsp�ķ��ʷ�ʽ
[html] view plain copy
   
1.	<servlet-mapping>  
2.	<servlet-name>jsp</servlet-name>  
3.	<url-pattern>*.jsp</url-pattern>  
4.	</servlet-mapping>  


�������������
[html] view plain copy
   
1.	<servlet-mapping>  
2.	<servlet-name>jsp</servlet-name>  
3.	<url-pattern>*.htm</url-pattern>  
4.	</servlet-mapping>  
5.	<servlet-mapping>  
6.	<servlet-name>jsp</servlet-name>  
7.	<url-pattern>*.html</url-pattern>  
8.	</servlet-mapping>  

���һ��OK����ʱ�ɷ���a.htm��
a.	html��work/web-inf/������a$htm.class,a$html.class����


jquery ajax��spring mvc controller�д�ֵ�����ܲ�����
��һ�ִ�ֵ��
controller���Ǽ��������Ļ������Ͳ���
spring MVC-controller
1.	@RequestMapping("update")  
2.	@ResponseBody//��ע�ⲻ��ʡ�� ����ajax�޷����ܷ���ֵ  
3.	public Map<String,Object> update(Long num, Long id, BigDecimal amount){  
4.	    Map<String,Object> resultMap = new HashMap<String, Object>();  
5.	    if(num == null || agentId == null || amount == null){  
6.	        resultMap.put("result", "�������Ϸ���");  
7.	        return resultMap;  
8.	    }  
9.	    //xxx�߼�����  
10.	    resultMap.put("result", result);  
11.	    return resultMap;  
12.	}  
jQuery ajax
1.	var params = {};  
2.	    //params.XX������Spring Mvc controller�еĲ�������һ��    
3.	    //������controller��ʹ��@RequestParam��  
4.	    params.num = num;  
5.	    params.id = id;  
6.	    params.amount = amount;  
7.	    $.ajax({  
8.	        async:false,  
9.	        type: "POST",  
10.	        url: "price/update",//ע��·��  
11.	        data:params,  
12.	        dataType:"json",  
13.	        success:function(data){  
14.	            if(data.result=='SUCCESS'){  
15.	                alert("�޸ĳɹ�");  
16.	            }else{  
17.	                alert("�޸�ʧ�ܣ�ʧ��ԭ��" + data + "��");  
18.	            }  
19.	        },  
20.	        error:function(data){  
21.	            alert(data.result);  
22.	        }  
23.	    });  
�ڶ��ִ�ֵ��
controller���ǲ�����ʵ��bean��bean�����Զ��ǻ�����������
Spring MVC-controller
1.	@RequestMapping("add")  
2.	    @ResponseBody//�˴�����ʡ�� ����ajax�޷���������ֵ  
3.	    public Map<String,Object> add(DataVo dataVo){  
4.	        Map<String, Object> result = null;  
5.	        if(dataVo.getNum() == null || StringUtils.isBlank(dataVo.geId())){  
6.	            result = new HashMap<String, Object>();  
7.	            result.put("msg", "�������Ϸ���");  
8.	            return result;  
9.	        }  
10.	        //xxxҵ���߼�����  
11.	        return result;  
12.	    }  
ʵ��bean DataVo
1.	public class DataVo {  
2.	    /** 
3.	     * ��� 
4.	     */  
5.	    private Long num;  
6.	    /** 
7.	     * id 
8.	     */  
9.	    private String id;  
10.	      
11.	    public Long getNum() {  
12.	        return num;  
13.	    }  
14.	    public void setNum(Long num) {  
15.	        this.num = num;  
16.	    }  
17.	    public String getId() {  
18.	        return id;  
19.	    }  
20.	    public void setId(String id) {  
21.	        this.id = id;  
22.	    }  
23.	}  
jquery ajax
1.	var params = {};  
2.	                params.num = $("#num").val();  
3.	                params.id = $("#id").val();//ע��params.����  ������ʵ��bean������һ��  
4.	                $.ajax({  
5.	                       type: "POST",  
6.	                       url: "price/add",  
7.	                       data:params,  
8.	                       dataType:"json",  
9.	//                     contentType: "application/json; charset=utf-8",//�˴��������ã������̨�޷���ֵ  
10.	                       success:function(data){  
11.	                           if(data.msg != ""){  
12.	                              alert( data.msg );  
13.	                           }  
14.	                       },  
15.	                       error:function(data){  
16.	                           alert("�����쳣���쳣ԭ��" + data + "��!");    
17.	                       }  
18.	                    });  
�����ִ�ֵ��
controller���ǲ�����ʵ��bean��bean������������
Spring MVC-controller
1.	@RequestMapping("add")  
2.	@ResponseBody//�˴�����ʡ�� ����ajax�޷���������ֵ  
3.	public Map<String,Object> add(@RequestBody DataVo dataVo){//@RequestBodyע�ⲻ��ʡ�ԣ������޷���ֵ  
4.	    Map<String,Object> resultMap = new HashMap<String, Object>();  
5.	    //ҵ���߼�����  
6.	    return resultMap;  
7.	}  

ʵ�� DataVo
1.	public class DataVo {  
2.	     
3.	   private BigDecimal[] nums;  
4.	    private String id;  
5.	  
6.	    public Long getId() {  
7.	        return id;  
8.	    }  
9.	  
10.	    public void setId(Long id) {  
11.	        this.id = id;  
12.	    }  
13.	  
14.	    public BigDecimal[] getNums() {  
15.	        return nums;  
16.	    }  
17.	  
18.	    public void setNums(BigDecimal[] nums) {  
19.	        this.nums = nums;  
20.	    }  
21.	  
22.	}  

jquery ajax  ��Ҫjquery json�Ĳ��  ����json���л���������ʹ����json.js
������
datatype:"json",  
contentType: "application/json; charset=utf-8",

1.	var params = {};  
2.	params.nums = [];  
3.	params.id = $("#id").val();//parmas.������ ע����ʵ��bean����������ͬ  
4.	var prices = document.getElementsByName("prices");//prices ��name="prices"һ��input��ǩ  
5.	for (var i = 0; i < prices.length; i++) {  
6.	    params.nums[i] =  prices[i].value;  
7.	}   
8.	$.ajax({   
9.	    type: "POST",   
10.	    url: "price/add",   
11.	    data:JSON.stringify(params),//json���л�   
12.	    datatype:"json", //�˴�����ʡ��   
13.	    contentType: "application/json; charset=utf-8",//�˴�����ʡ��   
14.	    success:function(data){   
15.	        alert(data);   
16.	    },   
17.	    error:function(data){  
18.	        alert(data)  
19.	    }   
20.	});  

