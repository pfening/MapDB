/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdb;

import java.util.List;

/**
 *
 * @author pfg
 */
public class Get {
    
    Get(String lang) throws Exception{
        
        Database.getInstance().connect();
        languagesDAO d=new languagesDAO();
        d.setSelectedItem(lang);

        
        List<languagesBean> list = (List<languagesBean>) d.getList();
        for (languagesBean n:list){
        System.out.println(n.langid +" - "+ n.getM_lang() +" - "+ n.getF_lang());
        }
        
        Database.getInstance().disconnect();  
    }

}
