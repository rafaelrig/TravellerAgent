/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import entities.Package;
/**
 *
 * @author rig
 */
@WebServlet(name = "DisplayImage", urlPatterns = {"/DisplayImage"})
public class DisplayImage extends HttpServlet {

    Package pack = new Package();
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @return 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }
        
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        
            try{
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource)ctx.lookup("jdbc/vta03resource");
                conn = ds.getConnection();
                System.out.println("Connection established!");
                pack = (Package) context().getExternalContext().getSessionMap().get("pack");
                System.out.println("Package got it: " + pack.toString());
                Integer imageId = pack.getId();
                System.out.println("Package id: " + imageId );
                String name = pack.getName();
                System.out.println("Package name: " + name );
                String query = "select picture from images where name = '" + name +"' ";     
                stm = conn.createStatement();
                rs = stm.executeQuery(query);
           
                String imgLen="";
                while(rs.next()){
                imgLen = rs.getString(1);
                System.out.println(imgLen.length());
                int len = imgLen.length();
                byte [] rb = new byte[len];
                InputStream readImg = rs.getBinaryStream(1);
                int index=readImg.read(rb, 0, len);  
                System.out.println("index----------------"+index);
                response.reset();
                response.setContentType("image/jpg");
                response.getOutputStream().write(rb,0,len);
                response.getOutputStream().flush();
                }
                //rs.close();
                response.getOutputStream().close();
           } catch (IOException | SQLException e){
                 System.out.println(e);
           } catch (NamingException ex) {
                 Logger.getLogger(DisplayImage.class.getName()).log(Level.SEVERE, null, ex);
           } finally {
            if (stm != null) { 
                try{stm.close();} catch(Exception e){e.printStackTrace();}}
            if(rs != null) {
                try{rs.close();} catch(Exception e) {e.printStackTrace();}}
            if(conn != null) {
                try{conn.close();} catch(Exception e) {e.printStackTrace();}}
        }
       }

}
