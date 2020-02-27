import java.util.LinkedList;
import java.util.Queue;

public class SolveQ1 {
	public static void updateChildren() {

		for (int i = 0; i < Ex1.var.size(); i++) {
			for (int j = 0; j < Ex1.var.get(i).parents.size(); j++) {
				Ex1.findNode(Ex1.var.get(i).parents.get(j).getData()).getChildren().add(Ex1.var.get(i));
			}
		}
	}

	public static boolean firstFormat(int i) {
		String first="";
		int ind=0;
		while(Ex1.Queries.get(i).charAt(ind)!='-') {
			first+=Ex1.Queries.get(i).charAt(ind);
			ind++;
		}
		ind++;
		String second="";
		while(Ex1.Queries.get(i).charAt(ind)!='|') {
			second+=Ex1.Queries.get(i).charAt(ind);
			ind++;
		}
		MyNode f = null;
		MyNode s = null; 
		f=Ex1.findNode(first);
		s=Ex1.findNode(second);
		boolean ans=false;
		if(Ex1.Queries.get(i).contains("=")) {               //with evidence
			ind++;
			for (int j =ind ; j < Ex1.Queries.get(i).length(); j++) {  
				String help="";
				while(Ex1.Queries.get(i).charAt(j)!='=') {
					help+=Ex1.Queries.get(i).charAt(j);
					j++;
				}
				for (int j2 = 0; j2 < Ex1.var.size(); j2++) {     //coloring nodes			

					if(Ex1.var.get(j2).getData().equals(help)) {
						//System.out.println(Ex1.var.get(j2));
						Ex1.var.get(j2).isColored=(true);
					}
				}
				j+=5;
			}
		}
		ans= BFS(f, s);
		clean();
		//System.out.println(ans);
		return ans;
	}
	public static boolean BFS (MyNode root, MyNode dist) {
		//System.err.println((Ex1.findNode("H").isColored));
		Queue<MyNode> q = new LinkedList<MyNode>();
		if (root == null||dist==null) 
			return true;
		q.add(root);
		push_All_Parents(q,root);
		push_All_Children(q,root);
		while (!q.isEmpty()) {	
			if(dist.visited)return false;
			MyNode n = (MyNode) q.remove();

			if(n.isColored&&n.fromChild) {
				continue;
			}
			else if(n.isColored&&n.fromParent)push_All_Parents(q, n);
			else if(n.fromChild) {
				push_All_Parents(q, n);
				push_All_Children(q, n);
			}
			else
				push_All_Children(q, n);
		}

		if(!dist.visited)return true;
		return false;
	}
	public static void push_All_Children(	Queue<MyNode> q ,MyNode n ) {
		for(int i=0;i<n.getChildren().size();i++) {
			//if(!n.getChildren().get(i).visited) {}
			n.getChildren().get(i).fromParent=true;
			n.getChildren().get(i).fromChild=false;
			if(n.numOfVisit<=4) {
				q.add(n.getChildren().get(i));
				n.numOfVisit++;
			}
			n.getChildren().get(i).visited=true;
			//System.out.println(n.getChildren().get(i));
		}
	}
	public static void clean() {
		for (int i = 0; i <Ex1.var.size(); i++) {
			Ex1.var.get(i).isColored=false;
			Ex1.var.get(i).visited=false;
			Ex1.var.get(i).fromChild=false;
			Ex1.var.get(i).fromParent=false;
			Ex1.var.get(i).numOfVisit=0;
		}
	}

	public static void push_All_Parents(Queue<MyNode> q ,MyNode n ) {
		for(int i=0;i<n.parents.size();i++) {
			n.parents.get(i).fromParent=false;
			n.parents.get(i).fromChild=true;
			if(n.numOfVisit<=4) {
				q.add(n.parents.get(i));
				n.numOfVisit++;
			}
			n.parents.get(i).visited=true;
		}
	}
}
