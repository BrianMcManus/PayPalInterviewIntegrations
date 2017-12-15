package Factory;

import Commands.Command;
import Commands.DropInUi.DropInUiCheckout;
import Commands.DropInUi.DropInUiCommand;
import Commands.DropInUi.DropInUiRefund;
import Commands.MerchantTransactions.AuthorizePaymentCommand;
import Commands.MerchantTransactions.MerchantTransactionsCommand;




public class CommandFactory {

    public Command createCommand(String action) {
        Command command = null;

        // Selecting the action
        switch (action) {
            // generate client token
            case "generateclienttoken":
                command = new DropInUiCommand();
                break;
                // do checkout
            case "submitnonce":
                command = new DropInUiCheckout();
                break;
                // refund transaction
            case "refundtransaction":
                command = new DropInUiRefund();
                break;
                // get merchants transactions
            case "getmerchanttransactions":
                command = new MerchantTransactionsCommand();
                break;
            case "settlepayment":
                command = new AuthorizePaymentCommand();
                break;
            default:
                command = null;
                break;
        }
        return command;
    }
}