import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class Create_Query_Test_Cases {

	private Call_SPSM spsmCall;
	private Best_Match_Results filterRes;
	private Repair_Schema getRepairedSchema;
	private Create_Query createQuery;
	
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
			testRes = new File("outputs/testing/Create_Queries_Tests.txt");
			testRes.createNewFile();
			
			new PrintWriter("outputs/testing/Create_Queries_Tests.txt").close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	@Before
	public void setup(){
		spsmCall = new Call_SPSM();
		filterRes = new Best_Match_Results();
		getRepairedSchema = new Repair_Schema();
		createQuery = new Create_Query();
		
		try{
			fOut = new PrintWriter(new FileWriter(testRes,true));
			
			if(alreadyWritten==false){
				fOut.write("Testing Results for Create_Query.java\n\n");
				alreadyWritten = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test //Sepa
	public void test11(){
		source="waterBodyPressures(dataSource,identifiedDate,affectsGroundwater,waterBodyId)";
		target="waterBodyPressures(dataSource,identifiedDate,affectsGroundwater,waterBodyId)";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		finalRes=spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
		
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
		
		fOut.write("Test "+counter+"\n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){	
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				Match_Struc current = finalRes.get(0);
					
				fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
				fOut.write("Expected Result:\n\n" + 
					
						"PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
						+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
						+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
						+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
						+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
						+ "SELECT *  \n"
						+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressures.n3>\n"
						+ "WHERE { ?id sepaw:dataSource ?dataSource;\n"
						+ "sepaw:identifiedDate  ?identifiedDate  ;\n"
						+ "sepaw:affectsGroundwater ?affectsGroundwater ;\n"
						+ "sepaw:waterBodyId ?waterBodyId .}"
						+ "\n\n");
				
				fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}
	
	@Test //Sepa
	public void test12(){
		source="water(timePeriod, geo, measure, resource)";
		target="water(timePeriod, geo, measure, resource)";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		finalRes=spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
		
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
		
		fOut.write("Test "+counter+"\n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){	
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				Match_Struc current = finalRes.get(0);
					
				fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
				fOut.write("Expected Result:\n\n" + 
					
						"PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
						+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
						+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
						+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
						+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
						+ "SELECT *  \n"
						+ "FROM <queryData/sepa/sepa_datafiles/water.n3>\n"
						+ "WHERE { ?id sepaw:timePeriod ?timePeriod;\n"
						+ "geo:geo ?geo  ;\n"
						+ "sepaw:measure ?measure ;\n"
						+ "sepaw:resource ?resource .}"
						+ "\n\n");
				
				fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}

	@Test //Sepa
	public void test13(){
	  source="waterBodyMeasures(timePeriod, geo, measure, resource)";
	  target="waterBodyMeasures(timePeriod, geo, measure, resource)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/waterBodyMeasures.n3>\n"
	          + "WHERE { ?id sepaw:timePeriod ?timePeriod;\n"
	          + "geo:geo ?geo  ;\n"
	          + "sepaw:measure ?measure ;\n"
	          + "sepaw:resource ?resource .}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Sepa
	public void test14(){
	  source="waterBodyPressures(identifiedDate,waterBodyId,assessmentCategory,source)";
	  target="waterBodyPressures(identifiedDate,waterBodyId,assessmentCategory,source)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/waterBodyPressures.n3>\n"
	          + "WHERE { ?id sepaw:identifiedDate ?identifiedDate;\n"
	          + "sepaw:waterBodyId ?waterBodyId  ;\n"
	          + "sepaw:assessmentCategory ?assessmentCategory ;\n"
	          + "sepaw:source ?source .}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Sepa
	public void test15(){
	  source="waterBodyMeasures(waterBodyId,secondaryMeasure,dataSource)";
	  target="waterBodyMeasures(waterBodyId,secondaryMeasure,dataSource)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/waterBodyMeasures.n3>\n"
	          + "WHERE { ?id sepaw:waterBodyId ?waterBodyId;\n"
	          + "sepaw:secondaryMeasure ?secondaryMeasure  ;\n"
	          + "sepaw:dataSource ?dataSource .}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Sepa
	public void test16(){
	  source="surfaceWaterBodies(riverName,associatedGroundwaterId)";
	  target="surfaceWaterBodies(riverName,associatedGroundwaterId)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/surfaceWaterBodies.n3>\n"
	          + "WHERE { ?id sepaw:riverName ?riverName;\n"
	          + "sepaw:associatedGroundwaterId ?associatedGroundwaterId .}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Sepa
	public void test17(){
	  source="bathingWaters(catchment, localAuthority, lat, long)";
	  target="bathingWaters(catchment, localAuthority, lat, long)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/bathingWaters.n3>\n"
	          + "WHERE { ?id sepaloc:catchment ?catchment;\n"
	          + "sepaloc:localAuthority ?localAuthority  ;\n"
	          + "geo:lat ?lat ;\n"
	          + "geo:long ?long .}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Sepa
	public void test18(){
	  source="surfaceWaterBodies(subBasinDistrict,riverName,altitudeTypology,associatedGroundwaterId)";
	  target="surfaceWaterBodies(subBasinDistrict,riverName,altitudeTypology,associatedGroundwaterId)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/surfaceWaterBodies.n3>\n"
	          + "WHERE { ?id sepaw:altitudeTypology ?altitudeTypology;\n"
	          + "sepaw:associatedGrounwaterId ?associatedGroundwaterId  ;\n"
	          + "sepaw:riverName ?riverName ;\n"
	          + "sepaw:subBasinDistrict ?subBasinDistrict .}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Sepa
	public void test19(){
	  source="bathingWaters(bathingWaterId)";
	  target="bathingWaters(bathingWaterId)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "sepa","queryData/sepa/sepa_datafiles/");
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){	
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
	          + "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
	          + "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n"
	          + "SELECT *  \n"
	          + "FROM <queryData/sepa/sepa_datafiles/bathingWaters.n3>\n"
	          + "WHERE { ?id sepaw:bathingWaterId ?bathingWaterId;\n"
	          + ".}"
	          + "\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test21(){
		source="City(country,populationTotal)";
		target="City(country,populationTotal)";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		finalRes=spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
		
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
		
		fOut.write("Test "+counter+"\n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				Match_Struc current = finalRes.get(0);
					
				fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
				fOut.write("Expected Result:\n\n" + 
					
						"PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
						+ "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
						+ "PREFIX  res: <http://dbpedia.org/resource/> \n"
						+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
						+ "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
						+ "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
						+ "SELECT DISTINCT *  \n"
						+ "WHERE { ?id rdf:type dbo:City ;\n"
						+ "dbo:country ?country ;\n"
						+ "dbo:populationTotal ?populationTotal .}\n"
						+ "LIMIT 20\n\n");
				
				fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}
	
	@Test //Dbpedia
	public void test22(){
		source="Country";
		target="Country";
		finalRes = new ArrayList<Match_Struc>();
		
		//call appropriate methods
		finalRes=spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
		
		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
		finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
		
		fOut.write("Test "+counter+"\n");
		
		if(finalRes!=null){
			if(finalRes.size() == 0){
				//then we have no results so end test
				fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
				fOut.write("Empty results returned. \n\n");
			}else{
				Match_Struc current = finalRes.get(0);
					
				fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
				fOut.write("Expected Result:\n\n" + 
					
						"PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
						+ "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
						+ "PREFIX  res: <http://dbpedia.org/resource/> \n"
						+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
						+ "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
						+ "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
						+ "SELECT DISTINCT *  \n"
						+ "WHERE { ?id rdf:type dbo:City .}\n"
						+ "LIMIT 20\n\n");
				
				fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
			}
		}else{
			fOut.write("Null Results! \n\n");
		}
		
		counter++;
	}
	
	@Test //Dbpedia
	public void test23(){
	  source="Astronaut(nationality)";
	  target="Astronaut(nationality)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type Astronaut ;\n"
	          + "dbo:nationality ?nationality ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test24(){
	  source="Mountain(elevation)";
	  target="Mountain(elevation)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Mountain ;\n"
	          + "dbo:elevation ?elevation ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test25(){
	  source="Person(occupation, birthPlace)";
	  target="Person(occupation, birthPlace)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Person ;\n"
	          + "dbo:occupation ?occupation ;\n"
	          + "dbo:birthPlace ?birthPlace .}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test26(){
	  source="Person(occupation, instrument)";
	  target="Person(occupation, instrument)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Person ;\n"
	          + "dbo:occupation ?occupation ;\n"
	          + "dbo:instrument ?instrument .}\n"
	          + "LIMIT 20\n\n"); 
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test27(){
	  source="Cave(location)";
	  target="Cave(location)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Cave ;\n"
	          + "dbo:location ?location ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test28(){
	  source="FormulaOneRacer(races)";
	  target="FormulaOneRacer(races)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:FormulaOneRacer ;\n"
	          + "dbo:races ?races ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test29(){
	  source="Person(team,birthDate)";
	  target="Person(team,birthDate)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Person ;\n"
	          + "dbo:team ?team ;\n"
	          + "dbo:birthDate ?birthDate .}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test210(){
	  source="River(length)";
	  target="River(length)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:River ;\n"
	          + "dbo:length ?length ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test211(){
	  source="place(locationCountry)";
	  target="place(locationCountry)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:place ;\n"
	          + "dbo:locationCountry ?locationCountry ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test212(){
	  source="Person(birthPlace, deathPlace)";
	  target="Person(birthPlace, deathPlace)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type foaf:Person ;\n"
	          + "dbo:birthPlace ?birthPlace ;\n"
	          + "dbo:deathPlace ?deathPlace .}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test213(){
	  source="Royalty(parent)";
	  target="Royalty(parent)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Royalty ;\n"
	          + "dbo:parent ?parent ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test214(){
	  source="StatesOfTheUnitedStates(admittancedate)";
	  target="StatesOfTheUnitedStates(admittancedate)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:StatesOfTheUnitedStates ;\n"
	          + "dbp:admittancedate ?admittancedate ;\n"
	          + ".}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
	    }
	  }else{
	    fOut.write("Null Results! \n\n");
	  }
	  
	  counter++;
	}
	
	@Test //Dbpedia
	public void test215(){
	  source="Person(mission)";
	  target="Person(mission)";
	  finalRes = new ArrayList<Match_Struc>();
	  
	  //call appropriate methods
	  finalRes=spsmCall.getSchemas(finalRes, source, target);
	  finalRes = filterRes.getThresholdAndFilter(finalRes, 0.0, 0);
	  
	  if(finalRes!=null && finalRes.size()!=0){
	    finalRes = getRepairedSchema.prepare(finalRes);
	  }
	  
	  finalRes = createQuery.createQueryPrep(finalRes, "dbpedia",null);
	  
	  fOut.write("Test "+counter+"\n");
	  
	  if(finalRes!=null){
	    if(finalRes.size() == 0){
	      //then we have no results so end test
	      fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n");
	      fOut.write("Empty results returned. \n\n");
	    }else{
	      Match_Struc current = finalRes.get(0);
	        
	      fOut.write("Creating query from schema, "+current.getDatasetSchema() + "\n");
	      fOut.write("Expected Result:\n\n" + 
	        
	          "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
	          + "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
	          + "PREFIX  res: <http://dbpedia.org/resource/> \n"
	          + "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	          + "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
	          + "PREFIX yago: <hhtp://dbpedia.org/class/yaho/> \n\n"
	          + "SELECT DISTINCT *  \n"
	          + "WHERE { ?id rdf:type dbo:Person ;\n"
	          + "dbp:mission ?mission ;\n"
	          + " .}\n"
	          + "LIMIT 20\n\n");
	      
	      fOut.write("Actual Result: \n\n" + current.getQuery() + "\n\n");
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
