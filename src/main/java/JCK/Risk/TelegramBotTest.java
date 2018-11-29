package JCK.Risk;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class TelegramBotTest extends TelegramLongPollingBot {
	
	//on update received is where we decide what we want the bot to do whenever
	//we send it a text (an update)
	
	private static String response = "";
	
	
    public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
	}


	@Override
    public void onUpdateReceived(Update update) {
        //check if the update has a message and if the message has text
    	if(update.hasMessage() && update.getMessage().hasText())
    	{
    		String messageText = update.getMessage().getText();
    		long chatID = update.getMessage().getChatId();
    		
    		this.setResponse(messageText);
    		System.out.println("The chat id is " + chatID);
    		
    		System.out.println("We " + this.getResponse());
    		    		
    		//create a message object
    		SendMessage message = new SendMessage()
    				.setChatId(chatID)
    				.setText(messageText);
    		try {
    			execute(message); //bot tried to send this message to the user
    		} catch(TelegramApiException e) {
    			System.out.println("message was not able to be sent");
    			e.printStackTrace();
    			
    		}
    	}
    }
    
	
    
    public void sendMessageToChat(String nextOutput) {
    	
    	SendMessage message = new SendMessage()
				.setChatId("-198887148")
				.setText(nextOutput);
		try {
			execute(message); //bot tried to send this message to the user
		} catch(TelegramApiException e) {
			System.out.println("message was not able to be sent");
			e.printStackTrace();
			
		}
    }
    

    @Override
    public String getBotUsername() {
        // TODO
    	return "jckRisk_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "798372367:AAE0XQ_hDcfLjtAxKcwawsWY0MQ6j4hF2-E";
    }
}