package JCK.Risk;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class TelegramGameBot extends TelegramLongPollingBot {
	
	private static String response = "";
	
    private String getResponse() {
		return response;
	}

	private void setResponse(String chatResponse) {
		response = chatResponse;
	}
	
	private void resetResponse() {
		response = "";
	}
	

	@Override
    public void onUpdateReceived(Update update) {
        //check if the update has a message and if the message has text
    	if(update.hasMessage() && update.getMessage().hasText())
    	{
    		String messageText = update.getMessage().getText();
    		long chatID = update.getMessage().getChatId();
    		this.setResponse(messageText);	    		
    	}
    }
    
	
	public void sendMessageToChat(String generalMessage) {
    	SendMessage message = new SendMessage()
				.setChatId("-198887148")  //TODO: set chatID per game
				.setText(generalMessage);
		try {
			execute(message); //bot tried to send this message to the user
		} catch(TelegramApiException e) {
			System.out.println("message was not able to be sent");
			e.printStackTrace();	
		}
		
		
	}
	
    
    public String sendMessageGetResponse(String nextMessage) throws InterruptedException, TelegramApiException {
    	resetResponse();
    	SendMessage message = new SendMessage()
				.setChatId("-198887148")  //TODO: set chatID per game
				.setText(nextMessage);
    	
//    	execute(message);
		try {
			execute(message); //bot tried to send this message to the user
		} catch(TelegramApiException e) {
			System.out.println("message was not able to be sent");
			e.printStackTrace();	
		}
		
		if(checkMessageResponse() == true)
		{
			return getResponse();
		}
		else
		{
			return "empty";
		}
		
    }
    
    
    private boolean checkMessageResponse() throws InterruptedException {
    	
    	int sleepCount = 0;
    	boolean receivedResponse = false;
    	
    	while(sleepCount < 6)
    	{
    		Thread.sleep(5000);
    		
    		if(!Objects.equals(getResponse(), ""))
    		{
    			receivedResponse = true;
    			break;
    		}
    		
    		
    		sleepCount++;
    	}
    	
    	return receivedResponse;
    }
    
    

    @Override
    public String getBotUsername() {
    	return "jckRisk_bot";
    }

    @Override
    public String getBotToken() {
        return "798372367:AAE0XQ_hDcfLjtAxKcwawsWY0MQ6j4hF2-E";
    }
}