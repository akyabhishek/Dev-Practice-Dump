
public class Runner {

	public static void main(String[] args) {
		LinkedList linkedlist=new LinkedList();
		linkedlist.insert(5);
		linkedlist.insert(32);
		linkedlist.insert(44);
		linkedlist.insertAt(0,66);
		linkedlist.deleteAt(2);

		linkedlist.show();
	}
}
