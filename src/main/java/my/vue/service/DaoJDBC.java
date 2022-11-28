package my.vue.service;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
//import jennom.jpa.JpaNodes;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

@Repository
@Service
//@Transactional
public class DaoJDBC extends JdbcDaoSupport {
    
    @Value("classpath:users.sql")
    private Resource sqlUsers;

    @Inject
    public DaoJDBC(DataSource ds) {
        this.setDataSource(ds);
    }
    
    @PostConstruct
    public void afterBirn() {
        File file = new File("db/jeps");
        if (!file.exists()) {
            this.createTables();
            this.usersInsertRow("root","root","ROLE_admin",true);
            this.usersInsertRow("man", "man", "ROLE_guest",true);
            this.usersInsertRow("rest","rest","ROLE_rest",true);
            //this.genericChangeSQL("INSERT INTO roles(username,role) VALUES ('root','admin')");
            //this.genericChangeSQL("INSERT INTO roles(username,role) VALUES ('user','guest')");
        }
    } 
    
    public void genericChangeSQL(String sql) {
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC genericChangeSQL");
    }     
    
    ///////////     PWDS

    public void pwdsClearAll() {
        String sql = "delete from pwds";
        // queryForList(String sql, Class<T> elementType)
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC clearALL");
    } 
    
    public void pwdsDeleteByID (String id) {
        String sql = "DELETE FROM pwds WHERE id = " + id;
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC deleteByID = "+id);
    }
    
    public void pwdsInsertRow (String owner, String title, String login, String pass, String descr, String url) {
        String sql = "INSERT INTO pwds(owner,title,login,passw,descr,url) VALUES('" + owner + "','" + title + "','" + login + "','" + pass + "','" + descr + "','" + url + "')";
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC insertRow");
    } 
    
    public void pwdsEditRowByID (String owner, String title, String login, String pass, String descr, String url, int id) {
        String sql = "UPDATE pwds SET owner='"+owner+"',title='"+title+"',login='"+login+"',passw='"+pass+"',descr='"+descr+"',url='"+url+"'WHERE id = "+id;
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC changeRowByID");
    }     
    
    public SqlRowSet pwdsGetAllasSRS() {
        String sql = "SELECT * FROM pwds";
        System.out.println("ejbDaoJDBC getALLasSRS");
        return this.getJdbcTemplate().queryForRowSet(sql);
    } 

    public ResultSet pwdsGetAllasRS() {
        String sql = "SELECT * FROM pwds";
        System.out.println("ejbDaoJDBC getALLasRS");
        return ((ResultSetWrappingSqlRowSet) this.getJdbcTemplate().queryForRowSet(sql)).getResultSet();
    } 

    public SqlRowSet pwdsSearchByFieldasSRS(String field, String search) {
        field=field.toLowerCase();
        String sql = "SELECT * FROM pwds WHERE "+field+" LIKE '%" + search + "%'";
        System.out.println("ejbDaoJDBC searchByFieldasSRS");
        return this.getJdbcTemplate().queryForRowSet(sql);
    } 
    
    public SqlRowSet pwdsGetAsSRSbyOwner(String owner) {
        String sql = "SELECT * FROM pwds WHERE owner = " + owner;
        System.out.println("ejbDaoJDBC getALLasSRS");
        return this.getJdbcTemplate().queryForRowSet(sql);
    } 

    public ResultSet pwdsGetAsRSbyOwner(String owner) {
        String sql = "SELECT * FROM pwds WHERE owner = " + owner;
        System.out.println("ejbDaoJDBC getALLasRS");
        return ((ResultSetWrappingSqlRowSet) this.getJdbcTemplate().queryForRowSet(sql)).getResultSet();
    }     
    
    /////////   USERS
    
    public void usersClearAll() {
        String sql = "delete from users";
        // queryForList(String sql, Class<T> elementType)
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC clearALL");
    } 
    
    public void usersDeleteByID (String id) {
        String sql = "DELETE FROM users WHERE id = " + id;
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC deleteByID = "+id);
    }
    
    public void usersInsertRow (String username, String password, String level, boolean confirm) {
        String sql = "INSERT INTO users(username,password,level,confirm) VALUES('" + username + "','" + password + "','" + level + "','" + confirm + "')";
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC insertRow: login = "+ username + ", passw = " + password + ", role = " + level);
    } 
    
    public void usersEditRowByID (String username, String password, String level, String confirm, int id) {
        String sql = "UPDATE users SET username='"+username+"',password='"+password+"',level='"+level+"',confirm='"+confirm+"'WHERE id = "+id;
        this.getJdbcTemplate().update(sql);
        System.out.println("ejbDaoJDBC changeRowByID");
    }     
    
    public SqlRowSet usersGetAllasSRS() {
        String sql = "SELECT * FROM users";
        System.out.println("ejbDaoJDBC getALLasSRS");
        return this.getJdbcTemplate().queryForRowSet(sql);
    } 

    public ResultSet usersGetAllasRS() {
        String sql = "SELECT * FROM users";
        System.out.println("ejbDaoJDBC getALLasRS");
        return ((ResultSetWrappingSqlRowSet) this.getJdbcTemplate().queryForRowSet(sql)).getResultSet();
    } 

    public SqlRowSet usersSearchByFieldasSRS(String field, String search) {
        field=field.toLowerCase();
        String sql = "SELECT * FROM users WHERE "+field+" LIKE '%" + search + "%'";
        System.out.println("ejbDaoJDBC searchByFieldasSRS");
        return this.getJdbcTemplate().queryForRowSet(sql);
    }     
    
    //////////////////////

    public void createTables() {
        String s1 = "CREATE TABLE  pwds(id int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), owner varchar(255) NOT NULL, title varchar(255), login varchar(255) NOT NULL, passw varchar(255) NOT NULL, descr varchar(255), url varchar(255))";
        this.getJdbcTemplate().execute(s1);
        String s2 = "CREATE TABLE users(username varchar(255) NOT NULL, password varchar(255) NOT NULL, level varchar(255) NOT NULL, confirm BOOLEAN NOT NULL DEFAULT TRUE, PRIMARY KEY (username))";
        //String s2 = "CREATE TABLE users(username VARCHAR(99) NOT NULL, password VARCHAR(99) NOT NULL, enabled BOOLEAN NOT NULL DEFAULT TRUE, PRIMARY KEY (username))";
        this.getJdbcTemplate().execute(s2); 
        //String s3 = "CREATE TABLE roles(user_role_id int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), username varchar(99) NOT NULL, role varchar(99) NOT NULL, CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username))";
        //this.getJdbcTemplate().execute(s3); 
        //sqlUsers.
        //this.
        System.out.println("============= ejbDaoJDBC createTable");
    }     

}
