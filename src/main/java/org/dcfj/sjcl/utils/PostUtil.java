package org.dcfj.sjcl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


public class PostUtil {
	

	public static String doPost(String content,String url,String contentType) throws IOException {
		StringBuilder result = new StringBuilder();
        URL postUrl = new URL(url);  
        URLConnection con = postUrl.openConnection(); 
        con.setConnectTimeout(5000);
        con.setDoOutput(true);  
        con.setRequestProperty("Pragma", "no-cache");  
        con.setRequestProperty("Cache-Control", "no-cache"); 
        if(contentType!=null)
        {
        	con.setRequestProperty("Content-Type", contentType);  
        }
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());    
        out.write(new String(content.getBytes("UTF-8")));  
        out.flush();  
        out.close();  
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));  
        String line = "";  
        for (line = br.readLine(); line != null; line = br.readLine()) {  
        	result = result.append(line);
        }  
		return result.toString();
	}
}
