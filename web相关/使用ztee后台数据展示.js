��������jQueryztree��Դ�뼰��������ļ�
<script  src="js/jQuery/jquery-2.1.3.min.js"  type="text/javascript"></script> 
<script src="js/ztree/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="js/ztree/jquery.ztree.excheck.min.js" type="text/javascript"></script>
<link href="js/ztree/zTreeStyle.css"  type="text/css" rel="stylesheet">

<script type="text/javascript">
/**
 * ҳ���ʼ��
 */
$(document).ready(function(){
  onLoadZTree();
});
/**
 * �������νṹ����
 */
 var url="${ctx}/treeList.do";
function onLoadZTree(){
  var treeNodes;
  $.ajax({
    async:false,//�Ƿ��첽
    cache:false,//�Ƿ�ʹ�û���
    type:'POST',//����ʽ��post
    dataType:'json',//���ݴ����ʽ��json
    url:url,//�����action·��
    error:function(){
      //����ʧ�ܴ�����
      alert('�ף�����ʧ�ܣ�');
    },
    success:function(data){
      //console.log(data);
      //����ɹ�������
      treeNodes = data;//�Ѻ�̨��װ�õļ�Json��ʽ����treeNodes
    }
  });
  var zTree;
  var setting = {
    view: {
      dblClickExpand: false,//˫���ڵ�ʱ���Ƿ��Զ�չ�����ڵ�ı�ʶ
      showLine: true,//�Ƿ���ʾ�ڵ�֮�������
      fontCss:{'color':'black','font-weight':'bold'},//������ʽ����
      selectedMulti: false //�����Ƿ�����ͬʱѡ�ж���ڵ�
    },
    check:{
      //chkboxType: { "Y": "ps", "N": "ps" },
      chkStyle: "checkbox",//��ѡ������
      enable: true //ÿ���ڵ����Ƿ���ʾ CheckBox 
    },
    data: {
      simpleData: {//������ģʽ
        enable:true,
        idKey: "id",
        pIdKey: "pId",
        rootPId: ""
      },
      key{
    	  checked: "id",
    	  url:"url"
      }
    },
    callback: {
      beforeClick: function(treeId, treeNode) {
        zTree = $.fn.zTree.getZTreeObj("user_tree");
        if (treeNode.isParent) {
          zTree.expandNode(treeNode);//����Ǹ��ڵ㣬��չ���ýڵ�
        }else{
          zTree.checkNode(treeNode, !treeNode.checked, true, true);//������ѡ���ٴε���ȡ����ѡ
        }
      }
    }
  };
  var t = $("#user_tree");
  t = $.fn.zTree.init(t,setting,treeNodes);
}
</script>
     <style type="text/css">
     a{
     font-size: 20px;
     }
     </style>
     <script type="text/javascript">
     $(function(){
         $("#reg01").click(function(){
            $.get("showUser.do",function(data){
            	  window.location.href = "showUser.do";  
            });
     });
     });
     </script>


<div class="zTreeDemoBackgroundleft">
    <ul id="user_tree" class="ztree" style="border: 1px solid #617775;overflow-y: scroll;height: 500px;"></ul>
  </div> 