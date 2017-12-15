/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands.MerchantTransactions;

import Commands.Command;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.ResourceCollection;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionSearchRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brian
 */
public class MerchantTransactionsCommand implements Command
{
    
    private static String publicKey = "zxhs7q6bxfxwcdy3";
    private static String privateKey = "f225cfdfbee8490196258605d7489a52";
    private static String merchantId= "g5yr29rfmnyvr9vw";
    private static String forwardToJsp;
    private static String token;
    BraintreeGateway braintreeGateway = connectBraintreeGateway();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) 
    {
        
        HttpSession session = request.getSession();        
        
//        TransactionSearchRequest tRequest = new TransactionSearchRequest()
//            .merchantAccountId().is(merchantId);

TransactionSearchRequest searchRequest = new TransactionSearchRequest()
    .type().in(Transaction.Type.SALE);

        ResourceCollection<Transaction> collection = braintreeGateway.transaction().search(searchRequest);

//        for (Transaction transaction : collection) {
//            System.out.println(transaction.getAmount());
//        }
        
        session.setAttribute("merchantTransactions", collection);
        session.setAttribute("forwardToJsp", forwardToJsp);
        forwardToJsp = "merchantTransactions.jsp";
        
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
