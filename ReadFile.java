import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class ReadFile {
	public static void read_from_file(String filename) {
		try {
			FileReader fr = new FileReader(filename);  
			BufferedReader br = new BufferedReader(fr); 
			String str = br.readLine(); 
			if(str!=null) {
				str = br.readLine(); 
				if(str.contains("Variables:")) {
					str=str.substring(11);
					StringTokenizer st = new StringTokenizer(str,",");  
					while (st.hasMoreTokens()) {
						MyNode help=new MyNode (st.nextToken());
						Ex1.var.add(help);  
					}
				}
				str = br.readLine(); 
				//line 3
				int size=Ex1.var.size();
				int linenum=3;
				String name="";
				for (int j = 0; j < size; j++) {
					str = br.readLine(); //4
					if(str.contains("Var")) {
						str = br.readLine(); 
						if(str.contains("Values:")) {
							int count =1;
							name="";

							for (int i = 8; i <str.length(); i++) {
								if(str.charAt(i)==',') {
									count++;
									Ex1.var.get(j).values.add(name);
									//System.out.println(name);
									name="";
								}
								else {
									name+=str.charAt(i);
								}
								
							}
							Ex1.var.get(j).values.add(name);
							Ex1.var.get(j).setValue(count) ;
							str = br.readLine();
							linenum++;
							
						}
						if(str.contains("Parents:")) {
							String help=str.replaceAll("Parents: ", "");
							if(!help.contains("none")) {
								StringTokenizer st = new StringTokenizer(help,",");  
								while (st.hasMoreTokens()) {
									//System.out.println(st.nextToken());
									Ex1.var.get(j).parents.add(Ex1.findNode(st.nextToken()));
								}
							}
							str = br.readLine(); 
							linenum++;
						}
						
						if(str.contains("CPT:")) {
							str = br.readLine();
							linenum++;
							Ex1.var.get(j).probability=new String[(Ex1.var.get(j).getValues()*Cpt.getParentsVal(Ex1.var.get(j)))/Ex1.var.get(j).getValues()];
							//System.out.println(Cpt.getParentsVal(Ex1.var.get(j)));
							for (int i = 0; i < Ex1.var.get(j).probability.length; i++) {
								Ex1.var.get(j).probability[i]=new String(str);
								str = br.readLine();
								linenum++;
							}
						}
					}		
					else {
						j--;
					}
				}
			}
			br.close();
		}
		catch(Exception e) {
			e.getStackTrace();
		}
	}
	public static void Queries(String filename) {
		try {
			FileReader fr = new FileReader(filename);  
			BufferedReader br = new BufferedReader(fr); 
			String str = br.readLine(); 
			if(str!=null) {
				while(!str.contains("Queries")){
					str = br.readLine();
				}
				str = br.readLine();
				while(str!=null) {
					Ex1.Queries.add(str);
					str = br.readLine();
				}
			}
		}
		catch(Exception e) {
			e.getStackTrace();
		}
	}
}
