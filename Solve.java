import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Solve {
	public static void solve() {
		File file = new File("output.txt");
		//Create the file
		try {
			if (file.createNewFile())
			{
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Write Content
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			for (int i = 0; i < Ex1.Queries.size(); i++) {
				if(!Ex1.Queries.get(i).contains("P")) {                         //first  format 
					boolean ans=SolveQ1.firstFormat(i);
					if(ans)
						writer.write("yes\n");
					else
						writer.write("no\n");
				}
				else {                                           //second  format 
					writer.write(SolveQ2.secondFormat(i));
					writer.write(("\n"));

				}
			}
			writer.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
