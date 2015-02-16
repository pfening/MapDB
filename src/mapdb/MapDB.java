/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdb;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pfeniga1
 */
public class MapDB {

    public static void main(String[] args) throws Exception {
       
                Database.getInstance().connect();
                Connection conn = Database.getInstance().getConnection();
		PreparedStatement selectStatement = conn.prepareStatement("SELECT name FROM sqlite_sequence");		
		ResultSet result = selectStatement.executeQuery();		
		while(result.next()) {
                        String table = result.getString("name");
                        File file = new File(System.getProperty("user.dir")+"/src/mapdb/"+table+"Bean.txt");
                        if (!file.exists()) {file.createNewFile();}
                        PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
                        writer.println("public class "+table+"Bean {");
                        
                PreparedStatement selectStatement1 = conn.prepareStatement("PRAGMA table_info('"+table+"') ");		
		ResultSet result1 = selectStatement1.executeQuery();	
                
                List<String> attr = new ArrayList<>();
                List<AttributesObject> attributes = new ArrayList<>();
                
		while(result1.next()) {
                    
			String varName = result1.getString("name");
                        String varType = result1.getString("type");

                        if (result1.getString("type").equals("INTEGER")){
                            varType = "int";                            
                        }else{
                            varType = "";
                        }
                        
                        if (result1.getString("type").equals("TEXT")){
                            varType = "String";
                        }
                        
                        String atts = varType+" "+varName;
                        attr.add(atts);
                        
                         attributes.add(new AttributesObject(varType,varName));
                        
                        writer.println(atts+";");

                      
			}
                writer.println("public "+table+"Bean() {}");
                writer.println("public "+table+"Bean("+attr.toString().substring(1, attr.toString().length()-1)+") {");

                attributes.stream().forEach((m) -> {
                    String t = m.type;
                    String n = m.name;
                    
                    writer.println("this."+n+" = "+n);
                });
                writer.println("}");  
                    
                writer.println("}");
                
		result1.close();
		selectStatement1.close(); 
                writer.close(); 
			}		
		result.close();
		selectStatement.close();     
    
                Database.getInstance().disconnect(); 
                
                               
    }
   
}
