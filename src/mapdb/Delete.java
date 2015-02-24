/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdb;

/**
 *
 * @author pfg
 */
public class Delete {
        Delete(int id) throws Exception {
        
        Database.getInstance().connect();
        languagesDAO dao=new languagesDAO();
        
        languagesBean data = new languagesBean();

        data.setLangid(id);
        
        dao.deleteData(data);
        
        Database.getInstance().disconnect();     
    } 
}
