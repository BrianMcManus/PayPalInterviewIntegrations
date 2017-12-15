/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands.MerchantTransactions;

import Commands.Command;
import static Commands.MerchantTransactions.MerchantTransactionsCommand.connectBraintreeGateway;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brian
 */
public class AuthorizePaymentCommand implements Command{

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
        System.out.println(request.getParameter("transactionId"));
        System.out.println(request.getParameter("authorizeAmt"));
        Result<Transaction> result = braintreeGateway.transaction().submitForPartialSettlement(request.getParameter("transactionId"), new BigDecimal(request.getParameter("authorizeAmt")));
        Transaction transaction = result.getTarget();
        
        if (result.isSuccess()) 
                    {
                        forwardToJsp = "printResult.jsp";
                        System.out.println("Transaction Id: " + transaction.getId());
                        session.setAttribute("transactionId", transaction.getId());
                    } 
                    else 
                    {
                        //https://developers.braintreepayments.com/reference/response/transaction/java
                        Transaction trans = result.getTransaction();
                        trans.getStatus();
                        // Transaction.Status.PROCESSOR_DECLINED

                            if(trans.getStatus().equals("Transaction.Status.PROCESSOR_DECLINED"))
                            {
                                //transaction.getProcessorResponseCode();
                                // e.g. "2001"

                                //transaction.getProcessorResponseText();
                                // e.g. "Insufficient Funds"

                                //transaction.getAdditionalProcessorResponse();
                                // e.g. "05 : NOT AUTHORISED"
                                
                                forwardToJsp = "error.jsp";
                                String error = trans.getProcessorResponseText() + "\n" + trans.getAdditionalProcessorResponse();
                                session.setAttribute("error", error);
                                System.out.println("Error: " + error);
                            }
                            else if((trans.getStatus().equals("Transaction.Status.SETTLEMENT_DECLINED")))
                            {
                               //transaction.getStatus();
                               // Transaction.Status.SETTLEMENT_DECLINED

                               //transaction.getProcessorSettlementResponseCode();
                               // e.g. "4001"

                               //transaction.getProcessorSettlementResponseText();
                               // e.g. "Settlement Declined"
                               
                                forwardToJsp = "error.jsp";
                                String error = trans.getProcessorSettlementResponseText();
                                session.setAttribute("error", error);
                                System.out.println("Error: " + error);
                            }
                            else
                            {
                                forwardToJsp = "error.jsp";
                                String error = result.getMessage();
                                session.setAttribute("error", error);
                                System.out.println("Error: " + error);
                            }  
                    }
        return forwardToJsp;
    }
    
}
