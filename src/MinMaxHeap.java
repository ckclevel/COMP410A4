

public class MinMaxHeap {
	
	private int currentSize;
	private int[] arr;
	
	public MinMaxHeap(int capacity) {
		arr = new int[capacity +1];
		currentSize = 0;
	}
	
	public boolean isFull(){return currentSize == arr.length - 1;}
	public boolean isEmpty(){return currentSize == 0;}
	
	// COMPLETE THE FOLLOWING METHODS
	
	public void insert(int x) {
		if(isFull()) { return;}
		currentSize++;
		arr[currentSize] = x;
		if(isMinLevel(currentSize-1)) { 
			if(x > arr[currentSize/2] && currentSize > 1) { 
				swap(currentSize,currentSize/2);
				bubbleUpMax(currentSize/2);
			} else { 
				bubbleUpMin(currentSize);
			}
		} else { 
			if(x < arr[currentSize/2] && currentSize > 1) { 
				swap(currentSize,currentSize/2);
				bubbleUpMin(currentSize/2);
			} else { 
				bubbleUpMax(currentSize);
			}
		}
	}
		
	
	public int min() {
		if(currentSize == 0) { return 0;}
		return arr[1];
	}
	
	public int max() {
		if(currentSize == 0) { return 0; }
		if(currentSize == 1) { return arr[1];} 
		if(currentSize == 2) { return arr[2];}
		if(arr[2] > arr[3]) {
			return arr[2];
		} else return arr[3];
		
	}
	
	public int deleteMin() {
		int temp = arr[1];
		arr[1] = arr[currentSize];
		arr[currentSize] = 0;
		currentSize--;
		trickleDownMin(1);
		return temp;
	}
	
	public int deleteMax() {
		int max;
		if(arr[2] > arr[3]) { max = 2;} else max = 3; 
		int temp = arr[max];
		arr[max] = arr[currentSize];
		arr[currentSize] = 0;
		currentSize--;
		trickleDownMax(max);
		return temp;
	}
	
	//private methods below
	
	private boolean isMinLevel(int pos) {
		return (Double.doubleToRawLongBits(pos+1) >> 52) % 2 == 1;
	}
	
	private void swap(int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
	private void bubbleUpMax(int index) {
		int grandparent = index/4;
		if(grandparent != 0) {
			if(arr[index] > arr[grandparent]) {
				swap(index, grandparent);
				bubbleUpMax(grandparent);
			}
		}
	}
	
	private void bubbleUpMin(int index) {
		int grandparent = index/4;
		if(grandparent != 0) {
			if(arr[index] < arr[grandparent]) {
				swap(index, grandparent);
				bubbleUpMin(grandparent);
			}
		}
	}
	
	
	private boolean childOrGrandchild(int index, int test) { 
		if(index == 2*test || index == 2*test+1) {
			return true;
		} else return false;
	}
	
	private int findM(int index) { 
		int temp1=0, temp2=0;
		int child1 = 2*index;
		int child2 = 2*index+1;
		
		if(hasChildren(child1)) {
			if(2*child1+1 <= currentSize) { 
				if(arr[2*child1] <= arr[2*child1+1]) { 
					temp1 = 2*child1; 
				} else temp1 = (2*child1)+1;
			} else temp1 = 2*child1; 
		} else temp1 = child1;	
		if(hasChildren(child2)) { 
			if(2*child2+1 <= currentSize) {  
				if(arr[2*child2] <= arr[2*child2+1]) { 
					temp2 = 2*child2; 
				} else temp2 = (2*child2)+1;
			} else temp2 = 2*child2;
		} else temp2 = child1; 
		int temp3 = temp1;
		if(arr[temp1] > arr[temp2]) { temp3 = temp2; }
		if(arr[temp3] > arr[child1]) { temp3 = child1; }
		if(child2 <= currentSize) {
			if(arr[temp3] > arr[child2]) { temp3 = child2; }
		}
		return temp3;
	}
	
	private int findMM(int index) { 
		int child1 = 2*index;
		int child2 = 2*index+1;
		int temp1 = child1;
		int temp2 = child1;
		if(hasChildren(child1)) {
			if(2*child1+1 <= currentSize) {  
				if(arr[2*child1] > arr[2*child1+1]) { 
					temp1 = 2*child1; 
				} else temp1 = 2*child1+1;
			}
		}
		if(hasChildren(child2)) { 
			if(2*child2+1 <= currentSize) {  
				if(arr[2*child2] > arr[2*child2+1]) { 
					temp2 = 2*child2; 
				} else temp2 = 2*child2+1;
			}
		}
		int temp3 = temp1;
		if(arr[temp1] < arr[temp2]) { temp3 = temp2; }
		if(arr[temp3] < arr[child1]) { temp3 = child1; }
		if(child2 <= currentSize) {
			if(arr[temp3] < arr[child2]) { temp3 = child2; }
		}
		return temp3;
	}
	
	private void trickleDownMin(int index) {
		if(hasChildren(index)) {
			int m = findM(index);
			if(!childOrGrandchild(index, m)) {
				if(arr[m] < arr[index]) {
					swap(index, m);
					if(arr[m] > arr[m/2]) {
						swap(m, m/2);
					}
					trickleDownMin(m);
				}
			} else {
				if(arr[m] < arr[index]) {
					swap(index, m);
				}
			}
		}
	}
	
	private void trickleDownMax(int index) {
		if(hasChildren(index)) {
			int m = findMM(index);
			if(!childOrGrandchild(index, m)) {
				if(arr[m] > arr[index]) {
					swap(index, m);
					if(arr[m] < arr[m/2]) {
						swap(m, m/2);
					}
					trickleDownMax(m);
				}
			} else {
				if(arr[m] > arr[index]) {
					swap(index, m);
				}
			}
		}
	}
	
	private boolean hasChildren(int index) {
		if(2*index <= currentSize || 2*index+1 <= currentSize) { return true; }
		else return false;
	}
	
}
