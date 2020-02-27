
public class Cpt {


	public static void insertCpt() {   //insert the values and prob' to cpt

		for (int i = 0; i < Ex1.var.size(); i++) {
			int colInd=0;
			int lineInd=-1;
			int counter=0;
			double complet=1.0;
			Ex1.var.get(i).cpt=new String[Ex1.var.get(i).getValues()*getParentsVal(Ex1.var.get(i))][2+Ex1.var.get(i).parents.size()];
			//System.out.println(Ex1.var.get(i).cpt.length+" "+Ex1.var.get(i).cpt[0].length);
			empty_cpt(Ex1.var.get(i).cpt);
			for (int j = 0; j < Ex1.var.get(i).probability.length; j++) {
				
				colInd=0;
				lineInd++;
				counter=0;
				for (int j2 = 0; j2 <Ex1.var.get(i).probability[j].length(); j2++) {
					if(Ex1.var.get(i).probability[j].charAt(j2)=='=')counter++;
					if(Ex1.var.get(i).probability[j].charAt(j2)!=','&&counter<=1) {

						Ex1.var.get(i).cpt[lineInd][colInd]+=Ex1.var.get(i).probability[j].charAt(j2);
						if(j2+1==Ex1.var.get(i).probability[j].length()) {
							//System.out.println(Ex1.var.get(i).cpt[lineInd][colInd]);
							complet-=Double.parseDouble(Ex1.var.get(i).cpt[lineInd][colInd]);
						}
					}
					else if(counter<=1) {
						if(Ex1.var.get(i).cpt[lineInd][colInd].charAt(0)=='0') {
							complet-=Double.parseDouble(Ex1.var.get(i).cpt[lineInd][colInd]);
						//	System.out.println(Ex1.var.get(i).cpt[lineInd][colInd]);

						}

						colInd++;
					}

					else {                       //j2='='
						lineInd++;
						for (int k = 0; k <Ex1.var.get(i).parents.size(); k++) {
							Ex1.var.get(i).cpt[lineInd][k]=Ex1.var.get(i).cpt[lineInd-1][k];

						}
						counter--;colInd-=2;
					}
				}
				colInd=-1;int l=getParentsVal(Ex1.var.get(i))*(Ex1.var.get(i).numofvalue-1);
				for (int k = 0; k <Ex1.var.get(i).parents.size(); k++) {
					Ex1.var.get(i).cpt[l+lineInd-(j+1)*(Ex1.var.get(i).numofvalue-2) ][k]=Ex1.var.get(i).cpt[lineInd][k];
					colInd=k;
				}
				colInd++;
				Ex1.var.get(i).cpt[l+lineInd-(j+1)*(Ex1.var.get(i).numofvalue-2) ][colInd]=Ex1.var.get(i).values.get(Ex1.var.get(i).values.size()-1);
				colInd++;
				Ex1.var.get(i).cpt[l+lineInd-(j+1)*(Ex1.var.get(i).numofvalue-2) ][colInd]=Double.toString(complet);
				complet=1.0;
			}
		}

	}
	private static void empty_cpt(String[][] cpt) {      //empty cpt
		// TODO Auto-generated method stub
		for (int i = 0; i < cpt.length; i++) {
			for (int j = 0; j < cpt[i].length; j++) {
				cpt[i][j]="";
			}
		}
	}
	public static int getParentsVal(MyNode myNode) { //returns the multiply of parents value
		// TODO Auto-generated method stub
		int ans=1;
		for (int i = 0; i < myNode.parents.size(); i++) {
			ans*=myNode.parents.get(i).getValues();
		}
		return ans;
	}
	public static void remEq() {                          //fixing cpt
		for (int i = 0; i <Ex1.var.size(); i++) {
			for (int i1 = 0; i1 < Ex1.var.get(i).cpt.length; i1++) {
				for (int j = 0; j <  Ex1.var.get(i).cpt[0].length; j++) {
					if(Ex1.var.get(i).cpt[i1][j].contains("=")) {
						Ex1.var.get(i).cpt[i1][j]=	Ex1.var.get(i).cpt[i1][j].replaceAll("=", "");
					}
					if(Ex1.var.get(i).cpt[i1][j].contains(" ")) {
						Ex1.var.get(i).cpt[i1][j]=	Ex1.var.get(i).cpt[i1][j].replaceAll("\\s","");
					}
				}
			}
		}
	}
}


