/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapdb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author pfg
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final List<String> demoValues =
    Arrays.asList("AB","AAB","AAAB","AAAAB","AAAAAB");
String value = demoValues.stream().reduce((a, b) -> a).get();
    System.out.println("value = " + value);
    
        List<String> value1 = demoValues.stream().skip(1).collect(Collectors.toList());
    System.out.println("value = " + value1);
    }
    
}
