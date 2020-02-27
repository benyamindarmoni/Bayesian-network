import java.text.DecimalFormat;
import java.util.LinkedList;

public class SolveQ2 {
	static int numOfMul=0;
	static int numOfAdd=0;
	public static String secondFormat(int i) {
		LinkedList<MyNode> currentNode=new LinkedList<>();
		LinkedList<MyNode> hiden=new LinkedList<>();
		LinkedList<String> values=new LinkedList<>();
		String name="";
		String value="";
		boolean flag=false;
		int ind=2;
		for (int j = 2; j < Ex1.Queries.get(i).length(); j++) {
			if(Ex1.Queries.get(i).charAt(j)=='=') {
				currentNode.add(Ex1.findNode(name));

				ind=j;
				break;
			}
			name+=Ex1.Queries.get(i).charAt(j);
		}

		name="";
		ind++;
		if(ind<Ex1.Queries.get(i).length()-1)
			while(Ex1.Queries.get(i).charAt(ind)!='|') {
				if(ind>=Ex1.Queries.get(i).length()-1)break;
				value+=Ex1.Queries.get(i).charAt(ind);
				ind++;
			}
		values.add(value);
		value="";
		ind++;
		if(ind<Ex1.Queries.get(i).length()-1)
			while(Ex1.Queries.get(i).charAt(ind)!=')') {
				if(ind>=Ex1.Queries.get(i).length()-1)break;
				if(Ex1.Queries.get(i).charAt(ind)=='=') {
					currentNode.add(Ex1.findNode(name));	

					name="";
					ind++;
					while(Ex1.Queries.get(i).charAt(ind)!=',') {
						value+=Ex1.Queries.get(i).charAt(ind);
						ind++;
						if(Ex1.Queries.get(i).charAt(ind)==')') {
							flag=true;
							break;
						}
					}
					values.add(value);
					value="";
					if(flag) 
						break;
					ind++;
				}
				else {
					name+=Ex1.Queries.get(i).charAt(ind);
					ind++;
				}
			}
		ind+=2;
		name="";
		for (int j = ind; j < Ex1.Queries.get(i).length(); j++) {             //adding hiden var to hiden list
			if(Ex1.Queries.get(i).charAt(j)=='-') {
				hiden.add(Ex1.findNode(name));
				name="";
			}
			else {
				name+=Ex1.Queries.get(i).charAt(j);
			}
		}
		hiden.add(Ex1.findNode(name));

		boolean haveAns=true;
		for (int j = 1; j < currentNode.size(); j++) {
			if(!currentNode.get(0).sonOf(currentNode.get(j))) {
				haveAns=false;		
				break;
			}
		}
		if(haveAns&&hiden.size()==1) {                                                         //from cpt tables
			double help=Double.parseDouble(new DecimalFormat(".#####").format((fromCpt(currentNode,values))));
			String ans=arrengeLen(help)+","+0+","+0;
			//	System.out.println(ans);
			return ans;
		}
		else {		                                              //manipulation- join emu'    
			RemoveIrrelevantNodes(hiden);
			arrengeCpt(hiden,currentNode,values);
			for (int j = 0; j < hiden.size(); j++) {
				elimination(hiden.get(j),hiden);
			}
			lastCalculation(hiden,currentNode);
			double help =Double.parseDouble(new DecimalFormat(".#####").format((normalisation(hiden.getLast(),values))));
			String ans=	arrengeLen(help)+","+numOfAdd+","+numOfMul;
			reset();
			return ans;		
		}
	}

	public static void elimination(MyNode n,LinkedList<MyNode>hiden){
		int[] ind;
		if(n.getChildren().size()==2) { 
			ind=indOfcommonFather(n.getChildren().get(0),n.getChildren().get(1));
			mult1(n.getChildren().get(0),n.getChildren().get(1),ind,n.numofvalue);
		}
		//(after elimination or not)
		ind=new int[2];
		ind[0]=n.cpt[0].length-2;
		ind[1]=  parentNumber(n.getChildren().get(0),n);                                             // parent number
		mult1(n,n.getChildren().get(0),ind,n.numofvalue);
		addition(n,ind[0]);
		n.getChildren().get(0).cpt=n.cpt;
	}

	public static void mult1(MyNode a, MyNode b,int[] ind,int valOfParent){
		String [][]ans=extendCpt(a,b,valOfParent);
		int col=0;
		int line=0;
		for (int i = 0; i < a.cpt.length; i++) {
			for (int j = 0; j < b.cpt.length; j++) {
				if((a.cpt[i][ind[0]].equals(b.cpt[j][ind[1]])&&(!a.cpt[i][a.cpt[i].length-1].equals("x"))))  {
					for (int j2 = 0; j2 < a.cpt[i].length-1; j2++) {
						ans[line][col]=a.cpt[i][j2];
						col++;
					}
					for (int j2 = 0; j2 < b.cpt[0].length-1; j2++) {
						if(j2!=ind[1]) {
							ans[line][col]=b.cpt[j][j2];
							col++;
						}
					}
					double h1=Double.parseDouble(a.cpt[i][a.cpt[i].length-1]);
					double h2=Double.parseDouble(b.cpt[j][b.cpt[j].length-1]);
					double h3=h1*h2;				
					numOfMul++;
					ans[line][ans[0].length-1]=Double.toString(h3);
					line++;
					col=0;
				}	
			}
		}
		a.cpt=ans;
		b.cpt=ans;
	}

	private static String[][] extendCpt(MyNode a, MyNode b, int valOfFather) {
		// TODO Auto-generated method stub
		int line=(a.cpt.length*b.cpt.length)/valOfFather;
		int col=a.cpt[0].length+b.cpt[0].length-2;
		String[][] newCpt=new String[line][col];
		return newCpt;
	}

	public static void addition(MyNode n,int irrelevent){  
		double ans=0.0;
		for (int i = 0; i < n.cpt.length; i++) {
			if((n.cpt[i][n.cpt[i].length-1]).equals("x"))continue;
			for (int j = i+1; j < n.cpt.length; j++) {
				if(eq(n.cpt[i],n.cpt[j],irrelevent)&&!(n.cpt[i][n.cpt[i].length-1]).equals("x")) { 
					ans+=Double.parseDouble(n.cpt[j][n.cpt[j].length-1]); 
					numOfAdd++;
					n.cpt[j][n.cpt[j].length-1]="x";
				}
			}
			ans+=Double.parseDouble(n.cpt[i][n.cpt[i].length-1]);
			n.cpt[i][n.cpt[i].length-1]=Double.toString(ans);
			ans=0;


		}


		halfCpt(n,"x");


		lessCol(n,irrelevent);

	}

	private static void RemoveIrrelevantNodes( LinkedList<MyNode> hiden) {
		// TODO Auto-generated method stub

		for (int i = 0; i < hiden.size(); i++) {
			if(hiden.get(i).getChildren().size()==0) {
				for (int j = 0; j <hiden.get(i).parents.size(); j++) {
					hiden.get(i).parents.get(j).getChildren().remove(hiden.get(i));	
				}
				hiden.remove(hiden.get(i));
			}
		}
	}

	private static void lessCol(MyNode n,int col) { //remove one irrelevant col from cpt
		// TODO Auto-generated method stub
		int colmn=0;
		String[][] help=new String[n.cpt.length][n.cpt[0].length-1];
		for (int i = 0; i < n.cpt.length; i++) {
			for (int j = 0; j <n.cpt[0].length; j++) {
				if(j==col) {
					continue;
				}
				else {
					help[i][colmn]=n.cpt[i][j];
					colmn++;
				}
			}
			colmn=0;
		}
		n.cpt=help;
	}

	private static void halfCpt(MyNode myNode,String val) {                        //remove down half cpt
		// TODO Auto-generated method stub
		int ind=0;
		String help[][]=new String [myNode.cpt.length/myNode.numofvalue][myNode.cpt[0].length];
		if(val.equals("x")){
			for (int i = 0; i <  myNode.cpt.length; i++) {
				if(!myNode.cpt[i][myNode.cpt[0].length-1].equals(val)) {
					help[ind]=myNode.cpt[i];
					ind++;
				}
				if(ind>=help.length)break;
			}
		}
		else {

			for (int i = 0; i <  myNode.cpt.length; i++) {
				if(myNode.cpt[i][myNode.cpt[0].length-2].contains(val)) {
					help[ind]=myNode.cpt[i];
					ind++;
				}
				if(ind>=help.length)break;
			}
		}
		myNode.cpt=help;
	}

	private static int parentNumber(MyNode myNode, MyNode n) {
		// TODO Auto-generated method stub
		int ans= -1;
		for (int i = 0; i < myNode.parents.size(); i++) {
			if(myNode.parents.get(i).getData().equals(n.getData())) {
				ans=i;
				break;
			}
		}
		return ans;
	}

	private static boolean eq(String[] strings, String[] strings2,int col) {
		// TODO Auto-generated method stub
		boolean ans=true;

		for (int j = 0; j < strings2.length-1; j++) {
			if(j==col)continue;
			if(!strings[j].equals(strings2[j])) {
				ans=false;
				break;
			}
		}
		return ans;
	}

	public static int[] indOfcommonFather(MyNode a,MyNode b) {
		int ans[]=new int [2];
		for (int i = 0; i < a.parents.size(); i++) {
			for (int j = 0; j <  b.parents.size(); j++) {
				if(a.parents.get(i)==b.parents.get(j)) {
					ans[0]=i;
					ans[1]=j;
				}
			}
		}
		return ans;
	}
	public static int findvalue(MyNode n,String val) {
		int ans=0;
		for (int i = 0; i < n.values.size(); i++) {
			if(n.values.get(i).equals(val)) {
				ans=i;
				break;
			}
		}
		return ans;
	}

	private static void arrengeCpt(LinkedList<MyNode> hiden, LinkedList<MyNode> currentNode,LinkedList<String> values) {
		// TODO Auto-generated method stub
		int colind=0,lineind=0;
		for (int i = 1; i < currentNode.size(); i++) {
			halfCpt(currentNode.get(i),values.get(i));
			lessCol(currentNode.get(i),currentNode.get(i).cpt[0].length-2 );
		}
		for (int i = 0; i <hiden.size(); i++) {
			for (int j = 1; j < currentNode.size(); j++) {
				if(hiden.get(i).sonOf(currentNode.get(j))) {
					//build cpt only with necessary val of parent and then remove irrelevant lines
					int col=parentNumber(hiden.get(i),currentNode.get(j));
					hiden.get(i).parents.remove(currentNode.get(j));
					String val=values.get(i);
					String newCpt[][]=new String[hiden.get(i).cpt.length/2][hiden.get(i).cpt[0].length-1];
					for (int l = 0; l < hiden.get(i).cpt.length; l++) {
						if(hiden.get(i).cpt[l][col].equals(val)) {
							for (int k = 0; k < hiden.get(i).cpt[l].length; k++) {
								if(k==col)continue;
								newCpt[lineind][colind]=hiden.get(i).cpt[l][k];
								colind++;
							}
							lineind++;
							colind=0;
						}
					}
					hiden.get(i).cpt=newCpt;
				}
			}
		}
	}

	private static double normalisation(MyNode last, LinkedList<String> values) {

		double ans=Double.parseDouble(last.cpt[0][last.cpt[0].length-1]);
		//Double.parseDouble(last.cpt[][last.cpt[0].length-1]);
		for (int i = 1; i < last.cpt.length; i++) {
			ans+=Double.parseDouble(last.cpt[i][last.cpt[0].length-1]);
			numOfAdd++;
		}
		double alpha=1.0/ans;
		ans=alpha*Double.parseDouble(last.cpt[findvalue(last, values.get(0))][last.cpt[0].length-1]);
		return ans;
	}

	private static double fromCpt(LinkedList<MyNode> currentNode, LinkedList<String> values) {
		LinkedList<String> values2=new LinkedList<String>();
		for (int i = 0; i < currentNode.get(0).parents.size(); i++) {
			for (int j = 0; j < currentNode.size(); j++) {
				if(currentNode.get(j)==currentNode.get(0).parents.get(i))values2.add(values.get(j));
			}
		}
		values2.add(values.getFirst());
		boolean flag=true;
		int line=currentNode.get(0).cpt.length;
		int col=currentNode.get(0).cpt[0].length;
		for (int i = 0; i <line; i++) {
			for (int j = 0; j < values2.size(); j++) {
				if(!values2.get(j).equals(currentNode.get(0).cpt[i][j])) {
					flag=false;
					break;
				}
			}
			if(flag) {
				return Double.parseDouble(currentNode.get(0).cpt[i][col-1]);
			}
			else {
				flag=true;
			}
		}
		return 0;
	}

	private static void reset() {                          //reset all var
		// TODO Auto-generated method stub
		numOfAdd=numOfMul=0;
		Ex1.var.clear();
		ReadFile.read_from_file(Ex1.FILENAME);  
		SolveQ1.updateChildren();
		Cpt.insertCpt();
		Cpt.remEq();
	}

	private static void lastCalculation(LinkedList<MyNode> hiden, LinkedList<MyNode> currentNode) {

		// TODO Auto-generated method stub
		int []indx=new int[2];
		indx[0]=0;
		indx[1]=0;
		MyNode h = null;				
		for (int j = 0; j < currentNode.size(); j++) {
			if(currentNode.get(j).parents.size()==0) {
				h=currentNode.get(j);
				break;
			}
		}
		if(h==currentNode.get(0))
			mult1(hiden.getLast(),h,indx,2);
		else {
			mult2(hiden.getLast(),h);
		}
	}

	private static void mult2(MyNode last, MyNode h) {
		// TODO Auto-generated method stub
		for (int i = 0; i <last.cpt.length; i++) {
			double a=Double.parseDouble(last.cpt[i][last.cpt[i].length-1]);
			double b=Double.parseDouble(h.cpt[0][h.cpt[0].length-1]);
			double ans=a*b;
			last.cpt[i][last.cpt[i].length-1]=Double.toString(ans);
		}
	}
	private static String arrengeLen(double help) {
		// TODO Auto-generated method stub
		String fix=Double.toString(help);
		int i2=fix.indexOf('.');
		i2=fix.length()-i2-1;
		while(i2<5)	{			
			fix+='0';
			i2++;
		}
		return fix;
	}
}
