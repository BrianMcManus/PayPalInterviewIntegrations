<%-- 
    Document   : printResult
    Created on : 26-Nov-2017, 15:20:23
    Author     : brian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Success</title>
    </head>
    <body>
        
        <nav class="navbar navbar-inverse navbar-fixed-top" style="margin-bottom: 5%">
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
                    <li><a href="merchantTransactions.jsp">Transaction List</a></li>
                    <li><a href="#">Another Feature</a></li>
                  </ul>
                </li>
              </ul>
            </div>
          </nav> 
        
        
        <h1 style="margin-top: 7%">Success!</h1>
        <h2>Transaction id: <%=session.getAttribute("transactionId")%></h2>
        <form action="processRequest" method="POST" name="submitForm" id="submitForm">
            <input type="hidden" name="action" value="refundtransaction" />
            <input type="submit" name="submit" value="Request refund" />
        </form>
        
        
        <a href="index.html">Home</a>
        
<!--         <button id = "refund" onclick = "refundTransaction()">Request refund</button> -->
    </body>
    
    <script>
        
        function refundTransaction() {
                $.ajax({
                    type: 'POST',
                    url: 'processRequest',
                    data: {
                        action : 'refundtransaction'
                          },
                    success: function(msg)
                    {     
                        window.alert("Refund was successful");
                    },
                    error: function(error)
                    {
                        console.log(error);
                        window.alert("Error" + error.toString());                        
                    }
            });
        
        };
        
        
        

    </script>
</html>
