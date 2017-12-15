/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands.DropInUi;

import Commands.Command;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.PaymentMethodNonce;
import com.braintreegateway.Result;
import com.braintreegateway.ThreeDSecureInfo;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brian
 */

//https://developers.braintreepayments.com/reference/response/transaction/java#result-object
public class DropInUiCheckout implements Command{
    private BraintreeGateway btGateway;
    private String forwardToJsp;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        btGateway = (BraintreeGateway) session.getAttribute("gateway");
        //System.out.println("Gateway: " + btGateway.toString());
        
        //This can be done simultaniously within the transaction sale as per documentation
        //but for my own understanding(readability) these have been left split in two.
        CustomerRequest cRequest = new CustomerRequest()
            .firstName(request.getParameter("fName"))
            .lastName(request.getParameter("lName"))
            .email(request.getParameter("email"))
            .phone(request.getParameter("phone"));
        
        
        Result<Customer> cResult = btGateway.customer().create(cRequest);
        
        //https://developers.braintreepayments.com/start/hello-server/java
        
        if (cResult.isSuccess()) 
            {
                String paymentMethod = (String) request.getParameter("paymentMethod");
                System.out.println("Payment Method: " + paymentMethod);
                TransactionRequest tRequest = null;
                        
                if(paymentMethod.equals("PayPalAccount"))
                {
                    tRequest = new TransactionRequest()
                    .amount(new BigDecimal(Float.parseFloat(request.getParameter("price"))))
                    .paymentMethodNonce(request.getParameter("nonce"))
                    .options()
                    .done();
                }
                else
                {
                    PaymentMethodNonce paymentMethodNonce = btGateway.paymentMethodNonce().find(request.getParameter("nonce"));
                    ThreeDSecureInfo info = paymentMethodNonce.getThreeDSecureInfo();
                    System.out.println(info.getStatus());
                    if (info == null)
                    {
                         forwardToJsp = "error.jsp";
                         String error = "3d Secure has failed";
                         session.setAttribute("error", error);

                    }
                    else
                    {
                    tRequest = new TransactionRequest()
                    .amount(new BigDecimal(Float.parseFloat(request.getParameter("price"))))
                    .paymentMethodNonce(request.getParameter("nonce"))
                    .options()
                    .submitForSettlement(true)
                    .done();
                    
                    tRequest.options().threeDSecure().required(true).done();
                    }
                }

               Result<Transaction> result = btGateway.transaction().sale(tRequest);
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
            }
        else
        {
            forwardToJsp = "error.jsp";
            String error = cResult.getMessage();
            session.setAttribute("error", error);
            System.out.println("Error: " + error);
        }
    
        session.setAttribute("forwardToJsp", forwardToJsp);
        return forwardToJsp;
        
    }
    
}
