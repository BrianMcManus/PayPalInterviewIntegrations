/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands.DropInUi;

import Commands.Command;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brian
 */
public class DropInUiRefund implements Command{

    private static String forwardToJsp;
    private static BraintreeGateway btGateway;
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
       
        
        HttpSession session = request.getSession();
        btGateway = (BraintreeGateway)session.getAttribute("gateway");
        
        
        
            Transaction transaction = btGateway.transaction().find((String)session.getAttribute("transactionId"));

            //https://developers.braintreepayments.com/reference/response/transaction/java#result-object
            if (transaction.getStatus() == Transaction.Status.SUBMITTED_FOR_SETTLEMENT) 
            {
                Result<Transaction> result = btGateway.transaction().voidTransaction((String)session.getAttribute("transactionId"));
                        
                        if(result.isSuccess())
                        {
                            session.setAttribute("refundOrVoid", "Transaction was not settled therefore the transaction was voided");
                            forwardToJsp = "refundSuccess.jsp";
                        }
                        else
                        {
                            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) 
                            {
                                System.out.println(error.getMessage());
                            }
                            
                            forwardToJsp = "error.jsp";
                            String errors = result.getMessage();
                            session.setAttribute("error", errors);
                            System.out.println("Error: " + errors);
                        }
            } 
            else if (transaction.getStatus() == Transaction.Status.SETTLED) 
            {
                
                //https://developers.braintreepayments.com/reference/request/transaction/refund/java
                Result<Transaction> result = btGateway.transaction().refund((String)session.getAttribute("transactionId"));
                
                
                        if (result.isSuccess()) 
                        {
                            session.setAttribute("refundOrVoid", "Refund was successful");
                            forwardToJsp = "refundSuccess.jsp";
                        } 
                        else 
                        {
                            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                                System.out.println(error.getMessage());
                            }

                            forwardToJsp = "error.jsp";
                            String errors = result.getMessage();
                            session.setAttribute("error", errors);
                            System.out.println("Error: " + errors);
                        }
                
                
            } 
            else 
            {
                //https://developers.braintreepayments.com/reference/request/transaction/refund/java
                Result<Transaction> result = btGateway.transaction().refund((String)session.getAttribute("transactionId"));
                
                if (result.isSuccess()) 
                {
                    session.setAttribute("refundOrVoid", "Refund was successful");
                    forwardToJsp = "refundSuccess.jsp";
                } 
                else 
                {
                    for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                        System.out.println(error.getMessage());

                        if(error.getMessage().equals("Cannot refund a transaction unless it is settled."))
                        {
                            result = btGateway.transaction().voidTransaction((String)session.getAttribute("transactionId"));

                            if(result.isSuccess())
                            {
                                session.setAttribute("refundOrVoid", "Transaction was not settled therefore the transaction was voided");
                                forwardToJsp = "refundSuccess.jsp";
                            }
                            else
                            {
                                forwardToJsp = "error.jsp";
                                String errors = result.getMessage();
                                session.setAttribute("error", errors);
                                System.out.println("Error: " + errors);
                            }
                        }
                        else
                        {
                            forwardToJsp = "error.jsp";
                            String errors = result.getMessage();
                            session.setAttribute("error", errors);
                            System.out.println("Error: " + errors);
                        }
                    }

                }
            }
        
        session.setAttribute("forwardToJsp", forwardToJsp);
    
        return forwardToJsp;
    }
    
}
