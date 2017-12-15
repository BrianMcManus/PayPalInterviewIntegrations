<%-- 
    Document   : index
    Created on : 25-Nov-2017, 19:40:20
    Author     : brian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Braintree Drop-in UI</title>
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
                    <li class="active"><a href="index.jsp">Drop-in UI</a></li>
                    <li><a href="merchantTransactions.jsp">Transaction List</a></li>
                  </ul>
                </li>
              </ul>
            </div>
          </nav> 

        <form class="form-horizontal" action="processRequest"  method="POST" id="submitForm" name="submitForm" style="margin-top: 7%">
            <div class="form-group">
                <label class="col-sm-2 control-label">Product</label>
                    <div class="col-sm-10">
                        <input type="text" name="product" value="XBOX ONE"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Price</label>
                    <div class="col-sm-10">
                        <input type="text" name="price" id="price" value="200.00"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                        <input type="text" name="fName" value="Brian"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                        <input type="text" name="lName" value="Mc Manus"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-10">
                        <input type="text" name="email" value="brian@fake.com"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Phone</label>
                    <div class="col-sm-10">
                        <input type="text" name="phone" value="0851234567"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Address</label>
                    <div class="col-sm-10">
                        <input type="text" name="address" value="60 Grange Drive"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">County</label>
                    <div class="col-sm-10">
                        <input type="text" name="county" value="Louth"/>
                    </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Country</label>
                    <div class="col-sm-10">
                        <input type="text" name="country" value="Ireland"/>
                    </div>
            </div>
                
                <input type="hidden" id="nonce" name="nonce" value=''/>
                <input type="hidden" id="paymentMethod" name="paymentMethod" value=''/>
                <input type="hidden" name="action" value="submitnonce" />
                <input type="hidden" value="Call Servlet" name="Call Servlet" id="call"/><br>
        </form>
        
        <div id="dropin-container"></div>
        
        <div id="dropin-wrapper">
            <div id="checkout-message"></div>
            <div id="dropin-container"></div>
            <button id="submit-button">Submit payment</button>
        </div>
  
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
                        action : 'generateClientToken'
                          },
                    success: function(msg)
                    {     
                        //window.alert("Success");
                        var token = '<%=(String)session.getAttribute("clienttoken")%>';
                    },
                    error: function(error)
                    {
                        
                        console.log(error);
                        //window.alert("Error"); 
                        //window.alert(error);                        
                    }
            });
        
        });
            

        
    </script>
    
    <script>
  var button = document.querySelector('#submit-button');

//https://developers.braintreepayments.com/start/hello-client/javascript/v3
  braintree.dropin.create({
    // Insert your tokenization key here
    authorization: '<%=(String)session.getAttribute("clienttoken")%>',
    container: '#dropin-container',
    //http://braintree.github.io/braintree-web/current/module-braintree-web_hosted-fields.html#~styleOptions
      card: {
        overrides: {
          styles: {
            input: {
              color: 'blue',
              'font-size': '18px'
            },
            '.number': {
              'font-family': 'monospace',
               placeholder: 'Card Number'
            },
            '.invalid': {
              color: 'red'
            },
            '.expirationDate': {
                color:'blue',
                'font-size': '18px'
            }
          }
        }
      },
    //https://developers.braintreepayments.com/guides/drop-in/customization/javascript/v3
    //https://developer.paypal.com/docs/integration/direct/express-checkout/integration-jsv4/customize-button/#button-styles
    paypal: {
        //Must use vault as 
        flow: 'checkout',
        amount: document.getElementById("price").value,
        currency: 'GBP',
        commit: true,
        buttonStyle: {
              color: 'blue',
              shape: 'rect',
              size: 'medium',
              label: 'pay'
            }
      },
    threeDSecure: {
    amount: document.getElementById("price").value
    }
    
  }, function (createErr, instance) {
      
      //https://developers.braintreepayments.com/guides/drop-in/setup-and-integration/javascript/v3
      if (createErr) {
    // An error in the create call is likely due to
    // incorrect configuration values or network issues.
    // An appropriate error will be shown in the UI.
    console.error(createErr);
    location.reload(true);
    //alert(createErr);
    return;
  }
  
  
    button.addEventListener('click', function (e) {
         e.preventDefault();
      instance.requestPaymentMethod(function (requestPaymentMethodErr, payload) {

        if (requestPaymentMethodErr) {
        // No payment method is available.
        // An appropriate error will be shown in the UI.
        console.error(requestPaymentMethodErr);
        //alert(requestPaymentMethodErr);
        return;
      }
      
      //https://developers.braintreepayments.com/guides/drop-in/setup-and-integration/javascript/v3
      if (payload.liabilityShifted || payload.type !== 'CreditCard') {
            
            
            document.getElementById("nonce").value = payload.nonce;
            
            //https://braintree.github.io/braintree-web-drop-in/docs/current/Dropin.html#~cardPaymentMethodPayload
            document.getElementById("paymentMethod").value = payload.type;
     
            if(document.getElementById("nonce").value === "")
            {
                //alert("Must enter card details");

            }
            else
            {
                //alert("Valid");
                document.getElementById("submitForm").submit();
                document.getElementById("submit-button").disabled = true;
            }
      } else {
        // Decide if you will force the user to enter a different
        // payment method if liablity was not shifted
        dropinInstance.clearSelectedPaymentMethod();
      }
        
        
      });
    });
  }
  );
</script>

</html>
