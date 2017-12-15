<%-- 
    Document   : merchantTransactions
    Created on : 30-Nov-2017, 16:09:42
    Author     : brian
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.braintreegateway.ResourceCollection"%>
<%@page import="com.braintreegateway.Transaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Merchant Transactions</title>
    </head>
    <body>
        
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand" href="index.html">PayPal Project</a>
              </div>
              <ul class="nav navbar-nav navbar-right ">
                <li><a href="index.html">Home</a></li>
                <li><a href="https://www.paypal.com/ie/webapps/mpp/about">About Us</a></li>
                <li><a href="https://www.paypal.com/ie/selfhelp/home">Contact</a></li>
                <li class="dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" href="#">Services
                  <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="index.jsp">Drop-in UI</a></li>
                    <li class="active"><a href="merchantTransactions.jsp">Transaction List</a></li>
                    <li><a href="#">Another Feature</a></li>
                  </ul>
                </li>
              </ul>
            </div>
          </nav> 
        
        <%
             ResourceCollection<Transaction> collection = (ResourceCollection<Transaction>) session.getAttribute("merchantTransactions");
             if(collection!=null)
             {
        %>
        <table class="table table-striped" style="width:100%; margin-top: 7%">
        <tr>
            <th>Transaction Id</th>
            <th>Date</th>
            <th>Method</th>
            <th>Status</th>
            <th>Settle</th>
            <th>Amount</th>
        </tr>
        
        
        <%
                for (Transaction transaction : collection) 
                {
                    String id = transaction.getId();
                    String status = transaction.getStatus().toString();
                    String amt = transaction.getAmount().toString();
                    Calendar date =  transaction.getCreatedAt();
                    String paymentMethod = transaction.getPaymentInstrumentType();
                    Date d = date.getTime();
        %>
        
        <tr>
            <td><%=id%></td>
            <td><%=d%></td>
            <td><%=paymentMethod%></td>
            <td><%=status%></td>
            
         <%
             if(status.equalsIgnoreCase("Authorized") && paymentMethod.equalsIgnoreCase("paypal_account"))
             {
         %>
         <td>
        <form class="form-horizontal" action="processRequest"  method="POST" id="submitForm" name="submitForm">
        <input type="text" name="authorizeAmt" value=""/>
        <input type="hidden" name="action" value="settlePayment">
        <input type="hidden" name="transactionId" value="<%=id%>">
        <input type="submit" name="submit" value="submit"/>
        </form>
         </td>
        <%
            }
            else
            {
        %>
        <td></td>
        <%
            }
        %>
            <td><%=amt%></td>
        </tr>
        
        
        
        
        
        
        
        
        <%
                }
            }
            else
            {
         %>
         
         <p>ERROR PLEASE REFRESH PAGE</p>
         <%     
            }
        %>
        </table>
    </body>
    
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://js.braintreegateway.com/web/dropin/1.9.1/js/dropin.min.js"></script>
    
    <script>
        
        $(document).ready(function() {
                $.ajax({
                    type: 'POST',
                    url: 'processRequest',
                    data: {
                        action : 'getMerchantTransactions'
                          },
                    success: function(msg)
                    {     
                       // window.alert("Success");
                        var token = '<%=(String)session.getAttribute("clienttoken")%>';
                    },
                    error: function(error)
                    {
                        console.log(error);
                        //window.alert("Error"); 
                        //window.alert(error);
                        merchantTransactions.reload();
                        
                    }
            });
        
        });

    </script>
</html>
