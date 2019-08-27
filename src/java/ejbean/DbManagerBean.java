/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejbean;

import entities.Details;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import entities.Package;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.Remove;

/**
 *
 * @author rig
 */

@Named
@Stateful
public class DbManagerBean {
    private final double igv = 0.16;
    private double percentage;
    private String cityOption;
    ResultSet rs =null;
    Statement stm = null;
    Connection conn = null;
    private Package pack = null;
    Details detail = null;
    private FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }

    public DbManagerBean() {
        try{
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/vta03resource");
            conn = ds.getConnection();
            pack = (Package) context().getExternalContext().
                    getSessionMap().get("pack");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            ex.getCause();
        } catch (NamingException exn) {
            System.err.println(exn.getMessage());
            exn.getCause();
        }
        
                 
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getCityOption() {
        return cityOption;
    }

    public void setCityOption(String cityOption) {
        this.cityOption = cityOption;
    }

    public String getIgv() {
        NumberFormat pf = NumberFormat.getPercentInstance();
        String igvformat = pf.format(igv);
    
        return igvformat;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
    
    
    
    public List<entities.Package> getPackagesByCategory() throws SQLException{
        List<entities.Package> packs = new ArrayList<>();
        String category = (String) context().getExternalContext().
                    getSessionMap().get("category");
        String query = "select * from packages where category='" + category +"'";
                
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(query);
            
        while (rs.next()) { 
            pack = new entities.Package(); 
            pack.setName(rs.getString("name"));
            pack.setDescription(rs.getString("description")); 
            pack.setPrice(rs.getDouble("price"));
            pack.setCity(rs.getString("city")); 
            pack.setCategory(rs.getString("category")); 
            packs.add(pack);

            }
        } catch(SQLException ex) {
            Logger.getLogger(DbManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (rs != null) { 
                try{rs.close();} catch(Exception e){e.getCause();}}
            if(stm != null) {
                try{stm.close();} catch(Exception e) {e.getStackTrace();}}
            if(conn != null) {
                try{conn.close();} catch(Exception e) {e.getCause();}}
        }
        
        return packs;    
    } 
    
    public List<Details> getDetails() throws Exception {
         List<Details> details = new ArrayList<>();
         
         String name = pack.getName();
        
         String query = "select * from details where name='" + name +"'";
                
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(query);
            
        while (rs.next()) { 
            detail = new Details(); 
            detail.setName(rs.getString("name"));
            detail.setDescription(rs.getString("description")); 
            details.add(detail);
            }
        } catch(SQLException ex) {
            Logger.getLogger(DbManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (rs != null) { 
                try{rs.close();} catch(Exception e){e.getCause();}}
            if(stm != null) {
                try{stm.close();} catch(Exception e) {e.getStackTrace();}}
            if(conn != null) {
                try{conn.close();} catch(Exception e) {e.getCause();}}
        }
       
        return details;
    }
    
    public List<entities.Package> getPackagesByCity() throws Exception{
        List<entities.Package> packsByCity = new ArrayList<>();
        String city = (String) context().getExternalContext().
                    getSessionMap().get("cityPack");
        String query = "select * from packages where city='" + city +"'";
                
        try {            
            stm = conn.createStatement();
            rs = stm.executeQuery(query);
            
        while (rs.next()) { 
            pack = new Package(); 
            pack.setName(rs.getString("name"));
            pack.setDescription(rs.getString("description")); 
            pack.setPrice(rs.getDouble("price"));
            pack.setCity(rs.getString("city")); 
            pack.setCategory(rs.getString("category"));
            packsByCity.add(pack);
            }
        } catch(SQLException ex) {
            Logger.getLogger(DbManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
            if (rs != null) { 
                try{rs.close();} catch(Exception e){e.getCause();}}
            if(stm != null) {
                try{stm.close();} catch(Exception e) {e.getStackTrace();}}
            if(conn != null) {
                try{conn.close();} catch(Exception e) {e.getCause();}}
        }
        
        return packsByCity;        
     }
     
    /*set subtotal in packreceipt*/
     
     public String getPrice() throws Exception {
         pack = (Package) context().getExternalContext().
                    getSessionMap().get("pack");
          double packId = pack.getPrice();
          String pricestring = NumberFormat.getCurrencyInstance().format(packId);
            return pricestring;
   
        }
     
     /*get total cost*/
     
     public String getTotal() throws Exception {
         pack = (Package) context().getExternalContext().
                    getSessionMap().get("pack");
         double packPrice = pack.getPrice();
         percentage = (packPrice * 16) / 100;
         double total = packPrice + percentage;
         String totalformat = NumberFormat.getCurrencyInstance().format(total);
        return totalformat;
        
        }
    
      /*create a new order*/
    
    @Remove
    public void createOrder(long id, String firstname, String lastname, 
                            String phone, String ccnumber, String packname, Date date, int keyclient) throws SQLException {
        packname = pack.getName();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO orders (id, firstname, lastname, phone, " +
             "ccnumber, packname, date, keyclient) VALUES(?,?,?,?,?,?,?,?)");
            
        try {
            conn.setAutoCommit(false);
            pstmt.setLong(1, id);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, phone);
            pstmt.setString(5, ccnumber);
            pstmt.setString(6, packname);
            pstmt.setDate(7, (java.sql.Date) date);
            pstmt.setInt(8, keyclient);
            pstmt.addBatch();
            pstmt.executeBatch();
            conn.commit();
            } catch (Exception e) {
            throw new EJBException(e.getMessage());
            }  finally {
            if (pstmt != null) { 
                try{pstmt.close();} catch(Exception e){e.getCause();}}
            if(conn != null) {
                try{conn.close();} catch(Exception e) {e.getStackTrace();}}
        }
 
        conn.setAutoCommit(true);
    }
    
    @Remove
    public void cancel(){    
    }
    
}
