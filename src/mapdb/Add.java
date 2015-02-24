/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdb;

import java.sql.SQLException;

/**
 *
 * @author pfg
 */
public class Add {
        Add(String m_lang, String f_lang) throws Exception {
        
        Database.getInstance().connect();
        languagesDAO dao=new languagesDAO();
        
        languagesBean data = new languagesBean();

        data.setM_lang(m_lang);
        data.setF_lang(f_lang);
        
        dao.addData(data);
        
        Database.getInstance().disconnect();     
    } 
}
