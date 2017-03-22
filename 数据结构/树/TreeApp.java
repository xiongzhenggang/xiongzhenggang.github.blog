/**
 * 
 */
package xzg.com.structure;

import java.util.Stack;

/**
 * @author hasee
 * @TIME 2017��3��17��
 * ע��������غ�ʵ������
 */

class Node {
	int iDate;
	double fDate;
	Node leftChild;//�����
	Node rightChild;//�Ҷ���
	public void display(){
	//	չʾ��ǰ�ڵ��ֵ
		System.out.println("idate=��"+iDate+"fDate=��"+fDate);
	}
	}

class Tree{
	Node root ;
	public Tree(){
		root = null;
	}
	public  int treeLength = 0;
	//��ѯkeyֵ��Ӧ�ļ���
	public Node find(int key){
		Node currentNode = root;
		while(currentNode.iDate!=key){//ѭ�������
			if(key<currentNode.iDate){//��ѯ�Ľڵ�ȵ�ǰ��С������ǰ�ڵ������ӣ������С���Ҷ��ӣ�
				currentNode=currentNode.leftChild;
			}else{
				currentNode=currentNode.rightChild;
			}
			//�����ǰ�ڵ㣨û�и��ڵ㣩�����ڣ����ؿ�
			if(currentNode == null){
				return null;
			}
		}
		return currentNode;
	}
	//���뷽��
	public void insert(int id,double data){
		Node newNode = new Node();//����ڵ�ĳ�ʼ��
		newNode.iDate=id;
		newNode.fDate=data;
		if(root == null){//�ȼ������
			treeLength++;
			root = newNode;
		}else{
			//������ڸ��ڵ���Ӹ��ڵ㿪ʼ������Ѱ����
			Node currentNode = root;
			Node parent;//��Ϊ�������ڸ��ڵ��ǰ���²��룬������Ҫ�ȶ��常�ڵ㡣
			while(true){
				parent = currentNode;
				if(id<parent.iDate){//С����������
					currentNode = parent.leftChild;//��ǰ�ڵ��������ڵ���������
					if(currentNode == null){
						treeLength++;
						parent.leftChild = newNode;
						return ;
					}
				}else{//���Ҷ�������
					currentNode = parent.rightChild;
					if(currentNode == null){//�������Ҷ���Ϊ��ʱ�����µĽڵ�
						treeLength++;
						parent.rightChild = newNode;
						return ;
					}
				}
			}
		}
	}
	/*ɾ����Ϊ���ӣ������������
	 * ��һ�֣��ýڵ���Ҷ�ڵ㣨û���ӽڵ㣩
	 * �ڶ��֣��ýڵ���һ���ӽڵ�
	 * �����֣��ýڵ��������ӽڵ㣬���
	 */	
	public boolean delete(int key){
		//ɾ��Ҳ�Ӹ��ڵ�
		Node currentNode = root;
		Node parent = root;
		boolean isLeftChild =true;//���ڱ�ʾɾ����������ӻ����Ҷ���
		 
		while(currentNode.iDate != key){
			parent = currentNode;
			if(currentNode.iDate < key){//����
				isLeftChild = true;
				currentNode = parent.leftChild;
			}else{
				isLeftChild = false;
				currentNode = parent.rightChild;
			}
			if(currentNode == null){
				return false;//û���ҵ�ֱ�ӷ���null
			}
		}
			//��һ�֣����û���ӽڵ��ֱ��ɾ��
			if(currentNode.leftChild == null && currentNode.rightChild == null){
				if(currentNode == root){//ֻ��һ�����ڵ㣬��ֱ��ɾ��
					root =null;
					}else if(isLeftChild){//������ӵ����
					parent.leftChild = null;
					}else{
					parent.rightChild = null;
				}
				//�ڶ������ֻ��һ���ڵ㣬�Ҷ��ӻ��������
			}else if(currentNode.rightChild == null){
					//ֻ������ӵ����
				if(currentNode == root){
					root = currentNode.leftChild;
				}else if(isLeftChild){
					parent.leftChild = currentNode.leftChild;
				}else{
					parent.rightChild = currentNode.leftChild;
				}
				//ֻ���Ҷ��ӵ����
			}else if(currentNode.leftChild == null){
				if(currentNode == root){
					root = currentNode.rightChild;
				}else if(isLeftChild){
					parent.leftChild = currentNode.rightChild;
				}else{
					parent.rightChild = currentNode.rightChild;
				}
			}else{
			//�����������Ҷ��Ӷ��е����
				Node successor = getSuccessor(currentNode);//��ȡ����Լ���̵�����
				if(currentNode == root){//Ҫɾ����Ϊrootʱ
					root = successor;
				}else if(isLeftChild){//�������
					parent.leftChild = successor;
				}else{//���Ҷ���
					parent.rightChild = successor;
					successor.leftChild = currentNode.leftChild;
				}
			}
			return true;//�ɹ�
		}
	//���������ڻ�ȡҪɾ���ڵ�ĺ�̽ڵ㣨���Ǳȵ�ǰҪɾ���ڵ������������С��һ�������û�о���Ҫɾ���ڵ���Ҷ��ӣ�
	private Node getSuccessor(Node delNode){
		Node successorParent = delNode;//��¼���ڵ�
		Node successor = delNode;//��ΪҪ���ص���С�ڵ�
		Node currentNode = delNode.rightChild;//���ڼ�¼��ǰ�ڵ�
		while(currentNode != null){
			successorParent = successor;
			successor = currentNode;//�����ǰ�ڵ�ĵ������Ϊnull����˽ڵ�Ϊ��̽ڵ�
			currentNode = currentNode.leftChild;//���������
		}
		//֪����ǰ�ڵ�Ϊ�յ�ʱ������������ײ�
		if(successor != delNode.rightChild){//���Ҫ���صĽڵ㲻�ǣ�Ҫɾ���ڵ���Ҷ���
			//�����������㷵�ؽڵ���ɾ���ڵ�����������С�ڵ㡣�жϴ˺�̽ڵ�
			successorParent.leftChild = successor.rightChild;//����֪��û����ڵ�ʱ������̵��Ҷ����滻���ԭ����λ�ã���ʹnull��
			successor.rightChild = delNode.rightChild;//��ԭ����ɾ���ڵ���������ӵ������
		}
		return successor;//���صĺ�̰��������������
	}
	//���ֱ������ṹ��ǰ�����򡢺��� �Ը���λ������
	public  void traverse(int traverseTye){
		switch(traverseTye){
			case 1:System.out.println("\npreOder traverse1");
						preOder(root);
						break;
			case 2:System.out.println("\ninOrder traverse2");
						inOrder(root);
						break;
			case 3:System.out.println("\npostOrder traverse3");
						postOrder(root);
						break;
		}
	}
	//ǰ�򣬸�-����-����
	private void preOder(Node localRoot){
		if(localRoot != null){
			System.out.println(localRoot.iDate+"  ");
			preOder(localRoot.leftChild);
			preOder(localRoot.rightChild);
		}
	}
	//������-����-����
	private void inOrder(Node localRoot){
		if(localRoot != null){
			preOder(localRoot.leftChild);
			System.out.println(localRoot.iDate+"  ");
			preOder(localRoot.rightChild);
			
		}
	}
	//������-����-����
	private void postOrder(Node localRoot){
		if(localRoot != null){
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.println(localRoot.iDate+"  ");
		}
	}
	//
	public void display(){
		Stack<Node> globalStack = new Stack<Node>();
		globalStack.push(root);
		int nBlank = 32;
		boolean isRowEmpty = false;
		System.out.println("..........................................................");
		while(isRowEmpty == false){
			Stack<Node> localStack =new Stack<Node>();
			isRowEmpty = true;
			for(int j=0;j<nBlank;j++)
				System.out.print(" ");
			while(!globalStack.isEmpty()){
				Node tmp = (Node)globalStack.pop();
				if(tmp != null){
					System.out.println(tmp.iDate);
					localStack.push(tmp.leftChild);
					localStack.push(tmp.rightChild);
					if(tmp.leftChild != null ||tmp.rightChild != null){
						isRowEmpty = false;
					}else{
						System.out.print("--");
						localStack.push(null);
						localStack.push(null);				}
				}
				for(int j=0;j<nBlank*2+2;j++){
					System.out.print("  ");
				}
				nBlank/=2;
				while(localStack.isEmpty() == false){
					globalStack.push(localStack.pop());
				}
			}
			System.out.println(".............................................");
		}
	}
}
public class TreeApp{
	public static void main(String[] args){
		Tree theTree = new Tree();
		theTree.insert(5,43);
		theTree.insert(3, 5.5);
		theTree.insert(7, 5.5);
		theTree.insert(4, 5.5);
		theTree.insert(7, 5.5);
		theTree.insert(8, 5.5);
		theTree.insert(9, 5.5);
		theTree.traverse(1);
	}
	
}
