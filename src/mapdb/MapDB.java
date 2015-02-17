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
                        File file = new File(System.getProperty("user.dir")+"/src/mapdb/"+table+"Bean.java");
                        if (!file.exists()) {file.createNewFile();}
                        PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
                        
                        writer.println("package mapdb;");
                        writer.println();
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
                
                writer.println();
                writer.println("public "+table+"Bean() {}");
                writer.println();
                
                writer.println("public "+table+"Bean("+attr.toString().substring(1, attr.toString().length()-1)+") {");                
                
                attributes.stream().forEach((m) -> {
                    String n = m.name;                    
                    writer.println("this."+n+" = "+n+";");
                });
                writer.println("}");  
                writer.println();
                
                attributes.stream().forEach((m) -> {
                    String n = m.name;
                    String t = m.type;
                    String nC = n.substring(0, 1).toUpperCase() + n.substring(1);
                    
                writer.println("public "+t+" get"+nC+"() {");
                writer.println("return "+n+";");
                writer.println("}");
                writer.println();
                
                writer.println("public void set"+nC+"("+t+" "+n+") {");
                writer.println("this."+n+" = "+n+";");
                writer.println("}"); 
                writer.println();
                });
                
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
