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
import java.util.stream.Collectors;

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

                writer.print("PreparedStatement listStatement = conn.prepareStatement(\"SELECT * FROM "+table+" WHERE ");                
                for(int x = 0; x < attributes.size(); x++) {
                String nn = attributes.get(x).name;    
                    if (x < attributes.size()-1){                
                writer.print(nn+"=? OR ");
                    }else{
                        writer.print(nn+"=?");
                    }
                }
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
                    String tC = t.substring(0, 1).toUpperCase() + t.substring(1);
                    
                    writer.println("entry.set"+nC+"(results.get"+tC+"(\""+n+"\"));");
                    });                   
                    
                    writer.println(table+".add(entry);");                   
		writer.println("}");		
		writer.println("results.close();");
		writer.println("listStatement.close();");
                writer.println("return "+table+";");           
                writer.println("}");                
                writer.println();
                //end of getList
                
                //addItem
                writer.println("public void addData("+table+"Bean data) throws SQLException, Exception {");		
		writer.println("Connection conn = Database.getInstance().getConnection();");	
                

		writer.print("PreparedStatement addStatement = conn.prepareStatement(\"INSERT INTO "+table+" ("); 
                List<AttributesObject> collect = attributes.stream().skip(1).collect(Collectors.toList());
                collect.stream().filter(item -> !item.equals(collect.stream().reduce((a, b) -> b).get())).forEach((e) -> writer.print(e.name+", "));
                writer.print(collect.stream().reduce((a, b) -> b).get().name);
                writer.print(") VALUES (");                

                collect.stream().filter(item -> !item.equals(collect.stream().reduce((a, b) -> b).get())).forEach((e) -> writer.print("?, "));
                writer.print("?");                 
                writer.print(")\");");
                writer.println();
                
                for (int as = 1; as < collect.size()+1; as++){
                String n = attributes.get(as).name;
                String t = attributes.get(as).type;
                String nC = n.substring(0, 1).toUpperCase() + n.substring(1);
                String tC = t.substring(0, 1).toUpperCase() + t.substring(1);  
                    
                writer.println("addStatement.set"+tC+"("+as+", data.get"+nC+"());");                
                }               
		writer.println("addStatement.executeUpdate();");		
		writer.println("addStatement.close();");		
                writer.println("}"); 
                writer.println();
                //end of addItem
                
                //updateItem
                writer.println("public void updateData("+table+"Bean data) throws SQLException, Exception {");		
		writer.println("Connection conn = Database.getInstance().getConnection();");	
		writer.print("PreparedStatement updateStatement = conn.prepareStatement(\"UPDATE "+table+" SET "); 
                //attributes.stream().filter(item -> !item.equals(attributes.stream().reduce((a, b) -> a).get())).forEach((e) -> writer.print(e.name+"=?, "));  
                
                List<AttributesObject> collect1 = attributes.stream().skip(1).collect(Collectors.toList());
                collect1.stream().filter(item -> !item.equals(collect1.stream().reduce((a, b) -> b).get())).forEach((e) -> writer.print(e.name+"=?, "));
                writer.print(collect.stream().reduce((a, b) -> b).get().name);
                
                writer.print("=? WHERE ");  
                writer.print(attributes.stream().reduce((a, b) -> a).get().name);
                writer.print("=?\");");                 
                writer.println();
                
                for (int as = 1; as < attributes.stream().skip(1).collect(Collectors.toList()).size()+1; as++){
                String n = attributes.get(as).name;
                String t = attributes.get(as).type;
                String nC = n.substring(0, 1).toUpperCase() + n.substring(1);
                String tC = t.substring(0, 1).toUpperCase() + t.substring(1);                      
                writer.println("updateStatement.set"+tC+"("+as+", data.get"+nC+"());");                
                }      
                String n = attributes.get(0).name;
                String nC = n.substring(0, 1).toUpperCase() + n.substring(1);                    
                writer.println("updateStatement.setInt("+attributes.size()+", data.get"+nC+"());");
		writer.println("updateStatement.executeUpdate();");		
		writer.println("updateStatement.close();");		
                writer.println("}"); 
                writer.println();
                //end of updateItem
                
                
                //deleteItem
                writer.println("public void deleteData("+table+"Bean data) throws SQLException, Exception {");		
		writer.println("Connection conn = Database.getInstance().getConnection();");	                
		writer.print("PreparedStatement deleteStatement = conn.prepareStatement(\"DELETE FROM "+table+" WHERE ");                
                writer.print(attributes.stream().reduce((a, b) -> a).get().name);
                writer.print("=?");               
               
                writer.print("\");");
                writer.println();

                n = attributes.get(0).name;
                nC = n.substring(0, 1).toUpperCase() + n.substring(1);                    
                writer.println("deleteStatement.setInt(1, data.get"+nC+"());");                
             
		writer.println("deleteStatement.executeUpdate();");		
		writer.println("deleteStatement.close();");		
                writer.println("}"); 
                writer.println();
                //end of deleteItem
                
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
