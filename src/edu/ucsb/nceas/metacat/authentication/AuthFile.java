/**
 *  '$RCSfile$'
 *  Copyright: 2013 Regents of the University of California and the
 *             National Center for Ecological Analysis and Synthesis
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ucsb.nceas.metacat.authentication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

import edu.ucsb.nceas.metacat.AuthInterface;
import edu.ucsb.nceas.metacat.properties.PropertyService;
import edu.ucsb.nceas.utilities.PropertyNotFoundException;

/**
 * This an authentication class base on a username/password file.
 * It is an alternative authentication mechanism of the ldap authentication.
 * This is a singleton class and the password file looks like:
 *<?xml version="1.0" encoding="UTF-8" ?>
 * <subjects>
 *  <users>
 *      <user name="uid=tao,o=NCEAS,dc=ecoinformatics,dc=org">
 *          <password>*******</password>
 *          <group>nceas-dev</group>
 *      </user>
 *  </users>
 *  <groups>
 *    <group name="nceas-dev"/>
 *  </groups>
 * </subjects>
 * http://commons.apache.org/proper/commons-configuration/userguide/howto_xml.html
 * @author tao
 *
 */
public class AuthFile implements AuthInterface {
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String SLASH = "/";
    private static final String AT = "@";
    private static final String SUBJECTS = "subjects";
    private static final String USERS = "users";
    private static final String USER = "user";
    private static final String GROUPS = "groups";
    private static final String GROUP = "group";
    private static final String INITCONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
                                    "<"+SUBJECTS+">\n"+"<"+USERS+">\n"+"</"+USERS+">\n"+"<"+GROUPS+">\n"+"</"+GROUPS+">\n"+"</"+SUBJECTS+">\n";
    
    private static AuthFile authFile = null;
    private XMLConfiguration userpassword = null;
    private static String passwordFilePath = null;
    /**
     * Get the instance of the AuthFile
     * @return
     * @throws AuthenticationException
     */
    public static AuthFile getInstance() throws AuthenticationException {
        if(authFile == null) {
            authFile = new AuthFile();
        }
        return authFile;
    }
    
    /**
     * Get the instance of the AuthFile from specified password file
     * @return
     * @throws AuthenticationException
     */
    public static AuthFile getInstance(String passwordFile) throws AuthenticationException {
        passwordFilePath = passwordFile;
        if(authFile == null) {
            authFile = new AuthFile();
        }
        return authFile;
    }
    
    /**
     * Constructor
     */
    private AuthFile() throws AuthenticationException {
        try {
            init();
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
        
    }
    
    /*
     * Initialize the user/password configuration
     */
    private void init() throws PropertyNotFoundException, IOException, ConfigurationException {
        if(passwordFilePath == null) {
            passwordFilePath  = PropertyService.getProperty("auth.file.path");
        }
        File passwordFile = new File(passwordFilePath);
        //if the password file doesn't exist, create a new one and set the initial content
        if(!passwordFile.exists()) {
            passwordFile.createNewFile();
            OutputStreamWriter writer = null;
            FileOutputStream output = null;
            try {
              output = new FileOutputStream(passwordFile);
              writer = new OutputStreamWriter(output, "UTF-8");
              writer.write(INITCONTENT);
            } finally {
              writer.close();
              output.close();
            }
          }
          userpassword = new XMLConfiguration(passwordFile);
          userpassword.setExpressionEngine(new XPathExpressionEngine());
          userpassword.setAutoSave(true);
          userpassword.setDelimiterParsingDisabled(true);
          userpassword.setAttributeSplittingDisabled(true);
    }
    
    @Override
    public boolean authenticate(String user, String password)
                    throws AuthenticationException {
        String passwordRecord = userpassword.getString(USERS+SLASH+USER+"["+AT+NAME+"='"+user+"']"+SLASH+PASSWORD);
        if(passwordRecord != null) {
            if(passwordRecord.equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String[][] getUsers(String user, String password)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String[] getUserInfo(String user, String password)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String[] getUsers(String user, String password, String group)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String[][] getGroups(String user, String password)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String[][] getGroups(String user, String password, String foruser)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public HashMap<String, Vector<String>> getAttributes(String foruser)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public HashMap<String, Vector<String>> getAttributes(String user,
                    String password, String foruser) throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getPrincipals(String user, String password)
                    throws ConnectException {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Add a user to the file
     * @param userName the name of the user
     * @param groups  the groups the user belong to. The group should exist in the file
     * @param password  the password of the user
     */
    public void addUser(String userName, String[] groups, String password) throws AuthenticationException{
        if(userName == null || userName.trim().equals("")) {
            throw new AuthenticationException("AuthFile.addUser - can't add a user whose name is null or blank.");
        }
        if(password == null || password.trim().equals("")) {
            throw new AuthenticationException("AuthFile.addUser - can't add a user whose password is null or blank.");
        }
        if(!userExists(userName)) {
            if(userpassword != null) {
              userpassword.addProperty(USERS+" "+USER+AT+NAME, userName);
              userpassword.addProperty(USERS+SLASH+USER+"["+AT+NAME+"='"+userName+"']"+" "+PASSWORD, password);
              if(groups != null) {
                  for(int i=0; i<groups.length; i++) {
                      String group = groups[i];
                      if(group != null && !group.trim().equals("")) {
                          if(groupExists(group)) {
                              userpassword.addProperty(USERS+SLASH+USER+"["+AT+NAME+"='"+userName+"']"+" "+GROUP, group);
                          }
                      }
                  }
              }
              userpassword.reload();
             }
        } else {
            throw new AuthenticationException("AuthFile.addUser - can't add the user "+userName+" since it already exists.");
        }
    }
    
    /**
     * Add a group into the file
     * @param groupName the name of group
     */
    public void addGroup(String groupName) throws AuthenticationException{
        if(groupName == null || groupName.trim().equals("")) {
            throw new AuthenticationException("AuthFile.addGroup - can't add a group whose name is null or blank.");
        }
        if(!groupExists(groupName)) {
            if(userpassword != null) {
              userpassword.addProperty(GROUPS+" "+GROUP+AT+NAME, groupName);
              userpassword.reload();
             }
        } else {
            throw new AuthenticationException("AuthFile.addGroup - can't add the group "+groupName+" since it already exists.");
        }
    }
    
    /**
     * Reset the password for the user
     * @param userName  the name of the user. The user should already exist
     * @param password  the password of the user.
     * @return
     */
    public String modifyUserPassword(String userName, String password) {
        return password;
    }
    
    /**
     * Add a user to a group
     * @param userName  the name of the user. the user should already exist
     * @param group  the name of the group. the group should already exist
     */
    public void addUserToGroup(String userName, String group) {
        
    }
    
    /**
     * Remove a user from a group.
     * @param userName  the name of the user. the user should already exist.
     * @param group the name of the group
     */
    public void removeUserFromGroup(String userName, String group) {
        
    }
    
    /**
     * If the specified user name exist or not
     * @param userName the name of the user
     * @return true if the user eixsit
     */
    private boolean userExists(String userName) throws AuthenticationException{
        if(userName == null || userName.trim().equals("")) {
            throw new AuthenticationException("AuthFile.userExist - can't judge if a user exists when its name is null or blank.");
        }
        List<Object> users = userpassword.getList(USERS+SLASH+USER+SLASH+AT+NAME);
        if(users != null && users.contains(userName)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * If the specified group exist or not
     * @param groupName the name of the group
     * @return true if the user exists
     */
    private boolean groupExists(String groupName) throws AuthenticationException{
        if(groupName == null || groupName.trim().equals("")) {
            throw new AuthenticationException("AuthFile.groupExist - can't judge if a group exists when its name is null or blank.");
        }
        List<Object> groups = userpassword.getList(GROUPS+SLASH+GROUP+SLASH+AT+NAME);
        if(groups != null && groups.contains(groupName)) {
            return true;
        } else {
            return false;
        }
    }
}