/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DBtoTable.DBContext;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.spi.DirStateFactory.Result;

/**
 *
 * @author datbe
 */
public class DAO {
    // note du liue tu DB len
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public User login(String email, String password){
        String query = "select * from [User]\n" 
                +"where email = ?\n"
                +"and password = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while(rs.next()){
                return new User(rs.getInt(1),
                                rs.getString(4),
                                rs.getString(3),
                                rs.getInt(9)
                
                );
            }
        } catch (Exception e) {
        }
        return null;
    }
    
}
