
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbeans;

import static com.sun.xml.ws.security.impl.policy.Constants.logger;
import ejbean.DbManagerBean;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Instance;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rig
 */
@ManagedBean
@RequestScoped
public class CashierBean implements Serializable {
    @EJB 
    private DbManagerBean dbmanager;
    private Package pack;
    @Random
    @Inject
    Instance<Integer> randomInt;
    protected FacesContext context() {
        return (FacesContext.getCurrentInstance());}
    private long id;
    private String firstname;
    private String lastname;
    private String phone;
    private String ccNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;    
    private String packname = null;
    private int keyClient;
    /**
     * Creates a new instance of CashierBean
     */
    public CashierBean() {
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
     public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }
    
    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getKeyClient() {
        return keyClient;
    }

    public void setKeyClient(int keyClient) {
        this.keyClient = keyClient;
    }
    
    @PostConstruct
    public void reset() {
        try{
        java.util.Date utilDate = new java.util.Date();
        java.util.Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        //cal.set(Calendar.HOUR_OF_DAY, 0);
        //cal.set(Calendar.MINUTE, 0);
        //cal.set(Calendar.SECOND, 0);
//cal.set(Calendar.MILLISECOND, 0);    
        date = new java.sql.Date(cal.getTime().getTime());
         System.out.println("Date = " + date);   
        this.keyClient = randomInt.get();
        System.out.println("keyclient = " + keyClient);
        
        } catch (Exception e){
            logger.warning("Problems on reset method");
            e.getCause();
        }
    }
    
    
    
    public String submitOrder() {
      
        
        try {
            dbmanager.createOrder(id, firstname, lastname, phone, ccNumber, packname, date, keyClient);

        } catch (SQLException e) {
            logger.warning("Problem creating order in submitOrder.");
            e.getStackTrace();
        }
        
        return ("confirmer");
    }
    
    
}
