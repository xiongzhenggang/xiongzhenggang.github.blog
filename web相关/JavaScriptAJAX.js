XMLHttpRequest ������ AJAX �Ĺؼ���
���� XMLHttpRequest ����
��ͬ�������ʹ�ò�ͬ�ķ��������� XMLHttpRequest ����
Internet Explorer ʹ�� ActiveXObject��
���������ʹ����Ϊ XMLHttpRequest �� JavaScript �ڽ�����
Ҫ�˷�������⣬����ʹ����μ򵥵Ĵ��룺
var XMLHttp=null
if (window.XMLHttpRequest)
  {
  XMLHttp=new XMLHttpRequest()
  }
else if (window.ActiveXObject)
  {
  XMLHttp=new ActiveXObject("Microsoft.XMLHTTP")
  }
������ͣ�
1.	���ȴ���һ����Ϊ XMLHttpRequest ����ʹ�õ� XMLHttp ������������ֵ����Ϊ null��
2.	Ȼ����� window.XMLHttpRequest �����Ƿ���á����°汾�� Firefox, Mozilla, Opera �Լ� Safari ������У��ö����ǿ��õġ�
3.	������ã�����������һ���¶���XMLHttp=new XMLHttpRequest()
4.	��������ã����� window.ActiveXObject �Ƿ���á��� Internet Explorer version 5.5 �����ߵİ汾�У��ö����ǿ��õġ�
5.	������ã�ʹ����������һ���¶���XMLHttp=new ActiveXObject()

