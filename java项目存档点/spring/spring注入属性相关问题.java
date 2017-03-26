ע�����Կ���ͨ������@Autowiredע�⣬����@Resource��@Qualifier��@PostConstruct��@PreDestroy��ע����ʵ�֡�
��ע������ǽӿڣ�ע���������ʵ���ࣩ
1.1. ʹ��ע����ǰ����������ע�����Ե�
���ʵ�֣�
1.	public class UserManagerImpl implements UserManager {     
2.	    private UserDao userDao;     
3.	    public void setUserDao(UserDao userDao) {     
4.	        this.userDao = userDao;     
5.	    }     
6.	    ...     
7.	}   
�����ļ���
1.	< bean id="userManagerImpl" class="com.kedacom.spring.annotation.service.UserManagerImpl"> 
2.	    < property name="userDao" ref="userDao" /> 
3.	< /bean> 
4.	< bean id="userDao" class="com.kedacom.spring.annotation.persistence.UserDaoImpl"> 
5.	    < property name="sessionFactory" ref="mySessionFactory" /> 
6.	< /bean>   
1.2. ����@Autowiredע�⣨���Ƽ�ʹ�ã�����ʹ��@Resource��
���ʵ�֣��Գ�Ա�������б�ע��
1.	public class UserManagerImpl implements UserManager {  
2.	    @Autowired 
3.	    private UserDao userDao;  
4.	    ...  
5.	}   
���ߣ��Է������б�ע��
1.	public class UserManagerImpl implements UserManager {  
2.	    private UserDao userDao;  
3.	    @Autowired 
4.	    public void setUserDao(UserDao userDao) {  
5.	        this.userDao = userDao;  
6.	    }  
7.	    ...  
8.	}   
�����ļ�
1.	< bean id="userManagerImpl" class="com.kedacom.spring.annotation.service.UserManagerImpl" /> 
2.	< bean id="userDao" class="com.kedacom.spring.annotation.persistence.UserDaoImpl"> <!��ʵ�ֶ��������ע���ʱ���Ҳ�����ص�bean-->
3.	    < property name="sessionFactory" ref="mySessionFactory" /
4.	< /bean>  
@Autowired���ԶԳ�Ա�����������͹��캯�����б�ע��������Զ�װ��Ĺ������������ֲ�ͬʵ�ַ�ʽ�У�@Autowired�ı�עλ�ò�ͬ�����Ƕ�����Spring�ڳ�ʼ��userManagerImpl���beanʱ���Զ�װ��userDao������ԣ������ǣ���һ��ʵ���У�Spring��ֱ�ӽ�UserDao���͵�Ψһһ��bean��ֵ��userDao�����Ա�������ڶ���ʵ���У�Spring����� setUserDao��������UserDao���͵�Ψһһ��beanװ�䵽userDao������ԡ�

1.3. ��@Autowired��������
Ҫʹ@Autowired�ܹ�����������Ҫ�������ļ��м������´���
1.	< bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor" />  
1.4. @Qualifier
@Autowired�Ǹ������ͽ����Զ�װ��ġ�������������У������Spring�������д��ڲ�ֹһ��UserDao���͵�beanʱ���ͻ��׳�BeanCreationException�쳣�����Spring�������в�����UserDao���͵�bean��Ҳ���׳� BeanCreationException�쳣�����ǿ���ʹ��@Qualifier���@Autowired�������Щ���⡣
a. ���ܴ��ڶ��UserDaoʵ��
1.	@Autowired 
2.	public void setUserDao(@Qualifier("userDao") UserDao userDao) {  
3.	    this.userDao = userDao;  
4.	}  
������Spring���ҵ�idΪuserDao��bean����װ�䡣
b. ���ܲ�����UserDaoʵ��
1.	@Autowired(required = false)  
2.	public void setUserDao(UserDao userDao) {  
3.	    this.userDao = userDao;  
4.	}   
1.5. @Resource��JSR-250��׼ע�⣬�Ƽ�ʹ����������Springר�е�@Autowiredע�⣩
Spring ����֧���Լ������@Autowiredע�⣬��֧�ּ�����JSR-250�淶�����ע�⣬���Ƿֱ���@Resource��@PostConstruct�Լ�@PreDestroy��
@Resource�������൱��@Autowired��ֻ����@Autowired��byType�Զ�ע�룬��@ResourceĬ�ϰ� byName�Զ�ע����ˡ�@Resource�����������ǱȽ���Ҫ�ģ��ֱ���name��type��Spring��@Resourceע���name���Խ���Ϊbean�����֣���type���������Ϊbean�����͡��������ʹ��name���ԣ���ʹ��byName���Զ�ע����ԣ���ʹ��type����ʱ��ʹ��byType�Զ�ע����ԡ�����Ȳ�ָ��nameҲ��ָ��type���ԣ���ʱ��ͨ���������ʹ��byName�Զ�ע����ԡ�
@Resourceװ��˳��
1. ���ͬʱָ����name��type�����Spring���������ҵ�Ψһƥ���bean����װ�䣬�Ҳ������׳��쳣
2. ���ָ����name������������в������ƣ�id��ƥ���bean����װ�䣬�Ҳ������׳��쳣
3. ���ָ����type��������������ҵ�����ƥ���Ψһbean����װ�䣬�Ҳ��������ҵ�����������׳��쳣
4. �����û��ָ��name����û��ָ��type�����Զ�����byName��ʽ����װ�䣨��2�������û��ƥ�䣬�����Ϊһ��ԭʼ���ͣ�UserDao������ƥ�䣬���ƥ�����Զ�װ�䣻
1.6. @PostConstruct��JSR-250��
�ڷ����ϼ���ע��@PostConstruct����������ͻ���Bean��ʼ��֮��Spring����ִ�У�ע��Bean��ʼ��������ʵ����Bean����װ��Bean�����ԣ�����ע�룩����
����һ�����͵�Ӧ�ó����ǣ�������Ҫ��Bean��ע��һ���丸���ж�������ԣ��������޷���д��������Ի����Ե�setter����ʱ���磺
1.	public class UserDaoImpl extends HibernateDaoSupport implements UserDao {  
2.	    private SessionFactory mySessionFacotry;  
3.	    @Resource 
4.	    public void setMySessionFacotry(SessionFactory sessionFacotry) {  
5.	        this.mySessionFacotry = sessionFacotry;  
6.	    }  
7.	    @PostConstruct 
8.	    public void injectSessionFactory() {  
9.	        super.setSessionFactory(mySessionFacotry);  
10.	    }  
11.	    ...  
12.	}   
����ͨ��@PostConstruct��ΪUserDaoImpl�ĸ����ﶨ���һ��sessionFactory˽�����ԣ�ע���������Լ������sessionFactory�������setSessionFactory����Ϊfinal�����ɸ�д����֮�����ǾͿ���ͨ������ super.getSessionFactory()�����ʸ������ˡ�
1.7. @PreDestroy��JSR-250��
�ڷ����ϼ���ע��@PreDestroy����������ͻ���Bean��ʼ��֮��Spring����ִ�С��������ǵ�ǰ��û����Ҫ�õ����ĳ��������ﲻ��ȥ��ʾ�����÷�ͬ@PostConstruct��
1.8. ʹ��< context:annotation-config />������
Spring2.1�����һ���µ�context��Schema�����ռ䣬�������ռ��ע�������������ļ����롢������֯��ȹ����ṩ�˱�ݵ����á�����֪��ע�ͱ����ǲ������κ�����ģ������ṩԪ������Ϣ��ҪʹԪ������Ϣ���������ã������ø�������ЩԪ���ݵĴ���������������
AutowiredAnnotationBeanPostProcessor�� CommonAnnotationBeanPostProcessor���Ǵ�����Щע��Ԫ���ݵĴ�����������ֱ����Spring�����ļ��ж�����Щ Bean�ԵñȽϱ�׾��SpringΪ�����ṩ��һ�ַ����ע����ЩBeanPostProcessor�ķ�ʽ�������< context:annotation-config />��
1.	< beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context" 
2.	    xsi:schemaLocation="http://www.springframework.org/schema/beans  
3.	    http://www.springframework.org/schema/beans/spring-beans-2.5.xsd  
4.	    http://www.springframework.org/schema/context  
5.	    http://www.springframework.org/schema/context/spring-context-2.5.xsd"> 
6.	    < context:annotation-config /> 
7.	< /beans>   
< context:annotationconfig />����ʽ����Spring����ע��AutowiredAnnotationBeanPostProcessor�� CommonAnnotationBeanPostProcessor�� PersistenceAnnotationBeanPostProcessor�Լ� RequiredAnnotationBeanPostProcessor��4��BeanPostProcessor��
����mybitis��springע������
һ��	��ʹ��mybitisʱ������ͨ��ʹ��
<mapper  namespace="com.lin.dao.LeaveRepository">  
������dao�ӿ���mapper��xml�ļ���sql�����󶨣�����ע�⣺��Ҫdao�ӿ��еķ�����Ҫ��xml������һ�·����޷�ʶ������:
  <select id="findOne" parameterType="java.lang.Long"  resultMap="org.activiti.web.simple.webapp.model.Leave">  
    select * from leave_f  where id = #{id} </select>
��dao�ӿ��з�����findOne
����springMvc��	���ýӿڵ�ʵ������ͨ��ʹ�ýӿڵ�ʵ���ࣨ������Ϊ springMVC ����Ľӿڣ������Ե�������service��ֱ��ע��ӿ���ʵ�ֳ־ò��ʱ�����޷�ע��ģ�����Ҫ�ƶ���ʵ���࣬���������ַ�ʽ��1��ʹ��jee��ע�ⷽʽ
@Resource(name="leaveRepositoryImple")
	private LeaveRepository leaveRepository;
2��ʹ��springmvc��ע��
@Autowired
   @Qualifier("leaveRepositoryImple ")
private LeaveRepository leaveRepository;
���߶�Ҫָ����ʵ���ࡣ
�����ڹ�����Ӧ�����£�
1��	����dao�ӿ�LeaveRepository.java
����save��Leave leave��
2��	�ӿ�ʵ����LeaveRepositoryImple.java
ʵ�ַ���save
	@Resource
	private LeaveRepository leaveRepository;
	public void save(Leave leave) {
		leaveRepository.save(leave);   }
ע��ӿ�bean��ʹspring�ܹ�����ʵ�������ã��������Ϊ��ȡ������ָ���쳣��mybatis��spring���ϣ�ͨ��spring����mapper�ӿڡ�ʹ��mapper��ɨ�����Զ�ɨ��mapper�ӿ���spring��
