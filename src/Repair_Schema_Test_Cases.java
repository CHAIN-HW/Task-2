import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Responsible for testing the element of CHAIn
 * that after calling SPSM and filtering the results
 * will try to create a repaired schema based on the 
 * match data between the source and target schemas
 */
public class Repair_Schema_Test_Cases {

	private Call_SPSM spsmCall;
	private Best_Match_Results filterRes;
	private Repair_Schema getRepairedSchema;
	
	private ArrayList<Match_Struc> finalRes;
	private String target, source;
	private static int counter;
	
	//for writing results
	private static File testRes;
	private PrintWriter fOut;
	private static boolean alreadyWritten;
	
	@BeforeClass
	public static void beforeAll(){
		alreadyWritten = false;
		counter=1;
		try{
			testRes = new File("outputs/testing/Schema_Repair_Tests.txt");
			testRes.createNewFile();
			
			new PrintWriter("outputs/testing/Schema_Repair_Tests.txt").close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	@Before
	public void setup(){
		spsmCall = new Call_SPSM();
		filterRes = new Best_Match_Results();
		getRepairedSchema = new Repair_Schema();
		
		try{
			fOut = new PrintWriter(new FileWriter(testRes,true));
			
			if(alreadyWritten==false){
				fOut.write("Testing Results for Call_SPSM.java & Best_Match_Results.java\n\n");
				alreadyWritten = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test11(){
		source="auto(brand,name,color)";
		target="car(year,brand,colour)";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		finalRes=spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 2);
		
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.0+" & limit: "+2+" \n");
		
		fOut.write("Expected Result: repaired schema == 'car(colour,brand)' \n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				//we can test repaired schema
				String rSchema = finalRes.get(0).getRepairedSchema();
				fOut.write("Actual Result: repaired schema == '" + rSchema + "' \n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}
	
	@Test
	public void test56(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="conference(paper(title,document(date(day,month,year),writer(name(first,second)))))";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		finalRes=spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 2);
	
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.0+" & limit: "+2+" \n");
		
		fOut.write("Expected Result: repaired schema == 'conference(paper(title,document(date(day,month,year),writer(name(first,second)))))' \n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				//we can test repaired schema
				Match_Struc result = finalRes.get(0);
				
				fOut.write("Actual Result: repaired schema == '" + result.getRepairedSchema() + "' \n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}
	
	@Test
	public void test57(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="conference(paper(title,document(category(day,month,year),writer(name(first,second)))))";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 2);
	
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.0+" & limit: "+2+" \n");
		
		fOut.write("Expected Result: repaired schema == 'conference(paper(title,document(writer(name(first,second)))))' \n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				//we can test repaired schema
				String rSchema = finalRes.get(0).getRepairedSchema();
				fOut.write("Actual Result: repaired schema == '" + rSchema + "' \n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}
	
	
	@After
	public void cleanUp(){
		fOut.close();
	}
}
