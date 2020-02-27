import java.util.ArrayList;
import java.util.List;

public class MyNode {
	//public static String[] values;
	private List<MyNode> children = new ArrayList<MyNode>();
	List<MyNode> parents = new ArrayList<MyNode>();
	int numofvalue;	
	List<String> values = new ArrayList<String>();
	String probability[];
	String cpt[][];
	private String data ;
	
	
	boolean visited;
	boolean fromChild;
	boolean fromParent;
	int numOfVisit=0;
	boolean isColored;
	
	public MyNode(String s) {
		data=s;
		visited=false;
		isColored=false;
		fromChild=false;
		fromParent=false;
	}
	public int getValues() {
		return numofvalue;
	}
	public List<MyNode> getChildren() {
		return children;
	}

	public void addParent(MyNode parent) {
		parents.add(parent);
	}
	public void setValue(int i) {
		numofvalue=i;

	}
	public void addChild(MyNode child) {
		children.add(child);
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}

	public int sum_of_val_of_parent() {
		int ans=1;
		for (int i = 0; i < parents.size(); i++) {
			MyNode s=parents.get(i);
			for (int j = 0; j < Ex1.var.size(); j++) {
				if(Ex1.var.get(j).getData().equals(s)) {
					ans*=Ex1.var.get(j).numofvalue;
					break;
				}
			}
		}
		return ans;
	}

	public void setColor(boolean b) {
		// TODO Auto-generated method stub
		isColored=b;	
	}
	public String toString() {
		String ans="name: "+getData();
		return ans;
	}
	public boolean sonOf(MyNode n) {              //if 'this' is the son of n return true
		for (int i = 0; i < parents.size(); i++) {
			if(parents.get(i).data.equals(n.data))return true;
		}
		return false;
	}
	public boolean has(String string) {
		// TODO Auto-generated method stub
		for (int i = 0; i < cpt.length; i++) {
			for (int j = 0; j < cpt[0].length; j++) {
				if(cpt[i][j]==null) {
					return true;
				}
			}
		}
		return false;
	}
}

