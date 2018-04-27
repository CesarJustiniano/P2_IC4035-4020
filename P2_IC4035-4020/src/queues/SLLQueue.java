package queues;
import interfaces.Queue;

public class SLLQueue<E> implements Queue<E> {
	// inner class for nodes in singly linked lists
		private static class Node<E> {    
			private E element; 
			private Node<E> next; 
		}	
		private Node<E> first, last;   // references to first and last node
		private int size; 
		
		public SLLQueue() {           // initializes instance as empty queue
			first = last = null; 
			size = 0; 
		}
		public int size() {
			return size;
		}
		public boolean isEmpty() {
			return size == 0;
		}
		public E first() {
			if (isEmpty()) return null;
			return first.element; 
		}
		public E last(){
			if (isEmpty()) return null;
			return last.element;
		}
		public E dequeue() {
			if (isEmpty()) return null;	
			
			Node<E> ntr = first;
			first = first.next;
			
			if(size == 1){
				first = last = null;
			}
			
			size--;
			E etr = ntr.element;
			ntr.element = null;
			ntr = null;
			
			return etr;
		}
		public E dequeueLast(){
			if (isEmpty()) return null;	
			
			Node<E> current = first;
			Node<E> ntr = last;
			
			while(current.next != last){
				current = current.next;
			}
			
			last = current;
			
			if(size == 1){
				first = last = null;
			}
			
			size--;
			E etr = ntr.element;
			ntr.element = null;
			ntr = null;
			
			return etr;
		}
		public void enqueue(E e) {
			Node<E> nuevo = new Node<>();
			nuevo.element = e;
			
			if (size == 0){
				first = last = nuevo; 
			}
			else { 
				last.next = nuevo;
				last = nuevo;
			}
			size++; 
		}
		
		public SLLQueue<E> clone() throws CloneNotSupportedException {			
			
			SLLQueue<E> copy = new SLLQueue<E>();
			Node<E> current = first;
			E etr;
			
			while(current != null){
				etr = current.element;
				copy.enqueue(etr);
				current = current.next;
			}
			
			return copy;
		}
		
		//For testing
		public void toPrint(){
			if(isEmpty()){
				System.out.println("The Queue is empty.");
			}
			else{
				Node<E> current = first;
				while(current != null){
					System.out.println(current.element);
					current = current.next;
				}
			}
		}
}
