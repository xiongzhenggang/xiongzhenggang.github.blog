Hibernate��domain�����ļ�
 
�������ĳ����������ͣ�
 
Oracle����<generator class="increment" />
 
 
SQLServer 2000����<generator class="identity" />
 
�D�D�D�D�D�D�D�D�D����SQLServer 2000�ШD�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D
<id name="userid" type="java.lang.Integer">
            <column name="userid" />
            <generator class="identity" />
 </id>
�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�DOracle�ШD�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D
<id name="userid" type="java.lang.Integer">
            <column name="userid" />
            <generator class="increment" />
 </id>

hibernate�ṩ�˲����Զ��������������Ķ��ֲ���,������incrementΪ��˵�������÷�: 
1�������ݿ��н�������������ΪID,����Ϊvarchar2���ַ����ͣ� 
2����**.hbm.xml��hibernateӳ���ļ������������� 
<class name="com.jat.bisarea.ho.Test" table="BA_VVTEST"> 
<id name="id" type="int" column="ID"> 
//�þ�ָ��ʹ��hibernate�Դ���increment������������ 
<generator class="increment"/> 
</id> 
<property name="uname" type="java.lang.String" column="UNAME"/> 
</class> 
3����java�ļ��жԱ����Ӽ�¼ʱ��ֻ����ӳ�ID��������ֶΣ�Ȼ��save���ɣ����java�������£� 
Session s = HibernateUtil.currentSession(); 
Transaction tx = s.beginTransaction(); 
Test test = new Test(); 
String uname = httpServletRequest.getParameter("uname"); 
test.setUname(uname); 
//ֻ���uname����set��id��hibernate���� 
s.save(test); 
tx.commit(); 
4��ʹ���������Եķ�������һ�£�����hilo��seqhilo�� 
Generator Ϊÿ�� POJO ��ʵ���ṩΨһ��ʶ��һ�����������ʹ�á�native����class ��ʾ�������������ӿ�net.sf.hibernate.id.IdentifierGenerator ʵ�ֵ�ĳ��ʵ�������а����� 
��assigned�� 
�������ⲿ���������ɣ��� save() ֮ǰָ��һ���� 
��hilo�� 
ͨ��hi/lo �㷨ʵ�ֵ��������ɻ��ƣ���Ҫ��������ݿ����ֶ��ṩ��λֵ��Դ�� 
��seqhilo�� 
��hilo ���ƣ�ͨ��hi/lo �㷨ʵ�ֵ��������ɻ��ƣ���Ҫ���ݿ��е� Sequence��������֧�� Sequence �����ݿ⣬��Oracle�� 
��increment�� 
��������ֵ˳��������˷�ʽ��ʵ�ֻ���Ϊ�ڵ�ǰӦ��ʵ����ά��һ���������Ա����ŵ�ǰ�����ֵ��֮��ÿ����Ҫ����������ʱ�򽫴�ֵ��1��Ϊ���������ַ�ʽ���ܲ����������ǣ������ڼ�Ⱥ��ʹ�á� 
��identity�� 
�������ݿ��ṩ���������ɻ��ơ���DB2��SQL Server��MySQL �е��������ɻ��ơ� 
��sequence�� 
�������ݿ��ṩ�� sequence ���������������� Oralce �е�Sequence�� 
��native�� 
�� Hibernate ����ʹ�õ����ݿ������жϲ��� identity��hilo��sequence ����һ����Ϊ�������ɷ�ʽ�� 
��uuid.hex�� 
�� Hibernate ����128 λ UUID �㷨����16 ������ֵ��������Գ���32 ���ַ�����ʾ����Ϊ������ 
��uuid.string�� 
��uuid.hex ���ƣ�ֻ�����ɵ�����δ���б��루����16��������Ӧ���� PostgreSQL ���ݿ��С� 
��foreign�� 
ʹ������һ��������Ķ���ı�ʶ����Ϊ������
1. native 
����õġ����Ա�֤������ݿ�֮��Ŀ���ֲ�ԡ������п�����ʱ��������⣺��Ϊ���ܿ���idֵ�������ݵ����ʱ������޷�����ҵ����Ҫ��
2. sequence
���ֵط����Խ��������nativeʱ������⣬������Ҫ�����ݿ���һЩ�������á�
3. uuid
�����Ͽ��Ա�֤������ݿ����ɵ�ID��һ��ϵͳ��Ψһ����ʱ��ͦ���á�����Ч����΢�͵㣨��ʵ������ν����
4. increment
��ò�Ҫ�á����������������ʡ��޸����ݿ⣬�ǾͿֲ��ˡ�
5. assigned
û��ô�ù���һ�㲻�����ֹ���ʽ��ֵ���������������������
6.  foreign
��one-to-one��ʱ����ܻ��õ���
7. ��ʹ�����ݿ��Զ�����������ʱ��SQL����������ͬ����Щ���ݿⲻ��������������ЩҪ������ֶα���Ϊnull����Щ����ȫ������д��������ֵ��
�������������
����1
������С����
Date d=new Date();
System.out.println(d.getTime());//����͵õ���Ψһ�ı�š�
����2
���ȣ���������3�����ʣ�1.Ψһ��  2.�����Ʋ��� 3.Ч����

Ψһ�ԺͲ����Ʋ��Բ���˵�ˣ�Ч������ָ����Ƶ����ȥ���ݿ��ѯ�Ա����ظ���
����������Щ������ͬʱ�����Ż�Ҫ�㹻�Ķ̡�
����java�¶��ƵĶ��������ɷ�ʽ���£�
int r1=(int)(Math.random()*(10));//����2��0-9�������
int r2=(int)(Math.random()*(10));
long now = System.currentTimeMillis();//һ��13λ��ʱ���
String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);// ����ID
//String ������Ѿ��ṩ�˽�����������̬ת���� String �� static ���� Ҳ���� String.valueOf() ����������صķ���
Ŀǰ������������������ͬһ΢���ύ�����ظ��ĸ���Ϊ1%
����3
�����ڲ���32λ�ľ���ȫ��Ψһ�ı�ţ�������hibernate��uuid���ɷ�ʽ
package com.anxin.utils;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * ��������hibernate��uuid 32λ��������
 * 
 * @version: V1.0
 */
public class UUIDGenerator {

	private static final int IP;

	public static int IptoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	static {
		int ipadd;
		try {
			ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}
	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	public UUIDGenerator() {
	}
	public static int getJVM() {
		return JVM;
	}
	public static short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}
	public static int getIP() {
		return IP;
	}
	public static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	public static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private final static String sep = "";

	public static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	public  static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public static String generate() {
		return String.valueOf(new StringBuffer(36).append(format(getIP())).append(sep)
				.append(format(getJVM())).append(sep)
				.append(format(getHiTime())).append(sep)
				.append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString());
	}
	public static void main(String args[]){
		System.out.println(UUIDGenerator.generate());
	}
}


Hibernate�е��ӳټ���
�ӳټ��ػ�����Ϊ�˱���һЩ��ν�����ܿ�����������ģ���ν�ӳټ��ؾ��ǵ���������Ҫ���ݵ�ʱ�򣬲�����ִ�����ݼ��ز�������Hibernate���ṩ�˶�ʵ�������ӳټ����Լ��Լ��ϵ��ӳټ��أ�������Hibernate3�л��ṩ�˶����Ե��ӳټ��ء��������Ǿͷֱ������Щ������ӳټ��ص�ϸ�ڡ� 
A��ʵ�������ӳټ��أ� 
������ʵ�����ʹ���ӳټ��أ�����Ҫ��ʵ���ӳ�������ļ��н�����Ӧ�����ã�������ʾ�� 
<hibernate-mapping> 
<class name=��net.ftng.entity.user�� table=��user�� lazy=��true��> 
���� 
</class> 
</hibernate-mapping> 
ͨ����class��lazy��������Ϊtrue��������ʵ����ӳټ������ԡ����������������Ĵ��룺 
User user=(User)session.load(User.class,��1��);��1�� 
System.out.println(user.getName());��2�� 
�����е�(1)��ʱ��Hibernate��û�з�������ݵĲ�ѯ��������Ǵ�ʱͨ��һЩ���Թ���(����JBuilder2005��Debug����)���۲��ʱuser������ڴ���գ����ǻᾪ��ķ��֣���ʱ���صĿ�����User$EnhancerByCGLIB$$bede8986���͵Ķ��󣬶���������Ϊnull,������ô���£����ǵ�ǰ����������session.load()�������᷵��ʵ�����Ĵ�����������������صĶ������;���User����Ĵ����������Hibernate��ͨ��ʹ��CGLIB,��ʵ�ֶ�̬����һ��Ŀ�����Ĵ�������󣬲����ڴ���������а���Ŀ�������������Ժͷ����������������Ծ�����ֵΪnull��ͨ����������ʾ���ڴ���գ����ǿ��Կ�����ʱ������User�����ǰ����ڴ�������CGLIB$CALBACK_0.target�����У����������е���2����ʱ����ʱ����user.getName()��������ʱͨ��CGLIB����Ļص����ƣ�ʵ���ϵ���CGLIB$CALBACK_0.getName()�����������ø÷���ʱ��Hibernate�����ȼ��CGLIB$CALBACK_0.target�����Ƿ�Ϊnull�������Ϊ�գ������Ŀ������getName���������Ϊ�գ���ᷢ�����ݿ��ѯ����������������SQL��䣺select * from user where id=��1��;����ѯ���ݣ�������Ŀ����󣬲��ҽ�����ֵ��CGLIB$CALBACK_0.target�����С� 
������ͨ��һ���м�������Hibernateʵ����ʵ����ӳټ��أ�ֻ�е��û�����������ʵ��������ԵĶ���ʱ���������ᷢ�����ݿ��ѯ����������ʵ����ӳټ�������ͨ���м��������ɵģ�����ֻ��session.load()�����Ż�����ʵ���ӳټ��أ���Ϊֻ��session.load()�����Ż᷵��ʵ����Ĵ�������� 
B�� �������͵��ӳټ��أ� 
��Hibernate���ӳټ��ػ����У���Լ������͵�Ӧ�ã���������Ϊ�ش�ģ���Ϊ���п���ʹ���ܵõ�����ȵ���ߣ�Ϊ��Hibernate�����˴�����Ŭ�������а�����JDK Collection�Ķ���ʵ�֣�������һ�Զ�����У�������������ɹ��������Set���ϣ�������java.util.Set���ͻ��������ͣ�����net.sf.hibernate.collection.Set���ͣ�ͨ��ʹ���Զ��弯�����ʵ�֣�Hibernateʵ���˼������͵��ӳټ��ء�Ϊ�˶Լ�������ʹ���ӳټ��أ����Ǳ��������������ǵ�ʵ����Ĺ��ڹ����Ĳ��֣� 

<hibernate-mapping> 
<class name=��net.ftng.entity.User�� table=��user��> 
��.. 
<set name=��addresses�� table=��address�� lazy=��true�� inverse=��true��> 
<key column=��user_id��/> 
<one-to-many class=��net.ftng.entity.Arrderss��/> 
</set> 
</class> 
</hibernate-mapping> 
ͨ����<set>Ԫ�ص�lazy��������Ϊtrue�������������͵��ӳټ������ԡ����ǿ�����Ĵ��룺 
User user=(User)session.load(User.class,��1��); 
Collection addset=user.getAddresses(); (1) 
Iterator it=addset.iterator(); (2) 
while(it.hasNext()){ 
Address address=(Address)it.next(); 
System.out.println(address.getAddress()); 
} 
������ִ�е�(1)��ʱ����ʱ�����ᷢ��Թ������ݵĲ�ѯ�����ع������ݣ�ֻ�����е�(2)��ʱ�����������ݶ�ȡ�����ŻῪʼ����ʱHibernate����ݻ����з������������������������ҷ���������ʵ����� 
��������������һ��ȫ�µĸ�������������������������Ƚ���һ��ʲô��������������Hibernate�жԼ������ͽ��л���ʱ���Ƿ������ֽ��л���ģ����Ȼ��漯��������ʵ���id�б�Ȼ�󻺴�ʵ�������Щʵ������id�б�������ν��������������������������ʱ�����û���ҵ���Ӧ��������������ʱ�ͻ�һ��select SQL��ִ�У���÷������������ݣ�������ʵ����󼯺Ϻ�����������Ȼ�󷵻�ʵ�����ļ��ϣ����ҽ�ʵ������������������Hibernate�Ļ���֮�С���һ���棬����ҵ���Ӧ�������������������������ȡ��id�б�Ȼ�����id�ڻ����в��Ҷ�Ӧ��ʵ�壬����ҵ��ʹӻ����з��أ����û���ҵ����ڷ���select SQL��ѯ�����������ǿ���������һ�����⣬���������ܻ�����ܲ���Ӱ�죬����Ǽ������͵Ļ�����ԡ���������������ü������ͣ� 
<hibernate-mapping> 
<class name=��net.ftng.entity.User�� table=��user��> 
��.. 
<set name=��addresses�� table=��address�� lazy=��true�� inverse=��true��> 
<cache usage=��read-only��/>
<key column=��user_id��/> 
<one-to-many class=��net.ftng.entity.Arrderss��/> 
</set> 
</class> 
</hibernate-mapping> 
��������Ӧ����<cache usage=��read-only��/>���ã�����������ֲ��������ü������ͣ�Hibernate��ֻ��������������л��棬������Լ����е�ʵ�������л��档��������������������Ĵ��룺 
User user=(User)session.load(User.class,��1��); 

Collection addset=user.getAddresses(); 
Iterator it=addset.iterator(); 
while(it.hasNext()){ 
Address address=(Address)it.next(); 
System.out.println(address.getAddress()); 
} 
System.out.println(��Second query������); 
User user2=(User)session.load(User.class,��1��); 
Collection it2=user2.getAddresses(); 
while(it2.hasNext()){ 
Address address2=(Address)it2.next(); 
System.out.println(address2.getAddress()); 
} 
������δ��룬��õ��������������� 
Select * from user where id=��1��; 
Select * from address where user_id=��1��; 
Tianjin 
Dalian 
Second query���� 
Select * from address where id=��1��; 

Select * from address where id=��2��; 
Tianjin 
Dalian 
���ǿ��������ڶ���ִ�в�ѯʱ��ִ����������address��Ĳ�ѯ������Ϊʲô��������������Ϊ����һ�μ���ʵ��󣬸��ݼ������ͻ�����Ե����ã�ֻ�Լ����������������˻��棬����û�жԼ����е�ʵ�������л��棬�����ڵڶ����ٴμ���ʵ��ʱ��Hibernate�ҵ��˶�Ӧʵ����������������Ǹ�������������ȴ�޷��ڻ������ҵ���Ӧ��ʵ�壬����Hibernate�����ҵ���������������������select SQL�Ĳ�ѯ��������������˶����ܵ��˷ѣ��������ܱ�����������أ����Ǳ���Լ��������е�ʵ��Ҳָ��������ԣ���������Ҫ���¶Լ������ͽ������ã� 
<hibernate-mapping> 
<class name=��net.ftng.entity.User�� table=��user��> 
��.. 
<set name=��addresses�� table=��address�� lazy=��true�� inverse=��true��> 
<cache usage=��read-write��/> 
<key column=��user_id��/> 
<one-to-many class=��net.ftng.entity.Arrderss��/> 
</set> 
</class> 
</hibernate-mapping> ��ʱHibernate��Լ��������е�ʵ��Ҳ���л��棬���������������ٴ���������Ĵ��룬����õ��������µ������ 
Select * from user where id=��1��; 
Select * from address where user_id=��1��; 
Tianjin 
Dalian 
Second query���� 
Tianjin 
Dalian 
��ʱ���������и��������������в�ѯ��SQL��䣬��Ϊ��ʱ����ֱ�Ӵӻ����л�ü��������д�ŵ�ʵ�����
C�� �����ӳټ��أ� 
��Hibernate3�У�������һ���µ����ԡ������Ե��ӳټ��أ����������Ϊ��ȡ�����ܲ�ѯ�ṩ�������Ĺ��ߡ���ǰ�����ǽ������ݶ����ȡʱ����User��������һ��resume�ֶΣ����ֶ���һ��java.sql.Clob���ͣ��������û��ļ�����Ϣ�������Ǽ��ظö���ʱ�����ǲ��ò�ÿһ�ζ�Ҫ��������ֶΣ������������Ƿ������Ҫ�����������ִ����ݶ���Ķ�ȡ���������ܴ�����ܿ�������Hibernate2�У�����ֻ��ͨ������ǰ�潲���������ܵ�����ϸ�֣����ֽ�User�࣬�����������⣨�������һ�ڵ���������������Hibernate3�У����ǿ���ͨ�������ӳټ��ػ��ƣ���ʹ���ǻ��ֻ�е�����������Ҫ��������ֶ�ʱ����ȥ��ȡ����ֶ����ݵ�������Ϊ�����Ǳ��������������ǵ�ʵ���ࣺ 
<hibernate-mapping> 
<class name=��net.ftng.entity.User�� table=��user��> 
���� 
<property name=��resume�� type=��java.sql.Clob�� column=��resume�� lazy=��true��/> 
</class> 
</hibernate-mapping> 
ͨ����<property>Ԫ�ص�lazy��������true���������Ե��ӳټ��أ���Hibernate3��Ϊ��ʵ�����Ե��ӳټ��أ�ʹ��������ǿ������ʵ�����Class�ļ�����ǿ������ͨ����ǿ������ǿ����CGLIB�Ļص������߼�������ʵ���࣬�������ǿ��Կ������Ե��ӳټ��أ�����ͨ��CGLIB��ʵ�ֵġ�CGLIB��Apache��һ����Դ���̣���������Բ���java����ֽ��룬�����ֽ�������̬�������Ҫ�������󡣸������������������������Ĵ��룺
String sql=��from User user where user.name=��zx�� ��; 
Query query=session.createQuery(sql); (1) 
List list=query.list(); 
for(int i=0;i<list.size();i++){ 
User user=(User)list.get(i); 
System.out.println(user.getName()); 
System.out.println(user.getResume()); (2) 
} 
��ִ�е�(1)��ʱ���������������µ�SQL��䣺 
Select id,age,name from user where name=��zx��; 
��ʱHibernate�����Userʵ�������з��ӳټ������Զ�Ӧ���ֶ����ݣ���ִ�е�(2)��ʱ���������������µ�SQL��䣺 
Select resume from user where id=��1��; 
��ʱ�ᷢ���resume�ֶ����������Ķ�ȡ������
