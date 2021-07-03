package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.ConService;
import com.techelevator.view.ConsoleService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	@RequestMapping
	private void viewCurrentBalance() {
		RestTemplate restTemplate = new RestTemplate();
		Integer getUserId = currentUser.getUser().getId();
		ConService spitItOut = new ConService();


//		try {
			HttpHeaders headers = new HttpHeaders();
//				headers.setBearerAuth(AUTH_TOKEN);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity entity = new HttpEntity<>(headers);

		ResponseEntity<Double>  response = restTemplate.exchange(API_BASE_URL + "getbalance/" + getUserId,
					HttpMethod.GET, entity, Double.class);

//		Double response = restTemplate.getForObject(API_BASE_URL + "getbalance/" + getUserId,
//				entity, Double.class);

			if (response.getBody() != null) {
				Double amt = response.getBody();
				System.out.println(String.format("Your current account balance is: $%.2f", amt));

//				spitItOut.DisplayMessage(/''String.format("Your current account balance is: $%.2f", amt)/'';


//				String messageOut = "Your current account balance is: " + String.valueOf(response.getBody());
//				System.out.println(messageOut);
//				spitItOut.DisplayMessage("Your current balance is: " + response.getBody());
			}
//        return response.getBody();

//		}
//			} catch (RestClientResponseException ex) {
//				throw new HotelServiceException(ex.getMessage());
//			} catch (ResourceAccessException ex) {
//				throw new HotelServiceException(ex.getMessage());
//			}
//			return null;
//		}
//	Double doubleBalance =

	}


	private void viewTransferHistory() {
//		XferData history = new XferData();
		RestTemplate restTemplate = new RestTemplate();
		Integer getUserId = currentUser.getUser().getId();
		ConService spitItOut = new ConService();
		XferData[] items = null;
		ResponseEntity<XferData[]> history = null;

		HttpHeaders headers = new HttpHeaders();
//				headers.setBearerAuth(AUTH_TOKEN);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity entity = new HttpEntity(headers);

//		ResponseEntity<XferData[]>  history = restTemplate.exchange(API_BASE_URL + "getalltransfers/" + getUserId,
		history = restTemplate.exchange(API_BASE_URL + "getalltransfers/" + getUserId,
				HttpMethod.GET, entity, XferData[].class);

		items = history.getBody();


		if (history.getBody() != null && items.length > 0) {
			System.out.println("------------------------------------");
			System.out.println(" Transfers");
			System.out.println(" ID      From/To             Amount");
			System.out.println("------------------------------------");
			for (int i = 0; i < items.length; i++) {
				int val1 = items[i].getTransferId();
				String val2 = items[i].getDirection();
				String val3 = items[i].getUsername();
				double val4 = items[i].getAmount();
				System.out.println(String.format(" %d %7s: %-12s\t$%.2f", val1, val2, val3, val4));
			}
			System.out.println("-------------------------------------------------------");
			Integer input = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel)");

			if (input.equals(0)) {
				System.out.println("");
			} else {
				ResponseEntity<XferDetail> response = restTemplate.exchange(API_BASE_URL + "transferdetail/" + input,
						HttpMethod.GET, entity, XferDetail.class);
				XferDetail data = response.getBody();

				if (data == null) {
					System.out.println("Invalid entry.");
				} else {
					String v1 = String.valueOf(data.getTransferId());
					String v2 = String.valueOf(data.getUserFrom());
					String v3 = String.valueOf(data.getUserTo());
					String v4 = String.valueOf(data.getType());
					String v5 = String.valueOf(data.getStatus());
					String v6 = String.format("%.2f", data.getAmount());

					System.out.println("------------------------------------");
					System.out.println("Transfer Details");
					System.out.println("------------------------------------");


					console.DisplayMessage("Id: " + v1);
					console.DisplayMessage("From: " + v2);
					console.DisplayMessage("To: " + v3);
					console.DisplayMessage("Type: " + v4);
					console.DisplayMessage("Status: " + v5);
					console.DisplayMessage("Amount: $" + v6);
				}
			}
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		RestTemplate restTemplate = new RestTemplate();
		Integer getUserId = currentUser.getUser().getId();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "users/" + getUserId,
				HttpMethod.GET, entity, User[].class);
//
		User[] users = response.getBody();

		if (response.getBody() == null) {
			System.out.println("Sorry, nobody wants your money :)");
		} else {
			System.out.println("----------------");
			System.out.println(" ID      Name  ");
			System.out.println("----------------");

			for (int i = 0; i < users.length; i++) {
				int val1 = users[i].getId();
				String val2 = users[i].getUsername();
				System.out.println(String.format("%d\t %-12s", val1, val2));
			}
			System.out.println("----------------");
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
