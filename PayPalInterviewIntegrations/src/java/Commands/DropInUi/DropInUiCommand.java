/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands.DropInUi;

import Commands.Command;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brian
 */
public class DropInUiCommand  implements Command{
    
    private static String publicKey = "zxhs7q6bxfxwcdy3";
    private static String privateKey = "f225cfdfbee8490196258605d7489a52";
    private static String merchantId= "g5yr29rfmnyvr9vw";
    private static String forwardToJsp;
    private static String token;
    BraintreeGateway braintreeGateway = connectBraintreeGateway();

    //https://developers.braintreepayments.com/start/hello-server/java
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) 
    {
        token = braintreeGateway.clientToken().generate();
        HttpSession session = request.getSession(true);
        token = generateClientToken();
        //System.out.println("Token: " + token);
        
        session.setAttribute("clienttoken", token);
        session.setAttribute("gateway", braintreeGateway);
        session.setAttribute("forwardToJsp", forwardToJsp);
        forwardToJsp = "index.jsp";
        return forwardToJsp;
    }
    
    
    public static BraintreeGateway connectBraintreeGateway() 
    {
        BraintreeGateway braintreeGateway = new BraintreeGateway(
                Environment.SANDBOX, merchantId, publicKey, privateKey);
        return braintreeGateway;
    }
    
    public static String generateClientToken() 
    {
        String clientToken = connectBraintreeGateway().clientToken().generate();
        
        return clientToken;
    }
    
}
