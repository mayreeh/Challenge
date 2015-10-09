package com.mary.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mary.model.Challenge;

@Controller
public class ChallengeController {

	@Autowired 
	ServletContext servletContext=null;
	@RequestMapping(value = "/jsonData")
	public void jsonData(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException  {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(servletContext.getRealPath("water-points.json")));
			try {
				StringBuilder sb = new StringBuilder();
				String line;
				try {
					line = br.readLine();
					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator()); 
					     line = br.readLine();
					}
					String json_data = sb.toString();
					JSONArray jsonArray = new JSONArray(json_data);
					/* So far we have the data loaded into our variable */
					int functional_wp = 0;
					int disfunctional_wp = 0;
					List<String> communities = new ArrayList<String>();
					List<Integer> community_functionals = new ArrayList<>();
					List<Integer> community_disfunctionals = new ArrayList<>();
					for(int i = 0; i < jsonArray.length(); i ++)
					{
				    	JSONObject jsonobj = jsonArray.getJSONObject(i);
				    	String community = jsonobj.get("communities_villages").toString();
				    	if(!communities.contains(community))
				    	{
				    		communities.add(community);
				    		community_functionals.add(communities.indexOf(community), 0);
				    		community_disfunctionals.add(communities.indexOf(community), 0);
				    	}
				    	int index = communities.indexOf(community);
				    	if(jsonobj.get("water_functioning").toString().equalsIgnoreCase("yes"))
				    	{
				    		functional_wp ++;
				    		community_functionals.add(index, community_functionals.get(index) + 1); 
				    	}
				    	else
				    	{
				    		community_disfunctionals.add(index, community_disfunctionals.get(index) + 1);
				    		disfunctional_wp ++;
				    	}
					}		
					JSONObject jsonObject = new JSONObject().put("success", true).put("functional_water_points", functional_wp).put("non_functional_water_points", disfunctional_wp);
					JSONArray jArray = new JSONArray();
					for(int i = 0; i < communities.size(); i ++)
					{
						JSONObject jobj = new JSONObject();
						String community = communities.get(i);
						int no_of_functional = community_functionals.get(i);
						int no_of_disfunctional = community_disfunctionals.get(i);
						int total_per_community = no_of_disfunctional + no_of_functional;
						double ranking = (no_of_disfunctional * 100) / total_per_community;
					/* List of Functional water Points , Non_functional and total water points*/
					//jobj.put(community, new JSONObject().put("ranking", ranking + "%").put("total_water_points", total_per_community).put("functional_water_points", no_of_functional).put("non_functional_water_points", no_of_disfunctional));
						jobj.put(community, new JSONObject().put("ranking", ranking + "%").put("functional_water_points", no_of_functional));
						jArray.put(jobj);
					}
					jsonObject.put("community_data", jArray);
					response.getWriter().print(jsonObject.toString());
					} finally {
					     br.close(); 
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					response.getWriter().print(new JSONObject().put("success", false).toString());
				}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			response.getWriter().print(new JSONObject().put("success", false).toString());
		}
		
	}
	
	@RequestMapping(value = "/mydata" , method = RequestMethod.POST)
	@ResponseBody
	public String myData(@RequestBody Challenge[] challenges)
	{
		for(Challenge challenge : challenges){
			
		    System.out.println(challenge.getCommunities_villages());
			System.out.println(challenge.getWater_functioning());
		
		}
		return "water";
	}

	
	
		

}
