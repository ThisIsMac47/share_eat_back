package fr.vmarchaud.shareeat.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.enums.EnumEnv;
import fr.vmarchaud.shareeat.objects.Meetup;
import fr.vmarchaud.shareeat.objects.Payement;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.CustomConfig;

public class StripeService {

	private DataService		dataSrv = Core.getInstance().getDataService();
	
	public StripeService() {
		Stripe.apiKey = "sk_test_3hlxc9p0CgECOsh5f5ez0z7I";
	}
	
	/**
	 * Create a charge for the user
	 * @param meetup : The meetup that user pay for
	 * @param user : The user that will pay
	 * @param token : Stripe payement token used to create the charge
	 * @return
	 */
	public boolean chargeUser(Meetup meetup, User user, String token) {
		
		// Create the charge with price and token
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("amount", meetup.getPrice() * 100);
		chargeParams.put("currency", "EUR");
		chargeParams.put("source", token);
		chargeParams.put("description", "ShareEat diner (" + meetup.getId().toString() + ")");
		
		// Try to create the carge
		Charge charge = null;
		try {
			charge = Charge.create(chargeParams);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			e.printStackTrace();
			return false;
		}
		
		dataSrv.getPayements().add(new Payement(user, CustomConfig.ENV == EnumEnv.PROD, token, charge.getId(), meetup));
		return true;
	}
}
