<%-- 
    Document   : refundSuccess
    Created on : 26-Nov-2017, 22:42:36
    Author     : brian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Refund/Void</title>
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
                    <li><a href="merchantTransactions.jsp">Transaction List</a></li>
                    <li><a href="#">Another Feature</a></li>
                  </ul>
                </li>
              </ul>
            </div>
          </nav> 
        
        
        <h1 style="margin-top: 7%"><%=session.getAttribute("refundOrVoid")%></h1>
        <a href="index.html">Home</a>
    </body>
</html>
