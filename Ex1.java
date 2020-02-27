
import java.util.LinkedList;

public class Ex1 {
	static LinkedList<MyNode> var=new LinkedList<>();
	static LinkedList<String> Queries=new LinkedList<>();
	static String FILENAME="input.txt";
	public static MyNode findNode(String s) {
		for (int i = 0; i < var.size(); i++) {
			if(var.get(i).getData().equals(s))return var.get(i);
		}
		return null;
	}

	public static void main(String[] args) {
		ReadFile.read_from_file(FILENAME);                 //create list and pointers from the file
		ReadFile.Queries(FILENAME);                        // put all Queries in Queries list
		SolveQ1.updateChildren();
		Cpt.insertCpt();	  
		Cpt.remEq();
		Solve.solve();                                //solve Queries list
	}
}