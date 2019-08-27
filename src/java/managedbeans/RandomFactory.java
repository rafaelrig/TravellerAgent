/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbeans;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author rig
 */
@ApplicationScoped
public class RandomFactory  implements Serializable{
    
    private java.util.Random random = 
       new java.util.Random( System.currentTimeMillis() );

   private int maxNumber = 1000000;

   java.util.Random getRandom() {
       return random;
   }

   @Produces @Random int next() {
       return getRandom().nextInt();
   }
    
}
