/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

/**
 * The Command class is implemented by all classes in a commands. package
 *
 * @author brian
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Provides the specification for the execute method to be used in all classes
 * the implement the Command class
 */
public interface Command 
{
    public String execute(HttpServletRequest request, HttpServletResponse response);
}
