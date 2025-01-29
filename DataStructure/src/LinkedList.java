
public class LinkedList {
	Node head;
	
	public void insert(int data) {
		Node node=new Node();
		node.data=data;
		if(head==null) {
			head=node;
		}
		else {
			Node temp=head;
			while(temp.next!=null) {
				temp=temp.next;
			}
			temp.next=node;
		}
	}
	public void show() {
		Node node=head;
		while(node.next!=null) {
			System.out.print(node.data+"-->");
			node=node.next;
		}
		System.out.print(node.data);
	}
	public void insertAtStart(int data){
		Node node =new Node();
		node.data=data;
		node.next=head;
		head=node; 
	}
	public void insertAt(int index,int data){
		Node node=new Node();
		node.data=data;
		if(index==0){
			insertAtStart(data);
		}
		else{
		Node temp=head;
		for(int i=0;i<index-1;i++){
			temp=temp.next;
		}
		node.next=temp.next;
		temp.next=node;
	}
	}
	public void deleteAt(int index){
		if(index==0){
			head=head.next;
		}
		else{
			Node temp=head;
			for(int i=0;i<index-1;i++){
				temp=temp.next;
			}
			Node temp1=temp.next;
			temp.next=temp1.next;
			System.out.println(temp1.data+" is deleted.");

		}
	}
}
