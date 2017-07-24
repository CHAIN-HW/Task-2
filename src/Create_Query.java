import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.*;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

/*
 * 
 * Responsible for using the repaired schema
 * to create either a sepa or dbpedia query, this 
 * query is added as a field onto the Match_Struc 
 * object structure
 * 
 */
public class Create_Query {
	
	public static void main(String [] args){
		Create_Query queryCreator = new Create_Query();
		Call_SPSM spsmCall = new Call_SPSM();
		Best_Match_Results filterResCall = new Best_Match_Results();
		Repair_Schema getRepairedSchema = new Repair_Schema();

//		String source="surfaceWaterBodies(subBasinDistrict,riverName,altitudeTypology,associatedGroundwaterId,nonsense)";
//		String target="surfaceWaterBodies(subBasinDistrict,riverName,altitudeTypology,associatedGroundwaterId,nonsense)";
		
		String source="Person(occupation, birthPlace)";
		String target="Person(occupation, birthPlace)";
		
		ArrayList<Match_Struc> finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.6, 0);

		if(finalRes!=null && finalRes.size()!=0){
			finalRes = getRepairedSchema.prepare(finalRes);
		}
		
//		finalRes = queryCreator.createQueryPrep(finalRes, "sepa", "queryData/sepa/sepa_datafiles/");
		finalRes = queryCreator.createQueryPrep(finalRes, "dbpedia", null);
	}
	
	//sets up ready to create query for each repaired schema in list of match structures
	public ArrayList<Match_Struc> createQueryPrep(ArrayList<Match_Struc> matchRes, String queryType, String additionalInfo){
		String type="";
		
		//first start off by checking query type required
		if(queryType == null){
			//we haven't passed in the query type, prompt user for one
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				//get query type through command line
				boolean correctType=false;
				while(!correctType){
					System.out.println("Please enter the query type (dbpedia or sepa): ");
					type=reader.readLine().toLowerCase();
					
					if(type.equals("dbpedia") || type.equals("sepa")){
						correctType=true;
					}
				}
				
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			type=queryType;
		}
		
		//for each of the match items, we want to create a query
		String query="";
		for(int i = 0 ; i < matchRes.size(); i++){
			
			Match_Struc curr = matchRes.get(i);
			
			//call the appropriate method based
			//on the type of query
			if(type.equals("sepa")){
				query = createSepaQuery(curr, additionalInfo);
				curr.setQuery(query);
				
				runSepaQuery(query,additionalInfo,curr);
			}else{
				query = createDbpediaQuery(curr);
				curr.setQuery(query);
				
				runDbpediaQuery(query,curr);
			}
		}
		
		return matchRes;
	}
	
	//creates structure for sepa query
	public String createSepaQuery(Match_Struc matchDetails, String datafileDir){
		
		ArrayList<Ontology_Struc> ontologies = new ArrayList<Ontology_Struc>();
		String query="";
		
		//start off by selecting ontology file
		try{
			String jsonTxt = new String(Files.readAllBytes(Paths.get("queryData/sepa/sepa_ontology.json")), StandardCharsets.UTF_8);
			JSONArray jsonArr = new JSONArray(jsonTxt);
			
			//now call function to get prefix
			ontologies = setupPrefixes(jsonArr, ontologies);
			
			//write prefix part of query
			query = query + writePrefix(ontologies);
			
			//start writing main bulk of query
			String filename = matchDetails.getRepairedSchemaTree().getValue() + ".n3";
			
			String dbDir = datafileDir + filename;
			query = query + "\nSELECT *\n" + "FROM <"+ dbDir + ">\n"+"WHERE { ?id ";
			
			//then start getting different parts of data to search for
			List<Node> schemaChildren = matchDetails.getRepairedSchemaTree().getChildren();
			
			if(schemaChildren.size()>0){
				query=query+ dataMatching(schemaChildren,ontologies);
			}else{
				query = query + "\n.}\n";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return query;
	}
	
	//creates structure for dbpedia query
	public String createDbpediaQuery(Match_Struc matchDetails){
		
		ArrayList<Ontology_Struc> ontologies = new ArrayList<Ontology_Struc>();
		String query="";
		
		//start off by selecting ontology file
		try{
			String jsonTxt = new String(Files.readAllBytes(Paths.get("queryData/dbpedia/dbpedia_ontology.json")), StandardCharsets.UTF_8);
			JSONArray jsonArr = new JSONArray(jsonTxt);
			
			//now call function to get prefix
			ontologies = setupPrefixes(jsonArr, ontologies);
			
			//write prefix part of query
			query = query + writePrefix(ontologies);
			
			//start writing main bulk of query
			query = query + "\nSELECT DISTINCT*\n"+"WHERE { ?id rdf:type ";
			
			for(int j = 0 ; j < ontologies.size() ; j++){
				Ontology_Struc currentOntology = ontologies.get(j);
				String[] properties = currentOntology.getProperties();
				
				if(!(properties == null)){
					int index;
					
					if((index = Arrays.asList(properties).indexOf(matchDetails.getRepairedSchemaTree().getValue())) != -1){
						query = query + currentOntology.getName() + ":" + matchDetails.getRepairedSchemaTree().getValue();
						break;
					}
				}
			}
			
			//then start getting different parts of data to search for
			List<Node> schemaChildren = matchDetails.getRepairedSchemaTree().getChildren();
			
			if(schemaChildren.size() > 0){
				query = query + ";\n";
				query=query+ dataMatching(schemaChildren,ontologies);
			}else{
				query = query + ".}\n";
			}
			
			//final line
			query=query+"LIMIT 20";		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return query;
	}
	
	//sets up prefix links for the queries
	public ArrayList<Ontology_Struc> setupPrefixes(JSONArray jsonArr, ArrayList<Ontology_Struc> ontologies){
		String name,link,propString;
		String[] properties;
		
		Ontology_Struc ontology;
		
		//for each element in json arr create prefix string
		for(int i = 0 ; i < jsonArr.length() ; i++){
				
			try {
				JSONObject current = jsonArr.getJSONObject(i);
				
				name = current.getString("name");
				link = current.getString("link");
				
				if(current.has("properties")){
					propString = current.getString("properties");
					properties = propString.split(",");	
				}else{
					properties=null;
				}
				
				ontology = new Ontology_Struc(name,link,properties);
				ontologies.add(ontology);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ontologies;
	}
	
	//writes the prefixes and links onto query string
	public String writePrefix(ArrayList<Ontology_Struc> ontologies){
		String currPrefixStr="",finalStr="";
		
		for(int i = 0 ; i < ontologies.size() ; i++){
			
			Ontology_Struc curr = ontologies.get(i);
			currPrefixStr = "PREFIX " + curr.getName() + ": " + curr.getLink() + "\n";
			
			finalStr = finalStr + currPrefixStr;
		}
		
		return finalStr;
	}
	
	//finds which ontology file the keywork relates to
	public String dataMatching(List<Node> children, ArrayList<Ontology_Struc> ontologies){
		String dataDetails="";
		
		for(int i = 0 ; i < children.size() ; i++){
			
			//get the names of the schema parameters
			//then see what ontology file they fall under
			//by looking at properties
			String paramName = children.get(i).getValue();
			
			for(int j = 0 ; j < ontologies.size() ; j++){

				Ontology_Struc currentOntology = ontologies.get(j);
				
				String[] properties = currentOntology.getProperties();
				
				if(!(properties == null)){
					
					int index;
					
					if((index = Arrays.asList(properties).indexOf(paramName)) != -1){
						String search = currentOntology.getName() + ":" + currentOntology.getProperties()[index] + " ?" + paramName;
						
						//check if it's the last one because otherwise
						//we want to add ;
						if(i+1 >= children.size()){
							search = search + "\n.}\n";
						}else{
							search=search+" ;\n";
						}
						
						dataDetails = dataDetails + search;
						break;
					}
				}			
			}
		}
		
		return dataDetails;
	}
	
	//runs a sepa query
	public void runSepaQuery(String query, String datasetToUseDir, Match_Struc currMatchStruc){
		System.out.println("\nRepaired Schema: "+currMatchStruc.getDatasetSchema());
		System.out.println("\n\nQuery:\t" + query);
		
		//create query object
		Query queryObj = QueryFactory.create(query);
		
		//load model locally
		String dbDir = datasetToUseDir + currMatchStruc.getRepairedSchemaTree().getValue() + ".n3";
		Model model = FileManager.get().loadModel(dbDir);
		
		//query execution factory
		QueryExecution queryExec = QueryExecutionFactory.create(queryObj, model);
		
		try{	
			System.out.println("\n\nResults:\n");
			//execute and print results to console
			ResultSet results = queryExec.execSelect();
			ResultSetFormatter.out(System.out, results);		
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	//runs a dbpedia query
	public void runDbpediaQuery(String query, Match_Struc currMatchStruc){
		System.out.println("\nRepaired Schema: "+currMatchStruc.getDatasetSchema());
		System.out.println("\n\nQuery:\t"+query);
		
		//create query object
		Query queryObj = QueryFactory.create(query);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", queryObj);
		
		try{
			System.out.println("\n\nResults:\n");
			
			//execute and print results to console
			ResultSet results = qexec.execSelect();
			ResultSetFormatter.out(System.out,results);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
