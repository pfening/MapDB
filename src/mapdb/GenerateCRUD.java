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
public class GenerateCRUD {

    public static void main(String[] args) throws Exception {
       
                Database.getInstance().connect();
                Connection conn = Database.getInstance().getConnection();
		PreparedStatement selectStatement = conn.prepareStatement("SELECT name FROM sqlite_sequence");		
		ResultSet result = selectStatement.executeQuery();		
		while(result.next()) {
                        String table = result.getString("name");
                        File file = new File(System.getProperty("user.dir")+"/src/mapdb/"+table+"DAO.java");
                        if (!file.exists()) {file.createNewFile();}
                        PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
                        
                        writer.println("package mapdb;");
                        writer.println();
                        writer.println("import java.sql.Connection;");
                        writer.println("import java.sql.PreparedStatement;");
                        writer.println("import java.sql.ResultSet;");
                        writer.println("import java.sql.SQLException;");
                        writer.println("import java.util.ArrayList;");
                        writer.println("import java.util.List;");
                        writer.println();
                        writer.println("public class "+table+"DAO {");
                        writer.println();
                        writer.println("private String selectedItem;");
                        writer.println();
                        writer.println("public String getSelectedItem() {");        
                        writer.println("return selectedItem;");
                        writer.println("}");
                        writer.println();
                        writer.println("public void setSelectedItem(String selectedItem) {");
                        writer.println("this.selectedItem = selectedItem;");      
                        writer.println("}");
                        writer.println();
                        
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
                         attributes.add(new AttributesObject(varType,varName));                    
			}
                
                //get list
                writer.println("public List<"+table+"Bean> getList() throws SQLException, Exception{");
                writer.println("List<"+table+"Bean> "+table+" = new ArrayList<>();");	
		writer.println("Connection conn = Database.getInstance().getConnection();");
                
		writer.print("PreparedStatement listStatement = conn.prepareStatement(\"SELECT * FROM "+table+" where ");
                attributes.stream().forEach((m) -> {
                String n = m.name;                    
                     writer.print(n+"=? OR ");
                });  
                writer.print("\");");
                writer.println();
                
                for (int as = 1; as < attributes.size()+1; as++){
                writer.println("listStatement.setString("+as+", selectedItem);");                
                }
		writer.println("ResultSet results = listStatement.executeQuery();");		
		writer.println("while(results.next()) {");
                    writer.println(table+"Bean entry = new "+table+"Bean();");
                    
                    attributes.stream().forEach((m) -> {
                    String n = m.name;
                    String t = m.type;
                    String nC = n.substring(0, 1).toUpperCase() + n.substring(1);
                    
                    writer.println("entry.set"+nC+"(results.get"+t+"(\""+n+"\"));");
                    });                   
                    
                    writer.println(table+".add(entry);");                   
		writer.println("}");		
		writer.println("results.close();");
		writer.println("listStatement.close();");
                writer.println("return "+table+";");           
                writer.println("}");

                writer.println();
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
